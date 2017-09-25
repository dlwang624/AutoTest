package org.czy.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.Test;
import org.czy.service.TestService;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
import org.czy.util.Path;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/file")
@Scope("prototype")
public class FileController {

	private final static Logger LOG = Logger.getLogger(FileController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping(value="/page.htm", method = RequestMethod.GET)
	public ModelAndView uploadPage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("prolist",prolist);
		model.setViewName("upload/showupload");
		return model;
	}
	
	/***
	 * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/fileUpload.htm", method = RequestMethod.POST)
	public ModelAndView fileUpload(@RequestParam("file") MultipartFile file,String testID,HttpServletRequest req) {
		boolean flag = saveFile(file,testID);
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("prolist",prolist);
		model.getModelMap().put("msg",flag?"上传成功":"上传失败");
		++ServerCount.apiCount;
		model.setViewName("upload/showupload");
		return model;
	}

	/***
	 * 读取上传文件中得所有文件并返回
	 * 
	 * @return
	 */
	@RequestMapping(value="/list.htm", method = RequestMethod.GET)
	public ModelAndView list(String testID) {
		String filePath = request.getSession().getServletContext().getRealPath("/") + "fileUpload/data/"+testID+"/";
		ModelAndView mav = new ModelAndView("list");
		File uploadDest = new File(filePath);
		String[] fileNames = uploadDest.list();
		for (int i = 0; i < fileNames.length; i++) {
			//打印出文件名
			LOG.info("文件名"+fileNames[i]);
		}
		return mav;
	}
	
