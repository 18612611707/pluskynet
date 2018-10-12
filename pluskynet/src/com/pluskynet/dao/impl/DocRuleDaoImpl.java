package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
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
	public String save(Docrule docrule) {
		String msg = null ;
		String hql = "from Docrule where sectionName = ? and fid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,docrule.getSectionName(),docrule.getFid());
		if (docrules.size()>0) {
			String queryStr = "update Docrule set sectionName = ? ,fid = ? where ruleid = ?";
			this.getHibernateTemplate().bulkUpdate(queryStr,docrule.getSectionName(),docrule.getFid(),docrules.get(0).getRuleid());
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
		String hql = "from Docrule where ruleid = ? and fid = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,docrule.getRuleid(),docrule.getFid());
		if (docrules.size()>0) {
			if (docrule.getRule()==null || docrule.getRule().equals("")) {
				hqls = "update Docrule set sectionName = ?,fid = ? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getSectionName(),docrule.getFid(),docrule.getRuleid());
			}else if (docrule.getSectionName()==null || docrule.getSectionName().equals("")) {
				hqls = "update Docrule set rule = ?,fid = ?  where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(),docrule.getFid(),docrule.getRuleid());
			}else{
				hqls = "update Docrule set rule = ?,sectionName = ?,fid = ? where ruleid = ?";
				this.getHibernateTemplate().bulkUpdate(hqls, docrule.getRule(),docrule.getSectionName(),docrule.getFid(),docrule.getRuleid());
			}
			msg = "成功";
		}
		return msg;
	}

	@Override
	public List<Docrule> getDcoSectionList() {
		String hql = "from Docrule where fid = 0";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql);
//		if (docrules.size()>0) {
//			for (int i = 0; i < docrules.size(); i++) {
//				Map<String, Object> result=new HashMap<String, Object>();
//				result.put("ruleid",docrules.get(i).getRuleid());
//				result.put("sectionName", docrules.get(i).getSectionName());
//				list.add(result);
//			}
//		}
		return docrules;
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

	@Override
	public List<TreeDocrule> getNextSubSet(TreeDocrule voteTree) {
		String hql = "from Docrule where fid = ?";  
        List<Docrule> tNextLevel = this.getHibernateTemplate().find(hql,voteTree.getRuleid()); 
        List<TreeDocrule> list = new ArrayList<TreeDocrule>();
        for (int i = 0; i < tNextLevel.size(); i++) {
        	//遍历这个二级目录的集合
        	TreeDocrule treelatitude = new TreeDocrule();
			treelatitude.setRuleid(tNextLevel.get(i).getRuleid());
			treelatitude.setFid(tNextLevel.get(i).getFid());
			treelatitude.setSectionname(tNextLevel.get(i).getSectionName());
		            List<TreeDocrule> ts = getDeeptLevel(tNextLevel.get(i));  
		            //将下面的子集都依次递归进来 
		            treelatitude.setChildren(ts);
		            list.add(treelatitude);
		}
        return list;  
	}
	private List<TreeDocrule> getDeeptLevel(Docrule latitude) {
		 String hql = "from Latitude where latitudefid = ?";  
	     List<Docrule> tsLevel = this.getHibernateTemplate().find(hql,latitude.getRuleid());
	     List<TreeDocrule> list = new ArrayList<TreeDocrule>();
	     if(tsLevel.size()>0){  
	            for (int i = 0; i <tsLevel.size(); i++) {  
	            	TreeDocrule treelatitude = new TreeDocrule();
	            	treelatitude.setRuleid(tsLevel.get(i).getRuleid());
					treelatitude.setFid(tsLevel.get(i).getFid());
					treelatitude.setSectionname(tsLevel.get(i).getSectionName());
					treelatitude.setChildren(getDeeptLevel(tsLevel.get(i)));
					list.add(treelatitude);
	            }  
	        }  
	        return list;
	}

	@Override
	public List<Docrule> getSecNameShow(String sectionname) {
		String hql = "from Docrule where sectionname = ?";
		List<Docrule> docrules = this.getHibernateTemplate().find(hql,sectionname);
		return docrules;
	}

	@Override
	public List<Docrule> getRuleShow(Integer ruleid, String causeo, String causet, String spcx, String doctype) {
		String hql = "from Docrule where ruleid = ?";
		List<Docrule> list = this.getHibernateTemplate().find(hql,ruleid);
		List<Docrule> lists = new ArrayList<Docrule>();
		for (int i = 0; i < list.size(); i++) {
			Docrule docrule = new Docrule();
			String ruleString = "";
			JSONArray jsonarray = JSONArray.fromObject(list.get(i).getRule());
			for (int j = 0; j < jsonarray.size(); j++) {
				JSONObject jss = JSONObject.fromObject(jsonarray.get(j));
				if (causeo.length()!=0) {
					if(!jss.getString("causeo").equals(causeo)){
						continue;
					}
				}
				if (causet.length()!=0) {
					if(!jss.getString("causet").equals(causet)){
						continue;
					}
				}
				if (spcx.length()!=0) {
					if(!jss.getString("trialRound").equals(spcx)){
						continue;
					}
				}
				if (doctype.length()!=0) {
					if(!jss.getString("doctype").equals(doctype)){
						continue;
					}
				}
				ruleString = ruleString + jsonarray.get(j).toString();
			}
			docrule.setFid(list.get(i).getFid());
			docrule.setRuleid(list.get(i).getRuleid());
			docrule.setSectionName(list.get(i).getSectionName());
			docrule.setRule(ruleString);
			lists.add(docrule);
		}
				
		return lists;
	}

}
