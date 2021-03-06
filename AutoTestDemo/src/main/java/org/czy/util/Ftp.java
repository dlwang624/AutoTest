package org.czy.util;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;


public class Ftp {
	
	private final static Logger LOG = Logger.getLogger(Ftp.class);
	
	
	private FTPClient ftpClient;
	/**
	 * 
	 * <b>登陆ftp 返回ftpClient事件<b>
	 * 
	 * @param ip
	 *            ftp所在ip
	 * @param user
	 *            登陆名
	 * @param password
	 *            密码
	 */
	public FTPClient connect(String ip, String user, String password) {
		ftpClient = new FTPClient();
		try {
			if(ip.indexOf(":")>0){
				String[] ipt = ip.split(":");
				ftpClient.connect(ipt[0],Integer.valueOf(ipt[1]));
			}else{
				ftpClient.connect(ip);
			}
			ftpClient.login(user, password);
		} catch (SocketException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}

		if (!ftpClient.isConnected()) {
			ftpClient = null;
		}

		return ftpClient;
	}
	
	public void closed(){
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
		}
	}


}
