package org.czy.service.impl;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.dao.DescriptionMapper;
import org.czy.dao.ProjectMapper;
import org.czy.dao.RecordExecMapper;
import org.czy.dao.SwitchamMapper;
import org.czy.dao.TestMapper;
import org.czy.dao.UserMapper;
import org.czy.entity.Description;
import org.czy.entity.Qcdb;
import org.czy.entity.RecordExec;
import org.czy.entity.ReporterParams;
import org.czy.entity.Switcham;
import org.czy.entity.Test;
import org.czy.entity.User;
import org.czy.service.ExecService;
import org.czy.util.CopyFileUtil;
import org.czy.util.Daily;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
import org.czy.util.GetMaxOrMin;
import org.czy.util.MailUtil;
import org.czy.util.Path;
import org.czy.util.PrintWriter;
import org.czy.util.RunTestQueue;
import org.czy.util.TestEditor;
import org.czy.util.WEBDrivers;
import org.czy.websocket.WebsocketHandler;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.testng.Reporter;
import org.testng.TestNG;

import com.framework.NewImpAction;
import com.test.RunTest;


/**
 * 
 * @author dengpeng
 *
 */
@Service("execService")
public class ExecServiceImpl implements ExecService {
	
	private final static Logger LOG = Logger.getLogger(ExecServiceImpl.class);
	
	@Resource
	private DescriptionMapper descriptionMapper;
	
	@Resource
	private TestMapper testMapper;
	
	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RecordExecMapper recordExecMapper;
	
	@Resource
	private SwitchamMapper switchamMapper;
	

