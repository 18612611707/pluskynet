package com.pluskynet.dao;



import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Docrule;
@SuppressWarnings("all")
public interface DocRuleDao {

	String save(Docrule docrule);

	String update(Docrule docrule);

	List<Map> getDcoSectionList();

	Map<?, ?> getDcoSection(Docrule docrule);

}
