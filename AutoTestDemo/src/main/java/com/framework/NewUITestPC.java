package com.framework;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.czy.service.impl.ExecServiceImpl;
import org.czy.util.Path;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NewUITestPC {

	public static WebDriver driver;

	private final static Logger LOG = Logger.getLogger(ExecServiceImpl.class);

	public static WebDriver getDriver() {
		 String path = Path.getProjectPath("AutoTestDemo")+File.separator+"selenium"+File.separator+"phantomjs.exe";
//		String path = "/home/autoplat/opt/app/phantomjsLinux/bin/phantomjs";
		System.setProperty("phantomjs.binary.path", path);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		// 支持ssl认证
		desiredCapabilities.setCapability("acceptSslCerts", true);
		// 截屏支持
		desiredCapabilities.setCapability("takesScreenshot", true);
		// css搜索支持
		desiredCapabilities.setCapability("cssSelectorsEnabled", true);
		// js支持
		desiredCapabilities.setJavascriptEnabled(true);
		// 驱动支持
		desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
		driver = new PhantomJSDriver(desiredCapabilities);
		return driver;
	}

	public static WebDriver init(String ip) {
		// TODO Auto-generated method stub
		// File file = new File
		// (Path.getProjectPath("AutoTestDemo")+"\\selenium\\chrome\\extension_1_1_3.crx");
		// ChromeOptions options = new ChromeOptions();
		// options.addExtensions(file);
		DesiredCapabilities chromeDesiredcap = DesiredCapabilities.chrome();

		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		// chromeDesiredcap.setCapability(ChromeOptions.CAPABILITY, options);
		try {
			driver = new RemoteWebDriver(new URL("http://" + ip + ":4444/wd/hub"), chromeDesiredcap);
			// driver = WEBDrivers.getInstance().getWebDriver(uid);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

}
