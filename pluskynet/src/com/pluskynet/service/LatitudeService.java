package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.Treelatitude;
@SuppressWarnings("all")
public interface LatitudeService {

	String save(Latitude latitude);

	String update(Latitude latitude, User user);

	List<Map> getLatitudeList(User user);

	List<Treelatitude> treeList(int latitudeid,User user);

	Latitude getLatitude(Latitude latitude);

	List<String> getLatitudeName(Latitude latitude);

	List<StatsDoc> getDocList(Latitude latitude,User user);

	List<Map> getScreeList(String latitudeName,Integer latitudeId);

	List<Map> getLatitudeShow(String latitudename, User user);

	List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname);

	String updateName(Latitude latitude);


}
