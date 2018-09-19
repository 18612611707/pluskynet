package com.pluskynet.dao.impl;
import java.io.Serializable;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.BaseDao;
public class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {
	public Class<?> entityClass;
	public void insert(T t) {	
		this.getHibernateTemplate().save(t);	
	}
	public void update(T t) {
		this.getHibernateTemplate().update(t);	
	}
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);	
	}
	public T findById(PK id) {
		@SuppressWarnings("unchecked")
		T t = (T)this.getHibernateTemplate().get(entityClass, id);
		return t;
	}	
}
