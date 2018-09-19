package com.pluskynet.domain;

import java.sql.Timestamp;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */

public class Article implements java.io.Serializable {

	// Fields

	private Long id;
	private String docId;
	private String title;
	private String data;
	private Integer state;
	private String createdUser;
	private Timestamp createdTime;
	private String updatedUser;
	private Timestamp updatedTime;
	private Integer yn;
	private Long taskId;
	private String taskStartDate;
	private String decodeData;

	// Constructors

	/** default constructor */
	public Article() {
	}

	/** minimal constructor */
	public Article(Long id) {
		this.id = id;
	}

	/** full constructor */
	public Article(Long id, String docId, String title, String data, Integer state, String createdUser,
			Timestamp createdTime, String updatedUser, Timestamp updatedTime, Integer yn, Long taskId,
			String taskStartDate, String decodeData) {
		this.id = id;
		this.docId = docId;
		this.title = title;
		this.data = data;
		this.state = state;
		this.createdUser = createdUser;
		this.createdTime = createdTime;
		this.updatedUser = updatedUser;
		this.updatedTime = updatedTime;
		this.yn = yn;
		this.taskId = taskId;
		this.taskStartDate = taskStartDate;
		this.decodeData = decodeData;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreatedUser() {
		return this.createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedUser() {
		return this.updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Timestamp getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getYn() {
		return this.yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskStartDate() {
		return this.taskStartDate;
	}

	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public String getDecodeData() {
		return this.decodeData;
	}

	public void setDecodeData(String decodeData) {
		this.decodeData = decodeData;
	}

}