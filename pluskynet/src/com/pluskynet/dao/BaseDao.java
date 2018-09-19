package com.pluskynet.dao;
import java.io.Serializable;
public interface BaseDao<T, PK extends Serializable> {
	public void insert(T t);

	public void update(T t);

	public void delete(T t);

	public T findById(PK id);
	
  
}
