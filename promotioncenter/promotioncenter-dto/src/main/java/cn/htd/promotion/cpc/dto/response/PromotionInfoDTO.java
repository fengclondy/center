package cn.htd.promotion.cpc.dto.response;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 促销活动DTO
 */
public class PromotionInfoDTO extends GenricResDTO {

	/**
     * 促销活动ID
     */
    private Long id;
    /**
     * 促销活动编码
     */
    private String promotionId;
    /**
     * 促销活动名称
     */
    @NotBlank(message = "促销活动名称不能为空")
    private String promotionName;
    /**
     * 促销活动描述
     */
    private String promotionDescribe;
    /**
     * 促销活动发起方类型
     */
    @NotBlank(message = "促销活动发起方类型不能为空")
    private String promotionProviderType;
    /**
     * 促销活动发起方商家编码
     */
    private String promotionProviderSellerCode;

    /**
     * 促销活动商家名称
     */
    private String sellerName;
    /**
     * 促销活动发起方商家店铺ID
     */
    private Long promotionProviderShopId;
    /**
     * 促销活动发起方商家店铺名称
     */
    private String promotionProviderShopName;
    /**
     * 成本分摊类型
     */
    private String costAllocationType;
    /**
     * 促销活动类型
     */
    @NotBlank(message = "促销活动类型不能为空")
    private String promotionType;
    /**
     * 促销活动开始时间
     */
    @NotNull(message = "促销活动开始时间不能为空")
    private Date effectiveTime;
    /**
     * 促销活动结束时间
     */
    @NotNull(message = "促销活动结束时间不能为空")
    private Date invalidTime;
    /**
     * 是否是VIP会员专属标记
     */
    private int isVip;
    /**
     * 促销活动状态 1：活动未开始，2：活动进行中，3：活动已结束，9：已删除
     */
    private String status;

    /**
     * 促销活动展示状态 1：待审核，2：审核通过，3：审核被驳回，4：启用，5：不启用
     */
    private String showStatus;
    /**
     * 审核人ID
     */
    private Long verifierId;
    /**
     * 审核人名称
     */
    private String verifierName;
    /**
     * 审核时间
     */
    private Date verifyTime;
    /**
     * 审核备注
     */
    private String verifyRemark;
    /**
     * 修改促销活动编码
     */
    private String modifyPromotionId;
    /**
     * 处理标记
     */
    private Integer dealFlag;
    /**
     * 是否清除redis
     */
    private Integer hasRedisClean;
    /**
     * 创建人ID
     */
    private Long createId;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人ID
     */
    private Long modifyId;
    /**
     * 更新人名称
     */
    private String modifyName;
    /**
     * 更新时间（促销活动更新时必须传入做乐观排他用）
     */
    private Date modifyTime;
    /**
     * 层级买家规则
     */
    private PromotionBuyerRuleDTO buyerRuleDTO;
    /**
     * 层级卖家规则
     */
    private PromotionSellerRuleDTO sellerRuleDTO;
    /**
     * 活动层级列表
     */
    private List<? extends PromotionAccumulatyDTO> promotionAccumulatyList;
    /**
     * 促销活动状态履历
     */
    private List<PromotionStatusHistoryDTO> promotionStatusHistoryList;
    /**
     * 促销活动图片信息
     */
    private List<PromotionPictureDTO> promotionPictureList;
    /**
     * 促销活动Slogan
     */
    private PromotionSloganDTO promotionSloganDTO;
    /**
     * 促销活动详情描述
     */
    private PromotionDetailDescribeDTO promotionDetailDescribeDTO;
    /**
     * 删除状态
     */
    private String deleteStatus;
    /**
     * 当前任务类型的任务队列数量
     */
    private int taskQueueNum;
    /**
     * 当前调度服务器，分配到的可处理队列
     */
    private List<String> taskIdList;
    /**
     * 定时任务处理对象促销活动状态
     */
    private List<String> statusList;
    /**
     * 查询条件的审核状态列表
     */
    private List<String> verifyStatusList;

