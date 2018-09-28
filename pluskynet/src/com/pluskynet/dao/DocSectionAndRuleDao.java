package com.pluskynet.dao;

import java.sql.SQLException;
import java.util.List;

import com.pluskynet.domain.Docsectionandrule;
import com.sun.star.rdf.QueryException;

import javassist.expr.NewArray;


public interface DocSectionAndRuleDao {
	boolean save(Docsectionandrule docsectionandrule,String table) throws SQLException, QueryException;

	List<Docsectionandrule> getDocList();

	List<Docsectionandrule> getDoc(Docsectionandrule docsectionandrule);

	List<Docsectionandrule> getDocLists(String sectionname);

	void saveyl(Docsectionandrule docsectionandrule);

	void saveyldelete(String sectionname);
	
	List<Docsectionandrule> listdoc(String doctable,int rows,String sectionname);
	void update(String doctable,String sectionname);

	void plsave(List<Docsectionandrule> docsectionlist, String doctable);

}
