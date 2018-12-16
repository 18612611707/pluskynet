package com.pluskynet.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.ReasonnumDao;
import com.pluskynet.domain.Reasonnum;

public class ReasonnumDaoImpl extends HibernateDaoSupport implements ReasonnumDao {

	@Override
	public List<Reasonnum> select() {
		String queryString = "from Reasonnum ";
		List<Reasonnum> list = this.getHibernateTemplate().find(queryString);
		int nums = 0 ;
		Reasonnum reasonnum = new Reasonnum();
		for (int i = 0; i < list.size(); i++) {
			nums = nums + list.get(i).getNums(); 
		}
		reasonnum.setReason("总数");
		reasonnum.setNums(nums);
		list.add(reasonnum);
		return list;
	}

}
