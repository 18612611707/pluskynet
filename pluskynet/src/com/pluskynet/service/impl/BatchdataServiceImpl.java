package com.pluskynet.service.impl;

import com.pluskynet.dao.BatchdataDao;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.service.BatchdataService;

public class BatchdataServiceImpl implements BatchdataService {
	private BatchdataDao batchdataDao;
	
	public void setBatchdataDao(BatchdataDao batchdataDao) {
		this.batchdataDao = batchdataDao;
	}
	@Override
	public void save(Batchdata batchdata) {
		batchdataDao.save(batchdata);
	}

	@Override
	public void delete(Batchdata batchdata) {
		batchdataDao.delete(batchdata);
	}

}
