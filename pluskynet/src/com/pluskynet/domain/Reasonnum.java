package com.pluskynet.domain;

/**
 * Reasonnum entity. @author MyEclipse Persistence Tools
 */

public class Reasonnum implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reason;
	private Integer nums;

	// Constructors

	/** default constructor */
	public Reasonnum() {
	}

	/** full constructor */
	public Reasonnum(String reason, Integer nums) {
		this.reason = reason;
		this.nums = nums;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getNums() {
		return this.nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

}