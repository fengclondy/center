package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class TransactionRelationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1145447741573808227L;
	
	private String id;
	
	private String buyerId;
	
	private String buyerCode;
	
	private String buyerType;
	
	private String buyerName;
	
	private String sellerId;
	
	private String sellerCode;
	
	private String sellerType;
	
	private String sellerName;
	
	private String relatedTypeCode;
	
	private String relatedTypeName;
	
	private String isExist;
	
	private String isRelated;
	
	private String createId;
	
	private String createName;
	
	private Date createTime;
	
	private String modifyId;
	
	private String modifyName;
	
	private Date modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
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

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
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

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public String getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(String isRelated) {
		this.isRelated = isRelated;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
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

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
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
