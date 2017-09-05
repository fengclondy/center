package cn.htd.promotion.cpc.dto.response;

import java.util.Date;
import java.util.List;

public class PromotionInfoEditResDTO  extends GenricResDTO {

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

	private List<PromotionPictureDTO> promotionPictureDTO;

	private PromotionExtendInfoDTO promotionExtendInfoDTO;

	private PromotionBuyerRuleDTO promotionBuyerRuleDTO;

	private PromotionSellerRuleDTO promotionSellerRuleDTO;

	private List<PromotionAccumulatyResDTO> promotionAccumulatyList;

	private PromotionDetailDescribeDTO promotionDetailDescribeDTO;

	private PromotionSloganDTO promotionSloganDTO;
	
	private List<PromotionConfigureDTO> promotionConfigureList;
	
	public List<PromotionPictureDTO> getPromotionPictureDTO() {
		return promotionPictureDTO;
	}

	public void setPromotionPictureDTO(List<PromotionPictureDTO> promotionPictureDTO) {
		this.promotionPictureDTO = promotionPictureDTO;
	}

	public PromotionDetailDescribeDTO getPromotionDetailDescribeDTO() {
		return promotionDetailDescribeDTO;
	}

	public void setPromotionDetailDescribeDTO(PromotionDetailDescribeDTO promotionDetailDescribeDTO) {
		this.promotionDetailDescribeDTO = promotionDetailDescribeDTO;
	}

	public PromotionSloganDTO getPromotionSloganDTO() {
		return promotionSloganDTO;
	}

	public void setPromotionSloganDTO(PromotionSloganDTO promotionSloganDTO) {
		this.promotionSloganDTO = promotionSloganDTO;
	}

	public List<PromotionConfigureDTO> getPromotionConfigureList() {
		return promotionConfigureList;
	}

	public void setPromotionConfigureList(List<PromotionConfigureDTO> promotionConfigureList) {
		this.promotionConfigureList = promotionConfigureList;
	}

	public PromotionExtendInfoDTO getPromotionExtendInfoDTO() {
		return promotionExtendInfoDTO;
	}

	public void setPromotionExtendInfoDTO(PromotionExtendInfoDTO promotionExtendInfoDTO) {
		this.promotionExtendInfoDTO = promotionExtendInfoDTO;
	}

	public PromotionBuyerRuleDTO getPromotionBuyerRuleDTO() {
		return promotionBuyerRuleDTO;
	}

	public void setPromotionBuyerRuleDTO(PromotionBuyerRuleDTO promotionBuyerRuleDTO) {
		this.promotionBuyerRuleDTO = promotionBuyerRuleDTO;
	}

	public PromotionSellerRuleDTO getPromotionSellerRuleDTO() {
		return promotionSellerRuleDTO;
	}

	public void setPromotionSellerRuleDTO(PromotionSellerRuleDTO promotionSellerRuleDTO) {
		this.promotionSellerRuleDTO = promotionSellerRuleDTO;
	}

	public List<PromotionAccumulatyResDTO> getPromotionAccumulatyList() {
		return promotionAccumulatyList;
	}

	public void setPromotionAccumulatyList(List<PromotionAccumulatyResDTO> promotionAccumulatyResList) {
		this.promotionAccumulatyList = promotionAccumulatyResList;
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