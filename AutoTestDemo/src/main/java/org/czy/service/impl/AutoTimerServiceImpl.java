package org.czy.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.czy.dao.AutoTimerMapper;
import org.czy.entity.AutoTimer;
import org.czy.entity.WeekTime;
import org.czy.service.AutoTimerService;
import org.czy.util.Daily;
import org.czy.util.TimerTaskTest;
import org.springframework.stereotype.Service;

/**
 * 
 * @author dengpeng
 *
 */
@Service
public class AutoTimerServiceImpl implements AutoTimerService {
	
	@Resource
	private AutoTimerMapper autoTimerMapper;

	@Override
	public boolean addTimer(int swid,String testids,String time,int week,String tomails,String ccmails,int uid,int dbid,String remark,String ip) {
		String execdate = week+"@"+time+"@"+true+"@"+swid;
		AutoTimer timer = new AutoTimer();
		timer.setExecdate(execdate);
		timer.setCcuserid(ccmails);
		timer.setTouserid(tomails);
		timer.setTestid(testids);
		timer.setQcdbid(dbid);
		timer.setNewuserid(uid);
		timer.setNewdate(new Date());
		timer.setRemark(remark);
		timer.setIp(ip);
		boolean flag = autoTimerMapper.insert(timer)>0?true:false;
		AutoTimer autoTimer = null;
		if(flag){
			autoTimer = autoTimerMapper.selectByExecDate(execdate,ip);
		}
		if(null!=autoTimer){
			TimerTaskTest.startTimer(autoTimer);
		}
		return flag;
	}

	@Override
	public List<AutoTimer> getConfig(int id) {
		List<AutoTimer> list = new ArrayList<AutoTimer>();
		List<AutoTimer> autolist = autoTimerMapper.selectByQcdbID(id);
		if(null!=autolist){
			Map<Integer, Timer> map = TimerTaskTest.getMap();
			if(map.size()>0){
				for (AutoTimer autoTimer : autolist) {
					if(map.get(autoTimer.getId())!=null){
						autoTimer.setStartflag(true);
					}else{
						autoTimer.setStartflag(false);
					}
					list.add(autoTimer);
				}
			}else{
				return autolist;
			}
		}
		return list;
		
	}

