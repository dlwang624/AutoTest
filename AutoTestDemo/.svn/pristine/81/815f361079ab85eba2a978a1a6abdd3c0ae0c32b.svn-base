package com.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.czy.util.Daily;
import org.czy.util.Final;
import org.czy.util.Path;
import org.testng.TestNG;

public class TestNGDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 TestNG testNG = new TestNG();
//		 testNG.setUseDefaultListeners(false);
//		 testNG.addListener();
		 RunTest.yongliname="02航班搜索基本流程_直飞_无优惠卷提醒";
		 RunTest.uid="";
		 RunTest.ip="";
		 System.out.println(Path.getProjectPath(Final.PROJECTNAME));
		 List<String> suites = new ArrayList<>();
		 System.out.println(Path.getProjectPath("AutoTestDemo")+"\\src\\main\\webapp\\testng.xml");
		 suites.add(Path.getProjectPath("AutoTestDemo")+"\\src\\main\\webapp\\testng.xml");
		 testNG.setTestSuites(suites);
		 File fl = new File(Path.getProjectPath("AutoTestDemo")+"\\"+Daily.getCurrentDate()+"\\output");
		 testNG.setOutputDirectory(fl.getAbsolutePath());
		 testNG.run();
	}

}
