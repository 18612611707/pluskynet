package com.pluskynet.domain;

/**
 * Previewhis entity. @author MyEclipse Persistence Tools
 */

public class Previewhis implements java.io.Serializable {

	// Fields

	private Integer id;
	private String sample;
	private String createtime;
	private Integer sum;
	private Integer accord;
	private Integer noaccord;
	private String createuser;
	private String createname;

	// Constructors

	/** default constructor */
	public Previewhis() {
	}

	/** full constructor */
	public Previewhis(String sample, String createtime, Integer sum, Integer accord, Integer noaccord,
			String createuser, String createname) {
		this.sample = sample;
		this.createtime = createtime;
		this.sum = sum;
		this.accord = accord;
		this.noaccord = noaccord;
		this.createuser = createuser;
		this.createname = createname;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSample() {
		return this.sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getAccord() {
		return this.accord;
	}

	public void setAccord(Integer accord) {
		this.accord = accord;
	}

	public Integer getNoaccord() {
		return this.noaccord;
	}

	public void setNoaccord(Integer noaccord) {
		this.noaccord = noaccord;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

}