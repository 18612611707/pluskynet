package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.pluskynet.dao.DocRuleDao;
import com.pluskynet.domain.Docrule;
@SuppressWarnings("all")
public class DocRuleDaoImpl extends HibernateDaoSupport implements DocRuleDao {

	@Override
	public String save(Docrule docrule) {
		String msg = null ;
		String hql = "from Docrule where sectionName = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,docrule.getSectionName());
		if (docrules.size()>0) {
			String queryStr = "update Docrule set sectionName = ? where ruleid = ?";
			this.getHibernateTemplate().bulkUpdate(queryStr,docrule.getSectionName(),docrules.get(0).getRuleid());
			msg = "成功";
			return msg;
		}
		this.getHibernateTemplate().save(docrule);
		List<Docrule> docrulesTo = this.getHibernateTemplate().find(hql,docrule.getSectionName());
		if (docrulesTo.size()>0) {
			msg = "成功";
		}
		return msg;
	}

	@Override
	public String update(Docrule docrule) {
		String msg = "失败" ;
		String hqls = null;
		if (docrule.getRuleid()==null) {
			return msg;
		}
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,docrule.getRuleid());
		if (docrules.size()>0) {
			if (docrule.getRule()==null || docrule.getRule().equals("")) {
				hqls = "update Docrule set sectionName = ? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getSectionName(),docrule.getRuleid());
			}else if (docrule.getSectionName()==null || docrule.getSectionName().equals("")) {
				hqls = "update Docrule set rule = ? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(),docrule.getRuleid());
			}else{
				hqls = "update Docrule set rule = ?,sectionName = ? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(),docrule.getSectionName(),docrule.getRuleid());
			}
			msg = "成功";
		}
		return msg;
	}

	@Override
	public List<Map> getDcoSectionList() {
		List<Map> list = new ArrayList<Map>();
		String hql = "from Docrule";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql);
		if (docrules.size()>0) {
			for (int i = 0; i < docrules.size(); i++) {
				Map<String, Object> result=new HashMap<String, Object>();
				result.put("ruleid",docrules.get(i).getRuleid());
				result.put("sectionName", docrules.get(i).getSectionName());
				list.add(result);
			}
		}
		return list;
	}

	@Override
	public Map getDcoSection(Docrule docrule) {
		Map map = new HashMap();
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,docrule.getRuleid());
		if (docrules.size()>0) {
			map.put("ruleid", docrules.get(0).getRuleid());
			map.put("rule", docrules.get(0).getRule());
			map.put("sectionName", docrules.get(0).getSectionName());
		}
		return map;
	}

}
