package org.czy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.czy.dao.UserMapper;
import org.czy.entity.User;
import org.czy.service.UserService;
import org.czy.util.MailUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

	
	@Override
	public User userLogin(String name, String password) {
		return userMapper.selectByNameAndPass(name, password);
	}


	@Override
	public List<Map<String,String>> LikeMail(String mail) {
		List<User> list = userMapper.selectLikeMail(mail);
		List<Map<String,String>> mails = null;
		if(null!=list){
			mails = new ArrayList<Map<String,String>>();
			for (User user : list) {
				Map<String, String> map = new HashMap<String,String>();
				map.put("mail",user.getEmail());
				mails.add(map);
			}
		}
		return mails;
	}


	@Override
	public String userFindPass(String name, String email) {
		User user = userMapper.findpass(name, email);
		if(null==user){
			return "您输入的用户名或邮箱地址错误";
		}else{
			String[] to = {user.getEmail()};
			try {
				MailUtil.sendTextEmail(to, null, "自动化测试平台-找回密码", "您的密码是:"+user.getPassword());
			} catch (Exception e) {
				return "服务器异常";
			}
			return "找回成功,密码已发送至您的邮箱";
		}
	}

}
