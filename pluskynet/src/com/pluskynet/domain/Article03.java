package com.pluskynet.domain;

/**
 * Article03 entity. @author MyEclipse Persistence Tools
 */

public class Article03 implements java.io.Serializable {

	// Fields

	private Long id;
	private String docId;
	private String title;
	private String date;
	private String decodeData;
	private Integer states;
	private String spcx;
	private String doctype;
	private String court;
	private String casetype;
	private String reason;
	private String trialdate;
	private String appellor;
	private String legalbase;
	private Integer ccourtid;
	private String caseno;
	private String courtcities;
	private String courtprovinces;
	private String casename;

	// Constructors

	/** default constructor */
	public Article03() {
	}

	/** full constructor */
	public Article03(String docId, String title, String date, String decodeData, Integer states, String spcx,
			String doctype, String court, String casetype, String reason, String trialdate, String appellor,
			String legalbase, Integer ccourtid, String caseno, String courtcities, String courtprovinces,
			String casename) {
		this.docId = docId;
		this.title = title;
		this.date = date;
		this.decodeData = decodeData;
		this.states = states;
		this.spcx = spcx;
		this.doctype = doctype;
		this.court = court;
		this.casetype = casetype;
		this.reason = reason;
		this.trialdate = trialdate;
		this.appellor = appellor;
		this.legalbase = legalbase;
		this.ccourtid = ccourtid;
		this.caseno = caseno;
		this.courtcities = courtcities;
		this.courtprovinces = courtprovinces;
		this.casename = casename;
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

	public String getSpcx() {
		return this.spcx;
	}

	public void setSpcx(String spcx) {
		this.spcx = spcx;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getCourt() {
		return this.court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getCasetype() {
		return this.casetype;
	}

	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTrialdate() {
		return this.trialdate;
	}

	public void setTrialdate(String trialdate) {
		this.trialdate = trialdate;
	}

	public String getAppellor() {
		return this.appellor;
	}

	public void setAppellor(String appellor) {
		this.appellor = appellor;
	}

	public String getLegalbase() {
		return this.legalbase;
	}

	public void setLegalbase(String legalbase) {
		this.legalbase = legalbase;
	}

	public Integer getCcourtid() {
		return this.ccourtid;
	}

	public void setCcourtid(Integer ccourtid) {
		this.ccourtid = ccourtid;
	}

	public String getCaseno() {
		return this.caseno;
	}

	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	public String getCourtcities() {
		return this.courtcities;
	}

	public void setCourtcities(String courtcities) {
		this.courtcities = courtcities;
	}

	public String getCourtprovinces() {
		return this.courtprovinces;
	}

	public void setCourtprovinces(String courtprovinces) {
		this.courtprovinces = courtprovinces;
	}

	public String getCasename() {
		return this.casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

}