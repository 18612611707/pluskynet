package com.pluskynet.service;

import java.util.List;

import com.pluskynet.domain.Previewhis;

public interface PreviewhisService {

	List<Previewhis> select(String starttime,String endtime);

}
