package org.czy.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.czy.entity.AutoTimer;
import org.czy.service.ExecService;
import org.czy.service.QcdbService;
import org.czy.service.ServerCountService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dengpeng
 *
 */
@Component
public class TimerTaskTest {
	
	private final static Logger LOG = Logger.getLogger(TimerTaskTest.class);

	private static Map<Integer, Timer> map = new HashMap<Integer, Timer>();
	private static List<Timer> countmap = new ArrayList<Timer>();
	
	public static String reporturl;
	
	public static String serverip;

	@SuppressWarnings("deprecation")
	public static boolean startTimer(AutoTimer autoTimer){
		try {
			Timer timer = new Timer();
			String[] weekandtime = autoTimer.getExecdate().split("@");
			String[] hm = weekandtime[1].split(":");
			int swid;
			if(weekandtime.length>3){
				swid = Integer.valueOf(weekandtime[3]);
			}else{
				swid = -1;
			}
			//提交定时器
			Calendar now = Calendar.getInstance();
			//提交时星期几
			int tempWeekDay = now.get(Calendar.DAY_OF_WEEK);
			int temphours = now.getTime().getHours();
			int tempminute = now.getTime().getMinutes();
			int tempsecond=((temphours*3600 + tempminute*60)*1000)+now.getTime().getSeconds();
			int overplus = Final.DAYMESC-tempsecond;
			LOG.info("提交定时器当天剩余秒数:"+overplus);
			//设定值
			int weekDay = Integer.valueOf(weekandtime[0]);
			int hours = Integer.valueOf(hm[0]),minute=Integer.valueOf(hm[1]);
			int second=(hours*3600 + minute*60)*1000;
			LOG.info("定时器设定值当天等待秒数:"+second);
			//间隔天数
			int i = weekDay-tempWeekDay;
			//如果设置的是礼拜天
			if(tempWeekDay==weekDay){
				int j = second-tempsecond;
				if(tempsecond==second){
					LOG.info("创建["+autoTimer.getId()+"]定时器并执行用例");
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							LOG.info("["+autoTimer.getId()+"]定时器执行了");
							ExecService execService = (ExecService)SpringTool.getBean("execService");
							QcdbService qcdbService = (QcdbService)SpringTool.getBean("qcdbService");
							String[] toads = autoTimer.getTouserid().split(",");
							String[] ccads = autoTimer.getCcuserid().split(",");
							int flag = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
							switch (flag) {
								case 0:
									break;
								case 1:
									for (;;) {
										int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
										if(f!=1){
											++ServerCount.regressCount;
											break;
										}else{
											try {
												Thread.sleep(60000);
											} catch (InterruptedException e) {
												LOG.error("线程等待错误"+e);
											}
										}
									}
									break;
								case 2:
									break;
								case 3:
									break;
								default:
									break;
							}
						}
					}, 0, 7*Final.DAYMESC);
					
				}else if(j>0){
					int time = j;
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							LOG.info("["+autoTimer.getId()+"]定时器执行了");
							ExecService execService = (ExecService)SpringTool.getBean("execService");
							QcdbService qcdbService = (QcdbService)SpringTool.getBean("qcdbService");
							String[] toads = autoTimer.getTouserid().split(",");
							String[] ccads = autoTimer.getCcuserid().split(",");
							int flag = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
							switch (flag) {
								case 0:
									break;
								case 1:
									for (;;) {
										int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
										if(f!=1){
											++ServerCount.regressCount;
											break;
										}else{
											try {
												Thread.sleep(60000);
											} catch (InterruptedException e) {
												LOG.error("线程等待错误"+e);
											}
										}
									}
									break;
								case 2:
									break;
								case 3:
									break;
								default:
									break;
							}
						}
					}, time, 7*Final.DAYMESC);
					
					LOG.info(time);
					LOG.info(Daily.formatHMS(time));
				}else{
					int time = 7*Final.DAYMESC-overplus;
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							LOG.info("["+autoTimer.getId()+"]定时器执行了");
							ExecService execService = (ExecService)SpringTool.getBean("execService");
							QcdbService qcdbService = (QcdbService)SpringTool.getBean("qcdbService");
							String[] toads = autoTimer.getTouserid().split(",");
							String[] ccads = autoTimer.getCcuserid().split(",");
							int flag = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
							switch (flag) {
								case 0:
									break;
								case 1:
									for (;;) {
										int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
										if(f!=1){
											++ServerCount.regressCount;
											break;
										}else{
											try {
												Thread.sleep(60000);
											} catch (InterruptedException e) {
												LOG.error("线程等待错误"+e);
											}
										}
									}
									break;
								case 2:
									break;
								case 3:
									break;
								default:
									break;
							}
							
						}
					}, time, 7*Final.DAYMESC);
					
					LOG.info(time);
					LOG.info(Daily.formatHMS(time));
				}
			}else if(i>0){
				int daysecond = (i-1)*Final.DAYMESC;
				long time = daysecond+overplus+second;
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						LOG.info("["+autoTimer.getId()+"]定时器执行了");
						ExecService execService = (ExecService)SpringTool.getBean("execService");
						QcdbService qcdbService = (QcdbService)SpringTool.getBean("qcdbService");
						String[] toads = autoTimer.getTouserid().split(",");
						String[] ccads = autoTimer.getCcuserid().split(",");
						int flag = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
						switch (flag) {
							case 0:
								break;
							case 1:
								for (;;) {
									int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
									if(f!=1){
										++ServerCount.regressCount;
										break;
									}else{
										try {
											Thread.sleep(60000);
										} catch (InterruptedException e) {
											LOG.error("线程等待错误"+e);
										}
									}
								}
								break;
							case 2:
								break;
							case 3:
								break;
							default:
								break;
						}
					}
				}, time, 7*Final.DAYMESC);
				LOG.info(time);
			}else{
				int j = 7+i-1;
				int daysecond = j*Final.DAYMESC;
				long time = daysecond+overplus+second;
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						LOG.info("["+autoTimer.getId()+"]定时器执行了");
						ExecService execService = (ExecService)SpringTool.getBean("execService");
						QcdbService qcdbService = (QcdbService)SpringTool.getBean("qcdbService");
						String[] toads = autoTimer.getTouserid().split(",");
						String[] ccads = autoTimer.getCcuserid().split(",");
						int flag = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
						switch (flag) {
							case 0:
								break;
							case 1:
								for (;;) {
									int f = execService.timerBatchTest(swid,autoTimer.getTestid(), qcdbService.selQcdb(autoTimer.getQcdbid()),toads,ccads, reporturl, serverip, autoTimer.getId(),autoTimer.getRemark(),false,null);
									if(f!=1){
										++ServerCount.regressCount;
										break;
									}else{
										try {
											//每分钟刷一次
											Thread.sleep(60000);
										} catch (InterruptedException e) {
											LOG.error("线程等待错误"+e);
										}
									}
								}
								break;
							case 2:
								break;
							case 3:
								break;
							default:
								break;
						}
						
					}
				}, time, 7*Final.DAYMESC);
				LOG.info(time);
			}
			map.put(autoTimer.getId(), timer);
			
		} catch (NumberFormatException e) {
			LOG.error(e);
			return false;
		}
		return true;
	}
	
	
	public static Map<Integer, Timer> getMap() {
		return map;
	}


	public static boolean cancelTimer(int timerID){
		try {
			Timer timer = map.get(timerID);
			if(null!=timer){
				timer.cancel();
				map.remove(timerID);
			}
		} catch (Exception e) {
			LOG.error(e);
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static void serverCountTimer(){
		if(countmap.size()>0){
			return;
		}
		try {
			Timer timer = new Timer();
			//提交定时器
			Calendar now = Calendar.getInstance();
			//提交时星期几
			int temphours = now.getTime().getHours();
			int tempminute = now.getTime().getMinutes();
			int tempsecond=((temphours*3600 + tempminute*60)*1000)+now.getTime().getSeconds();
			int overplus = Final.DAYMESC-tempsecond;
			LOG.info("提交定时器当天剩余秒数:"+overplus);
			//设定值
			int hours = 23,minute=49;
			int second=(hours*3600 + minute*60)*1000;
			int time = second-tempsecond;
			LOG.info("定时器设定值当天等待秒数:"+second);
			LOG.info("创建[服务器统计]定时器");
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					ServerCountService serverCountService = (ServerCountService)SpringTool.getBean("serverCountService");
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("active", ServerCount.activeProject);
					org.czy.entity.ServerCount serverCount = new org.czy.entity.ServerCount();
					serverCount.setCounttime(new Date());
					serverCount.setPpcount(ServerCount.ppCount.toString());
					serverCount.setApicount(ServerCount.apiCount);
					serverCount.setAddtest(ServerCount.addTest);
					serverCount.setUptool(ServerCount.upTool);
					serverCount.setRuntest(ServerCount.runTest);
					serverCount.setUsetool(ServerCount.useTool);
					serverCount.setActiveproject(jsonobj.toString());
					serverCount.setUpdatetest(ServerCount.updateTest.toString());
					serverCount.setRegresscount(ServerCount.regressCount);
					serverCount.setReleasecount(ServerCount.releaseCount);
					serverCountService.addServerCount(serverCount);
					ServerCount.clearAll();
				}
			}, time, Final.DAYMESC);
			countmap.add(timer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.info("[服务器统计]定时器创建失败"+e);
		}
		LOG.info("创建[服务器统计]定时器");
	}
	
	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		//提交定时器
