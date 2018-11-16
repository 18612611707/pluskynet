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

	// Constructors

	/** default constructor */
	public LatitudedocKey() {
	}

	/** minimal constructor */
	public LatitudedocKey(String documentid) {
		this.documentid = documentid;
	}

	/** full constructor */
	public LatitudedocKey(String documentid, String latitudename, Integer latitudeid, Integer sectionid) {
		this.documentid = documentid;
		this.latitudename = latitudename;
		this.latitudeid = latitudeid;
		this.sectionid = sectionid;
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

}