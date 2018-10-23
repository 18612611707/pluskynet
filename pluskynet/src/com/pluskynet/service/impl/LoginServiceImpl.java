package com.pluskynet.service.impl;

import com.pluskynet.dao.LoginDao;
import com.pluskynet.domain.User;
import com.pluskynet.service.LoginService;

public class LoginServiceImpl implements LoginService {
	private LoginDao loginDao;
	
	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public String login(User user) {
		String msg = loginDao.login(user);
		return msg;
	}

}
