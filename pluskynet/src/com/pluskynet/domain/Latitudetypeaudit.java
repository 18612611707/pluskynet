package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * Latitudetypeaudit entity. @author MyEclipse Persistence Tools
 */

public class Latitudetypeaudit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer latitudeid;
	private String latitudename;
	private String createruser;
	private Timestamp creatertime;
	private String state;
	private String audituser;
	private Timestamp audittime;

	// Constructors

	/** default constructor */
	public Latitudetypeaudit() {
	}

	/** minimal constructor */
	public Latitudetypeaudit(Integer latitudeid, String latitudename) {
		this.latitudeid = latitudeid;
		this.latitudename = latitudename;
	}

	/** full constructor */
	public Latitudetypeaudit(Integer latitudeid, String latitudename,
			String createruser, Timestamp creatertime, String state,
			String audituser, Timestamp audittime) {
		this.latitudeid = latitudeid;
		this.latitudename = latitudename;
		this.createruser = createruser;
		this.creatertime = creatertime;
		this.state = state;
		this.audituser = audituser;
		this.audittime = audittime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLatitudeid() {
		return this.latitudeid;
	}

	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}

	public String getLatitudename() {
		return this.latitudename;
	}

	public void setLatitudename(String latitudename) {
		this.latitudename = latitudename;
	}

	public String getCreateruser() {
		return this.createruser;
	}

	public void setCreateruser(String createruser) {
		this.createruser = createruser;
	}

	public Timestamp getCreatertime() {
		return this.creatertime;
	}

	public void setCreatertime(Timestamp creatertime) {
		this.creatertime = creatertime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAudituser() {
		return this.audituser;
	}

	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}

	public Timestamp getAudittime() {
		return this.audittime;
	}

	public void setAudittime(Timestamp audittime) {
		this.audittime = audittime;
	}

}