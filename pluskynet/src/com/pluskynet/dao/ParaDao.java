package com.pluskynet.dao;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.domain.TParaVector;

public interface ParaDao {

	List<TParaCri> criList();

	Map save(TParaCri tParaCri);

	Map update(TParaCri tParaCri);

	List<TParaGrp> grpList();

	int savetParaGrp(TParaGrp tParaGrp);

	int saveCri(TParaCriGrp criGrp);

	Integer saveInfoOne(TParaOne tParaOne);

	List<TParaOne> grpInfoDetail(int po_rootId);

	List<TParaOne> grpInfoList(int pg_id);

	List<TParaCriGrp> cri2GrpList(int pc_id);

	void updatetParaGrp(TParaGrp tParaGrp);

	Integer updateInfoOne(TParaOne tParaOne);

	void delete(int integer);

	List<TParaVector> oneVectorList(int pg_id);

	void savevector(TParaVector tParaVector);

	boolean deletevector(int po_id);
	
	TParaOne getTParaOne(String poname,int pgid,int rootid);
	
	int getMaxOrder(TParaOne tParaOne);

}