	@Override
	public String SelNameExecTest(int swid,String testName,String uid,String ip,Qcdb db,String proid,int num,String dataFilename,String testID,String reporturl,boolean browserFlag) {
		if(RunTestQueue.queueFull()){
			RunTestQueue.addQueue(ip);
			if(RunTestQueue.queuePeek().equals(ip)){
				long startTime=System.currentTimeMillis();
				if(!(num>1)){
					num = 1;
				}
				Switcham sw = switchamMapper.selectByPrimaryKey(swid);
				Test test = testMapper.selectByPrimaryKey(Integer.valueOf(testID));
				int successCount = test.getSuccess(),tempsuccess = test.getSuccess();
				int failCount = test.getFail(),tempfail = test.getFail();
				List<Description> list = descriptionMapper.selectByTestID(test.getId());
				LOG.info(list!=null?"执行用例["+testID+"]有步骤":"执行用例["+testID+"]没步骤");
				List<Map<String, List<String>>> params = null;
				if(null!=dataFilename&&!dataFilename.equals("null")){
					Ftp f = new Ftp();
					FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
					params = GetFtpData.getExcelData(dataFilename, testID, ftp);
					f.closed();
				}
				RunTest.yongliname=testName;
				RunTest.testid = null;
				RunTest.ip=ip;
				RunTest.uid=uid;
				RunTest.db=db;
				RunTest.proid=proid;
				RunTest.browserFlag = browserFlag;
				RunTest.url = sw!=null?sw.getAmurl():"";
				Map<String,Description> desc = new HashMap<String,Description>();
				Map<String,Description> resultDesc = new HashMap<>();
				for (Description description : list) {
					desc.put(description.getName(), description);
					if(description.getResultname()!=null&&!description.getResultname().equals("")){
						resultDesc.put(description.getResultname(), description);
					}
				}
				RunTest.desc = desc;
				RunTest.resultDesc = resultDesc;
				TestNG testNG = new TestNG();
				List<String> suites = new ArrayList<>();
				suites.add(Path.getProjectPath(Final.PROJECTNAME)+File.separator+"testng.xml");
				testNG.setTestSuites(suites);
				if(null==params){
					File fl = new File(Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORT+File.separator+test.getId()+File.separator+Daily.getCurrentDate()+File.separator+Final.FIRSTREPORT);
					RunTest.filepath = Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORT+File.separator+test.getId()+File.separator+Daily.getCurrentDate()+File.separator+Final.FIRSTREPORT+File.separator;
					testNG.setOutputDirectory(fl.getAbsolutePath());
				}
				for (int i = 1; i <= num; i++) {
					if(null!=params){
						for (int j = 0; j < params.size(); j++) {
							File fl = new File(Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORT+File.separator+test.getId()+File.separator+Daily.getCurrentDate()+File.separator+(j+1));
							RunTest.filepath = Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORT+File.separator+test.getId()+File.separator+Daily.getCurrentDate()+File.separator+(j+1)+File.separator;
							testNG.setOutputDirectory(fl.getAbsolutePath());
							LOG.info("testngtestngtestngtestngtestngtestngtestngtestng"+fl.getAbsolutePath());
							RunTest.map=params.get(j);
							RunTest.num=i;
							RunTest.datanum=j;
							testNG.run();
							if(RunTest.successFlag&&RunTest.FailFlag){
								++successCount;
							}else{
								++failCount;
								NewImpAction.getDriver().quit();
							}
						}
					}else{
						RunTest.num=i;
						testNG.run();
						if(RunTest.successFlag&&RunTest.FailFlag){
							++successCount;
							test.setFlag(2);
						}else{
							++failCount;
							test.setFlag(1);
							NewImpAction.getDriver().quit();
						}
					}
				}
				long endTime=System.currentTimeMillis(); //获取结束时间
				String time = Daily.formatHMS(endTime-startTime);
				//统计
				test.setReporturl("output/"+test.getId()+"/");
				test.setSuccess(successCount);
				test.setFail(failCount);
				testMapper.updateByPrimaryKey(test);
				//发邮件
				User user = userMapper.selectByPrimaryKey(Integer.valueOf(uid));
				String title = "["+testName+"]用例自动化测试报告";
				String url = "";
				if(null!=params){
					for (int i = 1; i <= params.size(); i++) {
						url +="报告(数据"+i+"): "+ reporturl+"/"+Final.REPORT+"/"+test.getId()+"/"+Daily.getCurrentDate()+"/"+i+"/html/index.html\r\n\t\t";
					}
				}else{
					url = "测试报告URL: "+reporturl+"/"+Final.REPORT+"/"+test.getId()+"/"+Daily.getCurrentDate()+"/"+Final.FIRSTREPORT+"/html/index.html";
				}
				String content = "　　 用例名: "+ testName +"\r\n"
								+"     Passed: "+ (successCount-tempsuccess) +"次\r\n"
								+"     Failed: "+ (failCount-tempfail) +"次\r\n"
								+"　　　 耗时: "+ time +"\r\n"+url;
				String[] toads = {user.getEmail()};
				try {
					MailUtil.sendTextEmail(toads, null, title, content);
				} catch (Exception e) {
					LOG.info("收件人:["+toads+"]\n抄送人:["+null+"]");
					LOG.error(user.getEmail()+"邮件发送失败\n"+e);
				}
				return "SUCCESS@"+test.getId()+"@"+test.getFlag();
			}else{
				return "已进入排队,您前面还有["+RunTestQueue.queueIndex(ip)+"]人,是否继续等待?";
			}
		}else{
			return "当前排队人数["+RunTestQueue.queueSize()+"],排队限额已满!";
		}
	}

