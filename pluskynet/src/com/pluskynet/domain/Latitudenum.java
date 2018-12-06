package com.pluskynet.domain;

/**
 * Latitudenum entity. @author MyEclipse Persistence Tools
 */

public class Latitudenum implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer latitudeid;
	private String latitudename;
	private Integer nums;
	private Integer type;

	// Constructors

	/** default constructor */
	public Latitudenum() {
	}

	/** full constructor */
	public Latitudenum(Integer latitudeid, String latitudename, Integer nums, Integer type) {
		this.latitudeid = latitudeid;
		this.latitudename = latitudename;
		this.nums = nums;
		this.type = type;
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

	public Integer getNums() {
		return this.nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}