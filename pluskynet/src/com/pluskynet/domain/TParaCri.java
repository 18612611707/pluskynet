package com.pluskynet.domain;

/**
 * TParaCri entity. @author MyEclipse Persistence Tools
 */

public class TParaCri implements java.io.Serializable {

	// Fields

	private Integer pcId;
	private Integer pcgCauseId;
	private Integer pcgOrder;

	// Constructors

	/** default constructor */
	public TParaCri() {
	}

	/** full constructor */
	public TParaCri(Integer pcgCauseId, Integer pcgOrder) {
		this.pcgCauseId = pcgCauseId;
		this.pcgOrder = pcgOrder;
	}

	// Property accessors

	public Integer getPcId() {
		return this.pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getPcgCauseId() {
		return this.pcgCauseId;
	}

	public void setPcgCauseId(Integer pcgCauseId) {
		this.pcgCauseId = pcgCauseId;
	}

	public Integer getPcgOrder() {
		return this.pcgOrder;
	}

	public void setPcgOrder(Integer pcgOrder) {
		this.pcgOrder = pcgOrder;
	}

}