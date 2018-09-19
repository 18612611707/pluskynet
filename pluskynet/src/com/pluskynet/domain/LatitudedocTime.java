package com.pluskynet.domain;

/**
 * LatitudedocTime entity. @author MyEclipse Persistence Tools
 */

public class LatitudedocTime implements java.io.Serializable {

	// Fields

	private Integer id;
	private String documentid;
	private String latitudename;
	private String latitudetime;
	private Integer latitudeid;

	// Constructors

	/** default constructor */
	public LatitudedocTime() {
	}

	/** minimal constructor */
	public LatitudedocTime(String documentid) {
		this.documentid = documentid;
	}

	/** full constructor */
	public LatitudedocTime(String documentid, String latitudename, String latitudetime, Integer latitudeid) {
		this.documentid = documentid;
		this.latitudename = latitudename;
		this.latitudetime = latitudetime;
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

	public String getLatitudetime() {
		return this.latitudetime;
	}

	public void setLatitudetime(String latitudetime) {
		this.latitudetime = latitudetime;
	}

	public Integer getLatitudeid() {
		return this.latitudeid;
	}

	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}

}