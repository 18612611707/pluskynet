package com.pluskynet.otherdomain;

public class Screening {
	private String caseno;//案号
	public String getCaseno() {
		return caseno;
	}
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	public String getCourtname() {
		return courtname;
	}
	public void setCourtname(String courtname) {
		this.courtname = courtname;
	}
	public String getJudges() {
		return judges;
	}
	public void setJudges(String judges) {
		this.judges = judges;
	}
	public String getParties() {
		return parties;
	}
	public void setParties(String parties) {
		this.parties = parties;
	}
	public String getLaw() {
		return law;
	}
	public void setLaw(String law) {
		this.law = law;
	}
	public String getLawyer() {
		return lawyer;
	}
	public void setLawyer(String lawyer) {
		this.lawyer = lawyer;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String courtname;//法院名称
	private String judges;//审判人员
	private String parties;//当事人
	private String law;//律所
	private String lawyer;//律师
	private String legal;//法律依据
	private String dates;//开始日期
	private String date;//结束日期

}
