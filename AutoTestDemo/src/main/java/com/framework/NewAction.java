package com.framework;

import org.czy.entity.Qcdb;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface NewAction {

	public void navigate(String url, String ip);

	public String mainAction(MainAction action,Qcdb db);

	WebDriver getswitchTo(String string);

	public WebDriver getdefaultContent();

	public void delCookie();

	public void quit();
}
