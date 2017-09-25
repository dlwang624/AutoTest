package org.czy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class GetFtpData {
	
	private final static Logger LOG = Logger.getLogger(GetFtpData.class);

	/**
	 * 获取用例数据
	 * @author dengpeng
	 * @param dataFilename
	 * @param testID
	 * @return
	 */
	public static List<Map<String, List<String>>> getExcelData(String dataFilename,String testID,FTPClient ftp){
		List<Map<String, List<String>>> list = null;
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		//验证FTP服务器是否登录成功
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(Final.FTP_BASEPATH+testID);
				FTPFile[] file = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : file) {
					// 匹配到则进入
					if (ftpFile.getName().equals(dataFilename)) {
						// 将匹配到的文件流传入接口，转化成数组集合
						ftp.enterLocalPassiveMode();
						InputStream in = ftp.retrieveFileStream(new String(ftpFile.getName().getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
						List<List<List<String>>> excel = null;
						if(dataFilename.indexOf(Final.XLSX)>0){
							excel = ReaderExcel.readXlsx(in);
						}else{
							excel = ReaderExcel.readXls(in);
						}
						list = ReaderExcel.formatData(excel);
						in.close();
						ftp.getReply();
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		return list;
	}

	
	/**
	 * 获取指定用例下所有数据文件名
	 * @author dengpeng
	 * @param testID
	 * @return
	 */
	public static List<String> getFilesName(String testID){
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		List<String> list = new ArrayList<String>();
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				String path = Final.FTP_BASEPATH+testID;
				// 更改当前工作目录
 				if(!ftp.changeWorkingDirectory(path)){
 					return null;
 				}
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					if(ftpFile.isFile()){
						list.add(ftpFile.getName());
					}
				}
				f.closed();
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		return list;
	}
	
	/**
	 * 下载指定用例
	 * @author dengpeng
	 * @param dataFilename
	 * @param testID
	 * @return
	 */
	public static File downloadExcel(String dataFilename,String testID,FTPClient ftp){
		File file = null;
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(Final.FTP_BASEPATH+testID);
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					// 匹配到则进入
					if (ftpFile.getName().equals(dataFilename)) {
						// 将匹配到的文件流传入接口，转化成数组集合
						ftp.enterLocalPassiveMode();
						InputStream in = ftp.retrieveFileStream(new String(ftpFile.getName().getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
						file = isToFile(in,dataFilename);
//						ftp.getReply();
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		LOG.info("ftp提取["+dataFilename+"]文件成功");
		return file;
	}
	
	
	/**
	 * 下载指定帮助文档
	 * @author dengpeng
	 * @param dataFilename
	 * @param testID
	 * @return
	 */
	public static File downloadfile(String helpname,String path,FTPClient ftp){
		File file = null;
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(path);
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					// 匹配到则进入
					if (ftpFile.getName().equals(helpname)) {
//						file = new File(Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.TOOLTEMP+File.separator+helpname);
//						OutputStream outputStream = new FileOutputStream(file);
//						ftp.enterLocalPassiveMode();
//						boolean flag = ftp.retrieveFile(new String(ftpFile.getName().getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"),outputStream);
//						if(flag){
//							LOG.info("ftp上获取["+helpname+"]文件:成功");
//						}else{
//							LOG.info("ftp上获取["+helpname+"]文件:失败");
//						}
//						ftp.retrieveFile(helpname, outputStream);
//						outputStream.close();
//						 将匹配到的文件流传入接口，转化成数组集合
						ftp.enterLocalPassiveMode();
						InputStream in = ftp.retrieveFileStream(new String(ftpFile.getName().getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
						file = isToFile(in,helpname);
						ftp.getReply();
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		LOG.info("ftp提取["+helpname+"]文件成功");
		return file;
	}
	
	/**
	 * inputstream转file
	 * @author dengpeng
	 * @param ins
	 * @param dataFilename
	 * @return
	 */
	private static File isToFile(InputStream ins,String dataFilename){
		if(null!=ins){
			File file = new File(dataFilename);
			try {
				OutputStream os = new FileOutputStream(file);
				int bytesRead = 0;
				byte[] buffer = new byte[1024];
				while ((bytesRead = ins.read(buffer)) != -1) {
				   os.write(buffer, 0, bytesRead);
				}
				os.close();
				ins.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				LOG.error(e);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOG.error(e);
				return null;
			}
			return file;
		}else{
			return null;
		}
		
	}
	
	
	/**
	 * 上传用例数据
	 * @author dengpeng
	 * @param file
	 */
    public static boolean upload(MultipartFile file,FTPClient ftp){
    	boolean flag = false;
		try {
			ftp.setControlEncoding("UTF-8");
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			int replyCode = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				return false;
			}
			InputStream inStream = null;
			String fileName = new String(file.getOriginalFilename());
			inStream = file.getInputStream();
			flag = ftp.storeFile(new String(fileName.getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"), inStream);
			inStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			flag = false;
			LOG.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			LOG.error(e);
		}
		return flag;
	}
    
    
    /**
	 * 上传用例数据
	 * @author dengpeng
	 * @param files
	 */
    public static boolean upload(List<File> files,FTPClient ftp,String encoding){
    	boolean flag = false;
		try {
			ftp.setControlEncoding(encoding);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			int replyCode = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				return false;
			}
			for (File file : files) {
				InputStream inStream = null;
				String fileName = new String(file.getName());
				inStream = new FileInputStream(file);
				flag = ftp.storeFile(new String(fileName.getBytes(encoding),"ISO-8859-1"), inStream);
				inStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			flag = false;
			LOG.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			LOG.error(e);
		}
		return flag;
	}
    
    
    /**
     * 删除ftp服务器上指定文件
     * @author dengpeng
     * @param filename
     * @param path
     * @param ftp
     * @return
     */
    public static boolean removef(String filename,String path,FTPClient ftp){
    	boolean flag = false;
    	ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return false;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(path);
 				flag = ftp.deleteFile(new String(filename.getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
			} catch (IOException e) {
				LOG.error(e);
				return false;
			}
		}
    	return flag;
    }
    
    /**
	 * 获取指定用例下所有数据文件名
	 * @author dengpeng
	 * @param testID
	 * @return
	 */
	public static List<String> getfnames(String path){
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		List<String> list = new ArrayList<String>();
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				if(!ftp.changeWorkingDirectory(path)){
 					return null;
 				}
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					if(ftpFile.isFile()){
						list.add(ftpFile.getName());
					}
				}
				f.closed();
			} catch (IOException e) {
				f.closed();
				LOG.error(e);
				return null;
			}
		}
		return list;
	}
    
	
	/**
	 * 获取指定用例下所有数据文件名
	 * @author dengpeng
	 * @param testID
	 * @return
	 */
	public static List<String> getfnames(String path,FTPClient ftp){
		List<String> list = new ArrayList<String>();
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				if(!ftp.changeWorkingDirectory(path)){
 					return null;
 				}
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					if(ftpFile.isFile()){
						list.add(ftpFile.getName());
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		return list;
	}
    
	public static boolean delftpFiles(FTPClient ftp,String encoding){
    	boolean flag = false;
    	ftp.setControlEncoding(encoding);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return false;
		}
		if (null != ftp) {
			try {
				FTPFile[] files = ftp.listFiles();
				for (FTPFile file : files) {
					flag = ftp.deleteFile(new String(file.getName().getBytes(encoding),"ISO-8859-1"));
				}
			} catch (IOException e) {
				LOG.error(e);
				return false;
			}
		}
    	return flag;
    }
	
	
	
    /**
     * 删除ftp服务器上指定用例下指定文件
     * @author dengpeng
     * @param filename
     * @param testID
     * @param ftp
     * @return
     */
    public static boolean removeFile(String filename,String testID,FTPClient ftp){
    	boolean flag = false;
    	ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return false;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(Final.FTP_BASEPATH+testID);
 				flag = ftp.deleteFile(new String(filename.getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
			} catch (IOException e) {
				LOG.error(e);
				return false;
			}
		}
    	return flag;
    }
    
    
    public static List<List<Map<String, String>>> showExcelData(String dataFilename,String testID,FTPClient ftp){
    	List<List<Map<String, String>>> list = null;
		ftp.setControlEncoding(Final.SYSTEM_ENCODING);
		//验证FTP服务器是否登录成功
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
 				ftp.changeWorkingDirectory(Final.FTP_BASEPATH+testID);
				FTPFile[] file = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : file) {
					// 匹配到则进入
					if (ftpFile.getName().equals(dataFilename)) {
						// 将匹配到的文件流传入接口，转化成数组集合
						ftp.enterLocalPassiveMode();
						InputStream in = ftp.retrieveFileStream(new String(ftpFile.getName().getBytes(Final.SYSTEM_ENCODING),"ISO-8859-1"));
						List<List<List<String>>> excel = null;
						if(dataFilename.indexOf(Final.XLSX)>0){
							excel = ReaderExcel.readXlsx(in);
						}else{
							excel = ReaderExcel.readXls(in);
						}
						if(excel==null){
							return null;
						}
						list = ReaderExcel.formatShowData(excel);
						in.close();
						ftp.getReply();
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return null;
			}
		}
		return list;
	}
    
    
    /**
	 * 获取文本类型文件的内容,当前ftp目录只能存在一个此后缀的文本文件
	 * @param suffix 带.
	 * @param ftp
	 * @param encoding
	 * @return
	 */
	public static String getContent(String suffix,FTPClient ftp,String encoding){
		StringBuilder builder = null;
		ftp.setControlEncoding(encoding);
		int replyCode = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			return null;
		}
		if (null != ftp) {
			try {
				// 更改当前工作目录
				FTPFile[] files = ftp.listFiles();
				//遍历所有文件，匹配需要查找的文件
				for (FTPFile ftpFile : files) {
					// 匹配到则进入
					if (ftpFile.getName().substring(ftpFile.getName().lastIndexOf(".")).toLowerCase().equals(suffix)) {
						InputStream in = ftp.retrieveFileStream(new String(ftpFile.getName().getBytes(encoding),"ISO-8859-1"));
						BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
						String line;
						builder = new StringBuilder();
						while ((line = reader.readLine()) != null) {
							System.out.println(line);
							builder.append(line);
						}
						reader.close();
						if(in != null){
							in.close();
						}
						ftp.getReply();
					}
				}
			} catch (IOException e) {
				LOG.error(e);
				return "";
			}
		}
		return builder.toString();
	}
    
    
	public static void main(String[] args) {
		Ftp f = new Ftp();
		FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
		List<Map<String, List<String>>> list = getExcelData("Book1.xlsx",null,ftp);
		for (Map<String, List<String>> map : list) {
			for (String m : map.keySet()) {
				System.out.println(map.get(m));
			}
		}
		
		for (String filename : getFilesName("90")) {
			System.out.println(filename);
		}
		f.closed();
	}
}
