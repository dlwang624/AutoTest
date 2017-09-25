package org.czy.report;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

import com.framework.NewImpAction;

public class TestNGListener extends TestListenerAdapter {
	
	private static Logger logger = Logger.getLogger(TestNGListener.class);

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		logger.info(tr.getName() + " Failure");
		NewImpAction.takeScreenShot(tr);
	}

}