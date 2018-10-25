package com.pluskynet.otherdomain;

import java.util.ArrayList;
import java.util.List;

public class Dictionarytree {
	private int id;
	private String name;
	private int fid;
	private List<?> children = new ArrayList<Object>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public List<?> getChildren() {
		return children;
	}
	public void setChildren(List<?> children) {
		this.children = children;
	}
	

}
