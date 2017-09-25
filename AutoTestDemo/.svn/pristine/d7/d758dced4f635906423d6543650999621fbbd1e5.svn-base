package org.czy.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WEBDrivers {

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	private static final int MAXCOUNT = 10;
	
	private static WEBDrivers webDrivers = null;
	
	public static WEBDrivers getInstance(){
		if(null==webDrivers){
			webDrivers = new WEBDrivers();
		}
		return webDrivers;
	}
	
	public String addDriver(String uid,String ip){
		String msg = "";
		if(MAXCOUNT>=drivers.size()){
			WebDriver driver = null;
			DesiredCapabilities chromeDesiredcap = DesiredCapabilities.chrome();
			System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			try {
				driver = new RemoteWebDriver(new URL("http://"+ip+":4444/wd/hub"), chromeDesiredcap);
				drivers.put(uid, driver);
				msg = "webDriver["+uid+"]添加成功";
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg = "webDriver添加["+uid+"]失败";
			}
		}else{
			msg = "webDriver使用人数已满,最大人数"+MAXCOUNT+"人";
		}
		return msg;
	}
	
	public String removerWebDriver(String key){
		drivers.remove(key);
		return "webDriver["+key+"]移除成功";
	}
	
	public WebDriver getWebDriver(String key){
		return drivers.get(key);
	}
}
