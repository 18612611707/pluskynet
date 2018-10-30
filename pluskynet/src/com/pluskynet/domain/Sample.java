package com.pluskynet.domain;

/**
 * Sample entity. @author MyEclipse Persistence Tools
 */

public class Sample implements java.io.Serializable {

	// Fields

	private Integer id;
	private String rule;
	private String belonguser;
	private Integer belongid;
	private String reserved;

	// Constructors

	/** default constructor */
	public Sample() {
	}

	/** minimal constructor */
	public Sample(String rule, String belonguser, Integer belongid) {
		this.rule = rule;
		this.belonguser = belonguser;
		this.belongid = belongid;
	}

	/** full constructor */
	public Sample(String rule, String belonguser, Integer belongid, String reserved) {
		this.rule = rule;
		this.belonguser = belonguser;
		this.belongid = belongid;
		this.reserved = reserved;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getBelonguser() {
		return this.belonguser;
	}

	public void setBelonguser(String belonguser) {
		this.belonguser = belonguser;
	}

	public Integer getBelongid() {
		return this.belongid;
	}

	public void setBelongid(Integer belongid) {
		this.belongid = belongid;
	}

	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}