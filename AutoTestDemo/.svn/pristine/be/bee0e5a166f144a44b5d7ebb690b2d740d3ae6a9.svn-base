package org.czy.controller;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.MainTestInfo;
import org.czy.entity.Qcdb;
import org.czy.service.ProjectService;
import org.czy.service.QcdbService;
import org.czy.service.TestService;
import org.czy.util.PrintWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.utils.tools.Tool;

@Controller
@RequestMapping("/project")
@Scope("prototype")
public class ProjectController {
	private final static Logger LOG = Logger.getLogger(ProjectController.class);
	
	@Autowired
	private QcdbService qcdbService;
	
	@Autowired
	private TestService testService;
	

	/**
	 * 跳转主列表页面
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/main.htm", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		ModelAndView model = new ModelAndView();
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		MainTestInfo mti = testService.welcomeInfo(uid,db.getProjectid());
		model.getModelMap().addAttribute("allFail", mti.getAllFail());
		model.getModelMap().addAttribute("allUnknown", mti.getAllUnknown());
		model.getModelMap().put("info", mti);
		model.getModelMap().put("testlist", testService.selLimitByData(db.getProjectid().split(","),uid));
		model.setViewName("main");
		return model;
	}
	
	@RequestMapping(value="/config.htm", method = RequestMethod.GET)
	public String DBConfig(){
		return "addConfig";
	}
	
	@RequestMapping(value="/addconfig.htm", method = RequestMethod.POST)
	public ModelAndView addDBConfig(Qcdb db){
		ModelAndView model = new ModelAndView();
		db.setProjectid("");
		if(qcdbService.addQcdb(db)){
			model.getModelMap().put("list", qcdbService.getAll());
			model.setViewName("configList");
			LOG.info("QC库添加成功");
		}else{
			model.getModelMap().put("msg", "添加失败");
			model.setViewName("addConfig");
			LOG.info("QC库添加失败");
		}
		return model;
	}
	
	@RequestMapping(value="/configList.htm", method = RequestMethod.GET)
	public ModelAndView configList(){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("list", qcdbService.getAll());
		model.setViewName("configList");
		return model;
	}
	
	@RequestMapping(value="/selQcdb.htm", method = RequestMethod.GET)
	public ModelAndView selQcdb(int id){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("qcdb", qcdbService.selQcdb(id));
		model.setViewName("configPage");
		return model;
	}
	
	@RequestMapping(value="/updateQcdb.htm", method = RequestMethod.POST)
	public ModelAndView updateQcdb(Qcdb db){
		ModelAndView model = new ModelAndView();
		if(qcdbService.updateQcdb(db)){
			model.getModelMap().put("list", qcdbService.getAll());
			model.setViewName("configList");
			LOG.info("["+db.getId()+"]QC库更新成功");
		}else{
			model.getModelMap().put("msg", "更新失败");
			model.setViewName("configPage");
			LOG.info("["+db.getId()+"]QC库更新失败");
		}
		return model;
	}
	
	@RequestMapping(value="/tobackup.htm", method = RequestMethod.GET)
	public String backup(){
		return "copyFolder";
	}
	
	@RequestMapping(value="/backupsFolder.htm", method = RequestMethod.GET)
	public void qcCopyFolder(String baseid,String newid,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		String msg = testService.baseCopyToNew(db, baseid, newid, Integer.valueOf(req.getSession().getAttribute("uid").toString()));
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	
	@RequestMapping(value="/expectProject.htm", method = RequestMethod.POST)
	public String expectProject(String pName,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("prolist", Tool.getFolderIDByName(db,pName));
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("预想显示project:"+jsonobj.toString());
		return "copyFolder";
	}
	
	@RequestMapping(value="/expectReport.htm", method = RequestMethod.POST)
	public void expectProject(int qid,String pName,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = qcdbService.selQcdb(qid);
		if(null!=db){
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("prolist", Tool.getWordFolderIDByName(db,pName));
			new PrintWriter().ajaxResponse(res, jsonobj);
			LOG.info("测试总结报告画面预想显示project:"+jsonobj.toString());
		}
	}
	
}
