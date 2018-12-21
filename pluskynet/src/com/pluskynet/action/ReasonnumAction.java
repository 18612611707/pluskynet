package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Reasonnum;
import com.pluskynet.service.ReasonnumService;
import com.pluskynet.util.BaseAction;

public class ReasonnumAction extends BaseAction{
	/**
	 * 获取各个案由的文书数量
	 */
	private static final long serialVersionUID = 1L;
	private ReasonnumService reasonnumService;

	public void setReasonnumService(ReasonnumService reasonnumService) {
		this.reasonnumService = reasonnumService;
	}
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void select(){
		List<Reasonnum> list = reasonnumService.select();
		outJsonByMsg(list, "成功");
//		return "sucess";
	}

	private Reasonnum reasonnum;
	
	public Reasonnum getReasonnum() {
		return reasonnum;
	}

	public void setReasonnum(Reasonnum reasonnum) {
		this.reasonnum = reasonnum;
	}

	@Override
	public Object getModel() {
		reasonnum = new Reasonnum();
		return reasonnum;
	}
}
