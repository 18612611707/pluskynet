package com.pluskynet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.dao.ParaDao;
import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.service.ParaService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ParaServiceImpl implements ParaService {
	private ParaDao paraDao;

	public void setParaDao(ParaDao paraDao) {
		this.paraDao = paraDao;
	}


	@Override
	public List<TParaCri> criList() {
		List<TParaCri> list = paraDao.criList();
		return list;
	}


	@Override
	public Map saveCri(String data) {
		Map map = new HashMap();
		JSONObject jsonObject = JSONObject.fromObject(data);
		TParaCri tParaCri = new TParaCri();
		tParaCri.setPcId(jsonObject.getInt("pc_id"));
		tParaCri.setPcgCauseId(jsonObject.getInt("pcg_causeId"));
		tParaCri.setPcgOrder(jsonObject.getInt("pcg_order"));
		if (tParaCri.getPcId().toString().equals("")) {
			map = paraDao.save(tParaCri);
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject criGrpjsonObject = JSONObject.fromObject(jsonArray.get(i));
				TParaCriGrp criGrp = new TParaCriGrp();
				criGrp.setPcgOrder(criGrpjsonObject.getInt("pcg_order"));
				criGrp.setPcId((Integer) map.get("pc_id"));
				criGrp.setPgId(criGrpjsonObject.getInt("pg_id"));
				int pcg_id = paraDao.saveCri(criGrp);
			}
		}else{
			map = paraDao.update(tParaCri);
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject criGrpjsonObject = JSONObject.fromObject(jsonArray.get(i));
				TParaCriGrp criGrp = new TParaCriGrp();
				criGrp.setPcgOrder(criGrpjsonObject.getInt("pcg_order"));
				criGrp.setPcId((Integer) map.get("pc_id"));
				criGrp.setPgId(criGrpjsonObject.getInt("pg_id"));
				int pcg_id = paraDao.saveCri(criGrp);
			}
		}
		return map;
	}


	@Override
	public List<TParaGrp> grpList() {
		List<TParaGrp> list = paraDao.grpList();
		return list;
	}


	@Override
	public String saveGrp(String data) {
		TParaGrp tParaGrp = new TParaGrp();
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONObject tparaJsonObject = JSONObject.fromObject(jsonObject.getString("data"));
		tParaGrp.setPgName(tparaJsonObject.getString("pg_name"));
		int pg_id = paraDao.savetParaGrp(tParaGrp);
//		if (pg_id!=-1) {
//			JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
//			for (int i = 0; i < jsonArray.size(); i++) {
//				JSONObject criGrpjsonObject = JSONObject.fromObject(jsonArray.get(i));
//				TParaCriGrp criGrp = new TParaCriGrp();
//				criGrp.setPcgOrder(criGrpjsonObject.getInt("pcg_order"));
//				criGrp.setPcId(criGrpjsonObject.getInt("pc_id"));
//				criGrp.setPgId(pg_id);
//				int pcg_id = paraDao.saveCri(criGrp);
//			}
//		}else{
//			return "失败";
//		}
		return "成功";
	}


	@Override
	public List<TParaOne> grpInfoList(int pg_id) {
		List<TParaOne> list = paraDao.grpInfoList(pg_id);
		return list;
	}


	@Override
	public List<TParaOne> grpInfoDetail(int po_rootId) {
		List<TParaOne> list = paraDao.grpInfoDetail(po_rootId);
		return list;
	}


	@Override
	public Map saveInfoOne(String data) {
		JSONObject jsonObject = JSONObject.fromObject(data);
		TParaOne tParaOne = new TParaOne();
		tParaOne.setPoName(jsonObject.getString("po_name"));
		tParaOne.setPoPid(jsonObject.getInt("po_pid"));
		tParaOne.setPoOrder(jsonObject.getInt("po_order"));
		tParaOne.setPoType(jsonObject.getString("po_type"));
		tParaOne.setPoIsPara(jsonObject.getInt("po_isPara"));
		tParaOne.setPgId(jsonObject.getInt("pg_id"));
		tParaOne.setPoRootId(jsonObject.getInt("po_rootId"));
		tParaOne.setPoTier(jsonObject.getInt("po_tier"));
		Map map = paraDao.saveInfoOne(tParaOne);
		return map;
	}


	@Override
	public List<TParaCriGrp> cri2GrpList(int pc_id) {
		List<TParaCriGrp> list = paraDao.cri2GrpList(pc_id);
		return list;
	}

}
