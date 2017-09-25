package com.utils.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.czy.entity.Qcdb;
import org.czy.entity.SyncTest;

public class Resolveyongli {

	/**
	 * 获得解析后用例步骤内容
	 * 
	 * @param yongliname
	 * @param i
	 *            :用例步骤
	 * @param j
	 *            :description存储的内容如：0、动作；1、内容；2、哪里
	 * @return
	 */
	public static String DescriptionDetail(String s2, int i, int j) {
		String result = "";
		try {
			List<String> description = new ArrayList<String>();
			if (null != s2 && !s2.equals("")) {
				String str1 = s2.substring(s2.indexOf("<body>") + 6, s2.lastIndexOf("</body>"));
				// ^点击^登录按钮^${登录按钮2}^
				StringTokenizer str = new StringTokenizer(str1, "^");

				while (str.hasMoreTokens()) {

					description.add(str.nextToken());
				}
				if (j == 2&&description.size()==3) {
					String desc = description.get(2);
					result = desc.substring(desc.indexOf("${") + 2,desc.indexOf("}"));
				} else {
					if(description.size()==3){
						result = description.get(j);
					}else{
						result = "";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Tool.stringMultiValueMap.clear();
//			Tool.result = null;
		}

		return result;
	}


	/**
	 * 获得解析后用例步骤内容
	 * 
	 * @param yongliname
	 * @param i
	 *            :用例步骤
	 * @param j
	 *            :ExpectedResult存储的内容如：0、动作；1、内容；2、哪里
	 * @return
	 */
	public static String ExpectedDetail(String s1, int i, int j) {
		String result = "";
		List<String> ExpectedResult = new ArrayList<String>();
		if (s1 != null) {

			String str1 = s1.substring(s1.indexOf("<body>") + 6, s1.lastIndexOf("</body>"));
			StringTokenizer str = new StringTokenizer(str1, "^");
			while (str.hasMoreTokens()) {
				ExpectedResult.add(str.nextToken());
			}
			if (j == 2&&ExpectedResult.size()==3) {
				String redesc = ExpectedResult.get(2);
				result = redesc.substring(redesc.indexOf("${") + 2,redesc.indexOf("}"));
			} else {
				if(ExpectedResult.size()==3){
					result = ExpectedResult.get(j);
				}else{
					result = "";
				}
			}

		} else {
//			System.out.println("步骤 : " + i + "阿 哦 ~ 此 步 骤 没 写 预 期 结 果 ，请 忽 略");
		}
		return result;
	}
}
