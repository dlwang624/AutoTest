package org.czy.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.czy.dao.AuthorityMapper;
import org.czy.dao.UserMapper;
import org.czy.entity.Authority;
import org.czy.entity.User;
import org.czy.service.AuthorityService;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Resource
	private AuthorityMapper authorityMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public List<Authority> selectByIDs(int uid) {
		User user = userMapper.selectByPrimaryKey(uid);
		if(null!=user){
			return authorityMapper.selectByIDs(user.getAuthorityid().split(","));
		}else{
			return null;
		}
	}

	@Override
	public Set<Integer> selectByIDs(List<Authority> list) {
		Set<Integer> root = new HashSet<Integer>();
		for (Authority auth : list) {
			root.add(auth.getSection());
		}
		return root;
	}

}
