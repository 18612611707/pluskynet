package com.pluskynet.action;

import java.util.List;

import com.pluskynet.service.BatchStatisticalService;
import com.pluskynet.util.BaseAction;

public class BatchStatisticalAction extends BaseAction {
	private BatchStatisticalService batchStatisticalService;

	public void setBatchStatisticalService(BatchStatisticalService batchStatisticalService) {
		this.batchStatisticalService = batchStatisticalService;
	}

	public void docStatistical() {
		List<Integer> list = batchStatisticalService.docStatistical();
		outJsonByMsg(list, "成功");
	}

	public void laStatistical() {
		List<Integer> list = batchStatisticalService.laStatistical();
		outJsonByMsg(list, "成功");
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
