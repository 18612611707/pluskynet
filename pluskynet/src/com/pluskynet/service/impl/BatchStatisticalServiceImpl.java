package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.BatchStatisticalDao;
import com.pluskynet.service.BatchStatisticalService;

public class BatchStatisticalServiceImpl implements BatchStatisticalService {
	private BatchStatisticalDao batchStatisticalDao;
	public void setBatchStatisticalDao(BatchStatisticalDao batchStatisticalDao) {
		this.batchStatisticalDao = batchStatisticalDao;
	}


	@Override
	public List<Integer> docStatistical() {
		List<Integer> list = batchStatisticalDao.docStatistical();
		return list;
	}


	@Override
	public List<Integer> laStatistical() {
		List<Integer> list = batchStatisticalDao.laStatistical();
		return list;
	}

}
