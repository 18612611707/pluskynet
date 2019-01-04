package com.pluskynet.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Cause;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Latitude;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.CauseAndName;
@SuppressWarnings("all")
public interface LatitudeauditDao {

	void update(Latitudeaudit latitudeaudit);

	List<CauseAndName> getLatitudeauditList(int page,int rows) throws SQLException;

	int getCountBy();

	String updateState(String latitudeids);

	List<Latitudeaudit> getLatitude(int latitudetype);

	void updatebatchestats(List<Latitudeaudit> latitudeaudit);

	List<DocidAndDoc> getDocList(String cause, int latitudetype, int num, int rows, int page,int ruleid);

	int getDocby(String cause, int latitudetype, int num, int ruleid);

	String getDoc(String cause, int latitudetype, String docid);
}
