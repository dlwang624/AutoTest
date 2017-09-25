package com.check;

import java.util.List;

import org.apache.log4j.Logger;
import org.czy.controller.TestController;
import org.czy.entity.Qcdb;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.framework.MainAction;
import com.framework.NewUITestPC;

public class CheckElement extends NewUITestPC {

	// public static String isExistEleOrInfo = "";

	public static String isElementExist(MainAction action, WebElement element, Qcdb db) {
		final Logger LOG = Logger.getLogger(TestController.class);

		String logCon = "";
		String te = "";
		String resultPath = action.getResultpath();
		System.out.println("---------------- :" + resultPath);

		List<String> expects = action.getResultDescContent();

		if (null != expects && expects.size() > 1) {

//			if (resultPath != "" && null != resultPath && !resultPath.equals("")) {
				WebElement ele = driver.findElement(By.xpath(resultPath));
				te = ele.getText();
//			} else if (resultPath == null && resultPath != "" && resultPath.equals("")) {
//				Alert alert = driver.switchTo().alert();
//				te = alert.getText();
//			}

			if (expects.contains(te)) {
				logCon += "---" + expects + " 在页面中存在，并通过验证";
			} else {
				logCon += "@@@---" + expects + " 在页面中“不”存在，验证失败";
				// isExistEleOrInfo += expects + " 在页面中“不”存在，验证失败;";
			}
			// WebsocketHandler.sendMessageToUser(RunTest.uid, logCon);
		} else if (null != expects && expects.size() == 1) {
			String expect = expects.get(0).toString();

			try {
				Thread.sleep(2000);
//				if (resultPath != "" && null != resultPath && !resultPath.equals("")) {
					WebElement ele = driver.findElement(By.xpath(resultPath));
					te = ele.getText();
//				} else if (resultPath == null) {
//					Alert alert = driver.switchTo().alert();
//					te = alert.getText();
//				}

				if (te.contains(expect)) {
					logCon += "---" + expect + " 在页面中存在，并通过验证";
				} else {
					logCon += "@@@---" + expect + " 在页面中“不”存在，验证失败";
					// isExistEleOrInfo += expect + " 在页面中“不”存在，验证失败;";
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// WebsocketHandler.sendMessageToUser(RunTest.uid, logCon);
			LOG.info(logCon);
		}
		return logCon;

	}

}
