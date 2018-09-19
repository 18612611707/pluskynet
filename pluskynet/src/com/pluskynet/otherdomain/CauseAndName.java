package com.pluskynet.otherdomain;

public class CauseAndName {
	private String latitudeid; //规则id
	private int latitudetype;//规则类型 
	private String causetable;//案由表名
	private String causename;//案由名称
	private int sunnum;//总数
	private int cornum;//符合数
	private int ncornum;//不符合数
	private String rulestats;
	private String batchstat;
	
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
	

}
