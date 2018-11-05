package com.pluskynet.dao;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;

public interface ParaDao {

	List<TParaCri> criList();

	Map save(TParaCri tParaCri);

	Map update(TParaCri tParaCri);

	List<TParaGrp> grpList();

	int savetParaGrp(TParaGrp tParaGrp);

	int saveCri(TParaCriGrp criGrp);

	Map saveInfoOne(TParaOne tParaOne);

	List<TParaOne> grpInfoDetail(int po_rootId);

	List<TParaOne> grpInfoList(int pg_id);

	List<TParaCriGrp> cri2GrpList(int pc_id);

}
