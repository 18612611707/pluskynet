package com.pluskynet.domain;

/**
 * TParaCriGrp entity. @author MyEclipse Persistence Tools
 */

public class TParaCriGrp implements java.io.Serializable {

	// Fields

	private Integer pcgId;
	private Integer pcId;
	private Integer pgId;
	private Integer pcgOrder;

	// Constructors

	/** default constructor */
	public TParaCriGrp() {
	}

	/** full constructor */
	public TParaCriGrp(Integer pcId, Integer pgId, Integer pcgOrder) {
		this.pcId = pcId;
		this.pgId = pgId;
		this.pcgOrder = pcgOrder;
	}

	// Property accessors

	public Integer getPcgId() {
		return this.pcgId;
	}

	public void setPcgId(Integer pcgId) {
		this.pcgId = pcgId;
	}

	public Integer getPcId() {
		return this.pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getPgId() {
		return this.pgId;
	}

	public void setPgId(Integer pgId) {
		this.pgId = pgId;
	}

	public Integer getPcgOrder() {
		return this.pcgOrder;
	}

	public void setPcgOrder(Integer pcgOrder) {
		this.pcgOrder = pcgOrder;
	}

}