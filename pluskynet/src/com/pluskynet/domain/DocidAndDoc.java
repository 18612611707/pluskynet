package com.pluskynet.domain;


/*
 * 文书ID、文书内容、文书标题
 */
public class DocidAndDoc {
	private String docid;
	private String doc;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public  String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}

}
