package com.pluskynet.domain;

/**
 * DictionaryHis entity. @author MyEclipse Persistence Tools
 */

public class DictionaryHis implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer doctionaryid;
	private String name;
	private Integer fid;
	private String belonguser;
	private Integer belongid;
	private String belongtime;

	// Constructors

	/** default constructor */
	public DictionaryHis() {
	}

	/** minimal constructor */
	public DictionaryHis(Integer doctionaryid, String name, Integer fid) {
		this.doctionaryid = doctionaryid;
		this.name = name;
		this.fid = fid;
	}

	/** full constructor */
	public DictionaryHis(Integer doctionaryid, String name, Integer fid, String belonguser, Integer belongid,
			String belongtime) {
		this.doctionaryid = doctionaryid;
		this.name = name;
		this.fid = fid;
		this.belonguser = belonguser;
		this.belongid = belongid;
		this.belongtime = belongtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDoctionaryid() {
		return this.doctionaryid;
	}

	public void setDoctionaryid(Integer doctionaryid) {
		this.doctionaryid = doctionaryid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getBelonguser() {
		return this.belonguser;
	}

	public void setBelonguser(String belonguser) {
		this.belonguser = belonguser;
	}

	public Integer getBelongid() {
		return this.belongid;
	}

	public void setBelongid(Integer belongid) {
		this.belongid = belongid;
	}

	public String getBelongtime() {
		return this.belongtime;
	}

	public void setBelongtime(String belongtime) {
		this.belongtime = belongtime;
	}

}