	@Override
	public String getAddress(HttpServletRequest req) {
	    String ip = req.getHeader("x-forwarded-for");     
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	       ip = req.getHeader("Proxy-Client-IP");     
	    }     
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	       ip = req.getHeader("WL-Proxy-Client-IP");     
	    }     
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = req.getRemoteAddr();     
	    }     
	    return ip;
	}

	@Override
	public String addDriver(String ip, String uid) {
		// TODO Auto-generated method stub
		return WEBDrivers.getInstance().addDriver(uid, ip);
	}

	@Override
	public String execBatchTest(int swid,String testids, Qcdb db,String ip,String uid,String reporturl,boolean emailflag,boolean browserFlag) {
		// TODO Auto-generated method stub
		if(RunTestQueue.queueFull()){
			RunTestQueue.addQueue(ip);
			if(RunTestQueue.queuePeek().equals(ip)){
				long startTime=System.currentTimeMillis();
				Switcham sw = switchamMapper.selectByPrimaryKey(swid);
				List<ReporterParams> lrp = new ArrayList<ReporterParams>();
				List<Map<String, List<String>>> params = null;
				String pPath = Path.getProjectPath(Final.PROJECTNAME);
				String reportPath = pPath+File.separator+Final.REPORT+File.separator+Final.QCDB+db.getId()+File.separator+Daily.getCurrentDate();
				File file = new File(reportPath);
				int index = 1;
				//如果文件夹已生成取最大次+1,没有默认1
				if(file.exists()){
					index = GetMaxOrMin.getArrayMax(file.list())+1;
				}
				Map<Integer, String> ccMail = new HashMap<Integer, String>();
				String[] tids = testids.split(",");
				WebsocketHandler.waitSocketConntion(uid);
				for (int i = 0; i < tids.length; i++) {
					ReporterParams rp = new ReporterParams();
					int testID = Integer.valueOf(tids[i]);
					//获取default数据文件
					Ftp f = new Ftp();
					FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
					params = GetFtpData.getExcelData(Final.DEFAULT, tids[i], ftp);
					f.closed();
					Test test = testMapper.selectByPrimaryKey(testID);
					int successCount = test.getSuccess(),failCount=test.getFail();
					//执行中test发送给前端
					WebsocketHandler.sendMessageToUser(uid, test.getTestname());
					//填充抄送者
					ccMail.put(test.getNewuserid(), userMapper.selectByPrimaryKey(test.getNewuserid()).getEmail());
					//记录用例名id,name,url
					rp.setTestid(test.getId());
					rp.setTestname(test.getTestname());
					String[] ns = db.getDbname().split("_");
					rp.setProjectname(ns[ns.length-2]);
					List<Description> list = descriptionMapper.selectByTestID(test.getId());
					LOG.info("执行用例desc集合"+list);
					RunTest.ip=ip;
					RunTest.db=db;
					RunTest.uid=null;
					RunTest.testid=test.getTid();
					RunTest.num=1;
					RunTest.browserFlag = browserFlag;
					RunTest.url = sw!=null?sw.getAmurl():"";
					Map<String,Description> desc = new HashMap<String,Description>();
					Map<String,Description> resultDesc = new HashMap<>();
					for (Description description : list) {
						desc.put(description.getName(), description);
						if(description.getResultname()!=null&&!description.getResultname().equals("")){
							resultDesc.put(description.getResultname(), description);
						}
					}
					RunTest.desc = desc;
					RunTest.resultDesc = resultDesc;
					TestNG testNG = new TestNG();
					List<String> suites = new ArrayList<>();
					suites.add(pPath+File.separator+"testng.xml");	
					testNG.setTestSuites(suites);
					if(null==params){
						File fl = new File(reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator);
						RunTest.filepath = reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator;
						testNG.setOutputDirectory(fl.getAbsolutePath());
						testNG.run();
						rp.setPassed(TestEditor.checkHTMLPassed(reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator+"html"+File.separator+"suites.html"));
						rp.setUrl(Final.BATCHOUTPUTPATH+"/"+test.getId()+"/html/overview.html");
						rp.setFlag(true);
						lrp.add(rp);
					}else{
						for (int j = 0; j < params.size(); j++) {
							ReporterParams rpp = new ReporterParams();
							rpp.setTestid(rp.getTestid());
							rpp.setTestname(rp.getTestname());
							rpp.setProjectname(rp.getProjectname());
							File fl = new File(reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator);
							RunTest.filepath = reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator;
							testNG.setOutputDirectory(fl.getAbsolutePath());
							RunTest.map=params.get(j);
							RunTest.datanum=j;
							testNG.run();
							//模板
							rpp.setPassed(TestEditor.checkHTMLPassed(reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator+"html"+File.separator+"suites.html"));
							rpp.setUrl(Final.DATAOUTPUTPATH+"/"+test.getId()+"/"+(j+1)+"/html/overview.html");
							rpp.setFlag(false);
							lrp.add(rpp);
						}
					}
					
					//统计
					if(RunTest.successFlag&&RunTest.FailFlag){
						++successCount;
						test.setFlag(2);
					}else{
						++failCount;
						test.setFlag(1);
						NewImpAction.getDriver().quit();
					}
					test.setSuccess(successCount);
					test.setFail(failCount);
					testMapper.updateByPrimaryKey(test);
				}
				long endTime=System.currentTimeMillis(); //获取结束时间
				String time = Daily.formatHMS(endTime-startTime);
//				if(null==params){
//					//生成批量测试报告
//					TestEditor.outReporters(
//							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH, 
//							reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH, 
//							lrp);
//					//copy css和js到指定目录
//					CopyFileUtil.copyDirectory(
//							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH+File.separator+Final.REPORTCSSANDJSPATH, 
//							reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH, 
//							 true);
//				}else{
					TestEditor.outDataReporters(
							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH, 
							reportPath+File.separator+index, 
							lrp);
					//copy css和js到指定目录
					CopyFileUtil.copyDirectory(
							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH+File.separator+Final.REPORTCSSANDJSPATH, 
							reportPath+File.separator+index, 
							 true);
//				}
				
				
				String ReporterUrl = Final.REPORT+"/"+Final.QCDB+db.getId()+"/"+Daily.getCurrentDate()+"/"+index+"/index.html";
				//发邮件
				User user = userMapper.selectByPrimaryKey(Integer.valueOf(uid));
				int successCount=0,failCount=0;
				for (ReporterParams rp : lrp) {
					if(rp.getPassed()){
						successCount++;
					}else{
						failCount++;
					}
				}
				String title = "用例批量执行报告";
				String content = "　　 项目名: "+ lrp.get(0).getProjectname() +"\r\n"
								+"　　 用例数: "+tids.length+"个\r\n"
								+"     Passed: "+ successCount +"次\r\n"
								+"     Failed: "+ failCount +"次\r\n"
								+"　　　 耗时: "+ time +"\r\n"
								+"测试报告URL: "+ reporturl+"/"+ReporterUrl;
				String[] toads = {user.getEmail()};
				String[] ccads = null;
				if(emailflag){
					ccads = new String[ccMail.size()];
					int i = 0;
					for (int id : ccMail.keySet()) {
						 ccads[i]=ccMail.get(id);
						 ++i;
					}
				}
				try {
					MailUtil.sendTextEmail(toads, ccads, title, content);
				} catch (Exception e) {
					LOG.error(user.getEmail()+"邮件发送失败\n"+e);
					LOG.info("收件人:["+toads+"]\n抄送人:["+ccads+"]");
				}
				return "SUCCESS@"+ReporterUrl;
			}else{
				return "已进入排队,您前面还有["+RunTestQueue.queueIndex(ip)+"]人,是否继续等待?";
			}
		}else{
			return "当前排队人数["+RunTestQueue.queueSize()+"],排队限额已满!";
		}
	}


	@Override
	public int timerBatchTest(int swid,String testids, Qcdb db, String[] toads, String[] ccads, String reporturl,
			String serverip,int timerID,String remark,boolean releaseCheckFlag,RecordExec record) {
		String queueKey = serverip+"@"+timerID;
		int flag = 0;
		try {
			if(RunTestQueue.queueFull()){
				RunTestQueue.addQueue(queueKey);
				if(RunTestQueue.queuePeek().equals(queueKey)){
					if(null!=record){
						record.setExecdate(new Date());
					}
					long startTime=System.currentTimeMillis();
					Switcham sw = switchamMapper.selectByPrimaryKey(swid);
					List<ReporterParams> lrp = new ArrayList<ReporterParams>();
					List<Map<String, List<String>>> params = null;
					String pPath = Path.getProjectPath(Final.PROJECTNAME);
					String reportPath = pPath+File.separator+Final.REPORT+File.separator+Final.QCDB+db.getId()+File.separator+Daily.getCurrentDate();
					File file = new File(reportPath);
					int index = 1;
					//如果文件夹已生成取最大次+1,没有默认1
					if(file.exists()){
						index = GetMaxOrMin.getArrayMax(file.list())+1;
					}
					Map<Integer, String> ccMail = new HashMap<Integer, String>();
					String[] tids = testids.split(",");
					for (int i = 0; i < tids.length; i++) {
						ReporterParams rp = new ReporterParams();
						int testID = Integer.valueOf(tids[i]);
						Test test = testMapper.selectByPrimaryKey(testID);
						//获取default数据文件
						Ftp f = new Ftp();
						FTPClient ftp = f.connect(Final.FTP_IP, Final.FTP_USERNAME, Final.FTP_PASSWORD);
						params = GetFtpData.getExcelData(Final.DEFAULT, tids[i], ftp);
						f.closed();
						int successCount = test.getSuccess(),failCount=test.getFail();
						//填充抄送者
						ccMail.put(test.getNewuserid(), userMapper.selectByPrimaryKey(test.getNewuserid()).getEmail());
						//记录用例名id,name,url
						rp.setTestid(test.getId());
						rp.setTestname(test.getTestname());
						String[] ns = db.getDbname().split("_");
						rp.setProjectname(ns[ns.length-2]);
						List<Description> list = descriptionMapper.selectByTestID(test.getId());
						LOG.info("执行用例desc集合"+list);
						RunTest.ip=serverip;
						RunTest.db=db;
						RunTest.uid=null;
						RunTest.testid=test.getTid();
						RunTest.num=1;
						RunTest.browserFlag = false;
						RunTest.url = sw!=null?sw.getAmurl():"";
						Map<String,Description> desc = new HashMap<String,Description>();
						Map<String,Description> resultDesc = new HashMap<>();
						for (Description description : list) {
							desc.put(description.getName(), description);
							if(description.getResultname()!=null&&!description.getResultname().equals("")){
								resultDesc.put(description.getResultname(), description);
							}
						}
						RunTest.desc = desc;
						RunTest.resultDesc = resultDesc;
						TestNG testNG = new TestNG();
						List<String> suites = new ArrayList<>();
						suites.add(pPath+File.separator+"testng.xml");
						testNG.setTestSuites(suites);
						
						if(null==params){
							File fl = new File(reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator);
							RunTest.filepath = reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator;
							testNG.setOutputDirectory(fl.getAbsolutePath());
							testNG.run();
							rp.setPassed(TestEditor.checkHTMLPassed(reportPath+File.separator+index+File.separator+Final.BATCHOUTPUTPATH+File.separator+test.getId()+File.separator+"html"+File.separator+"suites.html"));
							rp.setUrl(Final.BATCHOUTPUTPATH+"/"+test.getId()+"/html/overview.html");
							rp.setFlag(true);
							lrp.add(rp);
						}else{
							for (int j = 0; j < params.size(); j++) {
								ReporterParams rpp = new ReporterParams();
								rpp.setTestid(rp.getTestid());
								rpp.setTestname(rp.getTestname());
								rpp.setProjectname(rp.getProjectname());
								File fl = new File(reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator);
								RunTest.filepath = reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator;
								testNG.setOutputDirectory(fl.getAbsolutePath());
								RunTest.map=params.get(j);
								RunTest.datanum=j;
								testNG.run();
								//模板
								rpp.setPassed(TestEditor.checkHTMLPassed(reportPath+File.separator+index+File.separator+Final.DATAOUTPUTPATH+File.separator+test.getId()+File.separator+(j+1)+File.separator+"html"+File.separator+"suites.html"));
								rpp.setUrl(Final.DATAOUTPUTPATH+"/"+test.getId()+"/"+(j+1)+"/html/overview.html");
								rpp.setFlag(false);
								lrp.add(rpp);
							}
						}
						
//						File fl = new File(reportPath+File.separator+index+File.separator+test.getId()+File.separator);
//						RunTest.filepath = reportPath+File.separator+index+File.separator+test.getId()+File.separator;
//						testNG.setOutputDirectory(fl.getAbsolutePath());
//						testNG.run();
//						rp.setPassed(TestEditor.checkHTMLPassed(reportPath+File.separator+index+File.separator+test.getId()+"\\html\\suites.html"));
//						rp.setUrl(test.getId()+"/html/overview.html");
//						lrp.add(rp);
						//统计
						if(RunTest.successFlag&&RunTest.FailFlag){
							++successCount;
							test.setFlag(2);
						}else{
							++failCount;
							test.setFlag(1);
							NewImpAction.getDriver().quit();
						}
						test.setSuccess(successCount);
						test.setFail(failCount);
						testMapper.updateByPrimaryKey(test);
					}
					long endTime=System.currentTimeMillis(); //获取结束时间
					String time = Daily.formatHMS(endTime-startTime);
					
					//生成批量测试报告
					TestEditor.outDataReporters(
							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH, 
							reportPath+File.separator+index, 
							lrp);
					//copy css和js到指定目录
					CopyFileUtil.copyDirectory(
							Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.REPORTTEMPPATH+File.separator+Final.REPORTCSSANDJSPATH, 
							reportPath+File.separator+index, 
							 true);
					
					String ReporterUrl = Final.REPORT+"/"+Final.QCDB+db.getId()+"/"+Daily.getCurrentDate()+"/"+index+"/index.html";
					//发邮件
					int successCount=0,failCount=0;
					for (ReporterParams rp : lrp) {
						if(rp.getPassed()){
							successCount++;
						}else{
							failCount++;
						}
					}
					if(null!=record){
						DecimalFormat df=new DecimalFormat("0.00");
						record.setExectime(time);
						record.setTestcount(tids.length);
						record.setFail(failCount);
						record.setSuccess(successCount);
						record.setSuccesrate(df.format((float)successCount/(float)record.getTestcount()));
					}
					String title = releaseCheckFlag?"releaseCheck["+remark+"]执行报告":"定期["+remark+"]执行报告";
					
					String content = "　　 项目名: "+ lrp.get(0).getProjectname() +"\r\n"
									+"　　 用例数: "+tids.length+"个\r\n"
									+"     Passed: "+ successCount +"次\r\n"
									+"     Failed: "+ failCount +"次\r\n"
									+"　　　 耗时: "+ time +"\r\n"
									+"测试报告URL: "+ reporturl+"/"+ReporterUrl;
					try {
						MailUtil.sendTextEmail(toads, ccads, title, content);
					} catch (Exception e) {
						LOG.error("定时器批量执行,邮件发送失败\n"+e);
						LOG.info("收件人:["+toads+"]\n抄送人:["+ccads+"]");
						flag = 2;
					}
					RunTestQueue.deleteQueue(queueKey);
					flag = 0;
					if(releaseCheckFlag){
						recordExecMapper.insert(record);
					}
				}else{
					flag = 1;
				}
			}else{
				flag = 3;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			RunTestQueue.deleteQueue(queueKey);
			LOG.error(e);
			flag = 2;
		}
		
		return flag;
	}

	
}
