package org.czy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.Authority;
import org.czy.entity.ConvertUtil;
import org.czy.entity.Description;
import org.czy.entity.MainTestInfo;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.Sidebar;
import org.czy.entity.Test;
import org.czy.entity.TreeFolder;
import org.czy.entity.User;
import org.czy.service.AmbientService;
import org.czy.service.AuthorityService;
import org.czy.service.ProjectService;
import org.czy.service.TestService;
import org.czy.util.GetFolderFileNames;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/CMS")
@SessionAttributes({"likename"})
@Scope("prototype")
public class TestController {
	private final static Logger LOG = Logger.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AmbientService ambientService;
	
	
	@RequestMapping(value="/main.htm", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		ModelAndView model = new ModelAndView();
		MainTestInfo mti = testService.welcomeInfo(uid,db.getProjectid());
		model.getModelMap().put("info", mti);
		model.getModelMap().put("testlist", testService.selLimitByData(db.getProjectid().split(","),uid));
		model.setViewName("main");
		return model;
	}
	
	
	/**
	 * 跳转添加用例页面,暂时无用
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/showAddPage.htm", method = RequestMethod.GET)
	public ModelAndView addTestPage(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		model.setViewName("addTestPage");
		LOG.info("进入添加用例页面");
		return model;
	}
	
	/**
	 * 添加用例,暂时无用
	 * @author dengpeng
	 * @param test
	 * @param descList
	 * @return
	 */
	@RequestMapping(value="/addTest.htm", method = RequestMethod.POST)
	public ModelAndView addTest(Test test,ConvertUtil descList){
		ModelAndView model = new ModelAndView();	
		String msg = testService.addTest(test, descList.getDescList(),"")?"添加成功":"添加失败";
		model.getModelMap().put("msg", msg);
		model.setViewName("showMSG");
		LOG.info("添加用例:"+msg);
		return model;
	}	
		
