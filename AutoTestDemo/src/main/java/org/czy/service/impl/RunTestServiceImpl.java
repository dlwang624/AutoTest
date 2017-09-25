package org.czy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.czy.dao.DescriptionMapper;
import org.czy.dao.ProjectMapper;
import org.czy.dao.TestMapper;
import org.czy.entity.Description;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.SyncTest;
import org.czy.entity.Test;
import org.czy.service.RunTestService;
import org.czy.util.Final;
import org.czy.util.ReaderExcel;
import org.czy.websocket.WebsocketHandler;
import org.springframework.stereotype.Service;
import org.testng.Reporter;

import com.framework.MainAction;
import com.framework.NewImpAction;
import com.test.RunTest;
import com.utils.tools.Resolveyongli;
import com.utils.tools.Tool;

@Service("runTestService")
public class RunTestServiceImpl implements RunTestService {
	
	private final static Logger LOG = Logger.getLogger(RunTestServiceImpl.class);

	@Resource
	private DescriptionMapper descriptionMapper;
	
	@Resource
	private TestMapper testMapper;
	
	@Resource
	private ProjectMapper projectMapper;
	
	
	public void shareTestRun(MainAction action,Qcdb db){
		String fileID = action.getContent();
		String testName = action.getTestName();
		String uid = action.getUid();
		Map<String, List<String>> map = action.getMap();
		
		Project pro = projectMapper.selectByPID(fileID);
		Test t = testMapper.selectByTestName(testName,pro.getId());
		List<Description> list = descriptionMapper.selectByTestID(t.getId());
		Map<String,Description> desc = new HashMap<String,Description>();
		Map<String,Description> resultDesc = new HashMap<>();
		for (Description description : list) {
			desc.put(description.getName(), description);
			if(description.getResultname()!=null&&!description.getResultname().equals("")){
				resultDesc.put(description.getResultname(), description);
			}
		}
		NewImpAction.SleepCount = 20; //最大等待秒数
		NewImpAction newAction = new NewImpAction();
		//qc库公共用例map
		Map<Integer,SyncTest> qcMap = Tool.getTestAll(testName, db, fileID);
		if(null==qcMap||!(qcMap.size()>0)){
			return;
		}
		RunTest.logCon = "-----------------公共用例["+testName+"]测试开始------------------";
		WebsocketHandler.sendMessageToUser(uid, RunTest.logCon);
		Reporter.log(RunTest.logCon);
		RunTest.inOutFlag = true;
		for (int key : qcMap.keySet()) {
			RunTest.successFlag = true;
			SyncTest test = qcMap.get(key);
			String step = String.valueOf(test.getStep());
			String desc0 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 0);
			String desc1 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 1);
			String desc2 = Resolveyongli.DescriptionDetail(test.getName(), test.getStep(), 2);
			
			String type="",xpath="";
			if(!desc0.equals(Final.ACTION2)){
				type = ((Description) desc.get(desc2)).getType(); // 对应数据库type字段
				xpath = ((Description) desc.get(desc2)).getDescription(); // 对应数据库xpath字段
			}else{
				testName = desc2;
			}
			MainAction shareAction = new MainAction();
			
			String result = Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 1);
			String result2 =  Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 2);
			if(!result.equals("")){
				String reslt2 = Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 2);
				shareAction.setResultpath(resultDesc.get(reslt2).getResultdescription());
				shareAction.setResultType(resultDesc.get(reslt2).getResulttype());
				List<String> l = new ArrayList<String>();
				l.add(result);
				shareAction.setResultDescContent(l);
			}
			
			if(null!=map&&map.size()>0){
				//如果使用共用用例,传达数据
				if(desc0.equals(Final.ACTION2)){
					RunTest.baseStep += test.getStep()+"-";
					shareAction.setMap(ReaderExcel.getPubTestData(RunTest.map,RunTest.baseStep));
				}
				
				if(desc1.substring(0,1).equals(RunTest.paramFlag)){
					desc1 = map.get(step).get(1);
				}
				
				if(!result.equals("")&&result.substring(0,1).equals(RunTest.paramFlag)){
					List<String> cols = map.get(step);
					if(cols.size()>2){
						cols.remove(0);
					}
					cols.remove(0);
					shareAction.setResultDescContent(cols);
					
				}else if( !result.equals("") && ! result.substring(0,1).equals(RunTest.paramFlag) ){
					List<String> cols = new ArrayList<String>();
					
					cols.add(result);
					cols.add(result2);
					shareAction.setResultDescContent(cols);
					
				}
			}
			if(desc0.equals(Final.ACTION3)){
				if (desc1.equals(Final.ACTION3VAL)) {
					shareAction.setIframeName("default");
				}else{
					shareAction.setIframeName(desc1);
				}
				
			}
			shareAction.setTestName(testName);
			shareAction.setFlag(desc0);
			shareAction.setContent(desc1);
			shareAction.setType(type);
			shareAction.setPath(xpath);
			shareAction.setIp(action.getIp());
			shareAction.setDescContent(test.getName());
			shareAction.setStep(test.getStep());
			shareAction.setResultDesc(resultDesc);
			shareAction.setDesc(desc);
//			shareAction.setIframeName(iframeName);
			RunTest.logCon = "   步骤: " + test.getStep() + "描述内容: " + desc1 + "预期内容 : "
					+ Resolveyongli.ExpectedDetail(test.getResultname(), test.getStep(), 1);
			String msg = newAction.mainAction(shareAction,db);
			if(RunTest.successFlag){
				RunTest.logCon += " 成功完成";
				WebsocketHandler.sendMessageToUser(uid, RunTest.logCon);
				Reporter.log(RunTest.logCon);
			}
			if(null!=msg&&""!=msg){
				WebsocketHandler.sendMessageToUser(uid, msg);
				Reporter.log(msg);
			}
		}
		RunTest.logCon = "-----------------公共用例["+testName+"]测试结束------------------";
		WebsocketHandler.sendMessageToUser(uid, RunTest.logCon);
		Reporter.log(RunTest.logCon);
		RunTest.inOutFlag = false;
	}
}
