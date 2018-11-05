package com.pluskynet.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.ParaDao;
import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;

public class ParaDaoImpl extends HibernateDaoSupport implements ParaDao {

	@Override
	public List<TParaCri> criList() {
		String sql = "from TParaCri";
		List<TParaCri> list = this.getHibernateTemplate().find(sql);
		return list;
	}

	@Override
	public Map save(TParaCri tParaCri) {
		this.getHibernateTemplate().save(tParaCri);
		Map map = new HashMap();
		map.put("pc_id", tParaCri.getPcId());
		return map;
	}

	@Override
	public Map update(TParaCri tParaCri) {
		this.getHibernateTemplate().update(tParaCri);
		Map map = new HashMap();
		map.put("pc_id", tParaCri.getPcId());
		return map;
	}

	@Override
	public List<TParaGrp> grpList() {
		String sql = "from TParaGrp";
		List<TParaGrp> list = this.getHibernateTemplate().find(sql);
		return list;
	}

	@Override
	public int savetParaGrp(TParaGrp tParaGrp) {
		int pg_id = -1 ;
		this.getHibernateTemplate().save(tParaGrp);
		pg_id = tParaGrp.getPgId();
		return pg_id;
	}

	@Override
	public int saveCri(TParaCriGrp criGrp) {
		this.getHibernateTemplate().save(criGrp);
		return criGrp.getPcgId();
	}

	@Override
	public Map saveInfoOne(TParaOne tParaOne) {
		this.getHibernateTemplate().save(tParaOne);
		Map map = new HashMap();
		map.put("po_id", tParaOne.getPoId());
		return map;
	}

	@Override
	public List<TParaOne> grpInfoDetail(int po_rootId) {
		String sql = "from TParaOne where poRootId = ?";
		List<TParaOne> list = this.getHibernateTemplate().find(sql,po_rootId);
		return list;
	}

	@Override
	public List<TParaOne> grpInfoList(int pg_id) {
		String sql = "from TParaOne where pgId = ? and poTier = 0";
		List<TParaOne> list = this.getHibernateTemplate().find(sql,pg_id);
		return list;
	}

	@Override
	public List<TParaCriGrp> cri2GrpList(int pc_id) {
		String sql = "from TParaCriGrp where pcId = ?";
		List<TParaCriGrp> list = this.getHibernateTemplate().find(sql,pc_id);
		return list;
	}

}
