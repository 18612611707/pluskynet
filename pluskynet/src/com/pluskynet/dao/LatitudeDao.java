package com.pluskynet.dao;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.otherdomain.Treelatitude;
@SuppressWarnings("all")
public interface LatitudeDao {

	String save(Latitude latitude);

	String update(Latitude latitude);

	List<Treelatitude> getFirstLevel();

	List<Treelatitude> getNextSubSet(Treelatitude voteTree);

	Latitude getLatitude(Latitude latitude);

	List<String> getLatitudeName(Latitude latitude);

	List<Map> getScreeList(String latitudeName,Integer latitudeId);
	
	Integer selectid(String latitudeName);

	List<Latitude> getLatitudeShow(String latitudename);

	List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname);

}
