package com.pluskynet.action;

import com.pluskynet.domain.Rule;
import com.pluskynet.domain.Sample;
import com.pluskynet.domain.User;
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
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		sampleService.random(sample,user);
		outJsonByMsg("成功");
	}
	public void select(){
		User user = isLogined();
		if (user==null) {
			outJsonByMsg("未登录");
			return;
		}
		sample = sampleService.select(user);
		outJsonByMsg(sample, "成功");
	}

}
