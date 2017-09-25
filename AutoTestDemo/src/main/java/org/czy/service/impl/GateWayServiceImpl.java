package org.czy.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.czy.dao.ServerCountMapper;
import org.czy.dao.TestMapper;
import org.czy.dao.ToolsMapper;
import org.czy.dao.TrainMapper;
import org.czy.dao.WorkDynamicMapper;
import org.czy.entity.GateWay;
import org.czy.entity.Tools;
import org.czy.entity.Train;
import org.czy.service.GateWayService;
import org.czy.util.Final;
import org.czy.util.ServerCount;
import org.springframework.stereotype.Service;

@Service
public class GateWayServiceImpl implements GateWayService{

	private final static Logger LOG = Logger.getLogger(GateWayServiceImpl.class);
	
	@Resource
	private TrainMapper trainMapper;
	
	@Resource
	private WorkDynamicMapper workDynamicMapper;
	
	@Resource
	private ServerCountMapper serverCountMapper;
	
	@Resource
	private TestMapper testMapper;
	
	@Resource
	private ToolsMapper toolsMapper;
	
	@Override
	public GateWay getGateWay() {
		GateWay gateway = new GateWay();
		//今日平台使用人数
		gateway.setPpCount(ServerCount.ppCount.size());
		//平台累计使用人数
		List<org.czy.entity.ServerCount> serlist = serverCountMapper.selectByCount();
		int totalPPCount = 0;
		int regressCount = 0;
		int releaseCount = 0;
		for (org.czy.entity.ServerCount serverCount : serlist) {
			String pp = serverCount.getPpcount();
			pp = pp.replaceAll("\\[|\\]", "");
			Set<String> ppcount = new HashSet<String>();
			if(null!=pp&&!pp.equals("")){
				String[] pps = pp.split(",");
				for (int i = 0; i < pps.length; i++) {
					ppcount.add(pps[i].trim());
				}
			}
			totalPPCount += ppcount.size();
			regressCount += serverCount.getRegresscount();
			releaseCount += serverCount.getReleasecount();
		}
		gateway.setTotalPPCount(totalPPCount);
		gateway.setRegressCount(regressCount);
		gateway.setRegressTime(regressCount*Final.REGRESS);
		gateway.setReleaseCount(releaseCount);
		gateway.setReleaseTime(releaseCount*Final.RELEASE);
		
		List<Tools> tlist = toolsMapper.selServerCount();
		int toolCount = 0;
		for (Tools tools : tlist) {
			toolCount += tools.getUsecount();
			if(tools.getToolclass()==0){
				gateway.setDowntoolCount(tools.getDownloadcount());
			}else{
				gateway.setUsetoolCount(tools.getDownloadcount());
			}
		}
		gateway.setToolCount(toolCount);
		return gateway;
	}

}
