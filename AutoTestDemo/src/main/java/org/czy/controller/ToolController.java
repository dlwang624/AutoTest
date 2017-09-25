package org.czy.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.entity.Tools;
import org.czy.service.ToolsService;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
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
@RequestMapping("/servertools")
@Scope("prototype")
public class ToolController {
	private final static Logger LOG = Logger.getLogger(ToolController.class);
	
	@Autowired
	private ToolsService toolsService;
	
	@RequestMapping(value="/addtool.htm", method = RequestMethod.GET)
	public String add(HttpServletRequest req){
		return "tool/addConfig";
	}
	
	@RequestMapping(value="/tools.htm", method = RequestMethod.GET)
	public ModelAndView all(int type){
		ModelAndView model = new ModelAndView();
		String tabtitle = "";
		switch (type) {
		case 0:
			tabtitle = "常用工具集";
			break;
		case 1:
			tabtitle = "性能测试工具集";
			break;
		case 2:
			tabtitle = "安全测试工具集";
			break;
		default:
			break;
		}
		model.getModelMap().put("toolsList",toolsService.getAllByType(type));
		model.getModelMap().put("tabtitle",tabtitle);
		model.getModelMap().put("httpBase",Final.PROJECTDATASOURCEHTTP);
		model.setViewName("tool/configList");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/checkname.htm", method = RequestMethod.GET)
	public void check(String name,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("checkname", toolsService.checkToolName(name));
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/usecount.htm", method = RequestMethod.GET)
	public void useOpen(int id,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("urlOpen", toolsService.useCount(id));
		++ServerCount.useTool;
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/gethelpfile.htm", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFile(String id,String filename,HttpServletRequest req) throws IOException {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		File file = GetFtpData.downloadfile(filename, File.separator+Final.STUDYURL+File.separator+id, ftp);
        HttpHeaders headers = new HttpHeaders();
        String dfileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        ++ServerCount.apiCount;
        f.closed();
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/gettoolfile.htm", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getToolFile(int id,String filename,HttpServletRequest req) throws IOException {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		File file = GetFtpData.downloadfile(filename, File.separator+Final.TOOLFILE+File.separator+id, ftp);
        HttpHeaders headers = new HttpHeaders();
        String dfileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        toolsService.downloadCount(id);
        ++ServerCount.useTool;
        ++ServerCount.apiCount;
        f.closed();
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/upload.htm", method = RequestMethod.POST)
	public ModelAndView addTool(Tools tool,@RequestParam("helps") MultipartFile[] helps,@RequestParam("tl") MultipartFile tl,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		tool.setUploaduid(uid);
		tool.setUploadtime(new Date());
		String msg = "";
		if(toolsService.addTool(tool, helps, tl)){
			msg = "上传成功";
			++ServerCount.upTool;
		}else{
			msg = "上传失败";
		}
		model.getModelMap().put("msg", msg);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]用户"+tl!=null?"上传工具["+tl.getOriginalFilename()+"]":"上传外部工具["+tool.getUrl()+"]");
		++ServerCount.apiCount;
		model.setViewName("tool/addConfig");
		return model;
	}
	
	@RequestMapping(value="/show.htm", method = RequestMethod.GET)
	public ModelAndView showTool(int id){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("tool",toolsService.getById(id));
		++ServerCount.apiCount;
		model.setViewName("tool/configPage");
		return model;
	}
	
	@RequestMapping(value="/remove.htm", method = RequestMethod.GET)
	public ModelAndView deleteTool(int id,int type){
		ModelAndView model = new ModelAndView();
		String tabtitle = "";
		switch (type) {
		case 0:
			tabtitle = "常用工具集";
			break;
		case 1:
			tabtitle = "性能测试工具集";
			break;
		case 2:
			tabtitle = "安全测试工具集";
			break;
		default:
			break;
		}
		model.getModelMap().put("msg",toolsService.deleteTool(id)?"删除成功":"删除失败");
		model.getModelMap().put("toolsList",toolsService.getAllByType(type));
		model.getModelMap().put("tabtitle",tabtitle);
		model.getModelMap().put("httpBase",Final.PROJECTDATASOURCEHTTP);
		model.setViewName("tool/configList");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/update.htm", method = RequestMethod.POST)
	public ModelAndView updateTool(Tools tool,@RequestParam("helps") MultipartFile[] helps,@RequestParam("tl") MultipartFile tl,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("msg", toolsService.updateTool(tool, helps, tl)?"更新成功":"更新失败");
		LOG.info("["+req.getSession().getAttribute("nickname")+"]用户"+tl!=null?"更新工具["+tl.getOriginalFilename()+"]":"更新外部工具["+tool.getUrl()+"]");
		model.getModelMap().put("tool",toolsService.getById(tool.getId()));
		model.setViewName("tool/configPage");
		++ServerCount.apiCount;
		return model;
	}
	
}
