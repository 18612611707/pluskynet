package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
@SuppressWarnings("all")
public interface LatitudeauditService {

	List<CauseAndName> getLatitudeList(int page, int rows);

	int getCountBy();

	String updateStats(String latitudeids);

	List<Latitudeaudit> getLatitude(int latitudetype);

	void updatebatchestats(List<Latitudeaudit> latitudeaudit);

	List<DocidAndDoc> getDocList(String causename, int latitudetype, int num,int rows,int page,int ruleid);

	int getDocby(String causename, int latitudetype, int num, int ruleid);

	String getDoc(String causename, int latitudetype, String docid);

	void update(Latitudeaudit latitudeaudit);
}
