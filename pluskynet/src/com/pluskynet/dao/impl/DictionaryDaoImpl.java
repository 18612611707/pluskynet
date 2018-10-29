package com.pluskynet.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.DictionaryDao;
import com.pluskynet.domain.Dictionary;
import com.pluskynet.otherdomain.Dictionarytree;

public class DictionaryDaoImpl extends HibernateDaoSupport implements DictionaryDao{

	@Override
	public List<Dictionarytree> first() {
		String sqlString = "from Dictionary where fid = 0";
		List<Dictionary> list = this.getHibernateTemplate().find(sqlString);
		List<Dictionarytree> firstList = new ArrayList<Dictionarytree>();
		for (int i = 0; i < list.size(); i++) {
			Dictionarytree dictionarytree = new Dictionarytree();
			dictionarytree.setFid(list.get(i).getFid());
			dictionarytree.setId(list.get(i).getId());
			dictionarytree.setName(list.get(i).getName());
			firstList.add(dictionarytree);
		}
		return firstList;
	}

	@Override
	public List<Dictionarytree> getNextSubSet(Dictionarytree voteTree) {
		String sql = "from Dictionary where fid = ?";
		List<Dictionary> tNextLevel = this.getHibernateTemplate().find(sql,voteTree.getId()); 
        List<Dictionarytree> list = new ArrayList<Dictionarytree>();
        for (int i = 0; i < tNextLevel.size(); i++) {
        	//遍历这个二级目录的集合
        	Dictionarytree treelatitude = new Dictionarytree();
			treelatitude.setId(tNextLevel.get(i).getId());
			treelatitude.setFid(tNextLevel.get(i).getFid());
			treelatitude.setName(tNextLevel.get(i).getName());
		            List<Dictionarytree> ts = getDeeptLevel(tNextLevel.get(i));  
		            //将下面的子集都依次递归进来 
		            treelatitude.setChildren(ts);
		            list.add(treelatitude);
		}
        return list;  
	}

	private List<Dictionarytree> getDeeptLevel(Dictionary dictionary) {
		String sql = "from Dictionary where fid = ?";
		List<Dictionary> tNextLevel = this.getHibernateTemplate().find(sql,dictionary.getId()); 
        List<Dictionarytree> list = new ArrayList<Dictionarytree>();
        for (int i = 0; i < tNextLevel.size(); i++) {
        	//遍历这个二级目录的集合
        	Dictionarytree treelatitude = new Dictionarytree();
			treelatitude.setId(tNextLevel.get(i).getId());
			treelatitude.setFid(tNextLevel.get(i).getFid());
			treelatitude.setName(tNextLevel.get(i).getName());
		            List<Dictionarytree> ts = getDeeptLevel(tNextLevel.get(i));  
		            //将下面的子集都依次递归进来 
		            treelatitude.setChildren(ts);
		            list.add(treelatitude);
		}
        return list;  
	}

	@Override
	public String addDic(Dictionary dictionary) {
		String sql = "from Dictionary where name = ?";
		List<Dictionary> list = this.getHibernateTemplate().find(sql,dictionary.getName());
		if (list.size()>0) {
			return "名称已存在";
		}
		this.getHibernateTemplate().save(dictionary);
		return "成功";
	}

	@Override
	public String updateDic(Dictionary dictionary) {
		String hql = "from Dictionary where id = ?";
		List<Dictionary> list = this.getHibernateTemplate().find(hql,dictionary.getId());
		if (list.size()>0) {
			this.getHibernateTemplate().update(dictionary);
			return "成功";
		}
		return "不存在";
	}

	@Override
	public String deleteDic(Dictionary dictionary) {
		String hql = "from Dictionary where id = ?";
		List<Dictionary> list = this.getHibernateTemplate().find(hql,dictionary.getId());
		if (list.size()>0) {
			this.getHibernateTemplate().delete(dictionary);
			return "成功";
		}
		return "不存在";
	}

	@Override
	public List<Dictionarytree> getDicname(String code) {
		String sqlString = "from Dictionary where code = ?";
		List<Dictionary> list = this.getHibernateTemplate().find(sqlString,code);
		List<Dictionarytree> firstList = new ArrayList<Dictionarytree>();
		for (int i = 0; i < list.size(); i++) {
			String hql = "from Dictionary where fid = ?";
			List<Dictionary> lists = this.getHibernateTemplate().find(hql,list.get(i).getId());
			for (int j = 0; j < lists.size(); j++) {
				Dictionarytree dictionarytree = new Dictionarytree();
				dictionarytree.setFid(lists.get(i).getFid());
				dictionarytree.setId(lists.get(i).getId());
				dictionarytree.setName(lists.get(i).getName());
				firstList.add(dictionarytree);
			}
		}
		return firstList;
	}

	@Override
	public List<Dictionary> getNextdic(Integer id) {
		String hql = "from Dictionary where fid = ?";
		List<Dictionary> lists = this.getHibernateTemplate().find(hql,id);
		return lists;
	}

	@Override
	public List<Dictionary> getCodedic(String code) {
		String hql = "from Dictionary where code = ?";
		List<Dictionary> lists = this.getHibernateTemplate().find(hql,code);
		return lists;
	}

}
