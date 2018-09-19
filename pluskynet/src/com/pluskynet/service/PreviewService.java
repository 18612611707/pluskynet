package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;

public interface PreviewService {

	List<StatsDoc> getDocList(Preview preview);

	Map<String, Object> getDoc(String docid,String rule);
	

}
