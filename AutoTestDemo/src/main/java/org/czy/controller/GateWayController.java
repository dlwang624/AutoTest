package org.czy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.czy.entity.Qcdb;
import org.czy.service.ExecService;
import org.czy.service.GateWayService;
import org.czy.service.QcdbService;
import org.czy.service.TrainService;
import org.czy.util.ServerCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gateway")
@Scope("prototype")
public class GateWayController {

	private final static Logger LOG = Logger.getLogger(GateWayController.class);
	
	@Autowired
	private TrainService trainService;
	
	@Autowired
	private QcdbService qcdbService;
	
	@Autowired
	private ExecService execService;
	
	@Autowired
	private GateWayService gateWayService;
	
	
	@RequestMapping(value="/index.htm", method = RequestMethod.GET)
	public ModelAndView indexview(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		String path = req.getContextPath();
		model.getModelMap().addAttribute("basePath", path + "/");
		model.getModelMap().addAttribute("userAddress", execService.getAddress(req));
		model.getModelMap().addAttribute("localAddress", req.getLocalAddr());
		List<Qcdb> list = qcdbService.getAllName();
		model.getModelMap().addAttribute("list",list);
		model.getModelMap().addAttribute("gateway",gateWayService.getGateWay());
		model.setViewName("index/index");
		return model;
	}
	
	
}
