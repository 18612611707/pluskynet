package com.pluskynet.domain;

/**
 * TParaGrp entity. @author MyEclipse Persistence Tools
 */

public class TParaGrp implements java.io.Serializable {

	// Fields

	private Integer pgId;
	private String pgName;
	private String pgShow;

	// Constructors

	/** default constructor */
	public TParaGrp() {
	}

	/** full constructor */
	public TParaGrp(String pgName, String pgShow) {
		this.pgName = pgName;
		this.pgShow = pgShow;
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

	public String getPgShow() {
		return this.pgShow;
	}

	public void setPgShow(String pgShow) {
		this.pgShow = pgShow;
	}

}