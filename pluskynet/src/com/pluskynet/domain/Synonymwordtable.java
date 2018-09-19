package com.pluskynet.domain;

/**
 * Synonymwordtable entity. @author MyEclipse Persistence Tools
 */

public class Synonymwordtable implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer typeid;
	private String word;

	// Constructors

	/** default constructor */
	public Synonymwordtable() {
	}

	/** minimal constructor */
	public Synonymwordtable(Integer typeid) {
		this.typeid = typeid;
	}

	/** full constructor */
	public Synonymwordtable(Integer typeid, String word) {
		this.typeid = typeid;
		this.word = word;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}