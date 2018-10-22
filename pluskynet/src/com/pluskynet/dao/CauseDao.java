package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Cause;

public interface CauseDao {
	String select(Cause cause);
	List<Cause> getArticleList(int ruletype);
	Cause save(Cause cause);
	Cause selectCause(Cause cause);

}
