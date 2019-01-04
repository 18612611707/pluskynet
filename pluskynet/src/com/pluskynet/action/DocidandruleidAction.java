package com.pluskynet.action;

import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.service.DocidandruleService;
import com.pluskynet.util.BaseAction;

public class DocidandruleidAction extends BaseAction{
	private Docidandruleid docidandruleid;
	@Override
	public Object getModel() {
		docidandruleid = new Docidandruleid();
		return docidandruleid;
	}
	private DocidandruleService docidandruleService;
	public void setDocidandruleService(DocidandruleService docidandruleService) {
		this.docidandruleService = docidandruleService;
	}
	public void save(){
		docidandruleService.save(docidandruleid);
	}
	public void delete(){
		docidandruleService.delete(docidandruleid);
	}

}
