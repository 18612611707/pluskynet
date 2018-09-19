package com.pluskynet.service;



import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Docsectionandrule;
@SuppressWarnings("all")
public interface DocRuleService {

	String save(Docrule docrule);

	String update(Docrule docrule);

	List<Map> getDcoSectionList();

	Map<?, ?> getDcoSection(Docrule docrule);

	void saveyldelete(String sectionname);

	void saveyl(Docsectionandrule docsectionandrule);
}
