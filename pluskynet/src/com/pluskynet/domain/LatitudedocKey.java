package com.pluskynet.domain;

/**
 * LatitudedocKey entity. @author MyEclipse Persistence Tools
 */

public class LatitudedocKey implements java.io.Serializable {

	// Fields

	private Integer id;
	private String documentid;
	private String latitudename;
	private Integer latitudeid;
	private Integer sectionid;
	private String updatatime;
	private String location;

	// Constructors

	/** default constructor */
	public LatitudedocKey() {
	}

	/** minimal constructor */
	public LatitudedocKey(String documentid) {
		this.documentid = documentid;
	}

	/** full constructor */
	public LatitudedocKey(String documentid, String latitudename, Integer latitudeid, Integer sectionid,
			String updatatime, String location) {
		this.documentid = documentid;
		this.latitudename = latitudename;
		this.latitudeid = latitudeid;
		this.sectionid = sectionid;
		this.updatatime = updatatime;
		this.location = location;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocumentid() {
		return this.documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public String getLatitudename() {
		return this.latitudename;
	}

	public void setLatitudename(String latitudename) {
		this.latitudename = latitudename;
	}

	public Integer getLatitudeid() {
		return this.latitudeid;
	}

	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}

	public Integer getSectionid() {
		return this.sectionid;
	}

	public void setSectionid(Integer sectionid) {
		this.sectionid = sectionid;
	}

	public String getUpdatatime() {
		return this.updatatime;
	}

	public void setUpdatatime(String updatatime) {
		this.updatatime = updatatime;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}