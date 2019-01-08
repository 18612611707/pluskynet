package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Latitudestatistical;
import com.pluskynet.domain.Previewhis;

public interface PreviewhisDao {

	List<Previewhis> select(String starttime,String endtime);
	void save(Previewhis previewhis);
	List<Latitudestatistical> Latitudestatistical();

}
