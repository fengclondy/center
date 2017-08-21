package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 促销活动DTO
 */
public class PromotionInfoDTO implements Serializable {

    private static final long serialVersionUID = -1587859622731343639L;
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
    //@NotBlank(message = "促销活动描述不能为空")
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
     * 会员规则ID: 新增优惠券活动时需要根据此ID获取会员规则信息
     */
    private Long buyerRuleId;
    /**
     * 供应商规则ID, 新增优惠券活动时需要根据此ID获取供应商规则信息
     */
    private Long sellerRuleId;
    /**
     * 品类商品规则ID, 新增优惠券活动时需要根据此ID获取品类商品规则信息
     */
    private Long categoryItemRuleId;
    /**
     * 会员规则描述
     */
    private String buyerRuleDesc;
    /**
     * 供应商规则描述
     */
    private String sellerRuleDesc;
    /**
     * 品类商品规则描述
     */
    private String categoryItemRuleDesc;
    /**
     * 活动层级列表
     */
    private List<PromotionAccumulatyDTO> promotionAccumulatyList;
    /**
     * 促销活动状态履历
     */
    private List<PromotionStatusHistoryDTO> promotionStatusHistoryList;
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

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getBuyerRuleId() {
        return buyerRuleId;
    }

    public void setBuyerRuleId(Long buyerRuleId) {
        this.buyerRuleId = buyerRuleId;
    }

    public Long getSellerRuleId() {
        return sellerRuleId;
    }

    public void setSellerRuleId(Long sellerRuleId) {
        this.sellerRuleId = sellerRuleId;
    }

    public Long getCategoryItemRuleId() {
        return categoryItemRuleId;
    }

    public void setCategoryItemRuleId(Long categoryItemRuleId) {
        this.categoryItemRuleId = categoryItemRuleId;
    }

    public String getBuyerRuleDesc() {
        return buyerRuleDesc;
    }

    public void setBuyerRuleDesc(String buyerRuleDesc) {
        this.buyerRuleDesc = buyerRuleDesc;
    }

    public String getSellerRuleDesc() {
        return sellerRuleDesc;
    }

    public void setSellerRuleDesc(String sellerRuleDesc) {
        this.sellerRuleDesc = sellerRuleDesc;
    }

    public String getCategoryItemRuleDesc() {
        return categoryItemRuleDesc;
    }

    public void setCategoryItemRuleDesc(String categoryItemRuleDesc) {
        this.categoryItemRuleDesc = categoryItemRuleDesc;
    }

    public List<PromotionAccumulatyDTO> getPromotionAccumulatyList() {
        return promotionAccumulatyList;
    }

    public void setPromotionAccumulatyList(List<PromotionAccumulatyDTO> promotionAccumulatyList) {
        this.promotionAccumulatyList = promotionAccumulatyList;
    }

    public List<PromotionStatusHistoryDTO> getPromotionStatusHistoryList() {
        return promotionStatusHistoryList;
    }

    public void setPromotionStatusHistoryList(List<PromotionStatusHistoryDTO> promotionStatusHistoryList) {
        this.promotionStatusHistoryList = promotionStatusHistoryList;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    public void setPromoionInfo(PromotionInfoDTO promotionInfoDTO) {
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
        this.buyerRuleId = promotionInfoDTO.getBuyerRuleId();
        this.buyerRuleDesc = promotionInfoDTO.getBuyerRuleDesc();
        this.sellerRuleId = promotionInfoDTO.getSellerRuleId();
        this.sellerRuleDesc = promotionInfoDTO.getSellerRuleDesc();
        this.categoryItemRuleId = promotionInfoDTO.getCategoryItemRuleId();
        this.categoryItemRuleDesc = promotionInfoDTO.getCategoryItemRuleDesc();
        this.promotionAccumulatyList = promotionInfoDTO.getPromotionAccumulatyList();
        this.promotionStatusHistoryList = promotionInfoDTO.getPromotionStatusHistoryList();
    }
}
