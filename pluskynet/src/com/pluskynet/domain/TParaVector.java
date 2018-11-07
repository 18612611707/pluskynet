package com.pluskynet.domain;

/**
 * TParaVector entity. @author MyEclipse Persistence Tools
 */

public class TParaVector implements java.io.Serializable {

	// Fields

	private Integer pvId;
	private Integer poId;
	private Integer pvVectorId;
	private Integer pgId;

	// Constructors

	/** default constructor */
	public TParaVector() {
	}

	/** full constructor */
	public TParaVector(Integer poId, Integer pvVectorId, Integer pgId) {
		this.poId = poId;
		this.pvVectorId = pvVectorId;
		this.pgId = pgId;
	}

	// Property accessors

	public Integer getPvId() {
		return this.pvId;
	}

	public void setPvId(Integer pvId) {
		this.pvId = pvId;
	}

	public Integer getPoId() {
		return this.poId;
	}

	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	public Integer getPvVectorId() {
		return this.pvVectorId;
	}

	public void setPvVectorId(Integer pvVectorId) {
		this.pvVectorId = pvVectorId;
	}

	public Integer getPgId() {
		return this.pgId;
	}

	public void setPgId(Integer pgId) {
		this.pgId = pgId;
	}

}