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
	private String reserved;

	// Constructors

	/** default constructor */
	public Docrule() {
	}

	/** full constructor */
	public Docrule(String sectionname, String rule, Integer fid, String reserved) {
		this.sectionname = sectionname;
		this.rule = rule;
		this.fid = fid;
		this.reserved = reserved;
	}

	// Property accessors

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getSectionname() {
		return this.sectionname;
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

	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}