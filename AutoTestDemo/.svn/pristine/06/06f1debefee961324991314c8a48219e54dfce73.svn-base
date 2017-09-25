package com.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.czy.entity.Qcdb;
import org.czy.entity.SyncTest;
import org.czy.websocket.WebsocketHandler;

import com.utils.tools.Tool;

public class FormatCheck {
	
	private final static Logger LOG = Logger.getLogger(FormatCheck.class);

	// 正则表达式规则
	private static String regEx = "^\\^[\\s\\S]+\\^[\\s\\S]+\\^\\$\\{[\\s\\S]+\\}\\^$";

	public static void formatcheck(String yongliname,String uid,Qcdb db,String projectid) {
		WebsocketHandler.waitSocketConntion(uid);
		boolean flag = true;
		//判断QC库中是否存在语法错误的描述,此判断在描述是否存在之后
		String log = "";
		Map<Integer, SyncTest> qcMap = Tool.getTestAll(yongliname, db,projectid);
		for (int key : qcMap.keySet()) {
			// 获得用例中步骤内容
			String desc = qcMap.get(key).getName(); 
			String Res = qcMap.get(key).getResultname();
			if (Res == null) {
				if(desc!=null){
					String description = desc.substring(desc.indexOf("<body>") + 6, desc.lastIndexOf("</body>"));
					// 编译正则表达式
					Pattern pattern = Pattern.compile(regEx);
					Matcher matcherDesc = pattern.matcher(description);
					boolean rsDesc = matcherDesc.find();
					if (rsDesc) {
//						log = "步骤 : " + qcMap.get(key).getStep() + "验证通过，符合格式!!!";
					} else {
						log = "@@@步骤 : " + qcMap.get(key).getStep() + "格式错误，请检查!!!";
						WebsocketHandler.sendMessageToUser(uid, log);
						flag = false;
					}
				}else{
					log = "@@@步骤 : " + qcMap.get(key).getStep() + "格式错误，请检查!!!";
					WebsocketHandler.sendMessageToUser(uid, log);
					flag = false;
				}
				
			} else {
				String description = desc.substring(desc.indexOf("<body>") + 6, desc.lastIndexOf("</body>"));
				String Result = Res.substring(Res.indexOf("<body>") + 6, Res.lastIndexOf("</body>"));


				// 编译正则表达式
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcherDesc = pattern.matcher(description);
				Matcher matcherRes = pattern.matcher(Result);


				boolean rsDesc = matcherDesc.find();
				boolean rsRes = matcherRes.find();
				if (rsDesc && rsRes) {
//					log = "步骤 : " + qcMap.get(key).getStep() + "验证通过，符合格式!!!";
				} else {
					log = "@@@步骤 : " + qcMap.get(key).getStep() + "格式错误，请检查!!!";
					WebsocketHandler.sendMessageToUser(uid, log);
					flag = false;
				}
			}
		}
		//map转list
		List<SyncTest> list = new ArrayList<SyncTest>();
		qcMap = Tool.getTestAllCheck(yongliname, db,projectid);
		for (int key : qcMap.keySet()) {
			list.add(qcMap.get(key));
		}
		if (flag) {
			String desclog = "";
			int n, j = 0;
			int length = list.size();
			//判断QC库描述是否有存在重复
			for (n = 0; n < length; n++) {
				desclog = "";
				flag = true;
				for (j = n; j < length; j++) {
					if (n == j) {
						++j;
						if (n != length - 1 && list.get(n).getName().equals(list.get(j).getName())) {
							// System.out.println("第"+n+"步骤与第"+j+"步骤描述是重复的");
							desclog = "@@@第" + list.get(n).getStep() + "步骤与第" + list.get(j).getStep() + "步骤描述是重复的";
							if(flag){
								WebsocketHandler.sendMessageToUser(uid, desclog);
							}
							flag = false;
						}
	
						if(null!=list.get(n).getResultname()){
							if (n != length - 1 && list.get(n).getResultname().equals(list.get(j).getResultname())
									&& !list.get(n).getResultname().equals("")) {
								// System.out.println("第"+n+"步骤与第"+j+"步骤期望是重复的");
								desclog = "@@@第" + list.get(n).getStep() + "步骤与第" + list.get(j).getStep() + "步骤期望是重复的";
								if(flag){
									WebsocketHandler.sendMessageToUser(uid, desclog);
								}
								flag = false;
							}
						}
						
					} else {
						if (list.get(n).getName().equals(list.get(j).getName())) {
							// System.out.println("第"+n+"步骤与第"+j+"步骤描述是重复的");
							desclog = "@@@第" + list.get(n).getStep() + "步骤与第" + list.get(j).getStep() + "步骤描述是重复的";
							WebsocketHandler.sendMessageToUser(uid, desclog);
							flag = false;
						}
						if(null!=list.get(n).getResultname()){
							if (list.get(n).getResultname().equals(list.get(j).getResultname())
									&& !list.get(n).getResultname().equals("")) {
								// System.out.println("第"+n+"步骤与第"+j+"步骤期望是重复的");
								desclog = "@@@第" + list.get(n).getStep() + "步骤与第" + list.get(j).getStep() + "步骤期望是重复的";
								if(flag){
									WebsocketHandler.sendMessageToUser(uid, desclog);
								}
								flag = false;
							}
						}
					}
				}
			}
		}
	}
}
