package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.czy.controller.TestController;
import org.czy.entity.Description;
import org.czy.entity.Qcdb;
import org.czy.entity.SyncTest;
import org.czy.util.Final;
import org.czy.util.ReaderExcel;
import org.czy.websocket.WebsocketHandler;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.check.CheckElement;
import com.framework.MainAction;
import com.framework.NewImpAction;
import com.framework.NewUITestPC;
import com.utils.tools.Resolveyongli;
import com.utils.tools.Tool;

public class RunTest extends NewUITestPC {

	private final static Logger LOG = Logger.getLogger(TestController.class);
	public static boolean inOutFlag = false;
	public static String baseStep = "";
	public static String testid = null;
	public static String uid;
	public static String ip;
	public static Qcdb db;
	public static Map<String, List<String>> map = new HashMap<String, List<String>>();
	public static String yongliname;
	public static String proid;
	public static int num = 0;
	public static int datanum = -1;
	public static String filepath = "";
	public static boolean browserFlag = false;
	public static String url = "";

	// 获取描述和预期结果
	public static Map<String, Description> desc;
	public static Map<String, Description> resultDesc;

	public static boolean successFlag;
	public static boolean FailFlag;
	public static String logCon = "";

	public static final String paramFlag = "@";

	@Test
	public void getRun() throws Exception {
		WebsocketHandler.sendMessageToUser(uid, "用例[" + yongliname + "]执行日志");
		Reporter.clear();
		NewImpAction.SleepCount = 20; // 最大等待秒数
		NewImpAction newAction = new NewImpAction();
		Map<Integer, SyncTest> qcMap = new HashMap<Integer, SyncTest>();
		if (null != testid) {
			qcMap = Tool.getTestAllByID(db, testid);
		} else {
			qcMap = Tool.getTestAll(yongliname, db, proid);
			WebsocketHandler.waitSocketConntion(uid);
		}

		if (null == qcMap || !(qcMap.size() > 0)) {
			return;
		}
		LOG.info(logCon);

		if (datanum == -1) {
			logCon = "-----------------------第[" + num + "]次执行用例开始-----------------------";
		} else {
			logCon = "-----------------------第[" + num + "]次第[" + (datanum + 1) + "]套数据执行用例开始-----------------------";
		}
		WebsocketHandler.sendMessageToUser(uid, logCon);
		Reporter.log(logCon);
		FailFlag = false;
		for (int key : qcMap.keySet()) {
			successFlag = true;
			SyncTest test = qcMap.get(key);
			String step = String.valueOf(test.getStep());
			String desc0 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 0);
			String desc1 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 1);
			String desc2 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 2);

			String type = "", xpath = "";
			if (!desc0.equals(Final.ACTION2)) {
				type = ((Description) desc.get(desc2)).getType(); // 对应数据库type字段
				xpath = ((Description) desc.get(desc2)).getDescription(); // 对应数据库xpath字段
			}

			MainAction action = new MainAction();

			String result = Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 1);
			if (!result.equals("")) {
				String reslt2 = Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 2);
				action.setResultpath(resultDesc.get(reslt2).getResultdescription());
				action.setResultType(resultDesc.get(reslt2).getResulttype());
				List<String> list = new ArrayList<String>();
				list.add(result);
				action.setResultDescContent(list);
			}

			if (null != map && map.size() > 0) {
				// 如果使用共用用例,传达数据
				if (desc0.equals(Final.ACTION2)) {
					baseStep = test.getStep() + "-";
					action.setMap(ReaderExcel.getPubTestData(map, baseStep));
				}

				if (desc1.substring(0, 1).equals(paramFlag)) {
					desc1 = map.get(step).get(1);
				}

				if (!result.equals("") && result.substring(0, 1).equals(paramFlag)) {
					List<String> list = map.get(step);
					if (list.size() > 2) {
						list.remove(0);
					}
					list.remove(0);
					action.setResultDescContent(list);

				} else if (!result.equals("") && !result.substring(0, 1).equals(paramFlag)) {
					List<String> list = new ArrayList<String>();

					list.add(result);
					action.setResultDescContent(list);

				}
			}
			if (desc0.equals(Final.ACTION3)) {
				if (desc1.equals(Final.ACTION3VAL)) {
					action.setIframeName("default");
				} else {
					action.setIframeName(desc1);
				}

			}

			action.setTestName(desc2);
			action.setFlag(desc0);
			action.setContent(desc1);
			action.setType(type);
			action.setPath(xpath);
			action.setIp(ip);
			action.setDescContent(test.getName());
			action.setStep(test.getStep());
			action.setResultDesc(resultDesc);
			action.setDesc(desc);

			action.setUid(uid);

			logCon = "   步骤: " + test.getStep() + "描述内容: " + desc1 + "预期内容 : "
					+ Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 1);
			String msg = newAction.mainAction(action, db);
			if (successFlag && !desc0.equals(Final.ACTION2)) {
				logCon += " 成功完成";
				if (null != msg && "" != msg) {
					logCon += msg;
					// WebsocketHandler.sendMessageToUser(uid, msg);
					// Reporter.log(msg);
				}
				WebsocketHandler.sendMessageToUser(uid, logCon);
				// newAction.phantomshot(logCon , test.getStep());
				Reporter.log(logCon);
				LOG.info(logCon);
			}

		}
		if (datanum == -1) {
			logCon = "-----------------------第[" + num + "]次执行用例结束-----------------------";
		} else {
			logCon = "-----------------------第[" + num + "]次第[" + (datanum + 1) + "]套数据执行用例结束-----------------------";
		}

		WebsocketHandler.sendMessageToUser(uid, logCon);
		Reporter.log(logCon);
//		if(CheckElement.isExistEleOrInfo!=""&&!CheckElement.isExistEleOrInfo.equals("")){
////			throw new Exception(CheckElement.isExistEleOrInfo);
//		}
		newAction.delCookie();
		newAction.quit();
		FailFlag = true;
	}
}
