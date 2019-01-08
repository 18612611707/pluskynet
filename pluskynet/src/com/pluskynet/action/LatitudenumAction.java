package com.pluskynet.action;

import java.util.ArrayList;
import java.util.List;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.service.LatitudenumService;
import com.pluskynet.util.BaseAction;
import com.pluskynet.util.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LatitudenumAction extends BaseAction{
	/*
	 * 统计各个维度的数量
	 */
	private Latitudenum latitudenum;
	
	public Latitudenum getLatitudenum() {
		return latitudenum;
	}

	public void setLatitudenum(Latitudenum latitudenum) {
		this.latitudenum = latitudenum;
	}

	@Override
	public Object getModel() {
		latitudenum = new Latitudenum();
		return latitudenum;
	}
	private LatitudenumService latitudenumService;
	
	public void setLatitudenumService(LatitudenumService latitudenumService) {
		this.latitudenumService = latitudenumService;
	}
	/*
	 * 统计各个新跑维度 type=1 ,各个新跑段落 type = 0 的数量
	 */
	public void countlat(){
		List<Latitudenum> list= latitudenumService.countlat(latitudenum.getType());
		JSONArray jsonList = JSONArray.fromObject(list);
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.sendPost("http://39.104.183.189:8081/pluskynet/LatitudenumAction!updatelat.action","numlist="+jsonList.toString());
		outJsonByMsg(list,list.size(),"成功","");
	}
	/*
	 * 同步统计结果
	 */
	public String numlist;
	
	public String getNumlist() {
		return numlist;
	}

	public void setNumlist(String numlist) {
		this.numlist = numlist;
	}

	public void updatelat(String numlist){
		JSONArray json = JSONArray.fromObject(numlist);
		List<Latitudenum> list = JSONArray.toList(json,Latitudenum.class);
		latitudenumService.updatelat(list);
	}
}
