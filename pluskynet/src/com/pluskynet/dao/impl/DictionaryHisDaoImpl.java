package com.pluskynet.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.DictionaryHisDao;
import com.pluskynet.domain.DictionaryHis;

public class DictionaryHisDaoImpl extends HibernateDaoSupport implements DictionaryHisDao {

	@Override
	public void save(DictionaryHis dictionaryHis) {
		this.getHibernateTemplate().save(dictionaryHis);
	}

}
