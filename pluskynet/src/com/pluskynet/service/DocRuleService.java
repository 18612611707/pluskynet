package com.pluskynet.service;



import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.User;
import com.pluskynet.otherdomain.TreeDocrule;
@SuppressWarnings("all")
public interface DocRuleService {

	Map save(Docrule docrule);

	String update(Docrule docrule);

	List<TreeDocrule> getDcoSectionList();

	Map<?, ?> getDcoSection(Docrule docrule);

	void saveyldelete(String sectionname, User user);

	void saveyl(Docsectionandrule docsectionandrule);

	List<Map> getSecNameShow(String sectionname);

	List<Docrule> getRuleShow(Integer ruleid, String causeo, String causet, String spcx, String doctype);

	String updatesecname(Docrule docrule);
}
