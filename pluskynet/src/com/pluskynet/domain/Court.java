package com.pluskynet.domain;

/**
 * Court entity. @author MyEclipse Persistence Tools
 */

public class Court implements java.io.Serializable {

	// Fields

	private Integer courtid;
	private String province;
	private String courttier;
	private String courtname;

	// Constructors

	/** default constructor */
	public Court() {
	}

	/** full constructor */
	public Court(String province, String courttier, String courtname) {
		this.province = province;
		this.courttier = courttier;
		this.courtname = courtname;
	}

	// Property accessors

	public Integer getCourtid() {
		return this.courtid;
	}

	public void setCourtid(Integer courtid) {
		this.courtid = courtid;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCourttier() {
		return this.courttier;
	}

	public void setCourttier(String courttier) {
		this.courttier = courttier;
	}

	public String getCourtname() {
		return this.courtname;
	}

	public void setCourtname(String courtname) {
		this.courtname = courtname;
	}

}