	/**
	 * 更新用例
	 * @author dengpeng
	 * @param test
	 * @param descList
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/updateTest.htm", method = RequestMethod.POST)
	public String updateTest(Test test,ConvertUtil descList,HttpServletRequest req,HttpServletResponse res){
		List<Description> list = descList.getDescList();
		test.setUpdateuserid(Integer.valueOf(req.getSession().getAttribute("uid").toString()));
		String msg = testService.updateTest(test, list)?"保存成功":"保存失败";
		++ServerCount.apiCount;
		ServerCount.updateTest.add(test.getId());
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("添加用例:"+msg);
		return "showTestPage";
	}
	
	/**
	 * 删除用例,暂时无用
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/delTest.htm", method = RequestMethod.GET)
	public ModelAndView delTest(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		int id = Integer.valueOf(req.getParameter("id"));
		String msg = testService.delDesc(id)?"删除成功":"删除失败";
		model.setViewName("showMSG");
		LOG.info("删除指定id的用例:"+msg);
		return model;
	}
	
	/**
	 * 初次加载testlist画面
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/allTest.htm", method = RequestMethod.GET)
	public ModelAndView allTest(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		//QC库中所有文件夹
		List<Project> prolist = testService.getProById(db);
		++ServerCount.apiCount;
		if(null!=prolist){
			String projectid = prolist.get(0).getProjectid();
			model.getModelMap().put("swlist",ambientService.getProAmbient(db));
			model.getModelMap().put("list",testService.getAllTest(db,projectid));
			model.getModelMap().put("prolist",prolist);
			model.getModelMap().put("projectid",projectid);
			model.setViewName("showTestList");
			LOG.info("获取["+db.getDbname()+"]QC库所有文件夹");
		}else{
			model.getModelMap().put("msg",db.getDbname()+"库没有添加文件夹");
			model.getModelMap().put("li", authorityService.selectByIDs(Integer.valueOf(req.getSession().getAttribute("uid").toString())));
			model.setViewName("main");
		}
		return model;
	}
	
	
	/**
	 * 获取用户用例集初次加载testlist画面
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/allUserTest.htm", method = RequestMethod.GET)
	public ModelAndView allUserTest(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		//QC库中所有文件夹
		++ServerCount.apiCount;
		List<Test> list = testService.getUserAllTest(uid, db.getProjectid().split(","));
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.getModelMap().put("list",list);
		model.getModelMap().put("viewname","usertestlist");
		model.getModelMap().put("welcomeMSG","我的用例");
		model.setViewName("showusertest");
		LOG.info("["+uid+"]用户获取所有自己创建的所有用例");
		return model;
	}
	
	/**
	 * 批量执行用例画面加载
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/smoking.htm", method = RequestMethod.GET)
	public ModelAndView smoking(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().put("swlist", ambientService.getProAmbient(db));
		model.setViewName("admin/execBatch");
		return model;
	}
	
	
	/**
	 * 批量执行用例画面加载
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/batchTest.htm", method = RequestMethod.GET)
	public void batchTest(HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		++ServerCount.apiCount;
		//QC库中所有文件夹
		List<TreeFolder> tree = testService.getTestTree(db);
		new PrintWriter().ajaxResponse(res, tree);
		LOG.info("获取["+db.getDbname()+"]QC库用例集");
	}
	
	/**
	 * 批量执行用例画面加载
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/byIdsTest.htm", method = RequestMethod.POST)
	public void batchTestByIds(String ids,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		++ServerCount.apiCount;
		//QC库中所有文件夹
		List<TreeFolder> tree = testService.getTestTree(db,ids);
		new PrintWriter().ajaxResponse(res, tree);
		LOG.info("获取["+db.getDbname()+"]QC库用例集");
	}
	
	
	/**
	 * 用例list选择下拉框中的文件名获取对应用例
	 * @param projectid 文件名对应id,对应project主键
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/changeForm.htm", method = RequestMethod.POST)
	public ModelAndView changeForm(String proid,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		
		//QC库中所有文件夹
		List<Project> prolist = testService.getProById(db);
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.getModelMap().put("list",testService.getAllTest(db,proid));
		model.getModelMap().put("prolist",prolist);
		model.getModelMap().put("projectid",proid);
		model.setViewName("showTestList");
		LOG.info("获取指定projectid:["+proid+"]所有用例");
		++ServerCount.apiCount;
		return model;
	}
	
	/**
	 * 查找单个用例并修改当前状态为锁定
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/selusertest.htm", method = RequestMethod.GET)
	public ModelAndView showuserTest(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		int id = Integer.valueOf(req.getParameter("id"));
		String proid = req.getParameter("proid");
		
		Test test = testService.getTest(id);
		test.setValue("1");
		test.setUpdateuserid(Integer.valueOf(req.getSession().getAttribute("uid").toString()));
		testService.updateTest(test);
		//同步
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		testService.selSyncTest(db,id,projectService.getProjectByID(Integer.valueOf(proid)).getProjectid());
//		if(!testService.selSyncTest(db,id,proid)){
//			test.setValue("0");
//			testService.updateTest(test);
//			Logout.SessionInit(req);
//			List<Qcdb> list = qcdbService.getAll();
//			model.getModelMap().addAttribute("list",list);
//			//登出
//			model.setViewName("login");
//			LOG.info("session共享问题:登出!");
//		}else{
			//获取
			model.getModelMap().put("data",testService.selTestAndDescByID(id));
			model.getModelMap().put("viewname",req.getParameter("viewname"));
			model.getModelMap().put("statusflag",req.getParameter("statusflag"));
			model.setViewName("showTestPage");
			++ServerCount.apiCount;
			LOG.info("获取单个用例");
//		}
		return model;
	}
	
	
	/**
	 * 查找单个用例并修改当前状态为锁定
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/SelTest.htm", method = RequestMethod.GET)
	public ModelAndView showTest(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		int id = Integer.valueOf(req.getParameter("id"));
		String proid = req.getParameter("proid");
		Test test = testService.getTest(id);
		test.setValue("1");
		test.setUpdateuserid(Integer.valueOf(req.getSession().getAttribute("uid").toString()));
		testService.updateTest(test);
		//同步
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		testService.selSyncTest(db,id,proid);
//		if(!testService.selSyncTest(db,id,proid)){
//			test.setValue("0");
//			testService.updateTest(test);
//			Logout.SessionInit(req);
//			List<Qcdb> list = qcdbService.getAll();
//			model.getModelMap().addAttribute("list",list);
//			//登出
//			model.setViewName("login");
//			LOG.info("session共享问题:登出!");
//		}else{
			//获取
			model.getModelMap().put("data",testService.selTestAndDescByID(id));
			model.getModelMap().put("viewname",req.getParameter("viewname"));
			model.setViewName("showTestPage");
			++ServerCount.apiCount;
			LOG.info("获取单个用例");
//		}
		return model;
	}
	
	/**
	 * check用例是否符合规范
	 * @author dengpeng
	 * @param testName
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/checkQC.htm", method = RequestMethod.POST)
	public String checkQC(String testName,String projectid,HttpServletRequest req){
		LOG.info("check单个QC");
		++ServerCount.apiCount;
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		testService.checkQC(testName,projectid, req.getSession().getAttribute("uid").toString(),db);
		return "showTestList";
	}
	
	/**
	 * check用例是否符合规范
	 * @author dengpeng
	 * @param testName
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/usercheckQC.htm", method = RequestMethod.POST)
	public String usercheckQC(String testName,String projectid,HttpServletRequest req){
		LOG.info("check单个QC");
		++ServerCount.apiCount;
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		testService.checkQC(testName,projectService.getProjectByID(Integer.valueOf(projectid)).getProjectid(), req.getSession().getAttribute("uid").toString(),db);
		return "showTestList";
	}
	
	/**
	 * 同步用例
	 * @author dengpeng
	 * @param testName
	 * @param projectid 对应project主键
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/syncTest.htm", method = RequestMethod.POST)
	public String syncTest(String testName,String projectid,HttpServletResponse res,HttpServletRequest req){
		JSONObject jsonobj = new JSONObject();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		Object[] idAndFlag = testService.syncTest(testName,projectid,Integer.valueOf(req.getSession().getAttribute("uid").toString()),db);
		++ServerCount.apiCount;
		if(null!=idAndFlag){
			int testID = Integer.valueOf(idAndFlag[0].toString());
			boolean flag = Boolean.valueOf(idAndFlag[1].toString());
			if(flag){
				String msg = "此用例已被他人同步";
				jsonobj.put("msg", msg);
				jsonobj.put("testid", testID);
				new PrintWriter().ajaxResponse(res, jsonobj);
				LOG.info("同步用例:"+msg);
			}else{
				String msg = testID > 0?"同步成功":"同步失败";
				jsonobj.put("msg", msg);
				jsonobj.put("testid", testID);
				++ServerCount.addTest;
				new PrintWriter().ajaxResponse(res, jsonobj);
				LOG.info("同步用例:"+msg);
			}
		}else{
			jsonobj.put("msg", "null");
			new PrintWriter().ajaxResponse(res, jsonobj);
		}
		return "showTestList";
	}
	
	/**
	 * 执行用例画面预想结果list输出
	 * @author dengpeng
	 * @param tName
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/expectTest.htm", method = RequestMethod.POST)
	public String expectTest(String tName,String proid,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		List<Test> tests = testService.expect(tName,proid);
		jsonobj.put("tests", tests);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("预想显示test:"+jsonobj.toString());
		return "showTestList";
	}

	/**
	 * 查看用例是否锁定
	 * @author dengpeng
	 * @param id
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/testValue.htm", method = RequestMethod.POST)
	public String testValue(int id,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		Test test = testService.getTest(id);
		jsonobj.put("value", test.getValue());
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("预想显示test:"+jsonobj.toString());
		return "showTestList";
	}
	
	/**
	 * 修改页面返回调用
	 * @author dengpeng
	 * @param test
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/updateTestValue.htm", method = RequestMethod.GET)
	public ModelAndView updateTestValue(HttpServletResponse res,HttpServletRequest req){
		int id = Integer.valueOf(req.getParameter("id"));
		String viewname = req.getParameter("viewname");
		String statusflag = req.getParameter("statusflag");
		String likename = req.getSession().getAttribute("likename").toString();
		Test test = testService.getTest(id);
		test.setValue("0");
		String msg = testService.updateTest(test)?"解锁成功":"解锁失败";
		LOG.info("解锁用例:"+msg);
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		if(viewname.equals("usertestlist")){
			if(statusflag!=null&&!statusflag.equals("")){
				int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
				List<Test> list = testService.getUserStatusTest(Integer.valueOf(statusflag), uid, db.getProjectid().split(","));
				model.getModelMap().put("list",list);
				model.getModelMap().put("statusflag",statusflag);
			}else{
				int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
				//QC库中所有文件夹
				List<Test> list = testService.getUserAllTest(uid, db.getProjectid().split(","));
				model.getModelMap().put("list",list);
			}
			model.getModelMap().put("swlist",ambientService.getProAmbient(db));
			model.getModelMap().put("welcomeMSG","我的用例");
			model.setViewName("showusertest");
		}else if(viewname.equals("selqcdbtest")){
			List<Test> list = testService.getQcdbTestByTName(likename, db.getProjectid().split(","));
			model.getModelMap().put("swlist",ambientService.getProAmbient(db));
			model.getModelMap().put("list",list);
			model.getModelMap().put("viewname","selqcdbtest");
			model.getModelMap().put("welcomeMSG","用例搜索结果");
			model.setViewName("showusertest");
		}else{
			List<Project> prolist = testService.getProById(db);
			//获取project表中project_id
			String projectid = projectService.getProjectByID(test.getProjectid()).getProjectid().toString();
			model.getModelMap().put("swlist",ambientService.getProAmbient(db));
			model.getModelMap().put("list",testService.getAllTest(db,projectid));
			model.getModelMap().put("prolist",prolist);
			model.getModelMap().put("projectid",projectid);
			model.setViewName("showTestList");
		}
		++ServerCount.apiCount;
		LOG.info("获取["+db.getDbname()+"]QC库所有文件夹");
		return model;
	}
	
	@RequestMapping(value="/filenames.htm", method = RequestMethod.GET)
	public String fileNameList(int id,HttpServletRequest req,HttpServletResponse res){
		String path = req.getSession().getServletContext().getRealPath("/") + "fileUpload/data/"+id+"/";
		JSONObject jsonobj = new JSONObject();
		List<String> list = new ArrayList<String>();
		list = GetFolderFileNames.getFileName(path);
		List<Map<String, String>> json = new ArrayList<Map<String, String>>();
		if(null!=list){
			for (String filename : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("filename", filename);
				json.add(map);
			}
		}
		jsonobj.put("list",json);
		++ServerCount.apiCount;
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info(id+"文件夹下文件:"+list);
		return "upload/showupload";
	}
	
	@RequestMapping(value="/lock.htm", method = RequestMethod.GET)
	public void lock(HttpServletResponse res,HttpServletRequest req){
		int id = Integer.valueOf(req.getParameter("id"));
		Test test = testService.getTest(id);
		test.setValue("1");
		String msg = testService.updateTest(test)?"锁定成功":"锁定失败";
		LOG.info("锁定用例:"+msg);
	}
	
	@RequestMapping(value="/unlocked.htm", method = RequestMethod.GET)
	public void unlocked(HttpServletResponse res,HttpServletRequest req){
		JSONObject jsonobj = new JSONObject();
		int id = Integer.valueOf(req.getParameter("id"));
		Test test = testService.getTest(id);
		test.setValue("0");
		String msg = testService.updateTest(test)?"解锁成功":"解锁失败";
		jsonobj.put("msg",msg);
		++ServerCount.apiCount;
		LOG.info("["+req.getSession().getAttribute("nickname")+"]解锁用例["+test.getTestname()+"]:"+msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
	
	
	
	@RequestMapping(value="/getStatus.htm", method = RequestMethod.GET)
	public ModelAndView getfailTest(int flag,HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		int uid = Integer.valueOf(req.getSession().getAttribute("uid").toString());
		ModelAndView model = new ModelAndView();
		List<Test> list = testService.getUserStatusTest(flag, uid, db.getProjectid().split(","));
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.getModelMap().put("list",list);
		model.getModelMap().put("statusflag",flag);
		model.getModelMap().put("welcomeMSG","我的用例");
		model.getModelMap().put("viewname","usertestlist");
		model.setViewName("showusertest");
		++ServerCount.apiCount;
		String msg = "";
		switch (flag) {
		case 0:
			msg = "未执行";
			break;
		case 1:
			msg = "错误";
			break;
		case 2:
			msg = "执行通过";
			break;
		default:
			break;
		}
		LOG.info("["+uid+"]用户获取了自己创建的"+msg+"用例");
		return model;
	}
	
	@RequestMapping(value="/getTest.htm", method = RequestMethod.POST)
	public ModelAndView getTestByNQ(String likename,HttpServletRequest req){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		ModelAndView model = new ModelAndView();
		List<Test> list = testService.getQcdbTestByTName(likename, db.getProjectid().split(","));
		model.getModelMap().put("swlist",ambientService.getProAmbient(db));
		model.getModelMap().addAttribute("likename", likename);
		model.getModelMap().put("list",list);
		model.getModelMap().put("viewname","selqcdbtest");
		model.getModelMap().put("welcomeMSG","用例搜索结果");
		model.setViewName("showusertest");
		LOG.info("["+db.getDbname()+"]模糊查询["+likename+"]");
		++ServerCount.apiCount;
		return model;
	}
	
}
