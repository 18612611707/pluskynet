package com.pluskynet.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.LatitudeDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
import com.pluskynet.service.LatitudeauditService;
@SuppressWarnings("all")
public class LatitudeauditServiceImpl implements LatitudeauditService {
	private LatitudeauditDao latitudeauditDao;

	public void setLatitudeauditDao(LatitudeauditDao latitudeauditDao) {
		this.latitudeauditDao = latitudeauditDao;
	}
	private CauseDao causeDao;
	
	public void setCauseDao(CauseDao causeDao) {
		this.causeDao = causeDao;
	}
	private LatitudeDao latitudeDao;

	public void setLatitudeDao(LatitudeDao latitudeDao) {
		this.latitudeDao = latitudeDao;
	}
	List<Latitude> lalist ;
	@Override
	public List<CauseAndName> getLatitudeList(int page, int rows) {
		List<CauseAndName> map = null;
		try {
			map = latitudeauditDao.getLatitudeauditList(page,rows);
			lalist = latitudeDao.getLatitudeList();
		} catch (SQLException e) {
			System.out.println("出错了");
			e.printStackTrace();
		}
		return map;
	}
	public List<String> fcause(int latitudeid) {
		List<String> lists = new ArrayList<String>();
		for (int i = 0; i < lalist.size(); i++) {
			if (lalist.get(i).getLatitudeid()==latitudeid) {
				for (int a = 0; a < lalist.size(); a++) {
					if (lalist.get(a).getLatitudeid().equals(lalist.get(i).getLatitudefid())) {
						lists.add(0, lalist.get(a).getLatitudename());
						lists.add(1, lalist.get(a).getLatitudeid().toString());
						return lists;
					}
				}
			}
		}
		return null;
	}
	@Override
	public int getCountBy() {
		int totalSize = latitudeauditDao.getCountBy();
		return totalSize;
	}

	@Override
	public String updateStats(String latitudeids) {
		String msg = latitudeauditDao.updateState(latitudeids);
		return msg;
	}

	@Override
	public List<Latitudeaudit> getLatitude(int latitudetype) {
		List<Latitudeaudit> list = latitudeauditDao.getLatitude(latitudetype);
		return list;
	}

	@Override
	public void updatebatchestats(List<Latitudeaudit> latitudeaudit) {
		latitudeauditDao.updatebatchestats(latitudeaudit);
		
	}

	@Override
	public List<DocidAndDoc> getDocList(String causename, int latitudetype, int num,int rows,int page,int ruleid) {
//		List<DocidAndDoc> list = null;
//		Cause cause = new Cause();
//		if (causename!=null) {
//			cause.setCausename(causename);
//			cause = causeDao.selectCause(cause);
//		}
//		if (causename!=null) {
		List<DocidAndDoc> list = latitudeauditDao.getDocList(causename,latitudetype,num,rows,page,ruleid);
//		}
		return list;
	}

	@Override
	public int getDocby(String causename, int latitudetype, int num, int ruleid) {
		int list = 0;
//		Cause cause = new Cause();
//		if (causename!=null) {
//			cause.setCausename(causename);
//			cause = causeDao.selectCause(cause);
//		}
//		if (causename!=null) {
			 list = latitudeauditDao.getDocby(causename,latitudetype,num,ruleid);
//		}
		return list;
	}

	@Override
	public String getDoc(String causename, int latitudetype,String docid) {
		String htmlString = latitudeauditDao.getDoc(causename,latitudetype,docid);
		return htmlString;
	}

}