    private String effectiveTimeStr;

    private String invalidTimeStr;

    private PromotionSellerDetailDTO sellerDetailDTO;

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
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionDescribe() {
        return promotionDescribe;
    }

    public void setPromotionDescribe(String promotionDescribe) {
        this.promotionDescribe = promotionDescribe;
    }

    public String getPromotionProviderType() {
        return promotionProviderType;
    }

    public void setPromotionProviderType(String promotionProviderType) {
        this.promotionProviderType = promotionProviderType;
    }

    public String getPromotionProviderSellerCode() {
        return promotionProviderSellerCode;
    }

    public void setPromotionProviderSellerCode(String promotionProviderSellerCode) {
        this.promotionProviderSellerCode = promotionProviderSellerCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getPromotionProviderShopId() {
        return promotionProviderShopId;
    }

    public void setPromotionProviderShopId(Long promotionProviderShopId) {
        this.promotionProviderShopId = promotionProviderShopId;
    }

    public String getPromotionProviderShopName() {
        return promotionProviderShopName;
    }

    public void setPromotionProviderShopName(String promotionProviderShopName) {
        this.promotionProviderShopName = promotionProviderShopName;
    }

    public String getCostAllocationType() {
        return costAllocationType;
    }

    public void setCostAllocationType(String costAllocationType) {
        this.costAllocationType = costAllocationType;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
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
        this.status = status;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
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
        this.verifierName = verifierName;
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
        this.verifyRemark = verifyRemark;
    }

    public String getModifyPromotionId() {
        return modifyPromotionId;
    }

    public void setModifyPromotionId(String modifyPromotionId) {
        this.modifyPromotionId = modifyPromotionId;
    }

    public Integer getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Integer dealFlag) {
        this.dealFlag = dealFlag;
    }

    public Integer getHasRedisClean() {
        return hasRedisClean;
    }

