package com.pluskynet.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.DocumentsBasicDao;
import com.pluskynet.domain.Documentsbasic;
@SuppressWarnings("all")
public class DocumentsBasicDaoImpl extends HibernateDaoSupport implements DocumentsBasicDao {

	@Override
	public boolean sava(Documentsbasic documentsBasic) {
		String hql = "from Documentsbasic where documentsid = ?";
		List<Documentsbasic> list = this.getHibernateTemplate().find(hql,documentsBasic.getDocumentsid());
		if (list.size()>0) {
			return false;
		}
		this.getHibernateTemplate().save(documentsBasic);
		return true;
	}

}
