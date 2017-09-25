package org.czy.service;

import java.util.List;
import java.util.Map;

import org.czy.entity.User;

public interface UserService {

	User userLogin(String name,String password);
	
	String userFindPass(String name,String email);
	
	List<Map<String,String>> LikeMail(String mail);
}
