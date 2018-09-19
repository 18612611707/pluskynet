package com.pluskynet.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.DocidAndDoc;
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

	@Override
	public List<CauseAndName> getLatitudeList(int page, int rows) {
		List<CauseAndName> map = null;
		try {
			map = latitudeauditDao.getLatitudeauditList(page,rows);
		} catch (SQLException e) {
			System.out.println("出错了");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public int getCountBy() {
		int totalSize = latitudeauditDao.getCountBy();
		return totalSize;
	}

	@Override
	public String updateStats(Latitudeaudit latitudeaudit) {
		String msg = latitudeauditDao.updateState(latitudeaudit);
		return msg;
	}

	@Override
	public List<Latitudeaudit> getLatitude(String batchstats,int latitudetype) {
		List<Latitudeaudit> list = latitudeauditDao.getLatitude(batchstats,latitudetype);
		return list;
	}

	@Override
	public void updatebatchestats(Latitudeaudit latitudeaudit) {
		latitudeauditDao.updatebatchestats(latitudeaudit);
		
	}

	@Override
	public List<DocidAndDoc> getDocList(String causename, int latitudetype, int num,int rows,int page,int ruleid) {
		List<DocidAndDoc> list = null;
		Cause cause = new Cause();
		if (causename!=null) {
			cause.setCausename(causename);
			cause = causeDao.selectCause(cause);
		}
		if (causename!=null) {
			 list = latitudeauditDao.getDocList(cause,latitudetype,num,rows,page,ruleid);
		}
		return list;
	}

	@Override
	public int getDocby(String causename, int latitudetype, int num, int ruleid) {
		int list = 0;
		Cause cause = new Cause();
		if (causename!=null) {
			cause.setCausename(causename);
			cause = causeDao.selectCause(cause);
		}
		if (causename!=null) {
			 list = latitudeauditDao.getDocby(cause,latitudetype,num,ruleid);
		}
		return list;
	}

	@Override
	public String getDoc(String causename, int latitudetype,String docid,int ruleid) {
		String htmlString = null;
		Cause cause = new Cause();
		if (causename!=null) {
			cause.setCausename(causename);
			cause = causeDao.selectCause(cause);
		}
		if (causename!=null) {
			htmlString = latitudeauditDao.getDoc(cause,latitudetype,docid,ruleid);
		}
		return htmlString;
	}

}
