package com.pluskynet.domain;

/**
 * TParaOne entity. @author MyEclipse Persistence Tools
 */

public class TParaOne implements java.io.Serializable {

	// Fields

	private Integer poId;
	private String poName;
	private Integer poPid;
	private Integer poOrder;
	private String poType;
	private Integer poIsPara;
	private Integer pgId;
	private Integer poRootId;
	private Integer poTier;

	// Constructors

	/** default constructor */
	public TParaOne() {
	}

	/** full constructor */
	public TParaOne(String poName, Integer poPid, Integer poOrder, String poType, Integer poIsPara, Integer pgId,
			Integer poRootId, Integer poTier) {
		this.poName = poName;
		this.poPid = poPid;
		this.poOrder = poOrder;
		this.poType = poType;
		this.poIsPara = poIsPara;
		this.pgId = pgId;
		this.poRootId = poRootId;
		this.poTier = poTier;
	}

	// Property accessors

	public Integer getPoId() {
		return this.poId;
	}

	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	public String getPoName() {
		return this.poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public Integer getPoPid() {
		return this.poPid;
	}

	public void setPoPid(Integer poPid) {
		this.poPid = poPid;
	}

	public Integer getPoOrder() {
		return this.poOrder;
	}

	public void setPoOrder(Integer poOrder) {
		this.poOrder = poOrder;
	}

	public String getPoType() {
		return this.poType;
	}

	public void setPoType(String poType) {
		this.poType = poType;
	}

	public Integer getPoIsPara() {
		return this.poIsPara;
	}

	public void setPoIsPara(Integer poIsPara) {
		this.poIsPara = poIsPara;
	}

	public Integer getPgId() {
		return this.pgId;
	}

	public void setPgId(Integer pgId) {
		this.pgId = pgId;
	}

	public Integer getPoRootId() {
		return this.poRootId;
	}

	public void setPoRootId(Integer poRootId) {
		this.poRootId = poRootId;
	}

	public Integer getPoTier() {
		return this.poTier;
	}

	public void setPoTier(Integer poTier) {
		this.poTier = poTier;
	}

}