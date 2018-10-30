package com.pluskynet.otherdomain;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
public class Treelatitude {
	private Integer latitudeid;
	public Integer getLatitudeid() {
		return latitudeid;
	}
	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}
	public Integer getLatitudefid() {
		return latitudefid;
	}
	public void setLatitudefid(Integer latitudefid) {
		this.latitudefid = latitudefid;
	}
	public String getLatitudename() {
		return latitudename;
	}
	public void setLatitudename(String latitudename) {
		this.latitudename = latitudename;
	}
	public List<?> getChildren() {
		return children;
	}
	public void setChildren(List<?> children) {
		this.children = children;
	}
	private Integer latitudefid;
	private String latitudename;
	@SuppressWarnings("rawtypes")
	private List<?> children = new ArrayList<Object>();
	private String creator;
	private String stat;
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
}
