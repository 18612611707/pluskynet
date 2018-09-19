package com.pluskynet.domain;

/**
 * Cause entity. @author MyEclipse Persistence Tools
 */

public class Cause implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fid;
	private String causename;
	private String causetable;
	private String doctable;

	// Constructors

	/** default constructor */
	public Cause() {
	}

	/** full constructor */
	public Cause(Integer fid, String causename, String causetable, String doctable) {
		this.fid = fid;
		this.causename = causename;
		this.causetable = causetable;
		this.doctable = doctable;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getCausename() {
		return this.causename;
	}

	public void setCausename(String causename) {
		this.causename = causename;
	}

	public String getCausetable() {
		return this.causetable;
	}

	public void setCausetable(String causetable) {
		this.causetable = causetable;
	}

	public String getDoctable() {
		return this.doctable;
	}

	public void setDoctable(String doctable) {
		this.doctable = doctable;
	}

}