package org.czy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.czy.dao.QcdbMapper;
import org.czy.entity.Qcdb;
import org.czy.service.QcdbService;
import org.springframework.stereotype.Service;

@Service("qcdbService")
public class QcdbServiceImpl implements QcdbService {

	@Resource
	private QcdbMapper qcdbMapper;

	@Override
	public boolean addQcdb(Qcdb qcdb) {
		Qcdb q = qcdbMapper.selectByIP(qcdb.getIp(),qcdb.getDbname());
		if(null!=q){
			return false;
		}else{
			return qcdbMapper.insert(qcdb)>0?true:false;
		}
	}

	@Override
	public boolean updateQcdb(Qcdb qcdb) {
		// TODO Auto-generated method stub
		return qcdbMapper.updateByPrimaryKey(qcdb)>0?true:false;
	}

	@Override
	public List<Qcdb> getAll() {
		return qcdbMapper.selectAll();
	}

	@Override
	public Qcdb NameByQcdb(String name) {
		return qcdbMapper.selectByName(name);
	}

	@Override
	public Qcdb selQcdb(int id) {
		return qcdbMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Qcdb> getAllName() {
		List<Qcdb> temp = qcdbMapper.selectAll();
		List<Qcdb> list = new ArrayList<Qcdb>();
		for (Qcdb qcdb : temp) {
			String[] names = qcdb.getDbname().split("_");
			qcdb.setDbname(names[names.length-2]);
			list.add(qcdb);
		}
		return list;
	}
	
	

}
