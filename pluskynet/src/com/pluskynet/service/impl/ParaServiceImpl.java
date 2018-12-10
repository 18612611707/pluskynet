package com.pluskynet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.dao.ParaDao;
import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.domain.TParaVector;
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
		JSONObject js = jsonObject.getJSONObject("data");
		TParaCri tParaCri = new TParaCri();
		if (js.getString("pc_id").equals("")) {

		} else {
			tParaCri.setPcId(Integer.valueOf(js.getString("pc_id")));
		}
		// System.out.println(js.getString("pc_causeId") + ",----------" +
		// js.getString("pc_order"));
		if (js.getString("pc_causeId").equals("")) {

		} else {
			tParaCri.setPcCauseId(Integer.valueOf(js.getString("pc_causeId")));
		}
		if (js.getString("pc_order").equals("")) {

		} else {
			tParaCri.setPcOrder(Integer.valueOf(js.getString("pc_order")));
		}
		if (js.getString("pc_id").equals("")) {
			map = paraDao.save(tParaCri);
			paraDao.delete((Integer) map.get("pc_id"));
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
			for (int i = 0; i < jsonArray.size(); i++) {
				String criGrpjsonObject = jsonArray.get(i).toString();
				TParaCriGrp criGrp = new TParaCriGrp();
				criGrp.setPcgOrder(i);
				criGrp.setPcId((Integer) map.get("pc_id"));
				criGrp.setPgId(Integer.valueOf(criGrpjsonObject));
				int pcg_id = paraDao.saveCri(criGrp);
			}
		} else {
			map = paraDao.update(tParaCri);
			paraDao.delete((Integer) map.get("pc_id"));
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("list"));
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = JSONObject.fromObject(jsonArray.get(i));
				TParaCriGrp criGrp = new TParaCriGrp();
				criGrp.setPcgOrder(i);
				criGrp.setPcId((Integer) map.get("pc_id"));
				criGrp.setPgId(Integer.valueOf(jsonObject2.getString("pg_id")));
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
		// JSONObject tparaJsonObject =
		// JSONObject.fromObject(jsonObject.getString("data"));
		tParaGrp.setPgName(jsonObject.getString("pg_name"));
		tParaGrp.setPgShow(jsonObject.getString("pg_show"));
		if (!jsonObject.getString("pg_id").equals("")) {
			tParaGrp.setPgId(Integer.valueOf(jsonObject.getString("pg_id")));
			paraDao.updatetParaGrp(tParaGrp);
		} else {
			int pg_id = paraDao.savetParaGrp(tParaGrp);
		}
		// if (pg_id!=-1) {
		// JSONArray jsonArray =
		// JSONArray.fromObject(jsonObject.getString("list"));
		// for (int i = 0; i < jsonArray.size(); i++) {
		// JSONObject criGrpjsonObject =
		// JSONObject.fromObject(jsonArray.get(i));
		// TParaCriGrp criGrp = new TParaCriGrp();
		// criGrp.setPcgOrder(criGrpjsonObject.getInt("pcg_order"));
		// criGrp.setPcId(criGrpjsonObject.getInt("pc_id"));
		// criGrp.setPgId(pg_id);
		// int pcg_id = paraDao.saveCri(criGrp);
		// }
		// }else{
		// return "失败";
		// }
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
		Map map = new HashMap();
		int po_id = -1;
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONObject js = JSONObject.fromObject(jsonObject.getString("data"));
		TParaOne tParaOne = new TParaOne();
		tParaOne.setPoName(js.getString("po_name"));
		tParaOne.setPoPid(Integer.valueOf(js.getString("po_pid")));
		tParaOne.setPoOrder(Integer.valueOf(js.getString("po_order")));
		tParaOne.setPoType(js.getString("po_type"));
		/*if (!js.getString("po_isPara").equals("")) {
			tParaOne.setPoIsPara(Integer.valueOf(js.getString("po_isPara")));
		}*/
		tParaOne.setPgId(Integer.valueOf(js.getString("pg_id")));
		tParaOne.setPoRootId(Integer.valueOf(js.getString("po_rootId")));
		tParaOne.setPoTier(Integer.valueOf(js.getString("po_tier")));
		tParaOne.setPoRelation(js.getString("po_relation"));
		tParaOne.setPoLink(js.getString("po_link"));
		tParaOne.setPoProp(js.getString("po_prop"));
		tParaOne.setPoPropRelate(js.getString("po_propRelate"));
		tParaOne.setPoPropValue(js.getString("po_propValue"));
		tParaOne.setPoRangeType(js.getString("po_rangeType"));
		if (js.has("po_propKey")) {
			tParaOne.setPoPropKey(js.getString("po_propKey"));
		}
		if (!js.getString("po_id").equals("")) {
			tParaOne.setPoId(Integer.valueOf(js.getString("po_id")));
			po_id = paraDao.updateInfoOne(tParaOne);
		} else {
			po_id = paraDao.saveInfoOne(tParaOne);
		}
		JSONArray jsaArray = JSONArray.fromObject(jsonObject.getString("list"));
		boolean a = paraDao.deletevector(po_id);
		for (int i = 0; i < jsaArray.size(); i++) {
			String vejs = jsaArray.get(i).toString();
			TParaVector tParaVector = new TParaVector();
			tParaVector.setPgId(Integer.valueOf(js.getString("pg_id")));
			tParaVector.setPoId(po_id);
			tParaVector.setPvVectorId(Integer.valueOf(vejs));
			paraDao.savevector(tParaVector);
		}
		map.put("po_id", po_id);
		return map;
	}

	@Override
	public List<TParaCriGrp> cri2GrpList(int pc_id) {
		List<TParaCriGrp> list = paraDao.cri2GrpList(pc_id);
		return list;
	}

	@Override
	public List<TParaVector> oneVectorList(int pg_id) {
		List<TParaVector> list = paraDao.oneVectorList(pg_id);
		return list;
	}

}
