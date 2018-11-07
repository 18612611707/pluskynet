package com.pluskynet.domain;

/**
 * TParaCri entity. @author MyEclipse Persistence Tools
 */

public class TParaCri implements java.io.Serializable {

	// Fields

	private Integer pcId;
	private Integer pcCauseId;
	private Integer pcOrder;

	// Constructors

	/** default constructor */
	public TParaCri() {
	}

	/** full constructor */
	public TParaCri(Integer pcCauseId, Integer pcOrder) {
		this.pcCauseId = pcCauseId;
		this.pcOrder = pcOrder;
	}

	public Integer getPcId() {
		return pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getPcCauseId() {
		return pcCauseId;
	}

	public void setPcCauseId(Integer pcCauseId) {
		this.pcCauseId = pcCauseId;
	}

	public Integer getPcOrder() {
		return pcOrder;
	}

	public void setPcOrder(Integer pcOrder) {
		this.pcOrder = pcOrder;
	}

	// Property accessors

	

}