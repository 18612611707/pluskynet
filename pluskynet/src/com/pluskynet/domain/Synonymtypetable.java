package com.pluskynet.domain;

/**
 * Synonymtypetable entity. @author MyEclipse Persistence Tools
 */

public class Synonymtypetable implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer typeid;
	private String typename;
	private String synonym;

	// Constructors

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	/** default constructor */
	public Synonymtypetable() {
	}

	/** full constructor */
	public Synonymtypetable(String typename) {
		this.typename = typename;
	}

	// Property accessors

	public Integer getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}