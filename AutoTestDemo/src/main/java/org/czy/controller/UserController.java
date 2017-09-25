package org.czy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.Authority;
import org.czy.entity.MainTestInfo;
import org.czy.entity.Qcdb;
import org.czy.entity.Sidebar;
import org.czy.entity.User;
import org.czy.service.AuthorityService;
import org.czy.service.ExecService;
import org.czy.service.QcdbService;
import org.czy.service.TestService;
import org.czy.service.UserService;
import org.czy.util.MailUtil;
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
@RequestMapping("/user")
@SessionAttributes({"basePath","uid","nickname","userAddress","localAddress","QCDB","dbname","mainLi","mainRoot","allFail","allUnknown","layoutfull","sidebar","aggregate","likename","returnPageName"})
@Scope("prototype")
public class UserController {
	
	private final static Logger LOG = Logger.getLogger(UserController.class);
	
	@Autowired
	private ExecService execService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QcdbService qcdbService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private TestService testService;
	
	
	
	/**
	 * 登录
	 * @author dengpeng
	 * @param name
	 * @param password
	 * @param qcname
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/login.htm", method = RequestMethod.POST)
	public ModelAndView login(String uname,String upass,int qcname,String pagename,HttpServletRequest req){
		User user = userService.userLogin(uname, upass);
		Qcdb qcdb= qcdbService.selQcdb(qcname);
		ModelAndView model = new ModelAndView();
		if(null!=user){
			model.getModelMap().addAttribute("uid", user.getId());
			model.getModelMap().addAttribute("nickname", user.getNickname());
			model.getModelMap().addAttribute("QCDB", qcdb);
			model.getModelMap().addAttribute("layoutfull", "");
			String[] names = qcdb.getDbname().split("_");
			model.getModelMap().addAttribute("dbname", names[names.length-2]);
			List<Authority> list = authorityService.selectByIDs(user.getId());
			model.getModelMap().addAttribute("mainLi", list);
			model.getModelMap().addAttribute("likename", "");
			Map<Integer,Sidebar> sidebarMap = new HashMap<Integer,Sidebar>();
			sidebarMap.put(0,new Sidebar(0));
			sidebarMap.put(1,new Sidebar(1));
			sidebarMap.put(2,new Sidebar(2));
			sidebarMap.put(3,new Sidebar(3));
			model.getModelMap().addAttribute("sidebar", sidebarMap);
			model.getModelMap().addAttribute("mainRoot", authorityService.selectByIDs(list));
			MainTestInfo mti = testService.welcomeInfo(user.getId(),qcdb.getProjectid());
			model.getModelMap().addAttribute("allFail", mti.getAllFail());
			model.getModelMap().addAttribute("allUnknown", mti.getAllUnknown());
			model.getModelMap().addAttribute("returnPageName", pagename);
			model.getModelMap().put("info", mti);
			model.getModelMap().put("testlist", testService.selLimitByData(qcdb.getProjectid().split(","),user.getId()));
			ServerCount.ppCount.add(user.getName());
			ServerCount.loginPro(qcdb.getDbname());
			model.setViewName("main");
			LOG.info("用户["+user.getNickname()+"]访问了"+qcname+"库");
		}else{
			model.getModelMap().addAttribute("list",qcdbService.getAllName());
			model.getModelMap().addAttribute("msg", "输入的用户名或密码错误");
			LOG.info("用户名["+uname+"],密码["+upass+"]访问了失败");
			if(pagename.equals("login")){
				model.setViewName("login");
			}else{
				model.setViewName("index/index");
			}
		}
		return model;
	}
	
	/**
	 * 跳转登录页面
	 * @author dengpeng
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/index.htm", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		String path = req.getContextPath();
		model.getModelMap().addAttribute("basePath", path + "/");
		model.getModelMap().addAttribute("userAddress", execService.getAddress(req));
		model.getModelMap().addAttribute("localAddress", req.getLocalAddr());
		List<Qcdb> list = qcdbService.getAllName();
		model.getModelMap().addAttribute("list",list);
		model.setViewName("login");
		return model;
	}
	
	
	@RequestMapping(value="/layoutfull.htm", method = RequestMethod.POST)
	public ModelAndView layoutFull(HttpServletRequest req,HttpServletResponse res,boolean full){
		ModelAndView model = new ModelAndView();
		model.getModelMap().addAttribute("layoutfull", full?"layout-fullwidth":"");
		model.setViewName("nav/nav");
		return model;
	}
	
	
	@RequestMapping(value="/password.htm", method = RequestMethod.GET)
	public String findpassword(HttpServletRequest req){
		return "findpass";
	}
	
	@RequestMapping(value="/findpass.htm", method = RequestMethod.POST)
	public ModelAndView findpass(HttpServletRequest req,String uname,String email){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("msg", userService.userFindPass(uname, email));
		model.setViewName("findpass");
		return model;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/sidebar.htm", method = RequestMethod.POST)
	public void checkSidebar(HttpServletRequest req,HttpServletResponse res,int rt,boolean collapse){
		Map<Integer,Sidebar> sidebarMap = (Map<Integer,Sidebar>)req.getSession().getAttribute("sidebar");
		Sidebar sidebar = sidebarMap.get(rt);
		//如果true为展开,否则闭合
		if(collapse){
			sidebar.open();
		}else{
			sidebar.close();
		}
		sidebarMap.put(rt, sidebar);
		req.getSession().setAttribute("sidebar",sidebarMap);
		JSONObject jsonobj = new JSONObject();
		new PrintWriter().ajaxResponse(res, jsonobj);
	}
}
