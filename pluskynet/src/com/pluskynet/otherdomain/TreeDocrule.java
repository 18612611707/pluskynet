package com.pluskynet.otherdomain;

import java.util.ArrayList;
import java.util.List;

public class TreeDocrule {
	private Integer ruleid;
	private Integer fid;
	private String sectionname;
	private String rule;
	private String reserved;
	private List<TreeDocrule> children ;
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
	
	public List<TreeDocrule> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDocrule> children) {
		this.children = children;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	

}
