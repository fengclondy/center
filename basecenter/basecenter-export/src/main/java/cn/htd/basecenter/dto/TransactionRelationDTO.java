package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class TransactionRelationDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1114540397765119187L;
	

	private Long id;
	
	private Long buyerId;
	
	private String buyerCode;
	
	private String buyerType;
	
	private String buyerName;
	
	private Long sellerId;
	
	private String sellerCode;
	
	private String sellerType;
	
	private String sellerName;
	
	private String relatedTypeCode;
	
	private String relatedTypeName;
	
	private Boolean isExist;
	
	private Boolean isRelated;
	
	private Long createId;
	
	private String createName;
	
	private Date createTime;
	
	private Long modifyId;
	
	private String modifyName;
	
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
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

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public Boolean getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(Boolean isRelated) {
		this.isRelated = isRelated;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
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