	/***
	 * 保存文件
	 * @param file
	 * @return
	 */
	private boolean saveFile(MultipartFile file,String testID) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = request.getSession().getServletContext().getRealPath("/") + "fileUpload/data/"+testID+"/";
				File f = new File(filePath);
				if(!f.exists()){
					f.mkdirs();
				}
				filePath += file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				LOG.info(e);
			}
		}
		return false;
	}
	
	
	@RequestMapping(value="/filesUpload.htm", method = RequestMethod.POST)
	public ModelAndView filesUpload(@RequestParam("files") MultipartFile[] files,String testID,HttpServletRequest req) {
		boolean flag = true;
		//判断file数组不能为空并且长度大于0
		if(files!=null&&files.length>0){
			//循环获取file数组中得文件
			for(int i = 0;i<files.length;i++){
				MultipartFile file = files[i];
				//保存文件
				if(!saveFile(file,testID)){
					flag = false;
				}
			}
		}
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("prolist",prolist);
		model.getModelMap().put("msg",flag?"上传成功":"上传失败");
		++ServerCount.apiCount;
		model.setViewName("upload/showupload");
		return model;
	}
	
	@RequestMapping(value="/download.htm", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(String fileName,int id,HttpServletRequest req) throws IOException {
		++ServerCount.apiCount;
		String path = req.getSession().getServletContext().getRealPath("/") + "fileUpload/data/"+id+"/";
		path += fileName;
		File file = new File(path);
        String dfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	
	/***
	 * 保存文件
	 * @param file
	 * @return
	 */
	private boolean saveFileToFtp(MultipartFile file,FTPClient client) {
		boolean flag = false;
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				flag = GetFtpData.upload(file,client);
			} catch (Exception e) {
				LOG.info(e);
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 使用ftp上传文件到服务器
	 * @param files
	 * @param testID
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/filesUploadftp.htm", method = RequestMethod.POST)
	public ModelAndView filesUploadFTP(@RequestParam("files")MultipartFile[] files,String testID,HttpServletRequest req) {
		boolean flag = true;
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		String path = Final.FTP_BASEPATH+testID;
		try {
			if (!ftp.changeWorkingDirectory(Final.FTP_BASEPATH)) { 
				String msg = ftp.makeDirectory(Final.FTP_BASEPATH)?"创建成功":"创建失败";
				LOG.info(Final.FTP_BASEPATH+"文件夹"+msg);
			}
			if (!ftp.changeWorkingDirectory(path)) {  
				String msg = ftp.makeDirectory(path)?"创建成功":"创建失败";
				LOG.info(path+"文件夹"+msg);
            }
			if(ftp.changeWorkingDirectory(path)){
				LOG.info("FTP["+path+"]目录存在");
			}else{
				LOG.info("FTP["+path+"]目录不存在");
			}
		} catch (IOException e) {
			LOG.error(e);
			flag = false;
		}
		
		//筛选名字
//		CommonsMultipartFile multipartFile = null;
//        Iterator<String> itr =  fileReq.getFileNames();
//        while(itr.hasNext()){
//            String str = itr.next();
//            multipartFile = (CommonsMultipartFile)fileReq.getFile(str);
//            String fileName = multipartFile.getOriginalFilename();   //原文件名
//            MultipartFile file = fileReq.getFile(str);
//            if(fileName.indexOf(Final.XLS)>0||fileName.indexOf(Final.XLSX)>0){
//				if(!saveFileToFtp(file,ftp)){
//					flag = false;
//				}
//			}
//        }
		
		//判断file数组不能为空并且长度大于0
		if(files!=null&&files.length>0){
			//循环获取file数组中得文件
			for (MultipartFile file : files) {
				if(!saveFileToFtp(file,ftp)){
					flag = false;
				}else{
					LOG.info("["+req.getSession().getAttribute("nickname")+"]用户上传["+file.getName()+"]文件到"+path+"-----上传成功");
				}
			}
		}
		f.closed();
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("prolist",prolist);
		model.getModelMap().put("msg",flag?"上传成功":"上传失败");
		++ServerCount.apiCount;
		model.setViewName("upload/showupload");
		return model;
	}
	
	
	/***
	 * 读取上传文件中得所有文件并返回
	 * 
	 * @return
	 */
	@RequestMapping(value="/folderNames.htm", method = RequestMethod.GET)
	public String folderNames(String id,HttpServletResponse res) {
		JSONObject jsonobj = new JSONObject();
		List<String> list = new ArrayList<String>();
		list = GetFtpData.getFilesName(id);
		List<Map<String, String>> json = new ArrayList<Map<String, String>>();
		if(null!=list){
			for (String filename : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("filename", filename);
				map.put("id", id);
				json.add(map);
			}
		}
		jsonobj.put("list",json);
		++ServerCount.apiCount;
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info(id+"文件夹下文件:"+list);
		return "upload/showupload";
	}
	
	@RequestMapping(value="/getFile.htm", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFile(String fileName,String id,HttpServletRequest req) throws IOException {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		File file = GetFtpData.downloadExcel(fileName, id, ftp);
        String dfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        LOG.info("用户["+req.getSession().getAttribute("nickname")+"]下载了["+file+"]文件");
        ++ServerCount.apiCount;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        f.closed();
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/delFile.htm", method = RequestMethod.POST)
	public void delFile(String fileName,String id,HttpServletRequest req,HttpServletResponse res) throws IOException {
		JSONObject jsonobj = new JSONObject();
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		boolean flag = GetFtpData.removeFile(fileName, id, ftp);
        f.closed();
        ++ServerCount.apiCount;
        jsonobj.put("flag",flag);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]用户删除["+id+"]文件夹中["+fileName+"]文件:"+flag);
    }
	
	@RequestMapping(value="/showdata.htm", method = RequestMethod.POST)
	public void showdata(String filename,String testID,HttpServletResponse res) throws IOException {
		JSONObject jsonobj = new JSONObject();
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		List<List<Map<String, String>>> list = GetFtpData.showExcelData(filename, testID, ftp);
        f.closed();
        jsonobj.put("sheet",list);
        ++ServerCount.apiCount;
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("显示"+testID+"文件夹下["+filename+"]文件数据:"+list);
    }
	
	
	@RequestMapping(value="/getDescData.htm", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getData(String testname,String id,HttpServletRequest req) throws IOException {
		testname += Final.XLSX;
        String dfileName = new String(testname.getBytes("UTF-8"), "iso-8859-1");
        String filepath = Path.getProjectPath(Final.PROJECTNAME)+File.separator+"temp"+File.separator+testname;
        testService.exportExcelData(Integer.valueOf(id), filepath);
        LOG.info("用户["+req.getSession().getAttribute("nickname")+"]下载了用例["+testname+"]的数据文件");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        File file = new File(filepath);
        ++ServerCount.apiCount;
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	
	/**
	 * 使用ftp上传文件到服务器
	 * @param files
	 * @param testID
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/dataHandle.htm", method = RequestMethod.POST)
	public ModelAndView uploadData(@RequestParam("file")MultipartFile file,String id,HttpServletRequest req) {
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		ModelAndView model = new ModelAndView();
		String msg = testService.dataHandle(id,db,file,Integer.valueOf(req.getSession().getAttribute("uid").toString()))?"更新成功":"更新失败";
		model.getModelMap().put("data",testService.selTestAndDescByID(Integer.valueOf(id)));
		model.setViewName("showTestPage");
		LOG.info("数据上传["+msg+"] -> 获取单个用例描述");
		++ServerCount.apiCount;
		return model;
	}
	
	
	/**
	 * 使用ftp上传文件到服务器
	 * @param files
	 * @param testID
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/downfile.htm", method = RequestMethod.POST)
	public void ftpDownload(String path,String id,String filename,HttpServletRequest req,HttpServletResponse res) {
		path = "ftp://"+Final.FTP_USERNAME+":"+Final.FTP_PASSWORD+"@"+Final.FTP_IP+"/TESTDATA/"+path+"/"+id+"/"+filename.trim();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("url", path);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("ftp数据下载[TESTDATA/"+id+"/"+filename+"]");
		++ServerCount.apiCount;
	}
	
}
