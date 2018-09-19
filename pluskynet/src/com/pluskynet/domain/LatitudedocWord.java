package com.pluskynet.domain;

/**
 * LatitudedocWord entity. @author MyEclipse Persistence Tools
 */

public class LatitudedocWord implements java.io.Serializable {

	// Fields

	private Integer id;
	private String documentid;
	private String latitudename;
	private String latitudetext;
	private Integer latitudeid;

	// Constructors

	/** default constructor */
	public LatitudedocWord() {
	}

	/** minimal constructor */
	public LatitudedocWord(String documentid) {
		this.documentid = documentid;
	}

	/** full constructor */
	public LatitudedocWord(String documentid, String latitudename, String latitudetext, Integer latitudeid) {
		this.documentid = documentid;
		this.latitudename = latitudename;
		this.latitudetext = latitudetext;
		this.latitudeid = latitudeid;
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

	public String getLatitudetext() {
		return this.latitudetext;
	}

	public void setLatitudetext(String latitudetext) {
		this.latitudetext = latitudetext;
	}

	public Integer getLatitudeid() {
		return this.latitudeid;
	}

	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}

}