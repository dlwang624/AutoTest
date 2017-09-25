package org.czy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.AutoTimer;
import org.czy.entity.Qcdb;
import org.czy.entity.RecordExec;
import org.czy.entity.TreeFolder;
import org.czy.service.AutoTimerService;
import org.czy.service.ExecService;
import org.czy.service.QcdbService;
import org.czy.service.TestService;
import org.czy.util.Final;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.czy.util.TimerTaskTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/releaseCheck")
@Scope("prototype")
public class ReleaseCheckController {
	
	private final static Logger LOG = Logger.getLogger(ReleaseCheckController.class);
	
	@Autowired
	private QcdbService qcdbService;
	
	@Autowired
	private ExecService execService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private AutoTimerService autoTimerService;
	
	/**
	 * 发布后check
	 * @author dengpeng
	 * @param id timerID
	 * @return
	 */
	@RequestMapping(value="/batchTest.htm", method = RequestMethod.GET)
	public boolean batchTest(int id,String redmineID,String userNo,HttpServletRequest req,HttpServletResponse res){
		boolean flag = false;
		++ServerCount.apiCount;
		++ServerCount.releaseCount;
		RecordExec record = new RecordExec();
		
		String msg = "";
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		String serverip=req.getLocalAddr();
		AutoTimer autoTimer = autoTimerService.selConfigByID(id);
		String[] weekandtime = autoTimer.getExecdate().split("@");
		int swid;
		if(weekandtime.length>3){
			swid = Integer.valueOf(weekandtime[3]);
		}else{
			swid = -1;
		}
		String[] toads = autoTimer.getTouserid().split(",");
		String[] ccads = autoTimer.getCcuserid().split(",");
		Qcdb qcdb = qcdbService.selQcdb(autoTimer.getQcdbid());
		record.setRedmineno(redmineID);
		record.setUserno(userNo);
		record.setQcdbid(qcdb.getDbname());
		for (;;) {
			int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdb,toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),true,record);
			if(f!=1){
				msg = "批量用例执行完毕";
				LOG.info("releaseCheck批量用例执行完毕");
				break;
			}else{
				try {
					LOG.info("批量用例执行等待,每一分钟刷新一次");
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					msg = "线程等待错误"+e;
					LOG.error("releaseCheck线程等待错误"+e);
					flag = false;
				}
			}
		}
		res.setContentType("text/html"); 
	    res.setContentType("text/plain; charset=utf-8");
	    try {
			res.getWriter().print(msg);
			res.getWriter().flush();
		    res.getWriter().close();
			// TODO Auto-generated catch block
	    } catch (IOException e) {
			LOG.error("ajax输出error"+e);
			flag = false;
		}
	    flag = testService.selectExecStatus(1, autoTimer.getTestid().split(","));
	    return flag;
	}
}
