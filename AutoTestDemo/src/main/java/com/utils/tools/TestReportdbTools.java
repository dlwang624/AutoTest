package com.utils.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.types.resources.comparators.Date;
import org.czy.entity.Project;
import org.czy.entity.Qcdb;
import org.czy.entity.TestSumReport;

import com.utils.db.DB2Factory;

public class TestReportdbTools {

	public TestReportdbTools() {
		map = new HashMap<Integer, List<List<String>>>();
		ts = new TestSumReport();
	}

	private Map<Integer, List<List<String>>> map;

	private TestSumReport ts;

	public Map<Integer, List<List<String>>> getMap() {
		return map;
	}

	public void setMap(Map<Integer, List<List<String>>> map) {
		this.map = map;
	}

	public TestSumReport getTs() {
		return ts;
	}

	public void setTs(TestSumReport ts) {
		this.ts = ts;
	}

	public void getTableData(int id, Qcdb db) throws Exception {
		List<List<String>> rows = map.get(3) != null ? map.get(3) : new ArrayList<List<String>>();
		List<List<String>> rowsPostpondBug = map.get(4) != null ? map.get(4) : new ArrayList<List<String>>();
		;
		Connection conn = null;
		int Bugcount = ts.getFailCount();
		int PostpondBugCount = ts.getFailLeaveOut();
		int passnum = ts.getPassnum(), num = ts.getNum();
		int urgent = ts.getUrgent();
		int veryHigh = ts.getVeryHigh();
		int high = ts.getHigh();
		int medium = ts.getMedium();
		int low = ts.getLow();
		int execTestCount = ts.getExecTestCount();
		List<String> list = new ArrayList<String>();
		// 开发人员
		String sqlUser = "SELECT SF_COLUMN_NAME FROM td.SYSTEM_FIELD WHERE SF_USER_LABEL  LIKE '%开发人员%'";

		// 遗留缺陷详情
		String sqlPostpondBug = "SELECT LN_BUG_ID,BG_SUMMARY,BG_SEVERITY FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN "
				+ "(SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN ( SELECT   f.RN_RUN_ID FROM  ( "
				+ " SELECT       t.TS_NAME    AS n1,  t.TS_STEPS   AS n2,  c.CYID       AS n3,      c.CY         AS n4, T.TS_TEST_ID AS n5    FROM      td.TEST T,     "
				+ " td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM 	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in"
				+ " (select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3   =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID AND  B.BG_STATUS='Postpond'";
		// Bug总数
		String sqlButCount = "SELECT COUNT(0) FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN "
				+ "(SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN ( SELECT   f.RN_RUN_ID FROM  ( "
				+ " SELECT       t.TS_NAME    AS n1,  t.TS_STEPS   AS n2,  c.CYID       AS n3,      c.CY         AS n4, T.TS_TEST_ID AS n5    FROM      td.TEST T,     "
				+ " td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM 	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in"
				+ " (select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3   =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID";
		// 遗留缺陷总数
		String sqlPostpondBugCount = "SELECT COUNT(*) FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN (SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN "
				+ "( SELECT   f.RN_RUN_ID FROM  (  SELECT       t.TS_NAME    AS n1,  t.TS_STEPS   AS n2,  c.CYID       AS n3,      c.CY         AS n4, T.TS_TEST_ID AS n5  "
				+ "  FROM      td.TEST T,      td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in "
				+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3   =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID AND  B.BG_STATUS='Postpond'";
		// 缺陷严重等级占比
		String sqlBugSeverity = "SELECT BG_SEVERITY FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN (SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN"
				+ " ( SELECT   f.RN_RUN_ID FROM (  SELECT       t.TS_NAME    AS n1,  t.TS_STEPS   AS n2,  c.CYID       AS n3,      c.CY         AS n4, T.TS_TEST_ID AS n5  "
				+ "  FROM     td.TEST T,      td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in"
				+ " (select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3   =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID";

		// 测试用例数getexecTestCount
		String sqlexecTestCount = "SELECT e.n1 FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
				+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID ="
				+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID) C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID)e,td.RUN f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID";
		// 测试用例执行通过率
		String sqlsuccessRate = "SELECT t.TS_NAME,t.TS_CREATION_DATE,T.TS_EXEC_STATUS FROM td.TEST T, td.TESTCYCL d,"
				+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID ="
				+ " (select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
				+ ") AND a.CF_ITEM_ID = b.CY_FOLDER_ID) C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID";

		// bug收敛收敛趋势

		// String sqlBugvanished ="";

		try {

			conn = new DB2Factory(db).getDb();

			// 开发人员查询
			PreparedStatement prepuser = conn.prepareStatement(sqlUser);
			prepuser.executeBatch();
			ResultSet rsuser = prepuser.executeQuery();

			List<String> userlisttitle = new ArrayList<String>();
			while (rsuser.next()) {
				userlisttitle.add(rsuser.getString("SF_COLUMN_NAME"));

			}

			String str1 = null;
			for (int i = 0; i < userlisttitle.size(); i++) {
				String strin = userlisttitle.get(i).toString();
				int n = i + 6;
				str1 += "d." + strin + " AS n" + n + ",";
			}

			String str = str1.substring(4);
			String[] sting = str.split(",");
			String t = null;
			for (int i = 0; i < sting.length; i++) {
				int m = i + 6;
				t += "e.n" + m + ",";

			}
			String strl = t.substring(4, t.length() - 1);

			// 测试执行情况
			String sqluses = "SELECT e.n4, e.n1,COUNT(f.RN_RUN_NAME),e.n2,MAX(f.RN_STATUS) As status ,MAX(f.RN_EXECUTION_DATE+f.RN_EXECUTION_TIME) AS datetime ,"
					+ strl + "" + " FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4," + str + " "
					+ "T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID ="
					+ " (select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID = " + id
					+ " ) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = "
					+ "d.TC_CYCLE_ID)e,td.RUN f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID  GROUP BY n4,n1,n2,e.n6 ,"
					+ strl + "  ORDER BY  datetime ASC";

			System.out.println("-------" + sqluses);
			// 测试执行情况
			PreparedStatement prepTableData = conn.prepareStatement(sqluses);
			prepTableData.executeBatch();
			ResultSet rsTableData = prepTableData.executeQuery();
			ResultSetMetaData rsmd = rsTableData.getMetaData();
			int colCount = rsmd.getColumnCount();
			while (rsTableData.next()) {
				List<String> cols = new ArrayList<String>();

				cols.add(rsTableData.getString(1));
				cols.add(rsTableData.getString(2));
				cols.add(rsTableData.getString(3));
				cols.add(rsTableData.getString(4));
				cols.add(rsTableData.getString(5));
				for (int i = 7; i <= colCount; i++) {
					cols.add(rsTableData.getString(i));
				}
				rows.add(cols);
			}

			map.put(3, rows);

			// 遗留缺陷详情
			PreparedStatement prepPostpondBug = conn.prepareStatement(sqlPostpondBug);
			prepPostpondBug.executeBatch();
			ResultSet rsPostpondBug = prepPostpondBug.executeQuery();
			while (rsPostpondBug.next()) {
				List<String> cols = new ArrayList<String>();
				cols.add(rsPostpondBug.getString("LN_BUG_ID"));
				cols.add(rsPostpondBug.getString("BG_SUMMARY"));
				cols.add(rsPostpondBug.getString("BG_SEVERITY"));
				rowsPostpondBug.add(cols);
			}
			map.put(4, rowsPostpondBug);

			// Bug总数
			PreparedStatement prepButCount = conn.prepareStatement(sqlButCount);
			prepButCount.executeBatch();
			ResultSet rsButCount = prepButCount.executeQuery();

			while (rsButCount.next()) {
				Bugcount += rsButCount.getInt(1);
			}
			ts.setFailCount(Bugcount);

			// 遗留缺陷总数
			PreparedStatement prepPostpondBugCount = conn.prepareStatement(sqlPostpondBugCount);
			prepPostpondBugCount.executeBatch();
			ResultSet rsPostpondBugCount = prepPostpondBugCount.executeQuery();

			while (rsPostpondBugCount.next()) {
				PostpondBugCount += rsPostpondBugCount.getInt(1);
			}
			ts.setFailLeaveOut(PostpondBugCount);

			// 缺陷严重等级占比
			PreparedStatement prepBugSeverity = conn.prepareStatement(sqlBugSeverity);
			prepBugSeverity.executeBatch();
			ResultSet rsBugSeverity = prepBugSeverity.executeQuery();

			while (rsBugSeverity.next()) {

				list.add(rsBugSeverity.getString("BG_SEVERITY"));
			}

			for (int j = 0; j < list.size(); j++) {
				if ((list.get(j).toString()).contains("1-Low")) {
					low++;
				} else if ((list.get(j).toString()).contains("2-Medium")) {
					medium++;
				} else if ((list.get(j).toString()).contains("3-High")) {
					high++;
				} else if ((list.get(j).toString()).contains("4-Very High")) {
					veryHigh++;
				} else {
					urgent++;
				}

			}
			ts.setUrgent(urgent);
			ts.setVeryHigh(veryHigh);
			ts.setHigh(high);
			ts.setMedium(medium);
			ts.setLow(low);

			// 测试用例数
			Set<String> list1 = new HashSet<String>();
			PreparedStatement prepTestCount = conn.prepareStatement(sqlexecTestCount);
			prepTestCount.executeBatch();
			ResultSet rsexecTestCount = prepTestCount.executeQuery();

			while (rsexecTestCount.next()) {
				list1.add(rsexecTestCount.getString("n1"));
			}
			execTestCount += list1.size();
			ts.setExecTestCount(execTestCount);

			// 测试用例执行通过率

			List<String> list3 = new ArrayList<String>();
			PreparedStatement prepsuccessRate = conn.prepareStatement(sqlsuccessRate);
			prepsuccessRate.executeBatch();
			ResultSet rssuccessRate = prepsuccessRate.executeQuery();

			while (rssuccessRate.next()) {
				list3.add(rssuccessRate.getString("TS_EXEC_STATUS"));
			}
			for (int i = 0; i < list3.size(); i++) {
				String TS_EXEC_STATUS = list3.get(i).toString();
				if (TS_EXEC_STATUS.equals("Passed")) {
					passnum++;

				}
			}
			num += list3.size();
			ts.setNum(num);
			ts.setPassnum(passnum);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new Exception(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 获取率

	public void getRate() {
		int failcount = ts.getFailCount();
		int failleave = ts.getFailLeaveOut();
		int passnum = ts.getPassnum(), num = ts.getNum();
		int urgent = ts.getUrgent();
		int veryHigh = ts.getVeryHigh();
		int high = ts.getHigh();
		int medium = ts.getMedium();
		int low = ts.getLow();

		float rate1 = 0;
		if (failcount != 0) {
			rate1 = (failcount - failleave) / (float) failcount;
		}
		ts.setFailRate(rate1);

		float rate2 = 0;
		if (urgent + veryHigh + high + low + medium != 0) {
			rate2 = (urgent + veryHigh + high) / (float) (urgent + veryHigh + high + low + medium);
		}
		ts.setRate(rate2);

		float rate3 = 0;
		if (num != 0) {
			rate3 = passnum / (float) num;
		}
		ts.setSuccessRate(rate3);
	}

	// 获得父文件夹
	public List<Project> get_fid(Qcdb db, int id) {
		Connection conn = null;
		List<Project> list = new ArrayList<Project>();
		try {

			conn = new DB2Factory(db).getDb();
			String sql = "select CF_ITEM_ID,CF_ITEM_NAME from td.CYCL_FOLD a where a.CF_FATHER_ID =" + id;
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
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	// bug收敛收敛趋势，更具文件夹ID获取bug
	public static List<List<String>> Bugvanished(Qcdb db, int id) throws Exception {

		Connection conn = null;
		String sqlBugvanished, sqlstart, sqlend = "";
		String sqlchrildid = "with temp ( [CF_ITEM_ID]) as (	SELECT CF_ITEM_ID 	FROM td.CYCL_FOLD "
				+ " WHERE CF_FATHER_ID =" + id + " union all SELECT a.CF_ITEM_ID	"
				+ "FROM td.CYCL_FOLD   a inner join temp on a.CF_FATHER_ID = temp.[CF_ITEM_ID]) select * from temp";
		
		
		List<List<String>> list5 = new ArrayList<>();

		try {
			conn = new DB2Factory(db).getDb();
			PreparedStatement prepchrildid = conn.prepareStatement(sqlchrildid);
			prepchrildid.executeBatch();
			ResultSet rschrildid = prepchrildid.executeQuery();
			List<String> chrildlist = new ArrayList<>();
			String chrilds = "";
			while (rschrildid.next()) {
				chrildlist.add(rschrildid.getString(1));
			}
			for (int i = 0; i < chrildlist.size(); i++) {
				chrilds += chrildlist.get(i) + ",";
			}

			if (null != chrilds && chrilds.length() > 0) {
				sqlBugvanished = "SELECT b.BG_DETECTION_DATE,COUNT(*) AS sumnum  FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN (SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN "
						+ "( SELECT   f.RN_RUN_ID FROM  (SELECT t.TS_NAME AS n1, t.TS_STEPS  AS n2, c.CYID AS n3, c.CY AS n4, T.TS_TEST_ID AS n5    FROM      td.TEST T,     "
						+ "td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM 	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in(select a.CF_ITEM_ID "
						+ " from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id + ","
						+ chrilds.substring(0, chrilds.length() - 1)
						+ ")) AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3  "
						+ " =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID GROUP BY b.BG_DETECTION_DATE";
				sqlstart = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
						+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in ( "
						+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id + ","
						+ chrilds.substring(0, chrilds.length() - 1)
						+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
						+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime ASC";
				sqlend = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS "
						+ "AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
						+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in( "
						+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in ( " + id + ","
						+ chrilds.substring(0, chrilds.length() - 1)
						+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
						+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime desc";

			} else {
				sqlBugvanished = "SELECT b.BG_DETECTION_DATE,COUNT(*) AS sumnum  FROM td.LINK L,td.BUG B WHERE L.LN_ENTITY_TYPE = 'STEP' AND L.LN_ENTITY_ID IN (SELECT T.ST_ID FROM td.STEP T WHERE T.ST_RUN_ID IN "
						+ "( SELECT   f.RN_RUN_ID FROM  (SELECT t.TS_NAME AS n1, t.TS_STEPS  AS n2, c.CYID AS n3, c.CY AS n4, T.TS_TEST_ID AS n5    FROM      td.TEST T,     "
						+ "td.TESTCYCL d,(SELECT b.CY_CYCLE_ID AS CYID, b.CY_CYCLE AS CY  FROM 	 td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in(select a.CF_ITEM_ID "
						+ " from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id
						+ ")) AND a.CF_ITEM_ID = b.CY_FOLDER_ID )C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID) e, td.RUN f WHERE  e.n3  "
						+ " =f.RN_CYCLE_ID AND e.n5 = f.RN_TEST_ID) AND T.ST_STATUS = 'failed') AND L.LN_BUG_ID=B.BG_BUG_ID GROUP BY b.BG_DETECTION_DATE";
				sqlstart = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
						+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in ( "
						+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id
						+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
						+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime ASC";
				sqlend = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS "
						+ "AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
						+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in( "
						+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in ( " + id
						+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
						+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime desc";
			}

			int countNum = 0;
			Map<String, String> map = new HashMap<String, String>();

			PreparedStatement prepBugvanished = conn.prepareStatement(sqlBugvanished);
			prepBugvanished.executeBatch();
			ResultSet rsBugvanished = prepBugvanished.executeQuery();

			while (rsBugvanished.next()) {
				countNum += Integer.valueOf(rsBugvanished.getString(2));
				map.put(rsBugvanished.getString(1).substring(0, 10), countNum + "");
			}

			String startdate = "";
			PreparedStatement prepstart = conn.prepareStatement(sqlstart);
			prepstart.executeBatch();
			ResultSet rsstart = prepstart.executeQuery();

			while (rsstart.next()) {
				startdate = rsstart.getString(1).substring(0, 10);
			}

			String enddate = "";
			PreparedStatement prepend = conn.prepareStatement(sqlend);
			prepend.executeBatch();
			ResultSet rssend = prepend.executeQuery();

			while (rssend.next()) {
				enddate = rssend.getString(1).substring(0, 10);
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = format.parse(startdate);
			java.util.Date date2 = format.parse(enddate);

			int a = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
			String bugRecord = "";
			Calendar now = Calendar.getInstance();
			for (int i = 0; i <= a; i++) {
				List<String> list4 = new ArrayList<String>();
				now.setTime(date1);
				int day = now.get(Calendar.DATE);
				now.set(Calendar.DATE, day + i);
				String key = format.format(now.getTime());
				String bugnum = map.get(key);
				if (null != bugnum && !bugnum.equals("")) {
					list4.add(key);
					list4.add(bugnum);
					bugRecord = bugnum;
				} else {
					if (!bugRecord.equals("")) {
						list4.add(key);
						list4.add(bugRecord);
					} else {
						list4.add(key);
						list4.add(0 + "");
					}
				}
				list5.add(list4);
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list5;
	}
	
	
		// bug收敛收敛趋势,根据ReadminID获取bug总数
		public static List<List<String>> BugvanishedReadminID(Qcdb db, int id,String redmineID) throws Exception {

			Connection conn = null;
			String  sqlstart, sqlend = "";
			String sqlchrildid = "with temp ( [CF_ITEM_ID]) as (	SELECT CF_ITEM_ID 	FROM td.CYCL_FOLD "
					+ " WHERE CF_FATHER_ID =" + id + " union all SELECT a.CF_ITEM_ID	"
					+ "FROM td.CYCL_FOLD   a inner join temp on a.CF_FATHER_ID = temp.[CF_ITEM_ID]) select * from temp";
			
			
			List<List<String>> list5 = new ArrayList<>();

			try {
				conn = new DB2Factory(db).getDb();
				PreparedStatement prepchrildid = conn.prepareStatement(sqlchrildid);
				prepchrildid.executeBatch();
				ResultSet rschrildid = prepchrildid.executeQuery();
				List<String> chrildlist = new ArrayList<>();
				String chrilds = "";
				while (rschrildid.next()) {
					chrildlist.add(rschrildid.getString(1));
				}
				for (int i = 0; i < chrildlist.size(); i++) {
					chrilds += chrildlist.get(i) + ",";
				}

				String sqlBugcountReadmineID = "SELECT BG_DETECTION_DATE,count(*)   FROM td.BUG WHERE BG_USER_04='"+ redmineID +"' and BG_STATUS !='Canceled'"
						+ " GROUP BY BG_DETECTION_DATE ORDER BY BG_DETECTION_DATE";
				if (null != chrilds && chrilds.length() > 0) {
					
					sqlstart = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
							+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in ( "
							+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id + ","
							+ chrilds.substring(0, chrilds.length() - 1)
							+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
							+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime ASC";
					sqlend = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS "
							+ "AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
							+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in( "
							+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in ( " + id + ","
							+ chrilds.substring(0, chrilds.length() - 1)
							+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
							+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime desc";

				} else {
					sqlstart = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
							+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in ( "
							+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in( " + id
							+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
							+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime ASC";
					sqlend = "SELECT top 1 f.RN_EXECUTION_DATE AS datetime  FROM (SELECT t.TS_NAME  AS n1,t.TS_STEPS "
							+ "AS n2,c.CYID AS n3,c.CY AS n4,T.TS_TEST_ID AS n5 FROM td.TEST T, td.TESTCYCL d,"
							+ "( SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE a.CF_ITEM_ID in( "
							+ "(select a.CF_ITEM_ID from td.CYCL_FOLD a where a.CF_ITEM_ID in ( " + id
							+ " ))) AND a.CF_ITEM_ID = b.CY_FOLDER_ID)C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID ="
							+ " d.TC_CYCLE_ID)e,td.RUN  f WHERE e.n3=f.RN_CYCLE_ID and e.n5 = f.RN_TEST_ID    ORDER BY  datetime desc";
				}

				int countNum = 0;
				Map<String, String> map = new HashMap<String, String>();

				PreparedStatement prepBugvanished = conn.prepareStatement(sqlBugcountReadmineID);
				prepBugvanished.executeBatch();
				ResultSet rsBugvanished = prepBugvanished.executeQuery();

				while (rsBugvanished.next()) {
					countNum += Integer.valueOf(rsBugvanished.getString(2));
					map.put(rsBugvanished.getString(1).substring(0, 10), countNum + "");
				}

				String startdate = "";
				PreparedStatement prepstart = conn.prepareStatement(sqlstart);
				prepstart.executeBatch();
				ResultSet rsstart = prepstart.executeQuery();

				while (rsstart.next()) {
					startdate = rsstart.getString(1).substring(0, 10);
				}

				String enddate = "";
				PreparedStatement prepend = conn.prepareStatement(sqlend);
				prepend.executeBatch();
				ResultSet rssend = prepend.executeQuery();

				while (rssend.next()) {
					enddate = rssend.getString(1).substring(0, 10);
				}

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date1 = format.parse(startdate);
				java.util.Date date2 = format.parse(enddate);

				int a = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
				String bugRecord = "";
				Calendar now = Calendar.getInstance();
				for (int i = 0; i <= a; i++) {
					List<String> list4 = new ArrayList<String>();
					now.setTime(date1);
					int day = now.get(Calendar.DATE);
					now.set(Calendar.DATE, day + i);
					String key = format.format(now.getTime());
					String bugnum = map.get(key);
					if (null != bugnum && !bugnum.equals("")) {
						list4.add(key);
						list4.add(bugnum);
						bugRecord = bugnum;
					} else {
						if (!bugRecord.equals("")) {
							list4.add(key);
							list4.add(bugRecord);
						} else {
							list4.add(key);
							list4.add(0 + "");
						}
					}
					list5.add(list4);
				}
			} catch (SQLException e) {
				throw new Exception(e);
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			return list5;
		}

	
	public static int bugStatus(Qcdb db, int id) {
		int status = -1;
		String sqlwhichOne = "SELECT SF_COLUMN_NAME FROM td.SYSTEM_FIELD  WHERE SF_USER_LABEL  LIKE '%是否评审%'";

		Connection conn = null;
		String whichOne = null;

		try {
			conn = new DB2Factory(db).getDb();
			PreparedStatement prepwhichOne = conn.prepareStatement(sqlwhichOne);
			prepwhichOne.executeBatch();
			ResultSet rswhichOne = prepwhichOne.executeQuery();
			while (rswhichOne.next()) {
				whichOne = rswhichOne.getString(1);
			}

			String sqlsatus = "UPDATE td.TESTCYCL SET td.TESTCYCL." + whichOne + "='Reviewed' WHERE TC_TEST_ID in "
					+ "(SELECT TC_TEST_ID FROM td.TEST T, td.TESTCYCL d,( "
					+ "SELECT b.CY_CYCLE_ID AS CYID,b.CY_CYCLE AS CY FROM td.CYCL_FOLD a,td.CYCLE b WHERE "
					+ "a.CF_ITEM_ID =" + id
					+ " AND CF_ITEM_ID = CY_FOLDER_ID) C WHERE d.TC_TEST_ID=t.TS_TEST_ID AND C.CYID = d.TC_CYCLE_ID)";

			PreparedStatement prepsatus = conn.prepareStatement(sqlsatus);
			status = prepsatus.executeUpdate();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return status;
	}

	public static void main(String[] args) {
		TestReportdbTools tr = new TestReportdbTools();
		Qcdb db = new Qcdb();
		db.setIp("jdbc:sqlserver://192.168.0.216:1433");
		db.setDbname("research_td_spring3g_db");
		db.setUsername("testcq");
		db.setPassword("test1234");
		// db.setIp("jdbc:sqlserver://10.131.208.12:1433");
		// db.setDbname("aviation_hcc_db0");
		// db.setUsername("REQ_WRITE");
		// db.setPassword("CZYWQA");
		//
		try {
//			tr.Bugvanished(db, 2903);
//			tr.BugvanishedReadminID(db,2903,"1660");
//			 tr.Bugvanished(db, 133);
			 for (List<String> list : tr.BugvanishedReadminID(db,2903,"1660")) {
			 for (String string : list) {
			 System.out.print(string+"\t");
			 }
			 System.out.println();
			 }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
