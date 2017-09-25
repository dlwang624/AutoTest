package org.czy.util;

import java.util.ArrayList;
import java.util.List;

public class DescColumn {

	private static DescColumn descColumn = null;
	private static List<String> list = new ArrayList<String>();
	
	public static DescColumn getInstance(){
		if(descColumn==null){
			descColumn = new DescColumn();
		}
		return descColumn;
	}
	
	private DescColumn(){
//		list.add("描述编号");
		list.add("步骤");
		list.add("定位符类型");
		list.add("实际定位符名");
		list.add("定位符描述");
		list.add("预期定位符描述");
		list.add("预期定位符类型");
		list.add("预期定位符名");
//		list.add("用例编号");
	}
	
	public List<String> getHeadDesc(){
		return list;
	}
	
	
}
