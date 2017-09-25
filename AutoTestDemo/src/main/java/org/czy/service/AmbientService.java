package org.czy.service;

import java.util.List;

import org.czy.entity.Qcdb;
import org.czy.entity.Switcham;

public interface AmbientService {
	List<Switcham> getProAmbient(Qcdb db);
	boolean addAmbient(Switcham ambient,Qcdb db);
	boolean delAmbient(int id);
	boolean updateAmbient(Switcham ambient);
	Switcham getAmbient(int id);
}
