package org.czy.service;

import java.util.List;

import org.czy.entity.Project;
import org.czy.entity.ProjectCount;
import org.czy.entity.ProjectList;
import org.czy.entity.Qcdb;
import org.json.JSONObject;

public interface ProjectService {
	boolean addProject(Qcdb db,Project pro);
	
	List<Project> getAll();
	
	Project getProjectByID(int id);
	
	boolean delProject(int id);
	
	Project getProjectByProID(String proid);
	
	boolean updateProject(Qcdb db,Project pro);
	
	List<Project> getByIds(String[] ids);
	
	List<ProjectList> getProById(Qcdb db);
	
	List<ProjectCount> getProCount(Qcdb db);
	
	String exportWord(String uid,int qid,int folderID,String foldername);
	
	JSONObject chartsReporter(int qid,int folderID,String foldername,String redmineID);
	
	boolean correctTestStatus(int qid,int folderID,String foldername);
}
