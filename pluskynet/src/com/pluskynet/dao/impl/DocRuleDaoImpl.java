package com.pluskynet.dao.impl;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.core.DatabaseContextHolder;
import com.pluskynet.dao.DocRuleDao;
import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Latitude;
import com.pluskynet.otherdomain.TreeDocrule;
import com.pluskynet.otherdomain.Treelatitude;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class DocRuleDaoImpl extends HibernateDaoSupport implements DocRuleDao {

	@Override
	public Map save(Docrule docrule) {
		Map map = new HashMap();
		if (docrule.getFid() == null) {
			docrule.setFid(0);
		}
		String hql = "from Docrule where sectionName = ? and fid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql, docrule.getSectionname(), docrule.getFid());
		if (docrules.size() > 0) {
			String queryStr = "update Docrule set sectionName = ? ,fid = ? where ruleid = ?";
			this.getHibernateTemplate().bulkUpdate(queryStr, docrule.getSectionname(), docrule.getFid(),
					docrules.get(0).getRuleid());
			map.put("ruleid", docrules.get(0).getRuleid());
		} else {
			this.getHibernateTemplate().save(docrule);
			List<Docrule> docrulesTo = this.getHibernateTemplate().find(hql, docrule.getSectionname(),
					docrule.getFid());
			if (docrulesTo.size() > 0) {
				map.put("ruleid", docrulesTo.get(0).getRuleid());
			}
		}
		return map;
	}

	@Override
	public String update(Docrule docrule) {
		String msg = "失败";
		String hqls = null;
		if (docrule.getRuleid() == null) {
			return msg;
		}
		if (docrule.getFid() == null) {
			docrule.setFid(0);
		}
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql, docrule.getRuleid());
		if (docrules.size() > 0) {
			if (docrule.getRule() == null || docrule.getRule().equals("")) {
				hqls = "update Docrule set sectionName = ?,reserved=? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getSectionname(),
						docrule.getReserved(), docrule.getRuleid());
			} else if (docrule.getSectionname() == null || docrule.getSectionname().equals("")) {
				hqls = "update Docrule set rule = ? ,reserved=? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(), docrule.getReserved(),
						docrule.getRuleid());
			} else {
				hqls = "update Docrule set rule = ?,sectionName = ? ,reserved=? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(), docrule.getSectionname(),
						 docrule.getReserved(), docrule.getRuleid());
			}
			msg = "成功";
		}
		return msg;
	}

	@Override
	public List<Docrule> getDcoSectionList() {
//		DatabaseContextHolder.setCustomerType("dataSourceww"); //多数据源手动切换
		String hql = "from Docrule order by fid";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql);
		return docrules;
	}

	@Override
	public Map getDcoSection(Docrule docrule) {
		Map map = new HashMap();
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql, docrule.getRuleid());
		if (docrules.size() > 0) {
			map.put("ruleid", docrules.get(0).getRuleid());
			map.put("rule", docrules.get(0).getRule());
			map.put("sectionname", docrules.get(0).getSectionname());
			map.put("reserved", docrules.get(0).getReserved());
		}
		return map;
	}

	@Override
	public List<Docrule> getNextSubSet(TreeDocrule voteTree) {
		String hql = "from Docrule where fid = ?";
		List<Docrule> tNextLevel = this.getHibernateTemplate().find(hql, voteTree.getRuleid());
//		List<TreeDocrule> list = new ArrayList<TreeDocrule>();
//		for (int i = 0; i < tNextLevel.size(); i++) {
//			// 遍历这个二级目录的集合
//			TreeDocrule treelatitude = new TreeDocrule();
//			treelatitude.setRuleid(tNextLevel.get(i).getRuleid());
//			treelatitude.setFid(tNextLevel.get(i).getFid());
//			treelatitude.setSectionname(tNextLevel.get(i).getSectionname());
//			List<TreeDocrule> ts = getDeeptLevel(tNextLevel.get(i));
//			// 将下面的子集都依次递归进来
//			treelatitude.setChildren(ts);
//			list.add(treelatitude);
//		}
		return tNextLevel;
	}

	@Override
	public List<TreeDocrule> getDeeptLevel(Docrule latitude) {
		String hql = "from Docrule where fid = ?";
		List<Docrule> tsLevel = this.getHibernateTemplate().find(hql, latitude.getRuleid());
		List<TreeDocrule> list = new ArrayList<TreeDocrule>();
		if (tsLevel.size() > 0) {
			for (int i = 0; i < tsLevel.size(); i++) {
				TreeDocrule treelatitude = new TreeDocrule();
				treelatitude.setRuleid(tsLevel.get(i).getRuleid());
				treelatitude.setFid(tsLevel.get(i).getFid());
				treelatitude.setSectionname(tsLevel.get(i).getSectionname());
				treelatitude.setChildren(getDeeptLevel(tsLevel.get(i)));
				list.add(treelatitude);
			}
		}
		return list;
	}

	@Override
	public List<Docrule> getSecNameShow(String sectionname) {
		String hql = "from Docrule where sectionname = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql, sectionname);
		return docrules;
	}

	@Override
	public List<Docrule> getRuleShow(Integer ruleid, String causeo, String causet, String spcx, String doctype) {
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> list = this.getHibernateTemplate().find(hql, ruleid);
		List<Docrule> lists = new ArrayList<Docrule>();
		for (int i = 0; i < list.size(); i++) {
			Docrule docrule = new Docrule();
			String ruleString = "";
			JSONArray jsonarray = JSONArray.fromObject(list.get(i).getRule());
			JSONArray js = new JSONArray();
			for (int j = jsonarray.size() - 1; j >= 0; j--) {
				JSONObject jss = JSONObject.fromObject(jsonarray.get(j));

				if (causeo.length() != 0) {
					if (!jss.getString("causeo").equals(causeo)) {
						continue;
					}
				}
				if (causet.length() != 0) {
					if (!jss.getString("causet").equals(causet)) {
						continue;
					}
				}
				if (spcx.length() != 0) {
					if (!jss.getString("trialRound").equals(spcx)) {
						continue;
					}
				}
				if (doctype.length() != 0) {
					if (!jss.getString("doctype").equals(doctype)) {
						continue;
					}
				}
				js.add(jsonarray.get(j));
			}
			docrule.setFid(list.get(i).getFid());
			docrule.setRuleid(list.get(i).getRuleid());
			docrule.setSectionname(list.get(i).getSectionname());
			docrule.setRule(js.toString());
			lists.add(docrule);
		}

		return lists;
	}

	@Override
	public String updatesecname(Docrule docrule) {
		String msg = "失败";
		String hqls = null;
		if (docrule.getRuleid() == null) {
			return msg;
		}
		if (docrule.getFid() == null) {
			docrule.setFid(0);
		}
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql, docrule.getRuleid());
		if (docrules.size() > 0) {
			hqls = "update Docrule set sectionName = ?,fid = ? where ruleid = ?";
			this.getHibernateTemplate().bulkUpdate(hqls, docrule.getSectionname(), docrule.getFid(),
					docrule.getRuleid());
			return "成功";
		}
		return msg;
	}

}
