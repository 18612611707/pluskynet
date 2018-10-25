package com.pluskynet.dao;



import java.util.List;
import java.util.Map;

import com.pluskynet.domain.Docrule;
import com.pluskynet.otherdomain.TreeDocrule;
@SuppressWarnings("all")
public interface DocRuleDao {

	String save(Docrule docrule);

	String update(Docrule docrule);

	List<Docrule> getDcoSectionList();

	Map<?, ?> getDcoSection(Docrule docrule);

	List<TreeDocrule> getNextSubSet(TreeDocrule voteTree);

	List<Docrule> getSecNameShow(String sectionname);

	List<Docrule> getRuleShow(Integer ruleid, String causeo, String causet, String spcx, String doctype);

	String updatesecname(Docrule docrule);

}
