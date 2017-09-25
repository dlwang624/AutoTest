package org.czy.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.czy.entity.SCount;
import org.json.JSONObject;

public class ServerCount {

	
	//平台使用人数
	public static Set<String> ppCount = new HashSet<String>();
	//api调用次数
	public static int apiCount = 0;
	//添加用例数
	public static int addTest = 0;
	//用例执行数
	public static int runTest = 0;
	//更新用例数
	public static Set<Integer> updateTest = new HashSet<Integer>();
	//上传工具数
	public static int upTool = 0;
	//工具使用数
	public static int useTool = 0;
	//活跃项目统计
	public static Map<String, Integer> activeProject = new HashMap<String, Integer>();
	
	public static int regressCount = 0;
	
	public static int releaseCount = 0;
	
	public static void loginPro(String proName){
		if(null==activeProject.get(proName)){
			activeProject.put(proName, 1);
		}else{
			activeProject.put(proName,(activeProject.get(proName)+1));
		}
	}
	
	public static void clearAll(){
		ppCount = new HashSet<String>();
		apiCount = 0;
		addTest = 0;
		runTest = 0;
		updateTest = new HashSet<Integer>();
		upTool = 0;
		useTool = 0;
		activeProject = new HashMap<String, Integer>();
		regressCount = 0;
		releaseCount = 0;
	}
	
	public static SCount getServerCount(){
		SCount count = new SCount();
		count.setPpCount(ppCount);
		count.setApiCount(apiCount);
		count.setAddTest(addTest);
		count.setRunTest(runTest);
		count.setUpdateTest(updateTest);
		count.setUpTool(upTool);
		count.setUseTool(useTool);
		count.setActiveProject(activeProject);
		count.setRegressCount(regressCount);
		count.setReleaseCount(releaseCount);
		return count;
	}
	
	private static void write(){
		System.out.println("平台使用人数:"+ppCount.toString());
		System.out.println("api调用次数:"+apiCount);
		System.out.println("添加用例数:"+addTest);
		System.out.println("用例执行数:"+runTest);
		System.out.println("更新用例数:"+updateTest.toString());
		System.out.println("上传工具数:"+upTool);
		System.out.println("工具使用数:"+useTool);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("active", ServerCount.activeProject);
		System.out.println("活跃项目统计:"+jsonobj.toString());
		System.out.println("回归测试使次数:"+regressCount);
		System.out.println("构建后测试次数:"+releaseCount);
	}
	
	public static void main(String[] args) {
		write();
		System.out.println(getServerCount());
	}
	
}
