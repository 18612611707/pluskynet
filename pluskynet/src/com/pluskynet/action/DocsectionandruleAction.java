package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.service.DocSectionAndRuleService;
import com.pluskynet.util.BaseAction;

import net.sf.json.JSONObject;
@SuppressWarnings("all")
public class DocsectionandruleAction extends BaseAction{
	private Docsectionandrule docsectionandrule;
	public void setDocsectionandrule(Docsectionandrule docsectionandrule) {
		this.docsectionandrule = docsectionandrule;
	}
	@Override
	public Docsectionandrule getModel() {
		// TODO Auto-generated method stub
		return docsectionandrule;
	}
	private DocSectionAndRuleService docSectionAndRuleService;
	public void setDocSectionAndRuleService(DocSectionAndRuleService docSectionAndRuleService) {
		this.docSectionAndRuleService = docSectionAndRuleService;
	}
	private JSONObject doclist;
	public JSONObject getDoclist() {
		return doclist;
	}
	public void setDoclist(JSONObject doclist) {
		this.doclist = doclist;
	}
	/*
	 * 保存
	 */
	public String save(Docsectionandrule docsectionandrule,String table){
		docSectionAndRuleService.save(docsectionandrule,table);
		return "成功";
	}
	/*
	 * 获取所有符合和不符合规则的列表
	 */
	public String getDocList(){
		List<StatsDoc> list = docSectionAndRuleService.getDocList();
		doclist = JSONObject.fromObject(list);
		return "sucess";
	}
	/*
	 * 根据ID获取符合规则内容
	 */
	public void getDoc(){
		List<Docsectionandrule> list = docSectionAndRuleService.getDoc(docsectionandrule);
		if(list.size()>0){
			doclist = JSONObject.fromObject(list);
			outJsonByMsg(list, "成功");
		}else{
//			doclist.put("msg", "失败");
			outJsonByMsg("失败");
		}
	}
	public void update(String doctable,String sectionname){
		docSectionAndRuleService.update(doctable,sectionname);
	}
	public void plsave(List<Docsectionandrule> docsectionlist, String doctable) {
		docSectionAndRuleService.plsave(docsectionlist,doctable);
		
	}
	public void delete(Docsectionandrule docsectionandrule,String doctable){
		docSectionAndRuleService.delete(docsectionandrule,doctable);
	}
}
