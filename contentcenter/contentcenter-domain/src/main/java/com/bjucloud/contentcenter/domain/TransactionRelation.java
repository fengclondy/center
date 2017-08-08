package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;

public class TransactionRelation implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3948586261314299432L;

	private long id;
	
	private long buyerId;
	
	private String buyerCode;
	
	private String buyerType;
	
	private String buyerName;
	
	private long sellerId;
	
	private String sellerCode;
	
	private String sellerType;
	
	private String sellerName;
	
	private String relatedTypeCode;
	
	private String relatedTypeName;
	
	private int isExist;
	
	private int isRelated;
	
	private long createId;
	
	private String createName;
	
	private Date createTime;
	
	private long modifyId;
	
	private String modifyName;
	
	private Date modifyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getRelatedTypeCode() {
		return relatedTypeCode;
	}

	public void setRelatedTypeCode(String relatedTypeCode) {
		this.relatedTypeCode = relatedTypeCode;
	}

	public String getRelatedTypeName() {
		return relatedTypeName;
	}

	public void setRelatedTypeName(String relatedTypeName) {
		this.relatedTypeName = relatedTypeName;
	}

	public int getIsExist() {
		return isExist;
	}

	public void setIsExist(int isExist) {
		this.isExist = isExist;
	}

	public int getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(int isRelated) {
		this.isRelated = isRelated;
	}

	public long getCreateId() {
		return createId;
	}

	public void setCreateId(long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getModifyId() {
		return modifyId;
	}

	public void setModifyId(long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
