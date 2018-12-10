package com.pluskynet.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.domain.TParaVector;
import com.pluskynet.service.ParaService;
import com.pluskynet.util.BaseAction;

public class ParaAction extends BaseAction {
	// 前台参数
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private TParaCri tParaCri;

	@Override
	public Object getModel() {
		this.tParaCri = new TParaCri();
		return tParaCri;
	}

	private ParaService paraService;

	public void setParaService(ParaService paraService) {
		this.paraService = paraService;
	}

	// 获取案件类型列表
	public void criList() {
		List<TParaCri> list = paraService.criList();
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("pc_id", list.get(i).getPcId());
			map.put("pc_causeId", list.get(i).getPcCauseId());
			map.put("pc_order", list.get(i).getPcOrder());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}

	// 保存或修改案件信息
	public void saveCri() {
		Map map = paraService.saveCri(data);
		outJsonByMsg(map, "成功");
	}

	// 获取分类列表
	public void grpList() {
		List<TParaGrp> list = paraService.grpList();
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("pg_id", list.get(i).getPgId());
			map.put("pg_name", list.get(i).getPgName());
			map.put("pg_show", list.get(i).getPgShow());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}

	// 保存分组信息
	public void saveGrp() {
		String msg = paraService.saveGrp(data);
		outJsonByMsg(msg);
	}

	// 获取某一分类的内容列表
	private int pg_id;

	public int getPg_id() {
		return pg_id;
	}

	public void setPg_id(int pg_id) {
		this.pg_id = pg_id;
	}

	public void grpInfoList() {
		List<TParaOne> list = paraService.grpInfoList(pg_id);
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("po_id", list.get(i).getPoId());
			map.put("pg_id", list.get(i).getPgId());
			map.put("po_prop", list.get(i).getPoProp());
			map.put("po_name", list.get(i).getPoName());
			map.put("po_order", list.get(i).getPoOrder());
			map.put("po_pid", list.get(i).getPoPid());
			map.put("po_rootId", list.get(i).getPoRootId());
			map.put("po_tier", list.get(i).getPoTier());
			map.put("po_type", list.get(i).getPoType());
			map.put("po_link", list.get(i).getPoLink());
			map.put("po_propRelate", list.get(i).getPoPropRelate());
			map.put("po_propValue", list.get(i).getPoPropValue());
			map.put("po_rangeType", list.get(i).getPoRangeType());
			map.put("po_propKey", list.get(i).getPoPropKey());
			map.put("po_relation", list.get(i).getPoRelation());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}

	// 获取某一个内容的信息
	private int po_rootId;

	public int getPo_rootId() {
		return po_rootId;
	}

	public void setPo_rootId(int po_rootId) {
		this.po_rootId = po_rootId;
	}

	public void grpInfoDetail() {
		List<TParaOne> list = paraService.grpInfoDetail(po_rootId);
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("po_id", list.get(i).getPoId());
			map.put("pg_id", list.get(i).getPgId());
			map.put("po_isPara", list.get(i).getPoIsPara());
			map.put("po_name", list.get(i).getPoName());
			map.put("po_order", list.get(i).getPoOrder());
			map.put("po_pid", list.get(i).getPoPid());
			map.put("po_rootId", list.get(i).getPoRootId());
			map.put("po_tier", list.get(i).getPoTier());
			map.put("po_type", list.get(i).getPoType());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}

	// 保存内容信息
	public void saveInfoOne() {
		Map map = paraService.saveInfoOne(data);
		outJsonByMsg(map, "成功");
	}

	private int pc_id;

	public int getPc_id() {
		return pc_id;
	}

	public void setPc_id(int pc_id) {
		this.pc_id = pc_id;
	}

	public void cri2GrpList() {
		List<TParaCriGrp> list = paraService.cri2GrpList(pc_id);
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("pcg_id", list.get(i).getPcgId());
			map.put("pcg_order",list.get(i).getPcgOrder());
			map.put("pc_id", list.get(i).getPcId());
			map.put("pg_id", list.get(i).getPgId());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}
	public void oneVectorList(){
		List<TParaVector> list = paraService.oneVectorList(pg_id);
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			map.put("pv_id", list.get(i).getPvId());
			map.put("pg_id", list.get(i).getPgId());
			map.put("po_id", list.get(i).getPoId());
			map.put("pv_vectorId", list.get(i).getPvVectorId());
			lists.add(map);
		}
		outJsonByMsg(lists, "成功");
	}

}
