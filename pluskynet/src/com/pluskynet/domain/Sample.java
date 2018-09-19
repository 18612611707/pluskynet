package com.pluskynet.domain;

/**
 * Sample entity. @author MyEclipse Persistence Tools
 */

public class Sample implements java.io.Serializable {

	// Fields

	private String rule;

	// Constructors

	/** default constructor */
	public Sample() {
	}

	/** full constructor */
	public Sample(String rule) {
		this.rule = rule;
	}

	// Property accessors

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

}