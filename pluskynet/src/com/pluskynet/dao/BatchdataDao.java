package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Batchdata;

public interface BatchdataDao {
	void save(Batchdata batchdata);

	Boolean plsave(List<Batchdata> batchlist);

	boolean delete(Batchdata batchdata);
	

}
