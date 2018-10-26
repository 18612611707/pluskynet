package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Preview;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;

public interface PreviewService {

	List<StatsDoc> getDocList(Preview preview,User user);

	Map<String, Object> getDoc(String docid,String rule);
	

}
