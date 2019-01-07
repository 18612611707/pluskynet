package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Latitudenum;

public interface LatitudenumDao {

	List<Latitudenum> countlat(int type);//统计各个维度数量
	
	List<Latitudenum> getnums(int type);//获取0：段落 ，1：维度数量

	boolean updatelat(List<Latitudenum> list);

}
