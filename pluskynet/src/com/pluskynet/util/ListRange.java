package com.pluskynet.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 为返回结果生成JSON格式提供的工具类
 * @author Administrator
 *
 * @param <T>
 */
public class ListRange<T> {
	/** 调用是否成功*/
//	boolean success;
	int code;
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 当前是第几页数据
	 */
	int page;
	/**
	 * 共多少页
	 */
	long totalPage;
	
	/**查询结果总记录数*/
	long totalSize;
	
	/**返回结果集合*/
//	List<T> list;
	List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public ListRange() {
		this.totalSize = 0;
		this.data = new ArrayList<T>();
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}


}
