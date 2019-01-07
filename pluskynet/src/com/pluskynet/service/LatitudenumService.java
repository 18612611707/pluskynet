package com.pluskynet.service;

import java.util.List;

import com.pluskynet.domain.Latitudenum;

public interface LatitudenumService {

	List<Latitudenum> countlat(int type);

	void updatelat(List<Latitudenum> list);


}
