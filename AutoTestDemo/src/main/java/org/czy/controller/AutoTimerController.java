package org.czy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.AutoTimer;
import org.czy.entity.Qcdb;
import org.czy.service.AmbientService;
import org.czy.service.AutoTimerService;
import org.czy.service.UserService;
import org.czy.util.Final;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.czy.util.TimerTaskTest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/timer")
@Scope("prototype")
public class AutoTimerController {
	
	private final static Logger LOG = Logger.getLogger(AutoTimerController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AutoTimerService autoTimerService;
	
	@Autowired
	private AmbientService ambientService;
	
	@RequestMapping(value="/timer.htm", method = RequestMethod.GET)
	public ModelAndView addTimerConfig(HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("list", autoTimerService.getWeekTime("5",req.getLocalAddr()));
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.setViewName("admin/timerConfig");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/expectmail.htm", method = RequestMethod.POST)
	public void mail(String mail,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		List<Map<String, String>> mails = userService.LikeMail(mail);
		jsonobj.put("mails", mails);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("预想显示mail:"+jsonobj.toString());
	}
	
	@RequestMapping(value="/config.htm", method = RequestMethod.POST)
	public void addConfig(int swid,String testids,String time,int week,String tomails,String ccmails,String remark,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		JSONObject jsonobj = new JSONObject();
		String msg = autoTimerService.addTimer(swid,testids,time,week,tomails,ccmails,uid,db.getId(),remark,req.getLocalAddr())?"添加成功":"添加失败";
		++ServerCount.apiCount;
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		TimerTaskTest.reporturl=reporturl;
		TimerTaskTest.serverip=req.getLocalAddr();
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("用户["+uid+"]添加定期执行配置:"+msg);
	}
	
	@RequestMapping(value="/upconfig.htm", method = RequestMethod.POST)
	public void addConfig(AutoTimer timer,HttpServletRequest req,HttpServletResponse res){
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		JSONObject jsonobj = new JSONObject();
		String msg = autoTimerService.updateTimer(timer,uid,req.getLocalAddr())?"更新成功":"更新失败";
		++ServerCount.apiCount;
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		TimerTaskTest.reporturl=reporturl;
		TimerTaskTest.serverip=req.getLocalAddr();
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("用户["+uid+"]更新定期执行[ID:"+timer.getId()+"]配置:"+msg);
	}
	
	@RequestMapping(value="/allConfig.htm", method = RequestMethod.GET)
	public ModelAndView getAll(HttpServletRequest req,HttpServletResponse res){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().put("list", autoTimerService.getConfig(db.getId()));
		model.getModelMap().put("viewname","allconfig");
		model.setViewName("admin/timerConfigList");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/userConfig.htm", method = RequestMethod.GET)
	public ModelAndView getUserAll(HttpServletRequest req,HttpServletResponse res){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().put("list", autoTimerService.getUserConfig(db.getId(),Integer.valueOf(req.getSession().getAttribute("uid").toString())));
		model.getModelMap().put("viewname","userconfig");
		model.setViewName("admin/timerConfigList");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/selConfig.htm", method = RequestMethod.GET)
	public ModelAndView getConfig(String viewname,int id,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		ModelAndView model = new ModelAndView();
		AutoTimer timer = autoTimerService.selConfigByID(id);
		String[] wetime = timer.getExecdate().split("@");
		model.getModelMap().put("list", autoTimerService.getWeekTime(wetime[0],req.getLocalAddr()));
		model.getModelMap().put("timer", timer);
		model.getModelMap().put("week", wetime[0]);
		model.getModelMap().put("time", wetime[1]);
		model.getModelMap().put("swid", wetime.length>3?wetime[3]:-1);
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.getModelMap().put("viewname",viewname);
		model.setViewName("admin/timerPage");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/weekchange.htm", method = RequestMethod.GET)
	public void weekChange(String week,HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("list", autoTimerService.getWeekTime(week,req.getLocalAddr()));
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/weekChangeExcept.htm", method = RequestMethod.GET)
	public void weekChangeExcept(String week,String exceptTime,HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("list", autoTimerService.getWeekTime(week,req.getLocalAddr()));
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	
	@RequestMapping(value="/stop.htm", method = RequestMethod.GET)
	public void stopTimer(int timerID,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", autoTimerService.stopTimer(timerID)?"停止成功":"停止失败");
		++ServerCount.apiCount;
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/start.htm", method = RequestMethod.GET)
	public void startTimer(int timerID,HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		TimerTaskTest.reporturl=reporturl;
		TimerTaskTest.serverip=req.getLocalAddr();
		jsonobj.put("msg", autoTimerService.startTimer(timerID)?"启动成功":"启动失败");
		++ServerCount.apiCount;
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/serverTimerStart.htm", method = RequestMethod.GET)
	public void serverStart(HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		TimerTaskTest.reporturl=reporturl;
		TimerTaskTest.serverip=req.getLocalAddr();
		jsonobj.put("list", autoTimerService.serverStart(req.getLocalAddr()));
		new PrintWriter().ajaxResponse(res, jsonobj);
	}

}