//		Calendar now = Calendar.getInstance();
//		now.setTime(sdf.parse("2017-6-13 17:30"));
//		//提交时星期几
//		int tempWeekDay = now.get(Calendar.DAY_OF_WEEK);
//		int temphours = now.getTime().getHours();
//		int tempminute = now.getTime().getMinutes();
//		int tempsecond=((temphours*3600 + tempminute*60)*1000)+now.getTime().getSeconds();
//		int overplus = Final.DAYMESC-tempsecond;
//		LOG.info("提交定期器当天剩余秒数:"+overplus);
//		//设定值
//		int weekDay = 3;
//		int hours = 17,minute=30;
//		int second=(hours*3600 + minute*60)*1000;
//		LOG.info("定期器设定值当天等待秒数:"+second);
//		//间隔天数
//		int i = weekDay-tempWeekDay;
//		//如果设置的是礼拜天
//		if(tempWeekDay==weekDay){
//			int j = second-tempsecond;
//			if(tempsecond==second){
//				LOG.info("创建定时器并执行用例");
//			}else if(j>0){
//				int time = j;
//				LOG.info(time);
//				LOG.info(Daily.formatHMS(time));
//			}else{
//				int time = 7*Final.DAYMESC-overplus;
//				LOG.info(time);
//				LOG.info(Daily.formatHMS(time));
//			}
//		}else if(i>0){
//			int daysecond = i*Final.DAYMESC;
//			long time = daysecond+overplus+second;
//			
//			LOG.info(time);
//		}else{
//			int j = 7+i-1;
//			int daysecond = j*Final.DAYMESC;
//			long time = daysecond+overplus+second;
//			
//			LOG.info(time);
//		}
		
		
//		long startTime = System.currentTimeMillis();
//		Date date = sdf.parse("2017-6-12 17:30");
//		
//		
//		long endTime = date.getTime();
//		
//		String time = Daily.formatHMS(endTime-startTime);
//		
//		LOG.info(time);
		
	}
}
