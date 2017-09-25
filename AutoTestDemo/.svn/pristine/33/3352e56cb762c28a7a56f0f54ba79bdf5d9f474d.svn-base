package org.czy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.dao.TrainMapper;
import org.czy.entity.Train;
import org.czy.service.TrainService;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TrainServiceImpl implements TrainService {

	private final static Logger LOG = Logger.getLogger(TrainServiceImpl.class);

	@Resource
	private TrainMapper trainMapper;

	private Set<String> saveFile(MultipartFile[] files,int tid,String pptorWord) {
		Set<String> list = new HashSet<String>();
		String path = "";
		Ftp f = new Ftp();
		try {
			
			FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
			// 文件保存路径
			String filePath = "\\"+Final.TRAINPATH+"\\";
			if (!ftp.changeWorkingDirectory(filePath)) { 
				String msg = ftp.makeDirectory(filePath)?"创建成功":"创建失败";
				LOG.info(Final.TRAINPATH+"文件夹"+msg);
			}
			filePath += File.separator+tid+File.separator;
			if (!ftp.changeWorkingDirectory(filePath)) {  
				String msg = ftp.makeDirectory(filePath)?"创建成功":"创建失败";
				LOG.info(filePath+"文件夹"+msg);
	        }
			filePath += pptorWord + File.separator;
			if (!ftp.changeWorkingDirectory(filePath)) {  
				String msg = ftp.makeDirectory(filePath)?"创建成功":"创建失败";
				LOG.info(filePath+"文件夹"+msg);
	        }
			if(ftp.changeWorkingDirectory(filePath)){
				LOG.info("FTP["+filePath+"]目录存在");
			}else{
				LOG.info("FTP["+filePath+"]目录不存在");
			}
			for (MultipartFile file : files) {
				path = file.getOriginalFilename();
				// 判断文件是否为空
				if (!file.isEmpty()) {
					// 转存文件
					if(!saveFileToFtp(file,ftp)){
						path = null;
					}else{
						list.add(path);
					}
				}
			}
		} catch (Exception e) {
			LOG.info(e);
		}finally{
			f.closed();
		}
		return list;
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
	
	
	@Override
	public boolean addTrain(Train train,String nickname,MultipartFile[] tl,MultipartFile[] ppts) {
		boolean addStatus = false;
		train.setTime(new Date());
		train.setAuthor(nickname);
		addStatus = trainMapper.insert(train)>0?true:false;
		if(addStatus){
			Train t = trainMapper.selectByTitle(train.getTitle());
			Set<String> paths = saveFile(tl,t.getId(),Final.WORD);
			if(paths!=null&&paths.size()>0){
				t.setFileurl(StringUtils.strip(paths.toString(),"[]"));
			}
			Set<String> path = saveFile(ppts,t.getId(),Final.PPT);
			if(path!=null&&path.size()>0){
				t.setTemp(StringUtils.strip(path.toString(),"[]"));
			}
			addStatus = trainMapper.updateByPrimaryKey(t)>0?true:false;
		}else{
			return addStatus;
		}
		return addStatus;
	}
	
	
	
	@Override
	public boolean updateTrain(Train train, MultipartFile[] tl,MultipartFile[] ppts) {
		train.setTime(new Date());
		String pt = train.getTemp();
		String wd = train.getFileurl();
		Set<String> paths = saveFile(tl,train.getId(),Final.WORD);
		if(paths!=null&&paths.size()>0){
			if(null!=wd&&!wd.equals("")){
				String[] wds = wd.split(",");
				for (int i = 0; i < wds.length; i++) {
					paths.add(wds[i].trim());
				}
			}
			train.setFileurl(StringUtils.strip(paths.toString(),"[]"));
		}
		Set<String> path = saveFile(ppts,train.getId(),Final.PPT);
		if(path!=null&&path.size()>0){
			if(null!=pt&&!pt.equals("")){
				String[] pts = pt.split(",");
				for (int i = 0; i < pts.length; i++) {
					path.add(pts[i].trim());
				}
			}
			train.setTemp(StringUtils.strip(path.toString(),"[]"));
		}
		return trainMapper.updateByPrimaryKey(train)>0?true:false;
	}
	
	
	@Override
	public List<Train> getAll() {
		return trainMapper.selectByAll();
	}
	

	@Override
	public boolean delPPT(String pptName, int tid) {
		boolean flag = false;
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		String toolpath = File.separator+ Final.TRAINPATH+File.separator+tid+File.separator+Final.PPT+File.separator;
//		List<String> toollist = GetFtpData.getfnames(toolpath);
//		for (String filename : toollist) {
			if(!GetFtpData.removef(pptName, toolpath, ftp)){
				flag = false;
			}else{
				Train train = trainMapper.selectByPrimaryKey(tid);
				String[] ppt = train.getTemp().split(",");
				Set<String> list = new HashSet<String>();
				for (int i = 0; i < ppt.length; i++) {
					if(!ppt[i].trim().equals(pptName)){
						list.add(ppt[i].trim());
					}
				}
				if(list.size()>0){
					train.setTemp(StringUtils.strip(list.toString(),"[]"));
				}else{
					train.setTemp("");
				}
				flag = trainMapper.updateByPrimaryKey(train)>0?true:false;
			}
//		}
		f.closed();
		return flag;
	}

	@Override
	public List<Train> getTopAll(int num) {
		return trainMapper.selectByTopTimeDesc(num);
	}

	@Override
	public boolean selCount() {
		return trainMapper.selectByCount()>0?true:false;
	}

	@Override
	public List<String> getPPTNames(int tid) {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		String toolpath = File.separator+ Final.TRAINPATH+File.separator+tid+File.separator;
		ftp.setControlEncoding(Final.WORD_SYSTEM_ENCODING);
		List<String> list = GetFtpData.getfnames(toolpath,ftp);
		f.closed();
		return list;
	}

	@Override
	public Train getByID(int tid) {
		return trainMapper.selectByPrimaryKey(tid);
	}

	@Override
	public boolean checkTitle(String title) {
		return trainMapper.selectByTitle(title)==null?true:false;
	}

	@Override
	public boolean removeTrain(int tid) {
		Ftp f = new Ftp();
		boolean flag = trainMapper.deleteByPrimaryKey(tid)>0?true:false;
		if(flag){
			try {
			FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
			String toolpath = File.separator+ Final.TRAINPATH+File.separator+tid+File.separator;
			ftp.changeWorkingDirectory(toolpath+Final.PPT+File.separator);
			flag = GetFtpData.delftpFiles(ftp, Final.WORD_SYSTEM_ENCODING);
			ftp.changeWorkingDirectory(toolpath+Final.WORD+File.separator);
			flag = GetFtpData.delftpFiles(ftp, Final.WORD_SYSTEM_ENCODING);
			f.closed();
			} catch (IOException e) {
				f.closed();
				LOG.error(e);
				return false;
			}
		}
		return flag;
	}

	@Override
	public boolean delWORD(String wordName, int tid) {
		boolean flag = false;
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		String toolpath = File.separator+ Final.TRAINPATH+File.separator+tid+File.separator+Final.WORD+File.separator;
//		List<String> toollist = GetFtpData.getfnames(toolpath);
//		for (String filename : toollist) {
			if(!GetFtpData.removef(wordName, toolpath, ftp)){
				flag = false;
			}else{
				Train train = trainMapper.selectByPrimaryKey(tid);
				String[] word = train.getFileurl().split(",");
				Set<String> list = new HashSet<String>();
				for (int i = 0; i < word.length; i++) {
					if(!word[i].trim().equals(wordName)){
						list.add(word[i].trim());
					}
				}
				if(list.size()>0){
					train.setFileurl(StringUtils.strip(list.toString(),"[]"));
				}else{
					train.setFileurl("");
				}
				flag = trainMapper.updateByPrimaryKey(train)>0?true:false;
			}
//		}
		f.closed();
		return flag;
	}

	@Override
	public Map<String, List<String>> getpptWord(int tid) {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.WORD_FTP_IP, Final.WORD_FTP_USERNAME, Final.WORD_FTP_PASSWORD);
		ftp.setControlEncoding(Final.WORD_SYSTEM_ENCODING);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String path = File.separator+ Final.TRAINPATH+File.separator+tid+File.separator+Final.WORD+File.separator;
		List<String> word = GetFtpData.getfnames(path,ftp);
		path = File.separator + Final.TRAINPATH+File.separator+tid+File.separator+Final.PPT+File.separator;
		List<String> ppt = GetFtpData.getfnames(path,ftp);
		map.put(Final.WORD, word);
		map.put(Final.PPT, ppt);
		f.closed();
		return map;
	}

	
}
