package org.czy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.czy.dao.ProjectMapper;
import org.czy.dao.QcdbMapper;
import org.czy.dao.TestMapper;
import org.czy.entity.Project;
import org.czy.entity.ProjectCount;
import org.czy.entity.ProjectList;
import org.czy.entity.Qcdb;
import org.czy.entity.Test;
import org.czy.service.ProjectService;
import org.czy.util.WordReport;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.utils.tools.TestReportdbTools;
import com.utils.tools.Tool;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private final static Logger LOG = Logger.getLogger(ProjectServiceImpl.class);

	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private QcdbMapper qcdbMapper;
	
	@Resource
	private TestMapper testMapper;
	
	@Override
	public boolean addProject(Qcdb db,Project pro) {
		Project p = projectMapper.selectByPID(pro.getProjectid());
		if(null!=p){
			return false;
		}else{
			if(Tool.checkQCFolder(db, pro.getProjectid())){
				return projectMapper.insert(pro)>0?true:false;
			}else{
				return false;
			}
		}
	}

	@Override
	public List<Project> getAll() {
		return projectMapper.selectAll();
	}

	@Override
	public Project getProjectByID(int id) {
		return projectMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateProject(Qcdb db,Project pro) {
		if(Tool.checkQCFolder(db, pro.getProjectid())){
			return projectMapper.updateByPrimaryKey(pro)>0?true:false;
		}else{
			return false;
		}
	}

	@Override
	public List<Project> getByIds(String[] ids) {
		return projectMapper.selectAllByProID(ids);
	}

	@Override
	public Project getProjectByProID(String proid) {
		return projectMapper.selectByPID(proid);
	}

	@Override
	public List<ProjectList> getProById(Qcdb db) {
		if(!db.getProjectid().equals("")){
			List<Project> list = projectMapper.selectAllByProID(db.getProjectid().split(","));
			List<ProjectList> plist = new ArrayList<ProjectList>();
			for (Project p : list) {
				ProjectList pl = new ProjectList();
				pl.setId(p.getId());
				pl.setName(p.getName());
				pl.setProjectid(p.getProjectid());
				List<Test> lt = testMapper.selectByIds(p.getId());
				if(lt!=null&&lt.size()>0){
					pl.setFlag(false);
				}else{
					pl.setFlag(true);
				}
				plist.add(pl);
			}
			return plist;
		}else{
			return null;
		}
	}

	@Override
	public List<ProjectCount> getProCount(Qcdb db) {
		List<ProjectCount> list = new ArrayList<ProjectCount>();
			if(!db.getProjectid().equals("")){
			List<Project> projects = projectMapper.selectAllByProID(db.getProjectid().split(","));
			for (Project project : projects) {
				ProjectCount cou = new ProjectCount();
				cou.setId(project.getId());
				cou.setName(project.getName());
				cou.setProjectid(project.getProjectid());
				int fail=0,success=0;
				for (Test test : testMapper.selectByIds(project.getId())) {
					fail += test.getFail();
					success += test.getSuccess();
				}
				double count = fail+success;
				cou.setPercent((fail/count)*100);
				cou.setFail(fail);
				cou.setSuccess(success);
				list.add(cou);
			}
			return list;
		}else{
			return null;
		}
	}

	@Override
	public boolean delProject(int id) {
		return projectMapper.deleteByPrimaryKey(id)>0?true:false;
	}

	@Override
	public String exportWord(String uid,int qid,int folderID,String foldername) {
		String path = "";
		Qcdb db = qcdbMapper.selectByPrimaryKey(qid);
		String[] names = db.getDbname().split("_");
		List<Project> list = Tool.getWordFolderIDByName(db,foldername);
		if(null!=list&&list.size()==1){
			path = WordReport.downloadWord(uid,names[names.length-2],db,Integer.valueOf(list.get(0).getProjectid()));
		}else{
			path = WordReport.downloadWord(uid,names[names.length-2],db,folderID);
		}
		return path;
	}

	@Override
	public JSONObject chartsReporter(int qid, int folderID, String foldername,String redmineID) {
		JSONObject jsonobj = new JSONObject();
		Qcdb db = qcdbMapper.selectByPrimaryKey(qid);
		List<List<String>> map = null;
		List<Project> list = Tool.getWordFolderIDByName(db,foldername);
		try {
			if(null==redmineID||redmineID.equals("")){
				if(null!=list&&list.size()==1){
					map = TestReportdbTools.Bugvanished(db, Integer.valueOf(list.get(0).getProjectid()));
				}else{
					map = TestReportdbTools.Bugvanished(db, folderID);
				}
			}else{
				if(null!=list&&list.size()==1){
					map = TestReportdbTools.BugvanishedReadminID(db, Integer.valueOf(list.get(0).getProjectid()),redmineID);
				}else{
					map = TestReportdbTools.BugvanishedReadminID(db, folderID,redmineID);
				}
			}
			if(null!=map&&map.size()>0){
				String[] day = new String[map.size()];
				int[] bug = new int[map.size()];
				for (int i = 0; i < map.size(); i++) {
					List<String> lt = map.get(i);
					day[i] = lt.get(0);
					bug[i] = Integer.valueOf(lt.get(1));
				}
				jsonobj.put("day", day);
				jsonobj.put("bug", bug);
			}
		} catch (NumberFormatException e) {
			LOG.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return jsonobj;
	}

	@Override
	public boolean correctTestStatus(int qid, int folderID, String foldername) {
		Qcdb db = qcdbMapper.selectByPrimaryKey(qid);
		List<Project> list = Tool.getWordFolderIDByName(db,foldername);
		boolean flag = false;
		try {
			if(null!=list&&list.size()==1){
				flag = TestReportdbTools.bugStatus(db, Integer.valueOf(list.get(0).getProjectid()))>0?true:false;
			}else{
				flag = TestReportdbTools.bugStatus(db, folderID)>0?true:false;
			}
		} catch (NumberFormatException e) {
			LOG.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return flag;
	}

}
