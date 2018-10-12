package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.otherdomain.Treelatitude;
@SuppressWarnings("all")
public interface LatitudeService {

	String save(Latitude latitude);

	String update(Latitude latitude);

	List<Map> getLatitudeList();

	List<Treelatitude> treeList(int latitudeid);

	Latitude getLatitude(Latitude latitude);

	List<String> getLatitudeName(Latitude latitude);

	List<StatsDoc> getDocList(Latitude latitude);

	List<Map> getScreeList(String latitudeName,Integer latitudeId);

	List<Map> getLatitudeShow(String latitudename);

	List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname);


}
