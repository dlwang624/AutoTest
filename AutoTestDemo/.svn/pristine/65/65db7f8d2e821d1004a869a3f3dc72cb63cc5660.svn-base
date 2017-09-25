package org.czy.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;

public class PrintWriter {
	
	private final static Logger LOG = Logger.getLogger(PrintWriter.class);
	
	public void ajaxResponse(HttpServletResponse res,JSONObject jsonobj){
		res.setContentType("text/html"); 
	    res.setContentType("text/plain; charset=utf-8");
	    try {
			res.getWriter().print(jsonobj.toString());
			res.getWriter().flush();
		    res.getWriter().close();
			// TODO Auto-generated catch block
	    } catch (IOException e) {
			LOG.error("ajax输出error"+e);
		}
	}
	
	public void ajaxResponse(HttpServletResponse res,List<?> list){
		Gson gson = new Gson();
		String jsonstr = gson.toJson(list);
		res.setContentType("text/html"); 
	    res.setContentType("text/plain; charset=utf-8");
	    try {
			res.getWriter().print(jsonstr);
			res.getWriter().flush();
		    res.getWriter().close();
			// TODO Auto-generated catch block
	    } catch (IOException e) {
			LOG.error("ajax输出error"+e);
		}
	}
	
	
	
}
