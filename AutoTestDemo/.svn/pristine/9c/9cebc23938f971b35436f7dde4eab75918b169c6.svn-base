package org.czy.service;

import java.util.List;

import org.czy.entity.Tools;
import org.springframework.web.multipart.MultipartFile;

public interface ToolsService {
	boolean addTool(Tools tool,MultipartFile[] helps,MultipartFile tl);
	boolean checkToolName(String name);
	List<Tools> getAllByType(int type);
	void downloadCount(int id);
	String useCount(int id);
	Tools getById(int id);
	boolean updateTool(Tools tool,MultipartFile[] helps,MultipartFile tl);
	boolean deleteTool(int id);
}
