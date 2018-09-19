package com.pluskynet.domain;

/**
 * Docrule entity. @author MyEclipse Persistence Tools
 */

public class Docrule implements java.io.Serializable {

	// Fields

	private Integer ruleid;
	private String sectionName;
	private String rule;

	// Constructors

	/** default constructor */
	public Docrule() {
	}

	/** full constructor */
	public Docrule(String sectionName, String rule) {
		this.sectionName = sectionName;
		this.rule = rule;
	}

	// Property accessors

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

}