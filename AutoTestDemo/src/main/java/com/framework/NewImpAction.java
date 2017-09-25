package com.framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.czy.entity.Qcdb;
import org.czy.service.RunTestService;
import org.czy.util.Final;
import org.czy.util.SpringTool;
import org.czy.websocket.WebsocketHandler;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;

import com.check.CheckElement;
import com.google.common.base.Function;
import com.test.RunTest;

public class NewImpAction implements NewAction {

	private final static Logger LOG = Logger.getLogger(NewImpAction.class);

	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";

	private static int imgindex = 1;

	WebElement element = null;

	public static String errorlog;

	public static boolean flag;

	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static int SleepCount = 10;

	public NewImpAction() {
	}

	// public NewImpAction(WebDriver driver) {
	// this.driver = driver;
	// }

	public void navigate(String url, String ip) {
		// String regex =
		// "^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)(/)*$";
		String regex = "[a-zA-z]+://[^\\s]*";

		if (url.equals("")) {
			LOG.info("ip:" + ip + ";访问url不能为空");
			Reporter.log("访问url不能为空");
			throw new NullPointerException();
		}
		if (!Pattern.matches(regex, url)) {
			LOG.info("ip:" + ip + ";访问url:[" + url + "]格式不正确");
			Reporter.log("访问url:[" + url + "]格式不正确");
			throw new IllegalArgumentException();
		}
		driver = NewUITestPC.init(ip);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		System.out.println("成功打开:" + url);
	}

	public void phantom(String url, String ip) {
		String regex = "[a-zA-z]+://[^\\s]*";
		if (url.equals("")) {
			LOG.info("ip:" + ip + ";访问url不能为空");
			Reporter.log("访问url不能为空");
			throw new NullPointerException();
		}
		if (!Pattern.matches(regex, url)) {
			LOG.info("ip:" + ip + ";访问url:[" + url + "]格式不正确");
			Reporter.log("访问url:[" + url + "]格式不正确");
			throw new IllegalArgumentException();
		}
		driver = NewUITestPC.getDriver();
		driver.navigate().to(url);
		driver.manage().window().maximize();
		System.out.println("成功打开:" + url);
	}

