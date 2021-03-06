package org.czy.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.czy.entity.Qcdb;
import org.czy.entity.Test;
import org.czy.entity.TestCount;
import org.czy.service.ProjectService;
import org.czy.service.QcdbService;
import org.czy.service.ServerCountService;
import org.czy.service.TestService;
import org.czy.util.Final;
import org.czy.util.Ftp;
import org.czy.util.GetFtpData;
import org.czy.util.Path;
import org.czy.util.PrintWriter;
import org.czy.util.ServerCount;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/count")
@Scope("prototype")
public class CountController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ServerCountService serverCountService;
	
	@Autowired
	private QcdbService qcdbService;

	private final static Logger LOG = Logger.getLogger(CountController.class);
	
	@RequestMapping(value="/main.htm", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		Qcdb db = (Qcdb)req.getSession().getAttribute("QCDB");
		model.getModelMap().put("userList",testService.getUserCount());
		model.getModelMap().put("list", projectService.getProCount(db));
		model.setViewName("count/countList");
		LOG.info("["+req.getSession().getAttribute("nickname")+"]访问了统计用例主画面");
		return model;
	}
	
	@RequestMapping(value="/desc.htm", method = RequestMethod.GET)
	public ModelAndView desc(int id,String proname,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		List<Test> list = testService.getTestByProjectIds(id);
		model.getModelMap().put("list", list.size()==0?null:list);
		model.getModelMap().put("proname", proname);
		model.setViewName("count/descList");
		LOG.info("["+req.getSession().getAttribute("nickname")+"]访问了统计用例详细画面");
		return model;
	}
	
	@RequestMapping(value="/userdesc.htm", method = RequestMethod.GET)
	public ModelAndView userdesc(int uid,String name,HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		List<TestCount> list = testService.selectByUID(uid);
		model.getModelMap().put("list", list.size()==0?null:list);
		model.getModelMap().put("name", name);
		model.setViewName("count/userDescList");
		LOG.info("["+req.getSession().getAttribute("nickname")+"]访问了["+name+"]的用例统计信息");
		return model;
	}
	
	@RequestMapping(value="/servercount.htm", method = RequestMethod.GET)
	public void servercount(HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj1 = new JSONObject();
		JSONObject jsonobj2 = new JSONObject();
		jsonobj1.put("active", ServerCount.activeProject);
		org.czy.entity.ServerCount serverCount = new org.czy.entity.ServerCount();
		serverCount.setCounttime(new Date());
		serverCount.setPpcount(ServerCount.ppCount.toString());
		serverCount.setApicount(ServerCount.apiCount);
		serverCount.setAddtest(ServerCount.addTest);
		serverCount.setUptool(ServerCount.upTool);
		serverCount.setRuntest(ServerCount.runTest);
		serverCount.setUsetool(ServerCount.useTool);
		serverCount.setActiveproject(jsonobj1.toString());
		serverCount.setUpdatetest(ServerCount.updateTest.toString());
		serverCount.setRegresscount(ServerCount.regressCount);
		serverCount.setReleasecount(ServerCount.releaseCount);
		jsonobj2.put("msg", serverCountService.addServerCount(serverCount)?"服务器数据统计成功":"服务器数据统计失败");
		new PrintWriter().ajaxResponse(res, jsonobj2);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]执行了服务器统计功能");
	}
	
	@RequestMapping(value="/testreport.htm", method = RequestMethod.GET)
	public ModelAndView sumreport(){
		ModelAndView model = new ModelAndView();
		List<Qcdb> list = qcdbService.getAll();
		model.getModelMap().addAttribute("list",list);
		model.setViewName("count/sumreport");
		return model;
	}
	
	@RequestMapping(value="/newreport.htm", method = RequestMethod.POST)
	public void report(int qid,String folderID,String foldername,HttpServletRequest req,HttpServletResponse res){
		int fid = -1;
		if(!folderID.equals("")){
			fid = Integer.valueOf(folderID);
		}
		String path = projectService.exportWord(req.getSession().getAttribute("uid").toString(),qid, fid,foldername);
		JSONObject jsonobj = new JSONObject();
		if(path==""||null==path){
			jsonobj.put("filename", "");
		}else{
			jsonobj.put("filename", path.substring(path.lastIndexOf(File.separator)+1));
		}
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]生成了reportword["+path+"]");
		++ServerCount.apiCount;
	}
	
	
	@RequestMapping(value="/downreport.htm", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getFile(String filename,HttpServletRequest req) throws IOException {
		String path = Path.getProjectPath(Final.PROJECTNAME)+File.separator+Final.WORDPATH+File.separator+filename;
		File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String dfileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
    	return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
	
	
	@RequestMapping(value="/echartsreport.htm", method = RequestMethod.POST)
	public void servercount(int qid,String folderID,String foldername,String redmineID,HttpServletRequest req,HttpServletResponse res){
		int fid = -1;
		if(!folderID.equals("")){
			fid = Integer.valueOf(folderID);
		}
		JSONObject jsonobj = projectService.chartsReporter(qid, fid, foldername,redmineID);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]执行了生成了bug统计图");
	}
	
	@RequestMapping(value="/correct.htm", method = RequestMethod.POST)
	public void correct(int qid,String folderID,String foldername,HttpServletRequest req,HttpServletResponse res){
		JSONObject jsonobj = new JSONObject();
		int fid = -1;
		if(!folderID.equals("")){
			fid = Integer.valueOf(folderID);
		}
		String msg = projectService.correctTestStatus(qid, fid, foldername)?"批改成功":"批改失败";
		jsonobj.put("msg", msg);
		new PrintWriter().ajaxResponse(res, jsonobj);
		LOG.info("["+req.getSession().getAttribute("nickname")+"]批改了["+foldername+"]下所有用例的状态:"+msg);
	}
	
}
