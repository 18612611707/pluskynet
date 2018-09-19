package com.pluskynet.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.SynonymDao;
import com.pluskynet.domain.Synonymtypetable;
import com.pluskynet.domain.Synonymwordtable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings("all")
public class SynonymDaoImpl extends HibernateDaoSupport implements SynonymDao {
	private SessionFactory sessionFactory;
	@Override
	public List<Synonymtypetable> getTypeList() {
		String hql = "from Synonymtypetable";
		List<Synonymtypetable> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Synonymwordtable> getSynonym(Synonymtypetable synonymtypetable) {
		String hql = "from Synonymwordtable where typeid = ?";
		List<Synonymwordtable> list = this.getHibernateTemplate().find(hql,synonymtypetable.getTypeid());
		return list;
	}

	@Override
	@Transactional
	public String saveType(Synonymtypetable synonymtypetable) {
		String hql = null;
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (synonymtypetable.getTypeid()==null) {
			hql = "from Synonymtypetable where typename = '"+synonymtypetable.getTypename()+"'";
			List<Synonymtypetable> list = this.getHibernateTemplate().find(hql);
			if (list.size()==0) {
				this.getHibernateTemplate().save(synonymtypetable);
			}
			
		}else {
			hql = "update synonymtypetable set typename = '"+synonymtypetable.getTypename()+"' where typeid = "+synonymtypetable.getTypeid()+"";
			int i = session.createSQLQuery(hql).executeUpdate();
		}
		return "成功";
	}

	@Override
	@Transactional
	public String save(Synonymtypetable synonymtypetable) {
		String sql = "delete from Synonymwordtable where typeid = "+synonymtypetable.getTypeid();
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(sql).executeUpdate();
		JSONArray jsonArray = new JSONArray().fromObject(synonymtypetable.getSynonym());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = new JSONObject().fromObject(jsonArray.get(i));
			Synonymwordtable synonymwordtable = new Synonymwordtable();
			synonymwordtable.setTypeid(synonymtypetable.getTypeid());
			synonymwordtable.setWord(jsonObject.getString("word"));
			this.getHibernateTemplate().save(synonymwordtable);
			
		}
		return "成功";
	}

}
