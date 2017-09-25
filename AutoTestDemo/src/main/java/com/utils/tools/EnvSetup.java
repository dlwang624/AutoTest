package com.utils.tools;

public class EnvSetup {
	private static ConfigUtil configUtil = new ConfigUtil("config.properties");
	private static String stg = configUtil.getProperty("env");

	public EnvSetup() {

	}

	/**
	 * 获取配置文件中的环境变量
	 * 
	 * @return 环境变量
	 */
	public static String getStg() {
		return stg;
	}

	/**
	 * 获取系统链接地址
	 * 
	 * @param system
	 *            系统名
	 * @return url地址
	 */
	public static String getSystemUrl(String system) {
		return configUtil.getProperty(system + "_" + stg);
	}

	public static String getUrl(String system) {
		return configUtil.getProperty(system);
	}

	public static String getTestData(String name) {
		// System.out.println(stg);
		return configUtil.getProperty(stg + "_" + name);
	}

	public static Boolean getDebug() {
		return Boolean.valueOf(configUtil.getProperty("debug"));
	}

	public static String getTestphone() {
		return String.valueOf(configUtil.getProperty("testphone"));
	}

	public static String getPaidOrderNo() {
		return String.valueOf(configUtil.getProperty("paidOrderNo"));
	}

	/*
	 * public static String getData(String name){ ConfigUtil configUtil2 = new
	 * ConfigUtil("/testdata.properties"); return
	 * configUtil2.getProperty(name+"_"+stg); }
	 */

	public static void main(String[] args) {
		System.out.println(getTestData("FrontUserName"));
	}
}
