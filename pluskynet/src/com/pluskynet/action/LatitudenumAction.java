package com.pluskynet.action;

import java.util.List;

import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.service.LatitudenumService;
import com.pluskynet.util.BaseAction;

public class LatitudenumAction extends BaseAction{
	
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

	public void countlat(){
		List<Latitudenum> list= latitudenumService.countlat(latitudenum.getType());
		outJsonByMsg(list, "成功");
	}

}
