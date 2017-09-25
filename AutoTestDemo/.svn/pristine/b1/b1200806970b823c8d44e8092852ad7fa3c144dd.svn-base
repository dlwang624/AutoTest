package org.czy.service.impl;

import javax.annotation.Resource;

import org.czy.dao.ServerCountMapper;
import org.czy.entity.ServerCount;
import org.czy.service.ServerCountService;
import org.springframework.stereotype.Service;

@Service("serverCountService")
public class ServerCountServiceImpl implements ServerCountService {

	@Resource
	private ServerCountMapper serverCountMapper;

	@Override
	public boolean addServerCount(ServerCount sc) {
		return serverCountMapper.insert(sc)>0?true:false;
	}
	
}
