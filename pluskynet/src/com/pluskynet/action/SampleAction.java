package com.pluskynet.action;

import com.pluskynet.domain.Rule;
import com.pluskynet.domain.Sample;
import com.pluskynet.service.SampleService;
import com.pluskynet.util.BaseAction;

public class SampleAction extends BaseAction{
	private Sample sample;

	@Override
	public Object getModel() {
		sample = new Sample();
		return sample;
	}
	private SampleService sampleService;
	
	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	public void random(){
		sampleService.random(sample);
		outJsonByMsg("成功");
	}
	public void select(){
		sample = sampleService.select();
		outJsonByMsg(sample, "成功");
	}

}
