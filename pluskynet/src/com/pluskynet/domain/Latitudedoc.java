package com.pluskynet.domain;

/**
 * Latitudedoc entity. @author MyEclipse Persistence Tools
 */

public class Latitudedoc implements java.io.Serializable {

	// Fields

	private Integer id;
	private String documentid;
	private Integer latitudeid;
	private String latitudename;
	private String latitudetext;
	private Integer ruleid;

	// Constructors

	/** default constructor */
	public Latitudedoc() {
	}

	/** minimal constructor */
	public Latitudedoc(String documentid) {
		this.documentid = documentid;
	}

	/** full constructor */
	public Latitudedoc(String documentid, Integer latitudeid, String latitudename, String latitudetext,
			Integer ruleid) {
		this.documentid = documentid;
		this.latitudeid = latitudeid;
		this.latitudename = latitudename;
		this.latitudetext = latitudetext;
		this.ruleid = ruleid;
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

	public String getLatitudetext() {
		return this.latitudetext;
	}

	public void setLatitudetext(String latitudetext) {
		this.latitudetext = latitudetext;
	}

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

}