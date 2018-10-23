package com.pluskynet.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.opensymphony.xwork2.ActionContext;
import com.pluskynet.dao.LoginDao;
import com.pluskynet.domain.User;

public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

	@Override
	public String login(User user) {
		String sql = "from User where username = ? and password = ? ";
		List<User> list = this.getHibernateTemplate().find(sql,user.getUsername(),user.getPassword());
		if (list.size()>0) {
			ActionContext.getContext().getSession().put("user", list.get(0));
			return "成功";
		}
		return "用户名或密码错误！";
	}

}
