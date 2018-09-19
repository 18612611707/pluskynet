package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * CourtHis entity. @author MyEclipse Persistence Tools
 */

public class CourtHis implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer courtid;
	private String province;
	private String courttier;
	private String courtname;
	private Timestamp savetime;

	// Constructors

	/** default constructor */
	public CourtHis() {
	}

	/** minimal constructor */
	public CourtHis(Integer courtid, String province, String courttier, String courtname) {
		this.courtid = courtid;
		this.province = province;
		this.courttier = courttier;
		this.courtname = courtname;
	}

	/** full constructor */
	public CourtHis(Integer courtid, String province, String courttier, String courtname,
			Timestamp savetime) {
		this.courtid = courtid;
		this.province = province;
		this.courttier = courttier;
		this.courtname = courtname;
		this.savetime = savetime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Timestamp getSavetime() {
		return this.savetime;
	}

	public void setSavetime(Timestamp savetime) {
		this.savetime = savetime;
	}

}