    public void setHasRedisClean(Integer hasRedisClean) {
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

    public PromotionBuyerRuleDTO getBuyerRuleDTO() {
        return buyerRuleDTO;
    }

    public void setBuyerRuleDTO(PromotionBuyerRuleDTO buyerRuleDTO) {
        this.buyerRuleDTO = buyerRuleDTO;
    }

    public PromotionSellerRuleDTO getSellerRuleDTO() {
        return sellerRuleDTO;
    }

    public void setSellerRuleDTO(PromotionSellerRuleDTO sellerRuleDTO) {
        this.sellerRuleDTO = sellerRuleDTO;
    }

    public List<? extends PromotionAccumulatyDTO> getPromotionAccumulatyList() {
        return promotionAccumulatyList;
    }

    public void setPromotionAccumulatyList(
            List<? extends PromotionAccumulatyDTO> promotionAccumulatyList) {
        this.promotionAccumulatyList = promotionAccumulatyList;
    }

    public List<PromotionStatusHistoryDTO> getPromotionStatusHistoryList() {
        return promotionStatusHistoryList;
    }

    public void setPromotionStatusHistoryList(
            List<PromotionStatusHistoryDTO> promotionStatusHistoryList) {
        this.promotionStatusHistoryList = promotionStatusHistoryList;
    }

    public List<PromotionPictureDTO> getPromotionPictureList() {
        return promotionPictureList;
    }

    public void setPromotionPictureList(
            List<PromotionPictureDTO> promotionPictureList) {
        this.promotionPictureList = promotionPictureList;
    }

    public PromotionSloganDTO getPromotionSloganDTO() {
        return promotionSloganDTO;
    }

    public void setPromotionSloganDTO(PromotionSloganDTO promotionSloganDTO) {
        this.promotionSloganDTO = promotionSloganDTO;
    }

    public PromotionDetailDescribeDTO getPromotionDetailDescribeDTO() {
        return promotionDetailDescribeDTO;
    }

    public void setPromotionDetailDescribeDTO(
            PromotionDetailDescribeDTO promotionDetailDescribeDTO) {
        this.promotionDetailDescribeDTO = promotionDetailDescribeDTO;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getTaskQueueNum() {
        return taskQueueNum;
    }

    public void setTaskQueueNum(int taskQueueNum) {
        this.taskQueueNum = taskQueueNum;
    }

    public List<String> getTaskIdList() {
        return taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public List<String> getVerifyStatusList() {
        return verifyStatusList;
    }

    public void setVerifyStatusList(List<String> verifyStatusList) {
        this.verifyStatusList = verifyStatusList;
    }

    public String getEffectiveTimeStr() {
        return effectiveTimeStr;
    }

    public void setEffectiveTimeStr(String effectiveTimeStr) {
        this.effectiveTimeStr = effectiveTimeStr;
    }

    public String getInvalidTimeStr() {
        return invalidTimeStr;
    }

    public void setInvalidTimeStr(String invalidTimeStr) {
        this.invalidTimeStr = invalidTimeStr;
    }

    public PromotionSellerDetailDTO getSellerDetailDTO() {
        return sellerDetailDTO;
    }

    public void setSellerDetailDTO(PromotionSellerDetailDTO sellerDetailDTO) {
        this.sellerDetailDTO = sellerDetailDTO;
    }

    public void setPromoionInfo(PromotionInfoDTO promotionInfoDTO) {
        super.setMessageId(promotionInfoDTO.getMessageId());
        this.id = promotionInfoDTO.getId();
        this.promotionId = promotionInfoDTO.getPromotionId();
        this.promotionName = promotionInfoDTO.getPromotionName();
        this.promotionDescribe = promotionInfoDTO.getPromotionDescribe();
        this.promotionProviderType = promotionInfoDTO.getPromotionProviderType();
        this.promotionProviderSellerCode = promotionInfoDTO.getPromotionProviderSellerCode();
        this.promotionProviderShopId = promotionInfoDTO.getPromotionProviderShopId();
        this.costAllocationType = promotionInfoDTO.getCostAllocationType();
        this.promotionType = promotionInfoDTO.getPromotionType();
        this.effectiveTime = promotionInfoDTO.getEffectiveTime();
        this.invalidTime = promotionInfoDTO.getInvalidTime();
        this.isVip = promotionInfoDTO.getIsVip();
        this.showStatus = promotionInfoDTO.getShowStatus();
        this.status = promotionInfoDTO.getStatus();
        this.verifierId = promotionInfoDTO.getVerifierId();
        this.verifierName = promotionInfoDTO.getVerifierName();
        this.verifyTime = promotionInfoDTO.getVerifyTime();
        this.verifyRemark = promotionInfoDTO.getVerifyRemark();
        this.modifyPromotionId = promotionInfoDTO.getModifyPromotionId();
        this.createId = promotionInfoDTO.getCreateId();
        this.createName = promotionInfoDTO.getCreateName();
        this.createTime = promotionInfoDTO.getCreateTime();
        this.modifyId = promotionInfoDTO.getModifyId();
        this.modifyName = promotionInfoDTO.getModifyName();
        this.modifyTime = promotionInfoDTO.getModifyTime();
        this.promotionAccumulatyList = promotionInfoDTO.getPromotionAccumulatyList();
        this.promotionStatusHistoryList = promotionInfoDTO.getPromotionStatusHistoryList();
        this.promotionPictureList = promotionInfoDTO.getPromotionPictureList();
        this.promotionDetailDescribeDTO = promotionInfoDTO.getPromotionDetailDescribeDTO();
        this.promotionSloganDTO = promotionInfoDTO.getPromotionSloganDTO();
        this.buyerRuleDTO = promotionInfoDTO.getBuyerRuleDTO();
        this.sellerRuleDTO = promotionInfoDTO.getSellerRuleDTO();
        this.dealFlag = promotionInfoDTO.getDealFlag();
        this.hasRedisClean = promotionInfoDTO.getHasRedisClean();
    }
}
