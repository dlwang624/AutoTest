package org.czy.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.czy.entity.ReporterParams;

/**
 * 
 * @author dengpeng
 *
 */
public class TestEditor {
	
	private final static Logger LOG = Logger.getLogger(TestEditor.class);

	public static boolean checkHTMLPassed(String path){
		boolean flag = false;
        try {
            String tempStr = "";
            FileInputStream is = new FileInputStream(path);//读取模块文件
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while ((tempStr = br.readLine()) != null){
            	flag = matchPassed(tempStr, "span", "class","successIndicator",Final.REPORTPASSTITLE);
            	if(flag){
            		break;
            	}
            }
            is.close();
        } catch (IOException e) {
            LOG.error(e);
            return false;
        }
		return flag;
	}
	
	public static void outputIndexHTML(ReporterParams param){
		
	}
	
//	/**
//	 * 获取指定HTML标签的指定属性的值
//	 * @param source 要匹配的源文本
//	 * @param element 标签名称
//	 * @param attr 标签的属性名称
//	 * @return 属性值列表
//	 */
//	public static List<String> match(String source, String element, String attr,String attrVal) {
//		List<String> result = new ArrayList<String>();
//		String reg = "";
//		if(null!=attrVal&&attrVal!=""){
//			reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?"+attrVal+"['\"]?(\\s.*?)?>";
//			Matcher m = Pattern.compile(reg).matcher(source);
//			boolean flag = Pattern.matches(reg, source);
//			while (m.find()) {
//				String r = m.group(1);
//				r = r.split("=")[1].replace("'", "");
//				result.add(r);
//			}
//		}else{
//			reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
//			Matcher m = Pattern.compile(reg).matcher(source);
//			boolean flag = Pattern.matches(reg, source);
//			while (m.find()) {
//				String r = m.group(1);
//				result.add(r);
//			}
//		}
//		return result;
//	}
	
	/**
	 * 获取指定HTML标签的指定属性的值
	 * @param source 要匹配的源文本
	 * @param element 标签名称
	 * @param attr 标签的属性名称
	 * @return 属性值列表
	 */
	private static boolean matchPassed(String source, String element, String attr,String attrVal,String titleVal) {
		String reg = "";
		source = source.trim();
		reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?"+attrVal+"['\"]?(\\s.*?)?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		String title = "";
		while (m.find()) {
			String r = m.group(1);
			r = r.split("=")[1];
			title = r.substring(1,r.length()-1);
		}
		return title.equals(titleVal);
	}
	
	


    public static String relpaceIMGSRC(File file,int id){
    	String content = "";
    	try {
            String tempStr = "";
            FileInputStream is = new FileInputStream(file);//读取模块文件
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while ((tempStr = br.readLine()) != null){
            	content += tempStr;
            }
            is.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    	
		String relpaceVar = "";
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);
                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    String temp = str_src.substring(str_src.lastIndexOf("/"));
                    if(temp.indexOf("\\")>-1){
                    	relpaceVar = str_src.substring(0,str_src.lastIndexOf("\\")+1);
                    }else{
                    	relpaceVar = str_src.substring(0,str_src.lastIndexOf("/")+1);
                    }
                    break;
                }
                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        content = content.replace(relpaceVar, Final.PROJECTDATASOURCEHTTP+Final.WORDHTML+"/"+id+"/");
        return content;
    }


	
	
