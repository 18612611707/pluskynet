package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Statisticalnum;

public interface BatchStatisticalDao {

	List<Statisticalnum> docStatistical();

	List<Statisticalnum> laStatistical();

}
