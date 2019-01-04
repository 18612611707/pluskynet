package com.pluskynet.action;

import com.pluskynet.domain.Batchdata;
import com.pluskynet.service.BatchdataService;
import com.pluskynet.util.BaseAction;

public class BatchdataAction extends BaseAction{
	private Batchdata batchdata;

	@Override
	public Object getModel() {
		batchdata = new Batchdata();
		return batchdata;
	}
	public BatchdataService batchdataService;

	public void setBatchdataService(BatchdataService batchdataService) {
		this.batchdataService = batchdataService;
	}
	
	public void save(){
		batchdataService.save(batchdata);
	}
	public void delete(){
		batchdataService.delete(batchdata);
	}

}
