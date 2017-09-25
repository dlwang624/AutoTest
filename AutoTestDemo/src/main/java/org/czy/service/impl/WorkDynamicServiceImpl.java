package org.czy.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.dao.WorkDynamicMapper;
import org.czy.entity.WorkDynamic;
import org.czy.service.WorkDynamicService;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
import org.czy.util.TestEditor;
import org.czy.util.WordToHtml;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkDynamicServiceImpl implements WorkDynamicService {
	
	private final static Logger LOG = Logger.getLogger(WorkDynamicServiceImpl.class);

	@Resource
	private WorkDynamicMapper workDynamicMapper;
	
	
	
	private void getFilePath(Set<String> set,File[] flist){
		List<File> folderlist = new ArrayList<File>();
		for (File f : flist) {
			if(!f.isDirectory()){
				set.add(f.getAbsolutePath());
		    }else{
		    	folderlist.add(f);
		    }
		}
		for (File file : folderlist) {
			File cflist[] = file.listFiles();
			getFilePath(set,cflist);
		}
	}
	
	/***
	 * 保存文件
	 * @param file
	 * @return
	 */
	private String saveFile(MultipartFile file,int wid) {
		String path = "";
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = WordToHtml.BASEPATH+wid+File.separator;
				File f = new File(filePath);
				if(!f.exists()){
					f.mkdirs();
				}
				filePath += file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				path = filePath;
			} catch (Exception e) {
				LOG.info(e);
			}
		}
		return path;
	}
	
	
	@Override
	public boolean addWorkDynamic(WorkDynamic dynamic,MultipartFile file) {
		Ftp f = new Ftp();
		boolean flag = false;
		dynamic.setTime(new Date());
		flag = workDynamicMapper.insert(dynamic)>0?true:false;
		if(flag){
			WorkDynamic wd = workDynamicMapper.selectByTitle(dynamic.getTitle());
			String propath = saveFile(file, wd.getId());
			try {
				if(!propath.equals("")){
					String filename = new File(propath).getName();
					if(filename.substring(filename.lastIndexOf(".")).toLowerCase().equals(".docx")){
						WordToHtml.Word2007ToHtml(propath,wd.getId());
					}else{
						WordToHtml.Word2003ToHtml(propath,wd.getId());
					}
					Set<String>	set = new HashSet<String>();
					File fl = new File(WordToHtml.BASEPATH+wd.getId());
					getFilePath(set,fl.listFiles());
					List<File> flist = new ArrayList<File>();
					for (String path : set) {
						String suffix = path.substring(path.lastIndexOf(".")+1).toLowerCase();
						if(suffix.equals("html")){
							File html = new File(path);
							String htmlvar = TestEditor.relpaceIMGSRC(html,wd.getId());
							html.delete();
							BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(html), "UTF-8"));
							o.write(htmlvar);
							o.close();
						}
						flist.add(new File(path));
					}
					FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
					String path = File.separator+Final.WORDHTML+File.separator;
					if(!ftp.changeWorkingDirectory(path)){
						ftp.makeDirectory(path);
					}
					path += wd.getId()+File.separator;
					if(!ftp.changeWorkingDirectory(path)){
						ftp.makeDirectory(path);
						ftp.changeWorkingDirectory(path);
					}
					flag = GetFtpData.upload(flist,ftp,Final.WORD_SYSTEM_ENCODING);
					f.closed();
				}
			} catch (IOException e) {
				f.closed();
				LOG.error(e);
				return false;
			} catch (TransformerException e) {
				f.closed();
				LOG.error(e);
				return false;
			} catch (ParserConfigurationException e) {
				f.closed();
				LOG.error(e);
				return false;
			}
		}else{
			return false;
		}
		return flag;
	}

	@Override
	public boolean updateWorkDynamic(WorkDynamic dynamic,MultipartFile file) {
		Ftp f = new Ftp();
		boolean flag = false;
		dynamic.setTime(new Date());
		flag = workDynamicMapper.updateByPrimaryKey(dynamic)>0?true:false;
		if(flag){
			String propath = saveFile(file,dynamic.getId());
			try {
				if(!propath.equals("")){
					String filename = new File(propath).getName();
					if(filename.substring(filename.lastIndexOf(".")).toLowerCase().equals(".docx")){
						WordToHtml.Word2007ToHtml(propath,dynamic.getId());
					}else{
						WordToHtml.Word2003ToHtml(propath,dynamic.getId());
					}
					Set<String>	set = new HashSet<String>();
					File fl = new File(WordToHtml.BASEPATH+dynamic.getId());
					getFilePath(set,fl.listFiles());
					List<File> flist = new ArrayList<File>();
					for (String path : set) {
						String suffix = path.substring(path.lastIndexOf(".")+1).toLowerCase();
						if(suffix.equals("html")){
							File html = new File(path);
							String htmlvar = TestEditor.relpaceIMGSRC(html,dynamic.getId());
							html.delete();
							BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(html), "UTF-8"));
							o.write(htmlvar);
							o.close();
						}
						flist.add(new File(path));
					}
					FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
					String path = File.separator+Final.WORDHTML+File.separator;
					if(!ftp.changeWorkingDirectory(path)){
						ftp.makeDirectory(path);
					}
					path += dynamic.getId()+File.separator;
					if(!ftp.changeWorkingDirectory(path)){
						ftp.makeDirectory(path);
						ftp.changeWorkingDirectory(path);
					}
					flag = GetFtpData.delftpFiles(ftp,Final.WORD_SYSTEM_ENCODING);
					flag = GetFtpData.upload(flist,ftp,Final.WORD_SYSTEM_ENCODING);
					f.closed();
				}
			} catch (IOException e) {
				f.closed();
				LOG.error(e);
				return false;
			} catch (TransformerException e) {
				f.closed();
				LOG.error(e);
				return false;
			} catch (ParserConfigurationException e) {
				f.closed();
				LOG.error(e);
				return false;
			}
		}else{
			return false;
		}
		return flag;
	}

	@Override
	public boolean delWorkDynamic(int tid) {
		Ftp f = new Ftp();
		boolean flag = false;
		flag = workDynamicMapper.deleteByPrimaryKey(tid)>0?true:false;
		try {
			FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
			String path = File.separator+Final.WORDHTML+File.separator+tid+File.separator;
			ftp.changeWorkingDirectory(path);
			flag = GetFtpData.delftpFiles(ftp,Final.WORD_SYSTEM_ENCODING);
			f.closed();
		} catch (IOException e) {
			f.closed();
			LOG.error(e);
			return false;
		}
		return flag;
	}

	@Override
	public List<WorkDynamic> getAll() {
		return workDynamicMapper.selectByAll();
	}

	@Override
	public List<WorkDynamic> getTopAll(int num) {
		return workDynamicMapper.selectByTopTimeDesc(num);
	}

	@Override
	public boolean checktitle(String title) {
		return workDynamicMapper.selectByTitle(title)==null?true:false;
	}

	@Override
	public WorkDynamic getById(int id) {
		return workDynamicMapper.selectByPrimaryKey(id);
	}

	@Override
	public String getView(WorkDynamic dynamic) {
		Ftp f = new Ftp();
		String divContent = "";
		int id = dynamic.getId();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		String path = File.separator+Final.WORDHTML+File.separator+id+File.separator;
		try {
			ftp.changeWorkingDirectory(path);
			divContent = GetFtpData.getContent(Final.HTML,ftp,Final.WORD_SYSTEM_ENCODING);
		} catch (IOException e) {
			f.closed();
			LOG.error(e);
			return "";
		}
		return divContent;
	}

	@Override
	public boolean judgeBackShow() {
		return workDynamicMapper.selCount()>0?true:false;
	}

}
