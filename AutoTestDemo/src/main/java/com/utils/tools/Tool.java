package com.utils.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.SyncTest;
import org.czy.entity.Test;
import org.czy.entity.TestCopyModel;

import com.utils.db.DB2Factory;

public class Tool {

	private static ConfigUtil configUtil = new ConfigUtil("config.properties");
	public static String stg = configUtil.getProperty("env");

	public static MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap<>();
	public static List<String> result = null;

	/**
	 * 读取配置文件
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return configUtil.getProperty(name);
	}

	/**
	 * 获取用例步骤内容
	 * 
	 * @param yongliname
	 *            用例名
	 * @param buzhou
	 *            步骤
	 * @param i
	 *            0为:description。1为:EXPECTED
	 * @return
	 */
	public static String getyongli(String yongliname, String buzhou, int i, Qcdb db ,String id) {

		try {
			Connection conn = null;
			List<String> str = null;
			if (!stringMultiValueMap.isEmpty()) {
				str = stringMultiValueMap.getValues(buzhou);
			}
			if (str == null) {
				try {
					// System.out.println("****************************************************************");
					conn = new DB2Factory(db).getDb();
					String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME = "+ "'" + yongliname + "' and t.TS_SUBJECT = " + id;
					PreparedStatement prep = conn.prepareStatement(sql);
					prep.executeBatch();
					ResultSet rs = prep.executeQuery();
					while (rs.next()) {
						stringMultiValueMap.add(rs.getString("DS_STEP_NAME"), rs.getString("DS_DESCRIPTION"));
						stringMultiValueMap.add(rs.getString("DS_STEP_NAME"), rs.getString("DS_EXPECTED"));
					}
					result = stringMultiValueMap.getValues(buzhou);
//					System.out.println(result);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				} finally {

					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// System.out.println("------------------------------------------------------");
				// 直接返回数据
				// System.out.println("多次搜索 : " + result);
				return str == null ? null : str.get(i);
			}
		} catch (Exception e) {
			Tool.stringMultiValueMap.clear();
			Tool.result = null;
		}

		return result == null ? null : result.get(i);

	}

	/**
	 * 查找用例步骤总数及用例名字ID
	 * 
	 * @param yongliName
	 *            用例名字
	 * @param i
	 *            1:用例总步骤数、0:用例名字对应的ID
	 * @return
	 */
	public static String getyongliCount(String yongliName, int i, Qcdb db,String proid) {

		Connection conn;
		String result = "";
		MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap<>();
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT *FROM td.TEST where TS_NAME= " + "'" + yongliName + "' and TS_SUBJECT = "+proid;

			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				stringMultiValueMap.add(rs.getString("TS_NAME"), rs.getString("TS_TEST_ID"));
				stringMultiValueMap.add(rs.getString("TS_NAME"), rs.getString("TS_STEPS"));
			}
			result = stringMultiValueMap.getValue(yongliName, i);
			// System.out.println(result);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			Tool.stringMultiValueMap.clear();
			Tool.result = null;
		}
		return result;
	}
	
	
	public static boolean checkQCFolder(Qcdb db,String proid){
		Connection conn;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT * FROM td.ALL_LISTS a where a.AL_ITEM_ID = "+proid;

			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			String subjectid = "";
			while (rs.next()) {
				subjectid = rs.getString("AL_ITEM_ID");
			}
			if(subjectid.equals(proid)){
				return true;
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public static List<Project> getWordFolderIDByName(Qcdb db,String folderName){
		List<Project> list = new ArrayList<Project>();
		if(!(folderName.indexOf("%")>-1)){
			Connection conn;
			try {
				conn = new DB2Factory(db).getDb();
				String sql = "select top 10 * from td.CYCL_FOLD where CF_ITEM_NAME like '"+folderName+"%'";
				PreparedStatement prep = conn.prepareStatement(sql);
				prep.executeBatch();
				ResultSet rs = prep.executeQuery();
				while (rs.next()) {
					Project pro = new Project();
					pro.setProjectid(rs.getString("CF_ITEM_ID"));
					pro.setName(rs.getString("CF_ITEM_NAME"));
					list.add(pro);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public static List<Project> getFolderIDByName(Qcdb db,String folderName){
		List<Project> list = new ArrayList<Project>();
		if(!(folderName.indexOf("%")>-1)){
			Connection conn;
			try {
				conn = new DB2Factory(db).getDb();
				String sql = "SELECT TOP 10 * FROM td.ALL_LISTS a where a.AL_DESCRIPTION like '"+folderName+"%'";
	
				PreparedStatement prep = conn.prepareStatement(sql);
				prep.executeBatch();
				ResultSet rs = prep.executeQuery();
				while (rs.next()) {
					Project pro = new Project();
					pro.setProjectid(rs.getString("AL_ITEM_ID"));
					pro.setName(rs.getString("AL_DESCRIPTION"));
					list.add(pro);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static Map<String,TestCopyModel> checkQCFolderTest(Qcdb db,String proid){
		Map<String,TestCopyModel> map = new HashMap<String,TestCopyModel>();
		Connection conn;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT * FROM td.TEST where TS_SUBJECT = "+proid;

			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				TestCopyModel test = new TestCopyModel();
				test.setProid(rs.getString("TS_SUBJECT"));
				test.setTestname(rs.getString("TS_NAME"));
				test.setTid(rs.getString("TS_TEST_ID"));
				map.put(rs.getString("TS_NAME"),test);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map.size()>0?map:null;
	}
	
	public static String getFolderName(Qcdb db,String proid){
		String folderName = "";
		Connection conn;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT AL_DESCRIPTION FROM td.ALL_LISTS where AL_ITEM_ID ="+proid;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				folderName = rs.getString("AL_DESCRIPTION");
				break;
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return folderName;
	}

	/**
	 * 通过用例ID(TS_TEST_ID) 获取用例步骤内容，
	 * 
	 * @param yongliname
	 * @param i
	 *            对应的步骤
	 * @param db
	 * @return
	 */
	public static String getStepByTestId(String yongliname, int i, Qcdb db) {
		Connection conn;
		String result = "";

		try {
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME="
					+ "'" + yongliname + "'";
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			List<String> list = new ArrayList<String>();
			while (rs.next()) {

				list.add(rs.getString("DS_STEP_ORDER"));
			}
			result = list.get(i);
			// System.out.println(result);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int getTestNameByCount(String yongliName, Qcdb db) {

		Connection conn;
		int count = 0;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT TS_STEPS FROM td.TEST where TS_NAME= " + "'" + yongliName + "'";
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				count = rs.getInt("TS_STEPS");
			}
			System.out.println(count);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * 获取testname和id
	 * @param yongliName
	 * @param db
	 * @return
	 */
	public static List<Test> getTestNameID(String id, Qcdb db) {
		List<Test> list = new ArrayList<Test>();
		Connection conn = null;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT TS_TEST_ID,TS_NAME from td.TEST WHERE  TS_SUBJECT = " + id;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				Test test = new Test();
				test.setTid(rs.getString("TS_TEST_ID"));
				test.setTestname(rs.getString("TS_NAME"));
				list.add(test);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	/**
	 * 获取testname和id
	 * @param yongliName
	 * @param db
	 * @return
	 */
	public static String getTestName(String id, String testname,Qcdb db) {
		String tid = "";
		Connection conn;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT TS_TEST_ID from td.TEST WHERE TS_SUBJECT = " + id +"AND TS_NAME = '"+ testname +"'";
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				tid = rs.getString("TS_TEST_ID");
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return tid;
	}

	/**
	 * 获得用例名字
	 * 
	 * @return
	 */
	public static Map<String, String[]> getyongliname(String proids, Qcdb db) {
		Connection conn = null;
		String result = "";
		Map<String, String[]> testNameList = new HashMap<String, String[]>();
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "SELECT * FROM td.TEST where TS_SUBJECT = " + proids;

			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				result += rs.getString("TS_NAME") + ",";

			}
			testNameList.put(proids, result.split(","));
			rs.close();
			prep.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return testNameList;

	}

	private static String subDesc(String descStr) {
		List<String> description = new ArrayList<String>();
		String desc = "";
		if (null != descStr && !descStr.equals("")) {
			String strdes = descStr.substring(descStr.indexOf("<body>") + 6, descStr.lastIndexOf("</body>"));
			StringTokenizer strdescription = new StringTokenizer(strdes, "^");

			while (strdescription.hasMoreTokens()) {
				description.add(strdescription.nextToken());
			}
			
			desc = description.toString();
			desc = desc.substring(1,desc.length()-1);
			int begin = desc.indexOf("${");
			int end = desc.indexOf("}");
			if(begin>-1&&end>0){
				desc = desc.substring( begin+ 2, end);
			}
		}
		return desc;
	}

	
	private static String subflag(String descStr,int j) {
		String result = "";
		try {
			List<String> description = new ArrayList<String>();
			if (null != descStr && !descStr.equals("")) {
				String str1 = descStr.substring(descStr.indexOf("<body>") + 6, descStr.lastIndexOf("</body>"));
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
		}

		return result;
	}
	
	/**
	 * 获得用例所有列表内容
	 * @param yongliname
	 * @param db
	 * @return
	 */
	public static Map<Integer, SyncTest> getyongliAll(String yongliname, Qcdb db,String projectid) {
		Map<Integer, SyncTest> qcMap = new HashMap<Integer, SyncTest>();
		Connection conn = null;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME = "+ "'" + yongliname + "' and t.TS_SUBJECT = " + projectid;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				SyncTest test = new SyncTest();
				int step = Integer.valueOf(rs.getString("DS_STEP_NAME").substring(3));
				test.setName(subDesc(rs.getString("DS_DESCRIPTION")));
				test.setResultname(subDesc(rs.getString("DS_EXPECTED")));
				test.setDescflag(subflag(rs.getString("DS_DESCRIPTION"),1));
				test.setResultflag(subflag(rs.getString("DS_EXPECTED"),1));
				test.setStep(step);
				qcMap.put(step, test);
			}
			rs.close();
			prep.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

//		for (int i = 1; i < 30; i++) {
//			SyncTest test = qcMap.get(i);
//			if (null != test) {
//				System.out.println(test.getName() + "-------" + test.getResultname() + "--------" + test.getStep());
//			}
//		}
		return qcMap;
	}

	
	
	public static Map<Integer, SyncTest> getTestAllCheck(String yongliname, Qcdb db,String projectid) {
		Map<Integer, SyncTest> qcMap = new HashMap<Integer, SyncTest>();
		Connection conn = null;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME = "+ "'" + yongliname + "' and t.TS_SUBJECT = " + projectid;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				SyncTest test = new SyncTest();
				int step = Integer.valueOf(rs.getString("DS_STEP_NAME").substring(3));
				String desc = rs.getString("DS_DESCRIPTION");
				String result = rs.getString("DS_EXPECTED");
				if(null!=desc&&!desc.equals("")&&desc.indexOf("${")>0){
					test.setName(subDesc(desc));
				}else{
					test.setName(desc);
				}
				if(null!=result&&!result.equals("")&&result.indexOf("${")>0){
					test.setName(subDesc(result));
				}else{
					test.setResultname(result);
				}
				test.setStep(step);
				qcMap.put(step, test);
			}
			rs.close();
			prep.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return qcMap;
	}

	
	/**
	 * 
	 * @param yongliname
	 * @param db
	 * @return
	 */
	public static Map<Integer, SyncTest> getyongliFolderAndName(String yongliname, Qcdb db) {
		Map<Integer, SyncTest> qcMap = new HashMap<Integer, SyncTest>();
		Connection conn = null;
		try {
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME="
					+ "'" + yongliname + "'";
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				SyncTest test = new SyncTest();
				int step = Integer.valueOf(rs.getString("DS_STEP_NAME").substring(3));
				test.setName(subDesc(rs.getString("DS_DESCRIPTION")));
				test.setResultname(subDesc(rs.getString("DS_EXPECTED")));
				test.setStep(step);
				qcMap.put(step, test);
			}
			rs.close();
			prep.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

//		for (int i = 1; i < 30; i++) {
//			SyncTest test = qcMap.get(i);
//			if (null != test) {
//				System.out.println(test.getName() + "-------" + test.getResultname() + "--------" + test.getStep());
//			}
//		}
		return qcMap;
	}

	public static Map<Integer,SyncTest> getTestAll(String yongliname, Qcdb db,String proid) {
		Map<Integer,SyncTest> qcMap = new HashMap<Integer,SyncTest>();
		Connection conn = null;
		try {
			// System.out.println("****************************************************************");
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_NAME = "+ "'" + yongliname + "' and t.TS_SUBJECT = " + proid;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				SyncTest test = new SyncTest();
				int step = Integer.valueOf(rs.getString("DS_STEP_NAME").substring(3));
				test.setName(rs.getString("DS_DESCRIPTION"));
				test.setResultname(rs.getString("DS_EXPECTED"));
				test.setStep(step);
				qcMap.put(step, test);
			}
			rs.close();
			prep.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return qcMap;

	}
	
	public static Map<Integer,SyncTest> getTestAllByID(Qcdb db,String id) {
		Map<Integer,SyncTest> qcMap = new HashMap<Integer,SyncTest>();
		Connection conn = null;
		try {
			// System.out.println("****************************************************************");
			conn = new DB2Factory(db).getDb();
			String sql = "select * from td.DESSTEPS de, td.TEST t where de.DS_TEST_ID = t.TS_TEST_ID and t.TS_TEST_ID = "+ id;
			PreparedStatement prep = conn.prepareStatement(sql);
			prep.executeBatch();
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				SyncTest test = new SyncTest();
				int step = Integer.valueOf(rs.getString("DS_STEP_NAME").substring(3));
				test.setName(rs.getString("DS_DESCRIPTION"));
				test.setResultname(rs.getString("DS_EXPECTED"));
				test.setStep(step);
				qcMap.put(step, test);
			}

			rs.close();
			prep.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return qcMap;

	}
	
	public static void main(String[] args) {
//		getyongliAll("03航班搜索基本流程_中转", null);
//		String buzhou, int i, Qcdb db ,String id
//		getyongli("03 test","步骤 1", 0,null, "3555");
		// Map<String, String[]> testNameList = getyongliname("1064");
		// System.out.println(testNameList.get("1064").toString());
		// String [] a = testNameList.get("1064");
		// for (int i = 0; i < a.length; i++) {
		// System.out.println(a[i]);
		// }
		// getTestNameByCount("02航班搜索基本流程_直飞_无优惠卷提醒");
	}
}
