package org.czy.service;

import javax.servlet.http.HttpServletRequest;

import org.czy.entity.Qcdb;
import org.czy.entity.RecordExec;

public interface ExecService {

	/**
	 * 执行测试用例
	 * @author dengpeng
	 * @param name 需要执行的测试用例名
	 */
	String SelNameExecTest(int swid,String testName,String uid,String ip,Qcdb db,String proid,int num,String dataFilename,String testID,String reporturl,boolean browserFlag);
	
	String execBatchTest(int swid,String testids,Qcdb db,String ip,String uid,String reporturl,boolean emailflag,boolean browserFlag);
	
	/**
	 * 
	 * @param testids
	 * @param db
	 * @param toads
	 * @param ccads
	 * @param reporturl
	 * @param serverip
	 * @param timerID
	 * @param remark
	 * @return 执行情况,0:正常通过	1:排队	2:异常	3:队伍已满
	 */
	int timerBatchTest(int swid,String testids,Qcdb db,String[] toads,String[] ccads,String reporturl,String serverip,int timerID,String remark,boolean releaseCheckFlag,RecordExec record);
	
	/**
	 * 
	 * @author dengpeng
	 * @param req request对象
	 * @return 用户请求的ip地址
	 */
	String getAddress(HttpServletRequest req);
	
	String addDriver(String ip,String uid);
	
}
