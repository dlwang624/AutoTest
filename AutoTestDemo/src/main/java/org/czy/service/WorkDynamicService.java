package org.czy.service;

import java.util.List;

import org.czy.entity.WorkDynamic;
import org.springframework.web.multipart.MultipartFile;

public interface WorkDynamicService {
	
	boolean addWorkDynamic(WorkDynamic dynamic,MultipartFile file);
	
	boolean updateWorkDynamic(WorkDynamic dynamic,MultipartFile file);
	
	boolean delWorkDynamic(int tid);
	
	WorkDynamic getById(int id);
	
	List<WorkDynamic> getAll();
	
	List<WorkDynamic> getTopAll(int num);
	
	boolean checktitle(String title);
	
	String getView(WorkDynamic dynamic);
	
	boolean judgeBackShow();

}
