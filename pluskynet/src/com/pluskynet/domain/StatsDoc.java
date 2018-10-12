package com.pluskynet.domain;

public class StatsDoc {
	private String stats;//状态
	private DocidAndDoc docidAndDoc;//段落内容
	private Integer num;//数量
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	public DocidAndDoc getDocidAndDoc() {
		return docidAndDoc;
	}
	public void setDocidAndDoc(DocidAndDoc docidAndDoc) {
		this.docidAndDoc = docidAndDoc;
	}
	

}
