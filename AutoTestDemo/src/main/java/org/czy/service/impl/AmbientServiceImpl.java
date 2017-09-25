package org.czy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.czy.dao.SwitchamMapper;
import org.czy.entity.Qcdb;
import org.czy.entity.Switcham;
import org.czy.service.AmbientService;
import org.springframework.stereotype.Service;

@Service
public class AmbientServiceImpl implements AmbientService {
	
	@Resource
	private SwitchamMapper switchamMapper;

	@Override
	public List<Switcham> getProAmbient(Qcdb db) {
		return switchamMapper.selectAllByQID(db.getId());
	}

	@Override
	public boolean addAmbient(Switcham ambient, Qcdb db) {
		ambient.setQid(db.getId());
		return switchamMapper.insert(ambient)>0?true:false;
	}

	@Override
	public boolean delAmbient(int id) {
		return switchamMapper.deleteByPrimaryKey(id)>0?true:false;
	}

	@Override
	public boolean updateAmbient(Switcham ambient) {
		return switchamMapper.updateByPrimaryKey(ambient)>0?true:false;
	}

	@Override
	public Switcham getAmbient(int id) {
		return switchamMapper.selectByPrimaryKey(id);
	}

}