	@Override
	public AutoTimer selConfigByID(int id) {
		return autoTimerMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateTimer(AutoTimer timer,int updateuid,String ip) {
		timer.setUpdateuserid(updateuid);
		timer.setUpdatedate(new Date());
		timer.setIp(ip);
		boolean flag = autoTimerMapper.updateByPrimaryKey(timer)>0?true:false;
		AutoTimer autoTimer = null;
		if(flag){
			autoTimer = timer;
		}
		if(null!=autoTimer){
			TimerTaskTest.cancelTimer(autoTimer.getId());
			TimerTaskTest.startTimer(autoTimer);
		}
		return flag;
	}

	@Override
	public List<WeekTime> getWeekTime(String week,String ip) {
		List<WeekTime> list = new ArrayList<WeekTime>();
		//所有时间
		String [] times = Daily.getTime();
		List<AutoTimer> autolist = autoTimerMapper.selectByQcdbIDAndWeek(week,ip);
		//存在的时间
		String [] timestemp = new String[autolist.size()];
		for (int i=0;i<autolist.size();i++) {
			String[] weekandtime = autolist.get(i).getExecdate().split("@");
			timestemp[i] = weekandtime[1];
		}
		List<String> t = Arrays.asList(timestemp);
		for (String time : times) {
			WeekTime weektime = new WeekTime();
			weektime.setTime(time);
			if(t.contains(time)){
				//如果存在就设置禁用
				weektime.setEngross(true);
			}else{
				weektime.setEngross(false);
			}
			list.add(weektime);
		}
		return list;
	}

	@Override
	public List<WeekTime> getWeekTime(String week, String timeExcept,String ip) {
		List<WeekTime> list = new ArrayList<WeekTime>();
		//所有时间
		String [] times = Daily.getTime();
		List<AutoTimer> autolist = autoTimerMapper.selectByQcdbIDAndWeek(week,ip);
		//存在的时间
		String [] timestemp = new String[autolist.size()];
		for (int i=0;i<autolist.size();i++) {
			String[] weekandtime = autolist.get(i).getExecdate().split("@");
			timestemp[i] = weekandtime[1];
		}
		List<String> t = Arrays.asList(timestemp);
		for (String time : times) {
			WeekTime weektime = new WeekTime();
			weektime.setTime(time);
			if(t.contains(time)&&!time.equals(timeExcept)){
				//如果存在就设置禁用
				weektime.setEngross(true);
			}else{
				weektime.setEngross(false);
			}
			list.add(weektime);
		}
		return list;
	}

	@Override
	public boolean startTimer(int timerID) {
		AutoTimer autoTimer = autoTimerMapper.selectByPrimaryKey(timerID);
		if(null!=autoTimer){
			String execdate = autoTimer.getExecdate();
			String[] dates = execdate.split("@");
			if(dates.length==3){
				execdate = dates[0]+"@"+dates[1]+"@"+true;
			}else{
				execdate = dates[0]+"@"+dates[1]+"@"+true+"@"+dates[3];
			}
			autoTimer.setExecdate(execdate);
			autoTimerMapper.updateByPrimaryKey(autoTimer);
			return TimerTaskTest.startTimer(autoTimer);
		}
		return false;
	}

	@Override
	public boolean stopTimer(int timerID) {
		AutoTimer autoTimer = autoTimerMapper.selectByPrimaryKey(timerID);
		if(null!=autoTimer){
			String execdate = autoTimer.getExecdate();
			String[] dates = execdate.split("@");
			if(dates.length==3){
				execdate = dates[0]+"@"+dates[1]+"@"+false;
			}else{
				execdate = dates[0]+"@"+dates[1]+"@"+false+"@"+dates[3];
			}
			autoTimer.setExecdate(execdate);
			autoTimerMapper.updateByPrimaryKey(autoTimer);
			return TimerTaskTest.cancelTimer(timerID);
		}
		return TimerTaskTest.cancelTimer(timerID);
	}

	@Override
	public List<Map<String,String>> serverStart(String ip) {
		List<Map<String,String>> ls= new ArrayList<Map<String,String>>();
		List<AutoTimer> list = autoTimerMapper.selectByIP(ip);
		Map<Integer, Timer> timerMap = TimerTaskTest.getMap();
		for (AutoTimer autoTimer : list) {
			if(null==timerMap.get(autoTimer.getId())){
				Map<String,String> map = new HashMap<String,String>();
				String execdate = autoTimer.getExecdate();
				String[] dates = execdate.split("@");
				if(dates.length==3){
					execdate = dates[2];
				}else{
					execdate = dates[3];
				}
				if(execdate.equals("true")){
					if(TimerTaskTest.startTimer(autoTimer)){
						map.put("msg","["+autoTimer.getRemark()+"]定期计划启动成功");
					}else{
						map.put("msg","["+autoTimer.getRemark()+"]定期计划启动失败");
					}
				}else{
					map.put("msg","["+autoTimer.getRemark()+"]定期计划未设置启动");
				}
				ls.add(map);
			}
		}
		TimerTaskTest.serverCountTimer();
		return ls;
	}

	@Override
	public List<AutoTimer> getUserConfig(int qcdbid, int uid) {
		List<AutoTimer> list = new ArrayList<AutoTimer>();
		List<AutoTimer> autolist = autoTimerMapper.selectByQcdbAndID(qcdbid, uid);
		if(null!=autolist){
			Map<Integer, Timer> map = TimerTaskTest.getMap();
			if(map.size()>0){
				for (AutoTimer autoTimer : autolist) {
					if(map.get(autoTimer.getId())!=null){
						autoTimer.setStartflag(true);
					}else{
						autoTimer.setStartflag(false);
					}
					list.add(autoTimer);
				}
			}else{
				return autolist;
			}
		}
		return list;
	}

}
