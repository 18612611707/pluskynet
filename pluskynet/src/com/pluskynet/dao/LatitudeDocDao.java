package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.otherdomain.LatitudeDocList;

public interface LatitudeDocDao {

	int getCountBy(Docsectionandrule latitudedoc,String listLatitudedocs,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String dat);

	List<LatitudeDocList> findPageBy(Docsectionandrule latitudedoc, int page, int rows,String listLatitudedocs,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String date);

	List<LatitudeDocList> getDoc(Docsectionandrule latitudedoc);
	
//	List<LatitudeDocList> getDocLists(String sectionname);

}
