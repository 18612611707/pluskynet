package com.pluskynet.domain;

/**
 * Docidandruleid entity. @author MyEclipse Persistence Tools
 */

public class Docidandruleid implements java.io.Serializable {

	// Fields

	private Integer id;
	private String docid;
	private Integer ruleid;
	private Integer type;

	// Constructors

	/** default constructor */
	public Docidandruleid() {
	}

	/** minimal constructor */
	public Docidandruleid(String docid, Integer ruleid) {
		this.docid = docid;
		this.ruleid = ruleid;
	}

	/** full constructor */
	public Docidandruleid(String docid, Integer ruleid, Integer type) {
		this.docid = docid;
		this.ruleid = ruleid;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocid() {
		return this.docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}