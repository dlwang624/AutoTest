package org.czy.service;

import java.util.List;
import java.util.Map;

import org.czy.entity.Train;
import org.springframework.web.multipart.MultipartFile;

public interface TrainService {

	boolean addTrain(Train train,String nickname,MultipartFile[] tl,MultipartFile[] ppts);
	
	boolean updateTrain(Train train,MultipartFile[] tl,MultipartFile[] ppts);
	
	boolean delPPT(String pptName,int tid);
	
	boolean delWORD(String wordName,int tid);
	
	boolean removeTrain(int tid);
	
	Train getByID(int tid);
	
	List<Train> getAll();
	
	List<Train> getTopAll(int num);
	
	boolean checkTitle(String Title);
	
	boolean selCount();
	
	List<String> getPPTNames(int tid);
	
	Map<String, List<String>> getpptWord(int tid);
}
