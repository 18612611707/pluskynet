package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Docidandruleid;

public interface DocidandruleidDao {
	void save(Docidandruleid docidandruleid);

	void plsave(List<Docidandruleid> docidlist);

	boolean delete(Docidandruleid docidandruleid);

}
