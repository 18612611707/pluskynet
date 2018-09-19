package com.pluskynet.domain;

import java.util.Date;

/**
 * Documentsbasic entity. @author MyEclipse Persistence Tools
 */

public class Documentsbasic implements java.io.Serializable {

	// Fields

	private String documentsid;
	private Integer courtid;
	private String tbfotc;
	private String ato;
	private String tprocedure;
	private String caseno;
	private String reasonsnd;
	private String city;
	private String province;
	private String firstpar;
	private String nameotcase;
	private String nameotcourt;
	private String rmmessage;
	private String county;
	private String receivingdoc;
	private String doccontent;
	private String typeoftext;
	private String oricourtrecord;
	private String originaljudgment;
	private String endtext;
	private Date pubdate;
	private String casetype;
	private String litigant;
	private String documenttype;
	private Date thedataof;
	private String closedmanner;
	private String effhierarchy;
	private Date toactdate;
	private Date trialdate;
	private String tcoa;

	// Constructors

	/** default constructor */
	public Documentsbasic() {
	}

	/** minimal constructor */
	public Documentsbasic(String documentsid) {
		this.documentsid = documentsid;
	}

	/** full constructor */
	public Documentsbasic(String documentsid, Integer courtid, String tbfotc, String ato, String tprocedure,
			String caseno, String reasonsnd, String city, String province, String firstpar, String nameotcase,
			String nameotcourt, String rmmessage, String county, String receivingdoc, String doccontent,
			String typeoftext, String oricourtrecord, String originaljudgment, String endtext, Date pubdate,
			String casetype, String litigant, String documenttype, Date thedataof, String closedmanner,
			String effhierarchy, Date toactdate, Date trialdate, String tcoa) {
		this.documentsid = documentsid;
		this.courtid = courtid;
		this.tbfotc = tbfotc;
		this.ato = ato;
		this.tprocedure = tprocedure;
		this.caseno = caseno;
		this.reasonsnd = reasonsnd;
		this.city = city;
		this.province = province;
		this.firstpar = firstpar;
		this.nameotcase = nameotcase;
		this.nameotcourt = nameotcourt;
		this.rmmessage = rmmessage;
		this.county = county;
		this.receivingdoc = receivingdoc;
		this.doccontent = doccontent;
		this.typeoftext = typeoftext;
		this.oricourtrecord = oricourtrecord;
		this.originaljudgment = originaljudgment;
		this.endtext = endtext;
		this.pubdate = pubdate;
		this.casetype = casetype;
		this.litigant = litigant;
		this.documenttype = documenttype;
		this.thedataof = thedataof;
		this.closedmanner = closedmanner;
		this.effhierarchy = effhierarchy;
		this.toactdate = toactdate;
		this.trialdate = trialdate;
		this.tcoa = tcoa;
	}

	// Property accessors

	public String getDocumentsid() {
		return this.documentsid;
	}

	public void setDocumentsid(String documentsid) {
		this.documentsid = documentsid;
	}

	public Integer getCourtid() {
		return this.courtid;
	}

	public void setCourtid(Integer courtid) {
		this.courtid = courtid;
	}

	public String getTbfotc() {
		return this.tbfotc;
	}

	public void setTbfotc(String tbfotc) {
		this.tbfotc = tbfotc;
	}

	public String getAto() {
		return this.ato;
	}

	public void setAto(String ato) {
		this.ato = ato;
	}

	public String getTprocedure() {
		return this.tprocedure;
	}

	public void setTprocedure(String tprocedure) {
		this.tprocedure = tprocedure;
	}

	public String getCaseno() {
		return this.caseno;
	}

	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	public String getReasonsnd() {
		return this.reasonsnd;
	}

	public void setReasonsnd(String reasonsnd) {
		this.reasonsnd = reasonsnd;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getFirstpar() {
		return this.firstpar;
	}

	public void setFirstpar(String firstpar) {
		this.firstpar = firstpar;
	}

	public String getNameotcase() {
		return this.nameotcase;
	}

	public void setNameotcase(String nameotcase) {
		this.nameotcase = nameotcase;
	}

	public String getNameotcourt() {
		return this.nameotcourt;
	}

	public void setNameotcourt(String nameotcourt) {
		this.nameotcourt = nameotcourt;
	}

	public String getRmmessage() {
		return this.rmmessage;
	}

	public void setRmmessage(String rmmessage) {
		this.rmmessage = rmmessage;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getReceivingdoc() {
		return this.receivingdoc;
	}

	public void setReceivingdoc(String receivingdoc) {
		this.receivingdoc = receivingdoc;
	}

	public String getDoccontent() {
		return this.doccontent;
	}

	public void setDoccontent(String doccontent) {
		this.doccontent = doccontent;
	}

	public String getTypeoftext() {
		return this.typeoftext;
	}

	public void setTypeoftext(String typeoftext) {
		this.typeoftext = typeoftext;
	}

	public String getOricourtrecord() {
		return this.oricourtrecord;
	}

	public void setOricourtrecord(String oricourtrecord) {
		this.oricourtrecord = oricourtrecord;
	}

	public String getOriginaljudgment() {
		return this.originaljudgment;
	}

	public void setOriginaljudgment(String originaljudgment) {
		this.originaljudgment = originaljudgment;
	}

	public String getEndtext() {
		return this.endtext;
	}

	public void setEndtext(String endtext) {
		this.endtext = endtext;
	}

	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getCasetype() {
		return this.casetype;
	}

	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}

	public String getLitigant() {
		return this.litigant;
	}

	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}

	public String getDocumenttype() {
		return this.documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public Date getThedataof() {
		return this.thedataof;
	}

	public void setThedataof(Date thedataof) {
		this.thedataof = thedataof;
	}

	public String getClosedmanner() {
		return this.closedmanner;
	}

	public void setClosedmanner(String closedmanner) {
		this.closedmanner = closedmanner;
	}

	public String getEffhierarchy() {
		return this.effhierarchy;
	}

	public void setEffhierarchy(String effhierarchy) {
		this.effhierarchy = effhierarchy;
	}

	public Date getToactdate() {
		return this.toactdate;
	}

	public void setToactdate(Date toactdate) {
		this.toactdate = toactdate;
	}

	public Date getTrialdate() {
		return this.trialdate;
	}

	public void setTrialdate(Date trialdate) {
		this.trialdate = trialdate;
	}

	public String getTcoa() {
		return this.tcoa;
	}

	public void setTcoa(String tcoa) {
		this.tcoa = tcoa;
	}

}