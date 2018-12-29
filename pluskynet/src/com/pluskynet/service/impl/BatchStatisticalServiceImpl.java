package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.BatchStatisticalDao;
import com.pluskynet.domain.Statisticalnum;
import com.pluskynet.service.BatchStatisticalService;

public class BatchStatisticalServiceImpl implements BatchStatisticalService {
	private BatchStatisticalDao batchStatisticalDao;
	public void setBatchStatisticalDao(BatchStatisticalDao batchStatisticalDao) {
		this.batchStatisticalDao = batchStatisticalDao;
	}


	@Override
	public List<Statisticalnum> docStatistical() {
		List<Statisticalnum> list = batchStatisticalDao.docStatistical();
		return list;
	}


	@Override
	public List<Statisticalnum> laStatistical() {
		List<Statisticalnum> list= batchStatisticalDao.laStatistical();
		return list;
	}

}
