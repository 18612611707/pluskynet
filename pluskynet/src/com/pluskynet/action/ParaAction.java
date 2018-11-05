package com.pluskynet.action;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.service.ParaService;
import com.pluskynet.util.BaseAction;

public class ParaAction extends BaseAction{
	//前台参数
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
	public void criList(){
		List<TParaCri> list = paraService.criList();
		outJsonByMsg(list, "成功");
	}
	// 保存或修改案件信息
	public void saveCri(){
		Map map = paraService.saveCri(data);
		outJsonByMsg(map,"成功");
	}
	// 获取分类列表
	public void grpList(){
		List<TParaGrp> list = paraService.grpList();
		outJsonByMsg(list, "成功");
	}
	// 保存分组信息
	public void saveGrp(){
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
	public void grpInfoList(){
		List<TParaOne> list = paraService.grpInfoList(pg_id);
		outJsonByMsg(list, "成功");
	}
	// 获取某一个内容的信息
	private int po_rootId;
	
	public int getPo_rootId() {
		return po_rootId;
	}
	public void setPo_rootId(int po_rootId) {
		this.po_rootId = po_rootId;
	}
	public void grpInfoDetail(){
		List<TParaOne> list = paraService.grpInfoDetail(po_rootId);
	}
	// 保存内容信息
	public void saveInfoOne(){
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
	public void cri2GrpList(){
		List<TParaCriGrp> list = paraService.cri2GrpList(pc_id);
		outJsonByMsg(list, "成功");
	}

}
