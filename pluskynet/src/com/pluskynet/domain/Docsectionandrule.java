package com.pluskynet.domain;

/**
 * Docsectionandrule entity. @author MyEclipse Persistence Tools
 */

public class Docsectionandrule implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer ruleid;
	private String documentsid;
	private String sectionname;
	private String sectiontext;
	private Integer views;
	private Integer downloads;
	private String title;
	private Integer belongid;
	private String belonguser;

	// Constructors

	/** default constructor */
	public Docsectionandrule() {
	}

	/** full constructor */
	public Docsectionandrule(Integer ruleid, String documentsid, String sectionname, String sectiontext, Integer views,
			Integer downloads, String title, Integer belongid, String belonguser) {
		this.ruleid = ruleid;
		this.documentsid = documentsid;
		this.sectionname = sectionname;
		this.sectiontext = sectiontext;
		this.views = views;
		this.downloads = downloads;
		this.title = title;
		this.belongid = belongid;
		this.belonguser = belonguser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getDocumentsid() {
		return this.documentsid;
	}

	public void setDocumentsid(String documentsid) {
		this.documentsid = documentsid;
	}

	public String getSectionname() {
		return this.sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	public String getSectiontext() {
		return this.sectiontext;
	}

	public void setSectiontext(String sectiontext) {
		this.sectiontext = sectiontext;
	}

	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBelongid() {
		return this.belongid;
	}

	public void setBelongid(Integer belongid) {
		this.belongid = belongid;
	}

	public String getBelonguser() {
		return this.belonguser;
	}

	public void setBelonguser(String belonguser) {
		this.belonguser = belonguser;
	}

}