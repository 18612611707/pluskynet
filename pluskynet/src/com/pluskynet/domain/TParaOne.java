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
	private String poRelation;
	private String poLink;
	private String poProp;
	private String poPropRelate;
	private String poPropValue;
	private String poRangeType;
	private String poPropKey;

	// Constructors

	/** default constructor */
	public TParaOne() {
	}

	/** full constructor */
	public TParaOne(String poName, Integer poPid, Integer poOrder, String poType, Integer poIsPara, Integer pgId,
			Integer poRootId, Integer poTier, String poRelation, String poLink, String poProp, String poPropRelate,
			String poPropValue, String poRangeType, String poPropKey) {
		this.poName = poName;
		this.poPid = poPid;
		this.poOrder = poOrder;
		this.poType = poType;
		this.poIsPara = poIsPara;
		this.pgId = pgId;
		this.poRootId = poRootId;
		this.poTier = poTier;
		this.poRelation = poRelation;
		this.poLink = poLink;
		this.poProp = poProp;
		this.poPropRelate = poPropRelate;
		this.poPropValue = poPropValue;
		this.poRangeType = poRangeType;
		this.poPropKey = poPropKey;
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

	public String getPoRelation() {
		return this.poRelation;
	}

	public void setPoRelation(String poRelation) {
		this.poRelation = poRelation;
	}

	public String getPoLink() {
		return this.poLink;
	}

	public void setPoLink(String poLink) {
		this.poLink = poLink;
	}

	public String getPoProp() {
		return this.poProp;
	}

	public void setPoProp(String poProp) {
		this.poProp = poProp;
	}

	public String getPoPropRelate() {
		return this.poPropRelate;
	}

	public void setPoPropRelate(String poPropRelate) {
		this.poPropRelate = poPropRelate;
	}

	public String getPoPropValue() {
		return this.poPropValue;
	}

	public void setPoPropValue(String poPropValue) {
		this.poPropValue = poPropValue;
	}

	public String getPoRangeType() {
		return this.poRangeType;
	}

	public void setPoRangeType(String poRangeType) {
		this.poRangeType = poRangeType;
	}

	public String getPoPropKey() {
		return this.poPropKey;
	}

	public void setPoPropKey(String poPropKey) {
		this.poPropKey = poPropKey;
	}

}