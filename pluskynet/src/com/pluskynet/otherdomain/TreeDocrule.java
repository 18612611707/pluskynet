package com.pluskynet.otherdomain;

import java.util.ArrayList;
import java.util.List;

public class TreeDocrule {
	private Integer ruleid;
	private Integer fid;
	private String sectionname;
	private List<?> children = new ArrayList<Object>();
	public Integer getRuleid() {
		return ruleid;
	}
	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getSectionname() {
		return sectionname;
	}
	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}
	public List<?> getChildren() {
		return children;
	}
	public void setChildren(List<?> children) {
		this.children = children;
	}
	
	

}
