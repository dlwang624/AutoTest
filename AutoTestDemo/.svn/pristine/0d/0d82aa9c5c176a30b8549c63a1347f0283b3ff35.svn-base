package org.czy.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.Train;
import org.czy.entity.WorkDynamic;
import org.czy.service.TrainService;
import org.czy.service.WorkDynamicService;
import org.czy.util.Final;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/maindata")
@Scope("prototype")
public class IndexDataController {

private final static Logger LOG = Logger.getLogger(IndexDataController.class);
	
	@Autowired
	private TrainService trainService;
	
	@Autowired
	private WorkDynamicService workDynamicService;
	
	@RequestMapping(value="/train.htm", method = RequestMethod.GET)
	public ModelAndView addtrainpage() {
		ModelAndView model = new ModelAndView();
		boolean flag = trainService.selCount();
		if(flag){
			model.setViewName("redirect:trainll.htm");
		}else{
			model.getModelMap().addAttribute("backflag",trainService.selCount());
			model.setViewName("workstatus/addTrain");
		}
		return model;
	}
	
	@RequestMapping(value="/savetrain.htm", method = RequestMethod.POST)
	public ModelAndView inserttrain(Train train,@RequestParam("files") MultipartFile[] files,@RequestParam("pptfiles") MultipartFile[] pptfiles,HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		String msg = trainService.addTrain(train, req.getSession().getAttribute("nickname").toString(), files,pptfiles)?"添加成功":"添加失败";
		model.getModelMap().addAttribute("msg",msg);
		model.getModelMap().addAttribute("backflag",trainService.selCount());
		LOG.info(req.getSession().getAttribute("nickname")+" 添加了培训");
		model.setViewName("workstatus/addTrain");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/addTrain.htm", method = RequestMethod.GET)
	public ModelAndView addtrain() {
		ModelAndView model = new ModelAndView();
		model.getModelMap().addAttribute("backflag",true);
		model.setViewName("workstatus/addTrain");
		return model;
	}
	
	
	@RequestMapping(value="/checktrain.htm", method = RequestMethod.POST)
	public String checktrain(String title,HttpServletResponse res) {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("flag",trainService.checkTitle(title));
		new PrintWriter().ajaxResponse(res, jsonobj);
		return "workstatus/addTrain";
	}
	
	
	@RequestMapping(value="/trainll.htm", method = RequestMethod.GET)
	public ModelAndView getalltrain(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		List<Train> list = trainService.getAll();
		model.getModelMap().addAttribute("list",list);
		model.getModelMap().addAttribute("httpBase",Final.PROJECTDATASOURCEHTTP+Final.TRAINPATH+"/");
		model.setViewName("workstatus/trainList");
		LOG.info(req.getSession().getAttribute("nickname")+" 获取了所有培训");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/edittrain.htm", method = RequestMethod.POST)
	public ModelAndView editsynamic(HttpServletRequest req,Train train,@RequestParam("files")MultipartFile[] files,@RequestParam("pptfiles") MultipartFile[] pptfiles) {
		ModelAndView model = new ModelAndView();
		train.setAuthor(req.getSession().getAttribute("nickname").toString());
		String msg = trainService.updateTrain(train, files,pptfiles)?"更新成功":"更新失败";
		Train tr = trainService.getByID(train.getId());
		model.getModelMap().addAttribute("msg",msg);
		model.getModelMap().addAttribute("train",tr);
		model.getModelMap().addAttribute("files",trainService.getpptWord(train.getId()));
		model.setViewName("workstatus/trainpage");
		LOG.info(req.getSession().getAttribute("nickname")+" 编辑了["+train.getId()+"]培训");
		++ServerCount.apiCount;
		return model;
	}
	
	
	@RequestMapping(value="/deltrain.htm", method = RequestMethod.GET)
	public ModelAndView deltrain(int id) {
		ModelAndView model = new ModelAndView();
		boolean flag = trainService.removeTrain(id);
		model.getModelMap().addAttribute("flag",flag);
		List<Train> list = trainService.getAll();
		model.getModelMap().addAttribute("list",list);
		model.getModelMap().addAttribute("httpBase",Final.PROJECTDATASOURCEHTTP+Final.TRAINPATH+"/");
		model.setViewName("workstatus/trainList");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/deltrainppt.htm", method = RequestMethod.POST)
	public String deltrainppt(int id,String pptname,HttpServletResponse res) {
		JSONObject jsonobj = new JSONObject();
		boolean flag = trainService.delPPT(pptname, id);
		jsonobj.put("flag",flag);
		new PrintWriter().ajaxResponse(res, jsonobj);
		++ServerCount.apiCount;
		return "workstatus/trainpage";
	}
	
	@RequestMapping(value="/deltrainword.htm", method = RequestMethod.POST)
	public String deltrainword(int id,String wordname,HttpServletResponse res) {
		JSONObject jsonobj = new JSONObject();
		boolean flag = trainService.delWORD(wordname, id);
		jsonobj.put("flag",flag);
		new PrintWriter().ajaxResponse(res, jsonobj);
		++ServerCount.apiCount;
		return "workstatus/trainpage";
	}
	
	@RequestMapping(value="/showtrain.htm", method = RequestMethod.GET)
	public ModelAndView gettrain(int id) {
		ModelAndView model = new ModelAndView();
		Train train = trainService.getByID(id);
		model.getModelMap().addAttribute("files",trainService.getpptWord(id));
		model.getModelMap().addAttribute("train",train);
		++ServerCount.apiCount;
		model.setViewName("workstatus/trainpage");
		return model;
	}
	
	@RequestMapping(value="/downfile.htm", method = RequestMethod.POST)
	public void gettrain(String path,String id,String filename,HttpServletResponse res) {
		path = Final.PROJECTDATASOURCEHTTP+Final.TRAINPATH+"/"+id+"/"+path+"/"+filename.trim();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("url", path);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("ftp数据下载[TESTDATA/"+id+"/"+filename+"]");
		++ServerCount.apiCount;
	}
	
	
	
	
	
	
	@RequestMapping(value="/addsynamic.htm", method = RequestMethod.GET)
	public ModelAndView showsynamic() {
		ModelAndView model = new ModelAndView();
		boolean flag = workDynamicService.judgeBackShow();
		if(flag){
			model.setViewName("redirect:all.htm");
		}else{
			model.getModelMap().addAttribute("backflag",workDynamicService.judgeBackShow());
			model.setViewName("workstatus/addConfig");
		}
		return model;
	}
	
	@RequestMapping(value="/addwork.htm", method = RequestMethod.GET)
	public ModelAndView addwork() {
		ModelAndView model = new ModelAndView();
		model.getModelMap().addAttribute("backflag",true);
		model.setViewName("workstatus/addConfig");
		return model;
	}
	
	@RequestMapping(value="/checksynamic.htm", method = RequestMethod.POST)
	public String checksynamic(String title,HttpServletResponse res) {
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("flag",workDynamicService.checktitle(title));
		new PrintWriter().ajaxResponse(res, jsonobj);
		return "workstatus/addConfig";
	}
	
	
	@RequestMapping(value="/synamic.htm", method = RequestMethod.POST)
	public ModelAndView insertsynamic(HttpServletRequest req,WorkDynamic dynamic,@RequestParam("file") MultipartFile file) {
		ModelAndView model = new ModelAndView();
		dynamic.setCid(req.getSession().getAttribute("nickname").toString());
		String msg = workDynamicService.addWorkDynamic(dynamic, file)?"添加成功":"添加失败";
		model.getModelMap().addAttribute("msg",msg);
		model.getModelMap().addAttribute("backflag",workDynamicService.judgeBackShow());
		model.setViewName("workstatus/addConfig");
		LOG.info(req.getSession().getAttribute("nickname")+" 添加了公告");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/editsynamic.htm", method = RequestMethod.POST)
	public ModelAndView updatesynamic(HttpServletRequest req,WorkDynamic dynamic,@RequestParam("file") MultipartFile file) {
		ModelAndView model = new ModelAndView();
		dynamic.setCid(req.getSession().getAttribute("nickname").toString());
		String msg = workDynamicService.updateWorkDynamic(dynamic, file)?"更新成功":"更新失败";
		WorkDynamic dc = workDynamicService.getById(dynamic.getId());
		String div = workDynamicService.getView(dc);
		model.getModelMap().addAttribute("msg",msg);
		model.getModelMap().addAttribute("dynamic",dc);
		model.getModelMap().addAttribute("viewdesc",div);
		model.setViewName("workstatus/workpage");
		LOG.info(req.getSession().getAttribute("nickname")+" 更新了["+dynamic.getId()+"]公告");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/all.htm", method = RequestMethod.GET)
	public ModelAndView getall(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		List<WorkDynamic> list = workDynamicService.getAll();
		model.getModelMap().addAttribute("list",list);
		model.setViewName("workstatus/configList");
		LOG.info(req.getSession().getAttribute("nickname")+" 获取了所有公告");
		++ServerCount.apiCount;
		return model;
	}
	
	@RequestMapping(value="/showsynamic.htm", method = RequestMethod.GET)
	public ModelAndView getsynamic(int id) {
		ModelAndView model = new ModelAndView();
		WorkDynamic dynamic = workDynamicService.getById(id);
		String div = workDynamicService.getView(dynamic);
		model.getModelMap().addAttribute("dynamic",dynamic);
		model.getModelMap().addAttribute("viewdesc",div);
		++ServerCount.apiCount;
		model.setViewName("workstatus/workpage");
		return model;
	}
	
	@RequestMapping(value="/removesync.htm", method = RequestMethod.GET)
	public ModelAndView delsynamic(int id) {
		ModelAndView model = new ModelAndView();
		boolean flag = workDynamicService.delWorkDynamic(id);
		model.getModelMap().addAttribute("flag",flag);
		List<WorkDynamic> list = workDynamicService.getAll();
		model.getModelMap().addAttribute("list",list);
		model.setViewName("workstatus/configList");
		++ServerCount.apiCount;
		return model;
	}
}