	 public static boolean outReporters(String fileBasePath,String HtmlBasePath,List<ReporterParams> params) {
		 //生成suites.html
         String str = "";
         try {
        	 String tempStr = "";
        	 FileInputStream is = new FileInputStream(fileBasePath+File.separator+Final.SUITES);//读取模块文件
        	 InputStreamReader isr =  new InputStreamReader(is,"UTF-8");
             BufferedReader br = new BufferedReader(isr);
             while ((tempStr = br.readLine()) != null){
            	 str += tempStr;
             }
             is.close();
         } catch (IOException e) {
             LOG.error(e);
             return false;
         }
         try {
        	String trs = "";
			for (ReporterParams rep : params) {
				trs += "<tr><td class='test'>";
				if(rep.getPassed()){
					trs += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
				}else{
					trs += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
				}
				trs += "<a href='"+rep.getUrl()+"' target='main'>"+rep.getTestname()+"</a>";
				trs += "</td></tr>";
			}
			str = str.replaceAll(Final.REPORTPROJECT,params.get(0).getProjectname());
			str = str.replaceAll(Final.REPORTTRS,trs);//替换掉模块中相应的地方
			File f = new File(HtmlBasePath+File.separator+"suites.html");
			BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			o.write(str);
			o.close();
        } catch (IOException e) {
             LOG.error(e);
             return false;
        }
         
         
       //生成index.htm
        str = "";
        try {
        	 String tempStr = "";
        	 FileInputStream is = new FileInputStream(fileBasePath+File.separator+Final.INDEX);//读取模块文件
             BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
             while ((tempStr = br.readLine()) != null){
            	 str += tempStr;
             }
             is.close();
         } catch (IOException e) {
             LOG.error(e);
             return false;
         }
         try {
			str = str.replaceAll(Final.REPORTTESTID,String.valueOf(params.get(0).getTestid()));
			File f = new File(HtmlBasePath+File.separator+"index.html");
			BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
			o.write(str);
			o.close();
        } catch (IOException e) {
             LOG.error(e);
             return false;
        }
        return true;
 }

	
	 public static boolean outDataReporters(String fileBasePath,String HtmlBasePath,List<ReporterParams> params) {
		 //生成suites.html
         String str = "";
         try {
        	 String tempStr = "";
        	 FileInputStream is = new FileInputStream(fileBasePath+File.separator+Final.SUITESDATA);//读取模块文件
        	 InputStreamReader isr =  new InputStreamReader(is,"UTF-8");
             BufferedReader br = new BufferedReader(isr);
             while ((tempStr = br.readLine()) != null){
            	 str += tempStr;
             }
             is.close();
         } catch (IOException e) {
             LOG.error(e);
             return false;
         }
         try {
        	String trs = "";
        	String tbody = "";
        	boolean flag = true;
        	int j = 1;
        	for (int i = 0; i < params.size(); i++) {
        		ReporterParams rep = params.get(i);
        		//判断是否是参数化类型
        		if(rep.getFlag()){
        			trs += "<tr><td class='test'>";
    				if(rep.getPassed()){
    					trs += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
    				}else{
    					trs += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
    				}
    				trs += "<a href='"+rep.getUrl()+"' target='main'>"+rep.getTestname()+"</a>";
    				trs += "</td></tr>";
        		}else{
        			//只进入一次
        			if(flag){
        				tbody += "<thead><tr>"
    						+ "<th class='header suite' onclick=\"toggleElement('tests-"+(i+1)+"', 'table-row-group'); toggle('toggle-"+(i+1)+"')\">"
        				    + "<span id='toggle-"+(i+1)+"' class='toggle'>&#x25bc;</span>"+rep.getTestname()
        		    		+ "</th>"
        		    		+ "</tr>"
        		    		+ "</thead>"
        					+ "<tbody id='tests-"+(i+1)+"' class='tests'>";
	        				tbody += "<tr><td class='test'>";
	        				if(rep.getPassed()){
	        					tbody += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
	        				}else{
	        					tbody += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
	        				}
	        				tbody += "<a href='"+rep.getUrl()+"' target='main'>第("+j+")页数据</a>";
	        				tbody += "</td></tr>";
	        				++j;
        				flag = false;
        				continue;
        			}
        			//循环最后一次加回标签
        			if(i==(params.size()-1)){
        				if(rep.getTestname().equals(params.get((i-1)).getTestname())){
            				tbody += "<tr><td class='test'>";
            				if(rep.getPassed()){
            					tbody += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
            				}else{
            					tbody += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
            				}
            				tbody += "<a href='"+rep.getUrl()+"' target='main'>第("+j+")页数据</a>";
            				tbody += "</td></tr>";
            				tbody += "</tbody>";
            			}else{
            				tbody += "<thead><tr>"
            						+ "<th class='header suite' onclick=\"toggleElement('tests-"+(i+1)+"', 'table-row-group'); toggle('toggle-"+(i+1)+"')\">"
                				    + "<span id='toggle-"+(i+1)+"' class='toggle'>&#x25bc;</span>"+rep.getTestname()
                		    		+ "</th>"
                		    		+ "</tr>"
                		    		+ "</thead>"
                					+ "<tbody id='tests-"+(i+1)+"' class='tests'>";
            				tbody += "<tr><td class='test'>";
            				if(rep.getPassed()){
            					tbody += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
            				}else{
            					tbody += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
            				}
            				tbody += "<a href='"+rep.getUrl()+"' target='main'>第(1)页数据</a>";
            				tbody += "</td></tr>";
            				tbody += "</tbody>";
            			}
        				break;
        			}else{
        				//判断当前用例名是否和上次一致
            			if(rep.getTestname().equals(params.get((i-1)).getTestname())){
            				tbody += "<tr><td class='test'>";
            				if(rep.getPassed()){
            					tbody += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
            				}else{
            					tbody += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
            				}
            				tbody += "<a href='"+rep.getUrl()+"' target='main'>第("+j+")页数据</a>";
            				tbody += "</td></tr>";
            				++j;
            			}else{
            				j=1;
            				tbody += "</tbody>";
            				tbody += "<thead><tr>"
                				    + "<th class='header suite' onclick=\"toggleElement('tests-"+(i+1)+"', 'table-row-group'); toggle('toggle-"+(i+1)+"')\">"
                				    + "<span id='toggle-"+(i+1)+"' class='toggle'>&#x25bc;</span>"+rep.getTestname()
                		    		+ "</th>"
                		    		+ "</tr>"
                		    		+ "</thead>"
                					+ "<tbody id='tests-"+(i+1)+"' class='tests'>";
                			tbody += "<tr><td class='test'>";
            				if(rep.getPassed()){
            					tbody += "<span class='successIndicator' title='All tests passed.'>&#x2714;</span>";
            				}else{
            					tbody += "<span class='failureIndicator' title='Some tests failed.'>&#x2718;</span>";
            				}
            				tbody += "<a href='"+rep.getUrl()+"' target='main'>第("+j+")页数据</a>";
            				tbody += "</td></tr>";
            				++j;
            			}
        			}
        		}
			}
			str = str.replaceAll(Final.REPORTPROJECT,params.get(0).getProjectname());
			str = str.replaceAll(Final.REPORTTRS,trs);//替换掉模块中相应的地方
			str = str.replaceAll(Final.REPORTBODY,tbody);
			File f = new File(HtmlBasePath+File.separator+"suites.html");
			BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			o.write(str);
			o.close();
        } catch (IOException e) {
             LOG.error(e);
             return false;
        }
         
         
       //生成index.htm
        str = "";
        try {
        	 String tempStr = "";
        	 FileInputStream is = new FileInputStream(fileBasePath+File.separator+Final.INDEX);//读取模块文件
             BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
             while ((tempStr = br.readLine()) != null){
            	 str += tempStr;
             }
             is.close();
         } catch (IOException e) {
             LOG.error(e);
             return false;
         }
         try {
			str = str.replaceAll(Final.REPORTTESTID,params.get(0).getUrl());
			File f = new File(HtmlBasePath+File.separator+"index.html");
			BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
			o.write(str);
			o.close();
        } catch (IOException e) {
             LOG.error(e);
             return false;
        }
        return true;
 }
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String source = "<a title=中国体育报 href=''>aaa</a><a title='北京日报' href=''>bbb</a>";
		source = "<span class='successIndicator' title='用例全部通过.'>&#x2714;</span>";
//		List<String> list = match(source, "span", "class","successIndicator");
//		System.out.println(matchPassed(source, "span", "class","successIndicator",Final.REPORTPASSTITLE));
		System.out.println(checkHTMLPassed("E:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\AutoTestDemo\\output\\QCDB3\\batchReport\\20170613\\7\\84\\html\\suites.html"));
	}
}
