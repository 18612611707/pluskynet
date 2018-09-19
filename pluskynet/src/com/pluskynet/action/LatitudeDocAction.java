package com.pluskynet.action;

import java.util.List;

import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.otherdomain.LatitudeDocList;
import com.pluskynet.service.LatitudeDocService;
import com.pluskynet.util.BaseAction;
@SuppressWarnings("all")
public class LatitudeDocAction extends BaseAction{
	private Docsectionandrule docsectionandrule = new Docsectionandrule();
	public Docsectionandrule getDocsectionandrule() {
		return docsectionandrule;
	}
	public void setDocsectionandrule(Docsectionandrule docsectionandrule) {
		this.docsectionandrule = docsectionandrule;
	}
	private String documentsid;
	private String listLatitudedocs;
//	public String getLatitudeId() {
//		return latitudeid;
//	}
//	public void setLatitudeId(String latitudeId) {
//		this.latitudeid = latitudeId;
//	}
	public String getListLatitudedocs() {
		return listLatitudedocs;
	}
	public void setListLatitudedocs(String listLatitudedocs) {
		this.listLatitudedocs = listLatitudedocs;
	}
	private LatitudeDocService latitudeDocService;
	public LatitudeDocService getLatitudeDocService() {
		return latitudeDocService;
	}
	public void setLatitudeDocService(LatitudeDocService latitudeDocService) {
		this.latitudeDocService = latitudeDocService;
	}
	private String casename;//案件名称
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
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
	private String caseno;//案号
	private String courtname;//法院名称
	private String judges;//审判人员
	private String parties;//当事人
	private String law;//律所
	private String lawyer;//律师
	private String legal;//法律依据
	private String dates;//开始日期
	private String date;//结束日期
	

	public void getDocList(){
		List<LatitudeDocList> list = latitudeDocService.findPageBy(docsectionandrule, this.getPage(),
				this.getRows(),listLatitudedocs,caseno,courtname,judges,parties,law,lawyer,legal,dates,date);
		int totalSize = latitudeDocService.getCountBy(docsectionandrule,listLatitudedocs,caseno,courtname,judges,parties,law,lawyer,legal,dates,date);
		 
		outJsonByPage(list, totalSize, "成功", "yyyy-MM-dd HH:mm:ss");
	}
	public void getDoc(){
		Docsectionandrule docs = new Docsectionandrule();
		List<LatitudeDocList> list = latitudeDocService.getDoc(docs);
		outJsonByMsg(list, "成功");
	}
	
	
	@Override
	public Object getModel() {
		
		return docsectionandrule;
	}

}
