package org.czy.service;

import java.util.List;
import java.util.Map;

import org.czy.entity.AutoTimer;
import org.czy.entity.WeekTime;

public interface AutoTimerService {

	boolean addTimer(int swid,String testids,String time,int week,String tomails,String ccmails,int uid,int dbid,String remark,String ip);
	
	List<AutoTimer> getConfig(int id);
	
	List<AutoTimer> getUserConfig(int qcdbid,int uid);
	
	AutoTimer selConfigByID(int id);
	
	boolean updateTimer(AutoTimer timer,int updateuid,String ip);
	
	List<WeekTime> getWeekTime(String week,String ip);
	List<WeekTime> getWeekTime(String week,String timeExcept,String ip);
	
	boolean startTimer(int timerID);
	boolean stopTimer(int timerID);
	
	List<Map<String,String>> serverStart(String ip);
}
