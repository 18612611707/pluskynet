package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String username;
	private String name;
	private String password;
	private Integer phone;
	private String email;
	private Timestamp createtime;
	private String createuser;
	private Integer iseff;
	private String rolecode;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String password, Integer iseff) {
		this.username = username;
		this.password = password;
		this.iseff = iseff;
	}

	/** full constructor */
	public User(String username, String name, String password, Integer phone, String email, Timestamp createtime,
			String createuser, Integer iseff, String rolecode) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.createtime = createtime;
		this.createuser = createuser;
		this.iseff = iseff;
		this.rolecode = rolecode;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPhone() {
		return this.phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Integer getIseff() {
		return this.iseff;
	}

	public void setIseff(Integer iseff) {
		this.iseff = iseff;
	}

	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

}