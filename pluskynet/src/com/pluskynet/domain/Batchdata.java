package com.pluskynet.domain;

/**
 * Batchdata entity. @author MyEclipse Persistence Tools
 */

public class Batchdata implements java.io.Serializable {

	// Fields

	private Integer id;
	private String cause;
	private String documentid;
	private Integer ruleid;
	private String startword;
	private String endword;
	private String contain;
	private String notcon;
	private Integer nums;

	// Constructors

	/** default constructor */
	public Batchdata() {
	}

	/** full constructor */
	public Batchdata(String cause, String documentid, Integer ruleid, String startword, String endword, String contain,
			String notcon, Integer nums) {
		this.cause = cause;
		this.documentid = documentid;
		this.ruleid = ruleid;
		this.startword = startword;
		this.endword = endword;
		this.contain = contain;
		this.notcon = notcon;
		this.nums = nums;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getDocumentid() {
		return this.documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getStartword() {
		return this.startword;
	}

	public void setStartword(String startword) {
		this.startword = startword;
	}

	public String getEndword() {
		return this.endword;
	}

	public void setEndword(String endword) {
		this.endword = endword;
	}

	public String getContain() {
		return this.contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public String getNotcon() {
		return this.notcon;
	}

	public void setNotcon(String notcon) {
		this.notcon = notcon;
	}

	public Integer getNums() {
		return this.nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

}