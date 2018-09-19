package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * Latitudeaudit entity. @author MyEclipse Persistence Tools
 */

public class Latitudeaudit implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer latitudeid;
	private Integer latitudetype;
	private String latitudename;
	private String rule;
	private String stats;
	private String subuserid;
	private Timestamp subtime;
	private String aduituser;
	private Timestamp aduitime;
	private String batchstats;

	// Constructors

	/** default constructor */
	public Latitudeaudit() {
	}

	/** full constructor */
	public Latitudeaudit(Integer latitudeid, Integer latitudetype, String latitudename, String rule, String stats,
			String subuserid, Timestamp subtime, String aduituser, Timestamp aduitime, String batchstats) {
		this.latitudeid = latitudeid;
		this.latitudetype = latitudetype;
		this.latitudename = latitudename;
		this.rule = rule;
		this.stats = stats;
		this.subuserid = subuserid;
		this.subtime = subtime;
		this.aduituser = aduituser;
		this.aduitime = aduitime;
		this.batchstats = batchstats;
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

	public Integer getLatitudetype() {
		return this.latitudetype;
	}

	public void setLatitudetype(Integer latitudetype) {
		this.latitudetype = latitudetype;
	}

	public String getLatitudename() {
		return this.latitudename;
	}

	public void setLatitudename(String latitudename) {
		this.latitudename = latitudename;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getStats() {
		return this.stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getSubuserid() {
		return this.subuserid;
	}

	public void setSubuserid(String subuserid) {
		this.subuserid = subuserid;
	}

	public Timestamp getSubtime() {
		return this.subtime;
	}

	public void setSubtime(Timestamp subtime) {
		this.subtime = subtime;
	}

	public String getAduituser() {
		return this.aduituser;
	}

	public void setAduituser(String aduituser) {
		this.aduituser = aduituser;
	}

	public Timestamp getAduitime() {
		return this.aduitime;
	}

	public void setAduitime(Timestamp aduitime) {
		this.aduitime = aduitime;
	}

	public String getBatchstats() {
		return this.batchstats;
	}

	public void setBatchstats(String batchstats) {
		this.batchstats = batchstats;
	}

}