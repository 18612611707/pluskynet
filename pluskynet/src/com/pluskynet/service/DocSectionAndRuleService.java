package com.pluskynet.service;

import java.util.List;

import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.StatsDoc;

public interface DocSectionAndRuleService {
	void save(Docsectionandrule docsectionandrule,String table);

	List<StatsDoc> getDocList();

	List<Docsectionandrule> getDoc(Docsectionandrule docsectionandrule);

	void update(String doctable, String sectionname);

}
