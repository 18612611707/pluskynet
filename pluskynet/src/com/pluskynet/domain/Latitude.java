package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * Latitude entity. @author MyEclipse Persistence Tools
 */

public class Latitude implements java.io.Serializable {

	// Fields

	private Integer latitudeid;
	private String latitudetype;
	private Integer latitudefid;
	private String latitudename;
	private Integer ruletype;
	private String rule;
	private Integer num;
	private String createrole;
	private String createruser;
	private String stats;
	private Timestamp creatertime;

	// Constructors

	/** default constructor */
	public Latitude() {
	}

	/** minimal constructor */
	public Latitude(Integer latitudefid, String latitudename) {
		this.latitudefid = latitudefid;
		this.latitudename = latitudename;
	}

	/** full constructor */
	public Latitude(String latitudetype, Integer latitudefid, String latitudename, Integer ruletype, String rule,
			Integer num, String createrole, String createruser, String stats, Timestamp creatertime) {
		this.latitudetype = latitudetype;
		this.latitudefid = latitudefid;
		this.latitudename = latitudename;
		this.ruletype = ruletype;
		this.rule = rule;
		this.num = num;
		this.createrole = createrole;
		this.createruser = createruser;
		this.stats = stats;
		this.creatertime = creatertime;
	}

	// Property accessors

	public Integer getLatitudeid() {
		return this.latitudeid;
	}

	public void setLatitudeid(Integer latitudeid) {
		this.latitudeid = latitudeid;
	}

	public String getLatitudetype() {
		return this.latitudetype;
	}

	public void setLatitudetype(String latitudetype) {
		this.latitudetype = latitudetype;
	}

	public Integer getLatitudefid() {
		return this.latitudefid;
	}

	public void setLatitudefid(Integer latitudefid) {
		this.latitudefid = latitudefid;
	}

	public String getLatitudename() {
		return this.latitudename;
	}

	public void setLatitudename(String latitudename) {
		this.latitudename = latitudename;
	}

	public Integer getRuletype() {
		return this.ruletype;
	}

	public void setRuletype(Integer ruletype) {
		this.ruletype = ruletype;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCreaterole() {
		return this.createrole;
	}

	public void setCreaterole(String createrole) {
		this.createrole = createrole;
	}

	public String getCreateruser() {
		return this.createruser;
	}

	public void setCreateruser(String createruser) {
		this.createruser = createruser;
	}

	public String getStats() {
		return this.stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public Timestamp getCreatertime() {
		return this.creatertime;
	}

	public void setCreatertime(Timestamp creatertime) {
		this.creatertime = creatertime;
	}

}