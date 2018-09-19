package com.pluskynet.domain;

/**
 * Article04 entity. @author MyEclipse Persistence Tools
 */

public class Article04 implements java.io.Serializable {

	// Fields

	private Long id;
	private String docId;
	private String title;
	private String date;
	private String decodeData;
	private Integer states;

	// Constructors

	/** default constructor */
	public Article04() {
	}

	/** full constructor */
	public Article04(String docId, String title, String date, String decodeData, Integer states) {
		this.docId = docId;
		this.title = title;
		this.date = date;
		this.decodeData = decodeData;
		this.states = states;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDecodeData() {
		return this.decodeData;
	}

	public void setDecodeData(String decodeData) {
		this.decodeData = decodeData;
	}

	public Integer getStates() {
		return this.states;
	}

	public void setStates(Integer states) {
		this.states = states;
	}

}