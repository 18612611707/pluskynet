package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Synonymtypetable;
import com.pluskynet.domain.Synonymwordtable;

public interface SynonymDao {

	List<Synonymtypetable> getTypeList();

	List<Synonymwordtable> getSynonym(Synonymtypetable synonymtypetable);

	String saveType(Synonymtypetable synonymtypetable);

	String save(Synonymtypetable synonymtypetable);

}
