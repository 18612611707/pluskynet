package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.domain.Latitude;
import com.pluskynet.otherdomain.Treelatitude;
@SuppressWarnings("all")
public class LatitudeDaoImpl extends HibernateDaoSupport implements LatitudeDao {

	@Override
	public String save(Latitude latitude) {
		String hql = "from Latitude where latitudename = ?";
		List<Latitude> list = this.getHibernateTemplate().find(hql,latitude.getLatitudename());
		if(list.size()>0){
			String queryStr = "update Latitude set latitudename = ? ,latitudefid = ?  where latitudeid = ?";
			this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),latitude.getLatitudeid());
		}else {
			this.getHibernateTemplate().save(latitude);
		}
		return "成功";
	}

	@Override
	public String update(Latitude latitude) {
		String hql = "from Latitude where latitudeid = ?";
		List<Latitude> list = this.getHibernateTemplate().find(hql,latitude.getLatitudeid());
		if(list.size()>0){
			if (latitude.getLatitudename()==null || latitude.getLatitudename().equals("")) {
				String queryStr = "update Latitude set rule = ? ,ruletype = ?  where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getRule(),latitude.getRuletype(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";	
			}else if (latitude.getRule()==null || latitude.getRule().equals("")) {
				String queryStr = "update Latitude set latitudename = ? ,latitudefid = ?  where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";	
			}else{
				String queryStr = "update Latitude set latitudename = ? ,latitudefid = ? ,rule = ?,ruletype=? where latitudeid = ?";
				this.getHibernateTemplate().bulkUpdate(queryStr,latitude.getLatitudename(),latitude.getLatitudefid(),
						latitude.getRule(),latitude.getRuletype(),latitude.getLatitudeid());
				this.getHibernateTemplate().flush();
				return "成功";
			}
			
		}
		return "失败";
	}
	@Override
	//查询一级菜单  
	public List<Treelatitude> getFirstLevel() {
		String hql =null;
		hql = "from Latitude where latitudefid = 0";
		List<Latitude> listFirstLevel = this.getHibernateTemplate().find(hql);
		List<Treelatitude> list = new ArrayList<Treelatitude>();
		for (int i = 0; i < listFirstLevel.size(); i++) {
			Treelatitude treelatitude = new Treelatitude();
			treelatitude.setLatitudeid(listFirstLevel.get(i).getLatitudeid());
			treelatitude.setLatitudefid(listFirstLevel.get(i).getLatitudefid());
			treelatitude.setLatitudename(listFirstLevel.get(i).getLatitudename());
			list.add(treelatitude);
		}
		return list;
	}

	@Override
	 //根据一级id查询所有的子集
	public List<Treelatitude> getNextSubSet(Treelatitude voteTree) {
		 String hql = "from Latitude where latitudefid = ?";  
	        List<Latitude> tNextLevel = this.getHibernateTemplate().find(hql,voteTree.getLatitudeid()); 
	        List<Treelatitude> list = new ArrayList<Treelatitude>();
	        for (int i = 0; i < tNextLevel.size(); i++) {
	        	//遍历这个二级目录的集合
				Treelatitude treelatitude = new Treelatitude();
				treelatitude.setLatitudeid(tNextLevel.get(i).getLatitudeid());
				treelatitude.setLatitudefid(tNextLevel.get(i).getLatitudefid());
				treelatitude.setLatitudename(tNextLevel.get(i).getLatitudename());
			            List<Treelatitude> ts = getDeeptLevel(tNextLevel.get(i));  
			            //将下面的子集都依次递归进来 
			            treelatitude.setChildren(ts);
			            list.add(treelatitude);
			}
	        return list;  
	}
	private List<Treelatitude> getDeeptLevel(Latitude latitude) {
		 String hql = "from Latitude where latitudefid = ?";  
	     List<Latitude> tsLevel = this.getHibernateTemplate().find(hql,latitude.getLatitudeid());
	     List<Treelatitude> list = new ArrayList<Treelatitude>();
	     if(tsLevel.size()>0){  
	            for (int i = 0; i <tsLevel.size(); i++) {  
	            	Treelatitude treelatitude = new Treelatitude();
	            	treelatitude.setLatitudeid(tsLevel.get(i).getLatitudeid());
					treelatitude.setLatitudefid(tsLevel.get(i).getLatitudefid());
					treelatitude.setLatitudename(tsLevel.get(i).getLatitudename());
					treelatitude.setChildren(getDeeptLevel(tsLevel.get(i)));
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
}
