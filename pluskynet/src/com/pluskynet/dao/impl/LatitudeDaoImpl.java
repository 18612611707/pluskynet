package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.Treelatitude;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings("all")
public class LatitudeDaoImpl extends HibernateDaoSupport implements LatitudeDao {

	@Override
	public Map save(Latitude latitude,User user) {
		String hql = "from Latitude where latitudename = ?";
		List<Latitude> list = this.getHibernateTemplate().find(hql,latitude.getLatitudename());
		if(list.size()>0){
			String queryStr = "update Latitude set latitudename = ? ,latitudefid = ?  where latitudeid = ?";
			this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),latitude.getLatitudeid());
		}else {
			latitude.setCreateruser(user.getUsername());
			this.getHibernateTemplate().save(latitude);
		}
		Map map = new HashMap();
		list = this.getHibernateTemplate().find(hql,latitude.getLatitudename());
		map.put("latitudeid", list.get(0).getLatitudeid());
		return map;
	}

	@Override
	public String update(Latitude latitude,User user) {
		String hql = "from Latitude where latitudeid = ? and createruser = ?";
		List<Latitude> list = this.getHibernateTemplate().find(hql,latitude.getLatitudeid(),user.getUsername());
		if(list.size()>0){
			if (latitude.getLatitudename()==null || latitude.getLatitudename().equals("")) {
				String queryStr = "update Latitude set rule = ? ,ruletype = ? ,reserved =? where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getRule(),latitude.getRuletype(),latitude.getReserved(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";	
			}else if (latitude.getRule()==null || latitude.getRule().equals("")) {
				String queryStr = "update Latitude set latitudename = ? ,latitudefid = ?,reserved =?  where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),latitude.getReserved(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";	
			}else{
				String queryStr = "update Latitude set latitudename = ? ,latitudefid = ? ,rule = ?,ruletype=? ,reserved =? where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),
						latitude.getRule(),latitude.getRuletype(),latitude.getReserved(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";
			}
		}
		return "失败";
	}
	@Override
	//查询一级菜单  
	public List<Treelatitude> getFirstLevel(User user) {
		String hql =null;
		hql = "from Latitude where latitudefid = 0 and (createruser = ? or createruser = 'admin')  ";
		List<Latitude> listFirstLevel = this.getHibernateTemplate().find(hql,user.getUsername());
		List<Treelatitude> list = new ArrayList<Treelatitude>();
		for (int i = 0; i < listFirstLevel.size(); i++) {
			Treelatitude treelatitude = new Treelatitude();
			treelatitude.setLatitudeid(listFirstLevel.get(i).getLatitudeid());
			treelatitude.setLatitudefid(listFirstLevel.get(i).getLatitudefid());
			treelatitude.setLatitudename(listFirstLevel.get(i).getLatitudename());
			treelatitude.setCreator(listFirstLevel.get(i).getCreateruser());
			treelatitude.setStat(listFirstLevel.get(i).getStats());
			list.add(treelatitude);
		}
		return list;
	}

	@Override
	 //根据一级id查询所有的子集
	public List<Latitude> getNextSubSet(Treelatitude voteTree,User user) {
		 String hql = "from Latitude where latitudefid = ? and createruser = ? and createruser = 'admin'";  
	        List<Latitude> tNextLevel = this.getHibernateTemplate().find(hql,voteTree.getLatitudeid(),user.getUsername()); 
//	        List<Treelatitude> list = new ArrayList<Treelatitude>();
//	        for (int i = 0; i < tNextLevel.size(); i++) {
//	        	//遍历这个二级目录的集合
//				Treelatitude treelatitude = new Treelatitude();
//				treelatitude.setLatitudeid(tNextLevel.get(i).getLatitudeid());
//				treelatitude.setLatitudefid(tNextLevel.get(i).getLatitudefid());
//				treelatitude.setLatitudename(tNextLevel.get(i).getLatitudename());
//			            List<Treelatitude> ts = getDeeptLevel(tNextLevel.get(i));  
//			            //将下面的子集都依次递归进来 
//			            treelatitude.setChildren(ts);
//			            list.add(treelatitude);
//			}
	        return tNextLevel;  
	}
	@Override
	public List<Treelatitude> getDeeptLevel(Latitude latitude,User user) {
		 String hql = "from Latitude where latitudefid = ? and createruser = ? and createruser = 'admin'";  
	     List<Latitude> tsLevel = this.getHibernateTemplate().find(hql,latitude.getLatitudeid(),user.getUsername());
	     List<Treelatitude> list = new ArrayList<Treelatitude>();
	     if(tsLevel.size()>0){
	            for (int i = 0; i <tsLevel.size(); i++) {  
	            	Treelatitude treelatitude = new Treelatitude();
	            	treelatitude.setLatitudeid(tsLevel.get(i).getLatitudeid());
					treelatitude.setLatitudefid(tsLevel.get(i).getLatitudefid());
					treelatitude.setLatitudename(tsLevel.get(i).getLatitudename());
					treelatitude.setChildren(getDeeptLevel(tsLevel.get(i),user));
					treelatitude.setCreator(tsLevel.get(i).getCreateruser());
					treelatitude.setStat(tsLevel.get(i).getStats());
					list.add(treelatitude);
	            }  
	        }  
	        return list;
	}

	@Override
	public Latitude getLatitude(Latitude latitude) {
		String hqString = "from Latitude where latitudeid = ?";
		List<Latitude> list = this.getHibernateTemplate().find(hqString,latitude.getLatitudeid());
		return list.get(0);
	}

	@Override
	public List<String> getLatitudeName(Latitude latitude) {
		String hqString = "from Latitude where latitudeName like '%"+latitude.getLatitudename() +"%'";
		String s = "'%"+latitude.getLatitudename()+"%'";
		List<Latitude> list = this.getHibernateTemplate().find(hqString);
		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			strings.add(list.get(i).getLatitudename());
		}
		return strings;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getScreeList(String latitudeName ,Integer latitudeId) {
		String hql =null;
		if(latitudeName==null || latitudeName.equals("")){
		hql = "from Latitude where latitudeId = "+latitudeId+"";	
		}else{
		hql = "from Latitude where latitudeName = '"+latitudeName+"'";
		}
		List<Latitude> listLatitude = this.getHibernateTemplate().find(hql);
		hql = "from Latitude where latitudefid = ?";
		List<Latitude> listLatitudes = this.getHibernateTemplate().find(hql,listLatitude.get(0).getLatitudeid());
		
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < listLatitudes.size(); i++) {
			List<Latitude> nextList = this.getHibernateTemplate().find(hql,listLatitudes.get(i).getLatitudeid());
			Map<String, Comparable> map = new HashMap<String, Comparable>();
			map.put("latitudeid",listLatitudes.get(i).getLatitudeid());
			map.put("latitudename",listLatitudes.get(i).getLatitudename());
			if(nextList.size()>0){
				map.put("next", 1);
			}else{
				map.put("next", 0);
			}
			list.add(map);
		}
		return list;
	}
	public Integer selectid(String latitudeName){
		String hql = "from Latitude where latitudeName = '"+latitudeName+"'";
		List<Latitude> list = this.getHibernateTemplate().find(hql);
		if (list.size()>0) {
			return list.get(0).getLatitudeid();
		}
		return null;
	}

	@Override
	public List<Latitude> getLatitudeShow(String latitudename,User user) {
		String hql = "from Latitude where latitudeName = '"+latitudename+"' and createruser = ? ";
		List<Latitude> list = this.getHibernateTemplate().find(hql,user.getUsername());
		return list;
	}

	@Override
	public List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname) {
		String hql = "from Latitude where latitudeId = "+latitudeid+"";
		List<Latitude> list = this.getHibernateTemplate().find(hql);
		List<Latitude> lists = null;
		for (int i = 0; i < list.size(); i++) {
			Latitude latitude = new Latitude();
			JSONArray jsonArray = JSONArray.fromObject(list.get(i).getRule());
			String ruleString = "";
			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject jss = JSONObject.fromObject(jsonArray.get(i));
				if (spcx.length()!=0) {
					if (!jss.get("spcx").equals(spcx)) {
						continue;
					}
				}
				if (cause.length()!=0) {
					if (!jss.get("cause").equals(cause)) {
						continue;
					}
				}
				if (sectionname.length()!=0) {
					if (!jss.get("sectionname").equals(sectionname)) {
						continue;
					}
				}
				ruleString = ruleString + jsonArray.get(j).toString();
			}
			latitude.setLatitudefid(list.get(i).getLatitudefid());
			latitude.setLatitudeid(list.get(i).getLatitudeid());
			latitude.setRule(ruleString);
			latitude.setRuletype(list.get(i).getRuletype());
			latitude.setLatitudename(list.get(i).getLatitudename());
			lists.add(latitude);
		}
		
		return lists;
	}

	@Override
	public String updateName(Latitude latitude,User user) {
		String hql = "from Latitude where latitudeid = ? and createruser = ? ";
		List<Latitude> list = this.getHibernateTemplate().find(hql,latitude.getLatitudeid(),user.getUsername());
		if(list.size()>0){
				String queryStr = "update Latitude set latitudename = ? ,latitudefid = ?  where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";
	}
		return "段落不存在或无权限修改";
	}

	@Override
	public String approve(Latitude latitude,User user) {
		String sql = "from Latitude where latitudeid = ? and createruser = ? ";
		List<Latitude> list = this.getHibernateTemplate().find(sql,latitude.getLatitudeid(),user.getUsername());
		if (list.size()>0) {
			String hql = "update latitude set stats = ? where latitudeid = ? ";
			this.getHibernateTemplate().bulkUpdate(hql,latitude.getStats(),latitude.getLatitudeid());
			return "成功";
		}
		
		return "失败";
	}
}
