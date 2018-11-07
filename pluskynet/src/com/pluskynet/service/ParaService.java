package com.pluskynet.service;

import java.util.List;
import java.util.Map;

import com.pluskynet.domain.TParaCri;
import com.pluskynet.domain.TParaCriGrp;
import com.pluskynet.domain.TParaGrp;
import com.pluskynet.domain.TParaOne;
import com.pluskynet.domain.TParaVector;

public interface ParaService {

	List<TParaCri> criList();

	Map saveCri(String data);

	List<TParaGrp> grpList();

	String saveGrp(String data);

	List<TParaOne> grpInfoList(int pg_id);

	List<TParaOne> grpInfoDetail(int po_rootId);

	Map saveInfoOne(String data);

	List<TParaCriGrp> cri2GrpList(int pc_id);

	List<TParaVector> oneVectorList(int pg_id);

}
