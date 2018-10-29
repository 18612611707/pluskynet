package com.pluskynet.dao;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.Treelatitude;
@SuppressWarnings("all")
public interface LatitudeDao {

	Map save(Latitude latitude, User user);

	String update(Latitude latitude, User user);

	List<Treelatitude> getFirstLevel(User user);

	List<Latitude> getNextSubSet(Treelatitude voteTree, User user);

	Latitude getLatitude(Latitude latitude);

	List<String> getLatitudeName(Latitude latitude);

	List<Map> getScreeList(String latitudeName,Integer latitudeId);
	
	Integer selectid(String latitudeName);

	List<Latitude> getLatitudeShow(String latitudename, User user);

	List<Latitude> getRuleShow(Integer latitudeid, String cause, String spcx, String sectionname);

	List<Treelatitude> getDeeptLevel(Latitude treelatitude, User user);

	String updateName(Latitude latitude, User user);

}
