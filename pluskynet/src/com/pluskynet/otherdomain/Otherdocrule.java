package com.pluskynet.otherdomain;

public class Otherdocrule {
	private String judge;
	public Otherdocrule(String judges, String startword, String endword) {
		this.judge = judges;
		this.start = startword;
		this.end = endword;
	}
	public Otherdocrule(String judges, String startword, String endword,String spcx,String doctype) {
		this.judge = judges;
		this.start = startword;
		this.end = endword;
		this.spcx = spcx;
		this.doctype = doctype;
	}
	public Otherdocrule(String judges, String startword, String endword,String causet) {
		this.judge = judges;
		this.start = startword;
		this.end = endword;
		this.causet = causet;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	private String start;
	private String end;
	private String spcx;
	public String getSpcx() {
		return spcx;
	}
	public void setSpcx(String spcx) {
		this.spcx = spcx;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	private String doctype;
	private String causet;
	public String getCauset() {
		return causet;
	}
	public void setCauset(String causet) {
		this.causet = causet;
	}
	public Otherdocrule(){
		
	}

}