	@Override
	public String mainAction(MainAction action, Qcdb db) {

		String msg = "";
		if (!action.getFlag().equals(Final.ACTION1) && !action.getFlag().equals(Final.ACTION2)
				&& !action.getFlag().equals(Final.ACTION3) && !action.getFlag().equals(Final.ACTION4)
				&& !action.getFlag().equals(Final.ACTION6) && !action.getFlag().equals(Final.ACTION5)) {
			element = getElement(action.getType(), action.getPath());
			if (element == null) {
				RunTest.successFlag = false;
				WebsocketHandler.sendMessageToUser(RunTest.uid, "@@@" + RunTest.logCon + "发生错误,没有找到操作元素");
				Reporter.log("@@@" + RunTest.logCon + "发生错误,没有找到操作元素");
			}
		}
		// WebElement resultEle = null;
		// if( null != action.getResultDescContent()){
		// resultEle =
		// getElement(action.getResultType(),action.getResultpath(),action.getIframeName());
		// if(resultEle==null){
		// RunTest.successFlag = false;
		// WebsocketHandler.sendMessageToUser(RunTest.uid, "@@@" +RunTest.logCon
		// + "发生错误,没有找到操作元素");
		// }
		// }

		flag = RunTest.browserFlag;
		// flag = 1;
		switch (action.getFlag()) {
		case "打开":
			try {
				if (flag) {
					if (RunTest.url != "" && !RunTest.url.equals("")) {
						navigate(RunTest.url, action.getIp());
					} else {
						if (null != action.getPath() && !action.getPath().equals("")) {
							navigate(action.getPath(), action.getIp());
						} else {
							navigate(action.getContent(), action.getIp());
						}
					}
				} else {
					if (RunTest.url != "" && !RunTest.url.equals("")) {
						phantom(RunTest.url, action.getIp());
					} else {
						if (null != action.getPath() && !action.getPath().equals("")) {
							phantom(action.getPath(), action.getIp());
						} else {
							phantom(action.getContent(), action.getIp());
						}
					}
				}
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "输入":

			element.sendKeys(action.getContent());
			msg = CheckElement.isElementExist(action, element, db);
			break;
		case "点击":
			if (!RunTest.successFlag && RunTest.inOutFlag) {
				WebsocketHandler.sendMessageToUser(RunTest.uid,
						"-----------------公共用例[" + action.getTestName() + "]测试结束------------------");
				Reporter.log("-----------------公共用例[" + action.getTestName() + "]测试结束------------------");
				if (RunTest.datanum == -1) {
					WebsocketHandler.sendMessageToUser(RunTest.uid,
							"-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
				} else {
					WebsocketHandler.sendMessageToUser(RunTest.uid, "-----------------------第[" + RunTest.num + "]次第["
							+ (RunTest.datanum + 1) + "]套数据执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次第[" + (RunTest.datanum + 1)
							+ "]套数据执行用例结束-----------------------");
				}
			} else if (!RunTest.successFlag) {
				if (RunTest.datanum == -1) {
					WebsocketHandler.sendMessageToUser(RunTest.uid,
							"-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
				} else {
					WebsocketHandler.sendMessageToUser(RunTest.uid, "-----------------------第[" + RunTest.num + "]次第["
							+ (RunTest.datanum + 1) + "]套数据执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次第[" + (RunTest.datanum + 1)
							+ "]套数据执行用例结束-----------------------");
				}
			}
			try {

				element.click();
				msg = CheckElement.isElementExist(action, element, db);

			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "清空":
			try {
				element.clear();
				msg = CheckElement.isElementExist(action, element, db);
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "选择":
			if (!RunTest.successFlag && RunTest.inOutFlag) {
				WebsocketHandler.sendMessageToUser(RunTest.uid,
						"-----------------公共用例[" + action.getTestName() + "]测试结束------------------");
				Reporter.log("-----------------公共用例[" + action.getTestName() + "]测试结束------------------");
				if (RunTest.datanum == -1) {
					WebsocketHandler.sendMessageToUser(RunTest.uid,
							"-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
				} else {
					WebsocketHandler.sendMessageToUser(RunTest.uid, "-----------------------第[" + RunTest.num + "]次第["
							+ (RunTest.datanum + 1) + "]套数据执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次第[" + (RunTest.datanum + 1)
							+ "]套数据执行用例结束-----------------------");
				}
			} else if (!RunTest.successFlag) {
				if (RunTest.datanum == -1) {
					WebsocketHandler.sendMessageToUser(RunTest.uid,
							"-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次执行用例结束-----------------------");
				} else {
					WebsocketHandler.sendMessageToUser(RunTest.uid, "-----------------------第[" + RunTest.num + "]次第["
							+ (RunTest.datanum + 1) + "]套数据执行用例结束-----------------------");
					Reporter.log("-----------------------第[" + RunTest.num + "]次第[" + (RunTest.datanum + 1)
							+ "]套数据执行用例结束-----------------------");
				}
			}
			try {
				Select select = new Select(element);
				select.selectByValue(action.getContent());
				msg = CheckElement.isElementExist(action, element, db);
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "右击":
			try {
				Actions actions = new Actions(driver);
				actions.contextClick(element).perform();
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "移动":
			try {
				Actions actions = new Actions(driver);
				actions.moveToElement(element).perform();
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "双击":
			try {
				Actions actions = new Actions(driver);
				actions.doubleClick(element).perform();
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "上传":
			try {
				// driver.findElement(By.id("update")).sendKeys("C:/Documents
				// and Settings/user/Desktop/Selenium/update_file.html");
				element.sendKeys(action.getContent());
			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "回车":
			try {
				try {

					Actions actions = new Actions(driver);
					Thread.sleep(2000);
					actions.sendKeys(Keys.ENTER).perform();
					msg = CheckElement.isElementExist(action, element, db);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} catch (NoSuchElementException | NoSuchFrameException | ElementNotVisibleException e) {
				errorlog = e.toString();
			}
			break;
		case "前提":
			RunTestService runTestService = (RunTestService) SpringTool.getBean("runTestService");
			runTestService.shareTestRun(action, db);
			break;

		case "等待":
			try {
				Thread.sleep(Integer.parseInt(action.getContent()));
			} catch (NumberFormatException | InterruptedException e2) {
				errorlog = e2.toString();
			}
			break;
		case "消失":
			waitdisapper();
			break;
		case "时间":

			if (action.getContent().equals("今天")) {
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
				JavascriptExecutor driver_js = (JavascriptExecutor) driver;
				String js = "$('input[id=quarterStartDateId]').attr('readonly','')";
				driver_js.executeScript(js);
				String today = dateFormat.format(now);
				element.sendKeys(today);
			} else {

				JavascriptExecutor driver_js = (JavascriptExecutor) driver;
				// String js =
				// "document.getElementByName('FDate').removeAttribute('readonly')";
				// 1.原生js，移除属性

				// js = "$('input[id=txtBeginDate]').removeAttr('readonly')";
				// 2.jQuery，移除属性

				// js = "$('input[id=txtBeginDate]').attr('readonly',false)";
				// 3.jQuery，设置为false

				String js = "$('input[id=quarterStartDateId]').attr('readonly','')";
				driver_js.executeScript(js);
				element.sendKeys(action.getContent());
			}

			break;
		case "悬停":
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			msg = CheckElement.isElementExist(action, element, db);
			break;
		case "弹框":
			try {
				Alert alert = driver.switchTo().alert();
				if (null != action.getIframeName() && action.getIframeName().equals("确认")) {
					alert.accept();
				} else if (null!=action.getIframeName()&&action.getIframeName().equals("取消")) {
					alert.dismiss();
				} else {
					alert.accept();
				}
				// msg = CheckElement.isElementExist(action, element, db);
			} catch (NoAlertPresentException noe) {

			}
			break;
		case "页面切换":
			try {
				String thisHandleId = driver.getWindowHandle();
				if (driver.getWindowHandles().size() >= 2) {
					for (String tempHandleId : driver.getWindowHandles()) {
						if (!tempHandleId.equals(thisHandleId)) {
							driver.switchTo().window(tempHandleId);
						}
					}
				} else {
					for (String tempHandleId : driver.getWindowHandles()) {

						if ((driver.getTitle()).equals(action.getContent())) {
							driver.switchTo().window(tempHandleId);
						}
					}
				}
			} catch (NoAlertPresentException noe) {

			}
			break;
		case "切换":
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				LOG.error("iframe切换等待时发生异常");
				e1.printStackTrace();
			}
			try {
				if (null != action.getIframeName() && action.getIframeName().equals("default")) {
					driver.switchTo().defaultContent();
				} else {
					driver.switchTo().frame(action.getIframeName());
				}
				break;
			} catch (Exception e) {
				try {
					if (action.getType().equals("id") || action.getType().equals("name")) {
						driver.switchTo().frame(action.getPath());
					} else if (action.getType().equals("xpath")) {
						WebElement iframe = driver.findElement(By.xpath(action.getPath()));
						driver.switchTo().frame(iframe);
					}
				} catch (Exception ex) {
					LOG.info("用例名:[" + action.getTestName() + "]通过id name xpath 查找iframe失败.");
				}
				LOG.info("用例名:[" + action.getTestName() + "]通过iframeName查找iframe失败.");
			}
			break;
		default:
			break;
		}
		return msg;
	}

	public void waitdisapper() {
		Function<WebDriver, Boolean> waitfn = new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				try {
					if (element.isDisplayed()) {
						return false;
					}
				} catch (Exception ex) {
					return true;
				}

				return true;
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 120, 1000);
		wait.until(waitfn);
	}

	public static void phantomshot(String status, int setpnum) {
		TakesScreenshot shot = ((TakesScreenshot) driver);
		File srcFile = shot.getScreenshotAs(OutputType.FILE);

		if (status.contains("成功完成")) {

			String path = RunTest.filepath + "/successimgs";
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}

			for (int i = 1; i <= setpnum; i++) {
				srcFile.renameTo(new File(path + "/" + "步骤 " + i + ".jpg"));
			}
			Reporter.log("<img src='../successimgs/" + "步骤 " + setpnum + ".jpg' height='200' width='200'/>");
		}
	}

	public static void takeScreenShot(ITestResult tr) {

		System.setProperty(ESCAPE_PROPERTY, "false");
		TakesScreenshot shot = ((TakesScreenshot) driver);
		File srcFile = shot.getScreenshotAs(OutputType.FILE);

		String path = RunTest.filepath + "/html/imgs";
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String imgname = "test" + imgindex + ".jpg";
		String img = "test" + imgindex;
		srcFile.renameTo(new File(path + "/" + imgname));
		Reporter.log(
				// "<div style='position:relative'>"
				// + "<div><img src='./imgs/"+imgname+"' height='150'
				// width='250' onmouseout='showIMG("+img+")'
				// onmouseover='hideIMG("+img+")'/></div>"
				// + "<div
				// style='width:800px;height:550px;position:absolute;margin-left:-800px;margin-top:-550px;display:none;'
				// id="+img+"><img src='./imgs/"+imgname+"' height='550'
				// width='800' /></div>"
				// + "</div>"
				// + "<script type='text/javascript'>"
				// + "function showIMG(id){"
				// + "document.getElementById(id).style.display='none';"
				// + "}"
				// + "function hideIMG(id){"
				// + "document.getElementById(id).style.display='block';"
				// + "}"
				// + "</script>"
				"<img src='./imgs/" + imgname + "'  height='150' width='250' onclick=\"javascript:window.open('./imgs/"
						+ imgname + "')\"/>");
		++imgindex;
	}

	private WebElement getElement(String type, String path) {
		WebElement ele = null;
		for (int i = 0; i < SleepCount; i++) {
			try {
				Thread.sleep(1000);
				ele = driver.findElement(switchType(type, path));
			} catch (Exception e) {
				LOG.error(e);
				continue;
			}
			if (null != ele) {
				break;
			}
			continue;
		}
		return ele;
	}

	private By switchType(String type, String path) {
		By by = null;
		switch (type) {
		case "xpath":
			by = By.xpath(path);
			break;
		case "id":
			by = By.id(path);
			break;
		case "linktext":
			by = By.linkText(path);
			break;
		case "name":
			by = By.name(path);
			break;
		case "partialLinkText":
			by = By.partialLinkText(path);
			break;
		case "classname":
			by = By.className(path);
			break;
		case "css":
			by = By.cssSelector(path);
			break;
		default:
			by = By.xpath(path);
			break;
		}
		return by;
	}

	public WebDriver getdefaultContent() {

		driver.switchTo().defaultContent();

		return driver;
	}

	@Override
	public WebDriver getswitchTo(String iframe) {

		driver.switchTo().frame(iframe);

		return driver;
	}

	public void delCookie() {
		driver.manage().deleteAllCookies();
	}

	@Override
	public void quit() {
		driver.quit();
	}

}