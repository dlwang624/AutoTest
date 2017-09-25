package com.utils.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
/**
 * 用于处理配置文件 支持获取，修改，保存修改 配置文件
 * 
 * @File name : ConfigUtil.java
 */
public class ConfigUtil {

	private Properties prop = new Properties();
	String fp = null;
	private String projectPath = this.getClass().getResource("/").getPath() + "/";
	/**
	 * 初始化ConfigUtil
	 * 
	 * @param filepath
	 *            设置配置文件路径
	 */
	public ConfigUtil(String filepath) {
		try {
			fp = projectPath + filepath;
			InputStream is = new FileInputStream(new File(projectPath + filepath));
			prop.load(is);
		} catch (Exception e) {
			System.err.println("Load Property ERROR，Property File Path:" + filepath);
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置
	 * 
	 * @param property
	 *            配置key
	 * @return value
	 */
	public String getProperty(String property) {
		String value = null;
		try {
			value = prop.getProperty(property);
		} catch (Exception e) {
			System.err.println("GET Property Value ERROR，Property Key:"	+ property);
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 保存配置文件修改,通常在setProperty后调用保存修改到配置文件
	 * 
	 * @throws Exception
	 */
	public void saveProperty() throws Exception {
		FileOutputStream fos = new FileOutputStream(fp);
		prop.store(fos, null);
		fos.close();
	}

	/**
	 * 设置配置文件
	 */
	public void setProperty(String key, String value) {
		try {
			prop.setProperty(key, value);
		} catch (Exception e) {
			System.err.println("SET Property Value ERROR，Property Key:" + key
					+ ",Property value:" + value);
			e.printStackTrace();
		}
	}
}
