package org.czy.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.service.ExecService;
import org.czy.service.ProjectService;
import org.czy.service.QcdbService;
import org.czy.service.TestService;
import org.czy.util.Final;
import org.czy.util.Logout;
import org.czy.util.PrintWriter;
import org.czy.util.RunTestQueue;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/Exec")
@Scope("prototype")
public class ExecTest {
	
	@Autowired
	private ExecService execService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private QcdbService qcdbService;
	
	private final static Logger LOG = Logger.getLogger(ExecTest.class);
	
	/**
	 * 跳转执行用例页面
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/RunTest.htm", method = RequestMethod.GET)
	public ModelAndView StupTestPage(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("prolist",prolist);
		model.setViewName("ExecTest");
		LOG.info("["+req.getSession().getAttribute("nickname")+"]访问了执行单个用例画面");
		++ServerCount.apiCount;
		return model;
	}
	
	
	/**
	 * 同步指定文件夹下所有用例
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/syncfolder.htm", method = RequestMethod.GET)
	public void allTest(String id,HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		++ServerCount.apiCount;
		//QC库中所有文件夹
		testService.syncFolder(db,Integer.valueOf(id));
	}
	
	
	/**
	 * 根据用例名执行用例
	 * @author dengpeng
	 * @param tName
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/userExecTest.htm", method = RequestMethod.POST)
	public void userExec(int swid,String tName,String proid,int num,String dataFilename,String testID,boolean browserFlag,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		String msg = execService.SelNameExecTest(swid,tName,req.getSession().getAttribute("uid").toString(),req.getSession().getAttribute("userAddress").toString(),db,projectService.getProjectByID(Integer.valueOf(proid)).getProjectid(),num,dataFilename,testID,reporturl,browserFlag);
		JSONObject jsonobj = new JSONObject();
		String[] masAndTestID = null;
		if(msg.indexOf("@")>0){
			masAndTestID = msg.split("@");
			msg = masAndTestID[0];
			jsonobj.put("testID", masAndTestID[1]);
			jsonobj.put("execFlag", masAndTestID[2]);
		}
		++ServerCount.apiCount;
		++ServerCount.runTest;
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+tName+"]用例已执行完");
	}
	
	/**
	 * 根据用例名执行用例
	 * @author dengpeng
	 * @param tName
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/ExecTest.htm", method = RequestMethod.POST)
	public void Exec(int swid,String tName,String proid,int num,String dataFilename,String testID,boolean browserFlag,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		StringBuffer url = req.getRequestURL();
		String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
		String msg = execService.SelNameExecTest(swid,tName,req.getSession().getAttribute("uid").toString(),req.getSession().getAttribute("userAddress").toString(),db,proid,num,dataFilename,testID,reporturl,browserFlag);
		JSONObject jsonobj = new JSONObject();
		String[] masAndTestID = null;
		if(msg.indexOf("@")>0){
			masAndTestID = msg.split("@");
			msg = masAndTestID[0];
			jsonobj.put("testID", masAndTestID[1]);
			jsonobj.put("execFlag", masAndTestID[2]);
		}
		++ServerCount.apiCount;
		++ServerCount.runTest;
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+tName+"]用例已执行完");
	}
	
	
	/**
	 * 批量执行用例
	 * @author dengpeng
	 * @param tName
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/execbatchtest.htm", method = RequestMethod.POST)
	public String Exec(int swid,String testids,boolean emailflag,boolean browserFlag,HttpServletRequest req,HttpServletResponse res){
		if(testids!=""&&testids!=null){
			StringBuffer url = req.getRequestURL();
			String reporturl = url.substring(0,url.indexOf(Final.PROJECTNAME)+Final.PROJECTNAME.length());
			Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
			String msg = execService.execBatchTest(swid,testids, db, req.getSession().getAttribute("userAddress").toString(),req.getSession().getAttribute("uid").toString(),reporturl,emailflag,browserFlag);
			JSONObject jsonobj = new JSONObject();
			String[] masAndTestID = null;
			if(msg.indexOf("@")>0){
				masAndTestID = msg.split("@");
				msg = masAndTestID[0];
				jsonobj.put("url",masAndTestID[1]);
			}
			++ServerCount.apiCount;
			jsonobj.put("msg", msg);
			new PrintWriter().ajaxResponse(res, jsonobj);
			LOG.info("batch用例已执行完");
		}
		return "admin/execBatch";
	}
	
	/**
	 * 跳转添加项目页面
	 * @return
	 */
	@RequestMapping(value="/project.htm", method = RequestMethod.GET)
	public String addProjectPage(){
		return "addProject";
	}
	
	
	@RequestMapping(value="/RemoveQueue.htm", method = RequestMethod.GET)
	public void RemoveQueue(HttpServletRequest req,HttpServletResponse res){
		RunTestQueue.deleteQueue(req.getSession().getAttribute("userAddress").toString());
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", "退出队列");
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	@RequestMapping(value="/waitQueue.htm", method = RequestMethod.GET)
	public void waitQueue(HttpServletRequest req,HttpServletResponse res){
		int i = RunTestQueue.queueIndex(req.getSession().getAttribute("userAddress").toString());
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("index", i);
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	
	/**
	 * 添加项目
	 * @author dengpeng
	 * @param pro
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/addproject.htm", method = RequestMethod.POST)
	public String insertProject(Project pro,HttpServletRequest req,HttpServletResponse res){
		Project p = projectService.getProjectByProID(pro.getProjectid());
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		String msg = "";
		if(null==p){
			if(projectService.addProject(db,pro)){
				msg = "添加成功";
				String proids = "";
				if(db.getProjectid()==null||!db.getProjectid().equals("")){
					proids = db.getProjectid() + "," + projectService.getProjectByProID(pro.getProjectid()).getId();
				}else{
					proids = projectService.getProjectByProID(pro.getProjectid()).getId().toString();
				}
				db.setProjectid(proids);
				qcdbService.updateQcdb(db);
			}else{
				msg = "添加失败,QC库中文件夹编号不存在";
			}
			
		}else{
			msg = "此文件夹已添加过";
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+db.getDbname()+"]项目添加文件夹:["+pro.getName()+"]--"+msg);
		return "addProject";
	}
	
	/**
	 * 显示项目list
	 * @author dengpeng
	 * @return
	 */
	@RequestMapping(value="/projectlist.htm", method = RequestMethod.GET)
	public ModelAndView showProjectListPage(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().addAttribute("list", projectService.getProById(db));
		LOG.info("获取所有项目");
		model.setViewName("projectList");
		return model;
	}
	
	/**
	 * 查询单个项目
	 * @author dengpeng
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/selproject.htm", method = RequestMethod.GET)
	public ModelAndView selProjectPage(int id,HttpServletRequest req,HttpServletResponse res){
		ModelAndView model = new ModelAndView();
		Project pro = projectService.getProjectByID(id);
		model.getModelMap().addAttribute("pro",pro);
		LOG.info("获取id为["+id+"]的项目");
		model.setViewName("projectPage");
		return model;
	}
	
	/**
	 * 删除单个项目
	 * @author dengpeng
	 * @param id
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/delproject.htm", method = RequestMethod.GET)
	public ModelAndView delProjectPage(int id,HttpServletRequest req,HttpServletResponse res){
		String msg = projectService.delProject(id)?"删除成功":"删除失败";
		LOG.info("删除id为["+id+"]的文件夹:"+msg);
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().addAttribute("list", projectService.getProById(db));
		LOG.info("获取所有项目");
		model.setViewName("projectList");
		return model;
	}
	
	/**
	 * 更新项目
	 * @author dengpeng
	 * @param project
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/updatepro.htm", method = RequestMethod.POST)
	public void updateProject(Project project,HttpServletRequest req,HttpServletResponse res){
		String msg = "";
		JSONObject jsonobj = new JSONObject();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		if(projectService.updateProject(db,project)){
			msg = "文件夹更新成功";
			LOG.info("["+db.getDbname()+"]项目更新id为["+project.getId()+"]的项目更新成功");
		}else{
			msg = "文件夹更新失败,QC库中文件夹编号不存在";
			LOG.info("["+db.getDbname()+"]项目更新id为["+project.getId()+"]的文件夹->更新失败,QC库中文件夹编号不存在");
		}
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	/**
	 * 注销
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/logout.htm", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		String pagename = req.getSession().getAttribute("returnPageName").toString();
		Logout.SessionInit(req);
		List<Qcdb> list = qcdbService.getAllName();
		model.getModelMap().addAttribute("list",list);
		if(pagename.equals("login")){
			model.setViewName("login");
		}else{
			model.setViewName("index/index");
		}
		return model;
	}
	
}

