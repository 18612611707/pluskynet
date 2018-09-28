package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Batchdata;

public interface BatchdataDao {
	void save(Batchdata batchdata);

	void plsave(List<Batchdata> batchlist);

}
