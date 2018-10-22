package com.pluskynet.domain;

/**
 * Docrule entity. @author MyEclipse Persistence Tools
 */

public class Docrule implements java.io.Serializable {

	// Fields

	private Integer ruleid;
	private String sectionname;
	private String rule;
	private Integer fid;

	// Constructors

	/** default constructor */
	public Docrule() {
	}

	/** full constructor */
	public Docrule(String sectionName, String rule, Integer fid) {
		this.sectionname = sectionName;
		this.rule = rule;
		this.fid = fid;
	}

	// Property accessors

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

}