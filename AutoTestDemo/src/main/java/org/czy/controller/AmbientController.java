package org.czy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.czy.entity.Qcdb;
import org.czy.entity.Switcham;
import org.czy.service.AmbientService;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/switchambient")
@Scope("prototype")
public class AmbientController {
	
	private final static Logger LOG = Logger.getLogger(AmbientController.class);
	
	@Autowired
	private AmbientService ambientService;
	
	
	@RequestMapping(value="/ambient.htm", method = RequestMethod.GET)
	public ModelAndView showAmbient(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Switcham> list = ambientService.getProAmbient(db);
		if(null!=list&&list.size()>0){
			model.getModelMap().addAttribute("amlist",list);
			model.getModelMap().addAttribute("flag",true);
			model.setViewName("ambient/ambientList");
		}else{
			model.getModelMap().addAttribute("flag",false);
			model.setViewName("ambient/addAmbient");
		}
		return model;
	}
	
	@RequestMapping(value="/addAmbient.htm", method = RequestMethod.GET)
	public ModelAndView addAmbient(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		model.getModelMap().addAttribute("flag",true);
		model.setViewName("ambient/addAmbient");
		return model;
	}
	
	@RequestMapping(value="/append.htm", method = RequestMethod.POST)
	public void add(Switcham ambient,HttpServletRequest req,HttpServletResponse res){
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", ambientService.addAmbient(ambient, db));
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]用户添加环境"+db.getId()+":"+ambient.getAmdesc()+" --- "+ambient.getAmurl());
		++ServerCount.apiCount;
	}
	
	@RequestMapping(value="/edit.htm", method = RequestMethod.POST)
	public void update(Switcham ambient,HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", ambientService.updateAmbient(ambient));
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]更新环境:"+ambient.getAmdesc()+" --- "+ambient.getAmurl());
		++ServerCount.apiCount;
	}
	
	@RequestMapping(value="/remove.htm", method = RequestMethod.GET)
	public ModelAndView delete(int id,HttpServletRequest req,HttpServletResponse res){
		ModelAndView model = new ModelAndView();
		model.getModelMap().put("msg", ambientService.delAmbient(id));
		LOG.info("["+req.getSession().getAttribute("nickname")+"]删除环境["+id+"]");
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		List<Switcham> list = ambientService.getProAmbient(db);
		if(null!=list&&list.size()>0){
			model.getModelMap().addAttribute("amlist",list);
			model.getModelMap().addAttribute("flag",true);
			model.setViewName("ambient/ambientList");
		}else{
			model.getModelMap().addAttribute("flag",false);
			model.setViewName("ambient/addAmbient");
		}
		++ServerCount.apiCount;
		return model;
	}
	
	
	@RequestMapping(value="/getam.htm", method = RequestMethod.GET)
	public ModelAndView selam(int id,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Switcham ambient = ambientService.getAmbient(id);
		model.getModelMap().put("ambient", ambient);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]获取环境["+id+"]");
		++ServerCount.apiCount;
		model.setViewName("ambient/editAmbient");
		return model;
	}
	
}
