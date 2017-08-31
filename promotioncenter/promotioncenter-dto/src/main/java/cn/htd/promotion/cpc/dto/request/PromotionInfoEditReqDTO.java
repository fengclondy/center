package cn.htd.promotion.cpc.dto.request;

import java.util.Date;
import java.util.List;

public class PromotionInfoEditReqDTO extends GenricReqDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String promotionId;

	private String promotionName;

	private String promotionDescribe;

	private String promotionProviderType;

	private String promotionProviderSellerCode;

	private Long promotionProviderShopId;

	private String costAllocationType;

	private String promotionType;

	private Date effectiveTime;

	private Date invalidTime;

	private int isVip;

	private String status;

	private String showStatus;

	private Long verifierId;

	private String verifierName;

	private Date verifyTime;

	private String verifyRemark;

	private String modifyPromotionId;

	private int hasRedisClean;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	private int dealFlag;

	private List<PromotionPictureReqDTO> promotionPictureReqDTO;

	private PromotionExtendInfoReqDTO promotionExtendInfoReqDTO;

	private PromotionBuyerRuleReqDTO promotionBuyerRuleReqDTO;

	private PromotionSellerRuleReqDTO promotionSellerRuleReqDTO;


	private List<PromotionAccumulatyReqDTO> promotionAccumulatyList;

	private PromotionDetailDescribeReqDTO promotionDetailDescribeReqDTO;

	public List<PromotionPictureReqDTO> getPromotionPictureReqDTO() {
		return promotionPictureReqDTO;
	}

	public void setPromotionPictureReqDTO(List<PromotionPictureReqDTO> promotionPictureReqDTO) {
		this.promotionPictureReqDTO = promotionPictureReqDTO;
	}

	public PromotionDetailDescribeReqDTO getPromotionDetailDescribeReqDTO() {
		return promotionDetailDescribeReqDTO;
	}

	public void setPromotionDetailDescribeReqDTO(PromotionDetailDescribeReqDTO promotionDetailDescribeReqDTO) {
		this.promotionDetailDescribeReqDTO = promotionDetailDescribeReqDTO;
	}

	public PromotionExtendInfoReqDTO getPromotionExtendInfoReqDTO() {
		return promotionExtendInfoReqDTO;
	}

	public void setPromotionExtendInfoReqDTO(PromotionExtendInfoReqDTO promotionExtendInfoReqDTO) {
		this.promotionExtendInfoReqDTO = promotionExtendInfoReqDTO;
	}

	public PromotionBuyerRuleReqDTO getPromotionBuyerRuleReqDTO() {
		return promotionBuyerRuleReqDTO;
	}

	public void setPromotionBuyerRuleReqDTO(PromotionBuyerRuleReqDTO promotionBuyerRuleReqDTO) {
		this.promotionBuyerRuleReqDTO = promotionBuyerRuleReqDTO;
	}

	public PromotionSellerRuleReqDTO getPromotionSellerRuleReqDTO() {
		return promotionSellerRuleReqDTO;
	}

	public void setPromotionSellerRuleReqDTO(PromotionSellerRuleReqDTO promotionSellerRuleReqDTO) {
		this.promotionSellerRuleReqDTO = promotionSellerRuleReqDTO;
	}

	public List<PromotionAccumulatyReqDTO> getPromotionAccumulatyList() {
		return promotionAccumulatyList;
	}

	public void setPromotionAccumulatyList(List<PromotionAccumulatyReqDTO> promotionAccumulatyList) {
		this.promotionAccumulatyList = promotionAccumulatyList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId == null ? null : promotionId.trim();
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName == null ? null : promotionName.trim();
	}

	public String getPromotionDescribe() {
		return promotionDescribe;
	}

	public void setPromotionDescribe(String promotionDescribe) {
		this.promotionDescribe = promotionDescribe == null ? null : promotionDescribe.trim();
	}

	public String getPromotionProviderType() {
		return promotionProviderType;
	}

	public void setPromotionProviderType(String promotionProviderType) {
		this.promotionProviderType = promotionProviderType == null ? null : promotionProviderType.trim();
	}

	public String getPromotionProviderSellerCode() {
		return promotionProviderSellerCode;
	}

	public void setPromotionProviderSellerCode(String promotionProviderSellerCode) {
		this.promotionProviderSellerCode = promotionProviderSellerCode == null ? null
				: promotionProviderSellerCode.trim();
	}

	public Long getPromotionProviderShopId() {
		return promotionProviderShopId;
	}

	public void setPromotionProviderShopId(Long promotionProviderShopId) {
		this.promotionProviderShopId = promotionProviderShopId;
	}

	public String getCostAllocationType() {
		return costAllocationType;
	}

	public void setCostAllocationType(String costAllocationType) {
		this.costAllocationType = costAllocationType == null ? null : costAllocationType.trim();
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType == null ? null : promotionType.trim();
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus == null ? null : showStatus.trim();
	}

	public Long getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(Long verifierId) {
		this.verifierId = verifierId;
	}

	public String getVerifierName() {
		return verifierName;
	}

	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName == null ? null : verifierName.trim();
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
	}

	public String getModifyPromotionId() {
		return modifyPromotionId;
	}

	public void setModifyPromotionId(String modifyPromotionId) {
		this.modifyPromotionId = modifyPromotionId == null ? null : modifyPromotionId.trim();
	}

	public int getHasRedisClean() {
		return hasRedisClean;
	}

	public void setHasRedisClean(int hasRedisClean) {
		this.hasRedisClean = hasRedisClean;
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
		this.createName = createName == null ? null : createName.trim();
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
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(int dealFlag) {
		this.dealFlag = dealFlag;
	}
}