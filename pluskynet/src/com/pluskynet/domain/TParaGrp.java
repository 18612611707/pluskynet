package com.pluskynet.domain;

/**
 * TParaGrp entity. @author MyEclipse Persistence Tools
 */

public class TParaGrp implements java.io.Serializable {

	// Fields

	private Integer pgId;
	private String pgName;

	// Constructors

	/** default constructor */
	public TParaGrp() {
	}

	/** full constructor */
	public TParaGrp(String pgName) {
		this.pgName = pgName;
	}

	// Property accessors

	public Integer getPgId() {
		return this.pgId;
	}

	public void setPgId(Integer pgId) {
		this.pgId = pgId;
	}

	public String getPgName() {
		return this.pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

}