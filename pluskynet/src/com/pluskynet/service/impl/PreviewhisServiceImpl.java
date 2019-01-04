package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.PreviewhisDao;
import com.pluskynet.domain.Previewhis;
import com.pluskynet.service.PreviewhisService;

public class PreviewhisServiceImpl implements PreviewhisService {
	private PreviewhisDao previewhisDao; 

	public void setPreviewhisDao(PreviewhisDao previewhisDao) {
		this.previewhisDao = previewhisDao;
	}

	@Override
	public List<Previewhis> select(String starttime,String endtime) {
		List<Previewhis> list = previewhisDao.select(starttime,endtime);
		return list;
	}

}
