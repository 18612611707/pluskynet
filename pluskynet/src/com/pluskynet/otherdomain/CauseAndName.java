package com.pluskynet.otherdomain;

public class CauseAndName {
	private int id;//审批id
	private String latitudeid; //规则id
	private int latitudetype;//规则类型 
	private String causetable;//案由表名
	private String causename;//规则名称
	private int sunnum;//总数
	private int cornum;//符合数
	private int ncornum;//不符合数
	private String rulestats;
	private String batchstat;
	private String fcasename;
	private String subtime;
	
	public String getSubtime() {
		return subtime;
	}
	public void setSubtime(String subtime) {
		this.subtime = subtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLatitudeid() {
		return latitudeid;
	}
	public void setLatitudeid(String latitudeid) {
		this.latitudeid = latitudeid;
	}
	public int getLatitudetype() {
		return latitudetype;
	}
	public void setLatitudetype(int latitudetype) {
		this.latitudetype = latitudetype;
	}
	public String getRulestats() {
		return rulestats;
	}
	public void setRulestats(String rulestats) {
		this.rulestats = rulestats;
	}
	public String getBatchstat() {
		return batchstat;
	}
	public void setBatchstat(String batchstat) {
		this.batchstat = batchstat;
	}
	public int getSunnum() {
		return sunnum;
	}
	public void setSunnum(int sunnum) {
		this.sunnum = sunnum;
	}
	public int getCornum() {
		return cornum;
	}
	public void setCornum(int cornum) {
		this.cornum = cornum;
	}
	public int getNcornum() {
		return ncornum;
	}
	public void setNcornum(int ncornum) {
		this.ncornum = ncornum;
	}
	public String getCausetable() {
		return causetable;
	}
	public void setCausetable(String causetable) {
		this.causetable = causetable;
	}
	public String getCausename() {
		return causename;
	}
	public void setCausename(String causename) {
		this.causename = causename;
	}
	public String getFcasename() {
		return fcasename;
	}
	public void setFcasename(String fcasename) {
		this.fcasename = fcasename;
	}
	

}
