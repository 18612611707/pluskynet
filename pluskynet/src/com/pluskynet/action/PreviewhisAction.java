package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Latitudestatistical;
import com.pluskynet.domain.Previewhis;
import com.pluskynet.service.PreviewhisService;
import com.pluskynet.util.BaseAction;

public class PreviewhisAction extends BaseAction{
	private Previewhis previewhis;

	@Override
	public Object getModel() {
		previewhis = new Previewhis();
		return previewhis;
	}
	private PreviewhisService previewhisService;
	public void setPreviewhisService(PreviewhisService previewhisService) {
		this.previewhisService = previewhisService;
	}
	private String starttime;
	private String endtime;
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/*
	 * 查询预览历史
	 */
	public void select(){
		List<Previewhis> list = previewhisService.select(starttime,endtime);
		outJsonByMsg(list, "成功");
	}
	/*
	 * 保存预览历史
	 */
	public void save(){
		previewhisService.save(previewhis);
	}
	/*
	 * 结果数据展示
	 */
	public void latitudestatistical(){
		List<Latitudestatistical> list = previewhisService.latitudestatistical();
		outJsonByMsg(list, "成功");
	}
}
