package com.pluskynet.dao;

import java.sql.SQLException;
import java.util.List;

import com.pluskynet.domain.Docsectionandrule;

import javassist.expr.NewArray;


public interface DocSectionAndRuleDao {
	boolean save(Docsectionandrule docsectionandrule,String table) throws SQLException;

	List<Docsectionandrule> getDocList();

	List<Docsectionandrule> getDoc(Docsectionandrule docsectionandrule);

	List<Docsectionandrule> getDocLists(String sectionname);

	void saveyl(Docsectionandrule docsectionandrule);

	void saveyldelete(String sectionname);
	
	List<Docsectionandrule> listdoc(String doctable,int rows,String sectionname);
	void update(String doctable,String sectionname);

}
