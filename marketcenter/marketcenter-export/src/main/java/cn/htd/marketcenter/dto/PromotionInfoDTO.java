package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @NotBlank(message = "成本分摊类型不能为空")
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
     * 促销活动展示状态
     */
    private String showStatus;
    /**
     * 促销活动状态
     */
    private String status;
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
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
    /**
     * 关联B2C活动编号
     */
    private String b2cActivityCode;
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----
    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
    /**
     * 是否有活动提醒
     */
    private int isNeedRemind;
    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
    /**
     * 创建人ID
     */
    @NotNull(message = "创建人ID不能为空")
    private Long createId;
    /**
     * 创建人名称
     */
    @NotNull(message = "创建人名称不能为空")
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
     * 层级商品品类规则
     */
    private PromotionCategoryItemRuleDTO categoryItemRuleDTO;
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
    private List<? extends PromotionAccumulatyDTO> promotionAccumulatyList;
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

    public String getB2cActivityCode() {
        return b2cActivityCode;
    }

    public void setB2cActivityCode(String b2cActivityCode) {
        this.b2cActivityCode = b2cActivityCode;
    }

    public int getIsNeedRemind() {
        return isNeedRemind;
    }

    public void setIsNeedRemind(int isNeedRemind) {
        this.isNeedRemind = isNeedRemind;
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

    public PromotionCategoryItemRuleDTO getCategoryItemRuleDTO() {
        return categoryItemRuleDTO;
    }

    public void setCategoryItemRuleDTO(PromotionCategoryItemRuleDTO categoryItemRuleDTO) {
        this.categoryItemRuleDTO = categoryItemRuleDTO;
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

    public List<? extends PromotionAccumulatyDTO> getPromotionAccumulatyList() {
        return promotionAccumulatyList;
    }

    public void setPromotionAccumulatyList(List<? extends PromotionAccumulatyDTO> promotionAccumulatyList) {
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
        this.b2cActivityCode = promotionInfoDTO.getB2cActivityCode();
        this.isNeedRemind = promotionInfoDTO.getIsNeedRemind();
        this.createId = promotionInfoDTO.getCreateId();
        this.createName = promotionInfoDTO.getCreateName();
        this.createTime = promotionInfoDTO.getCreateTime();
        this.modifyId = promotionInfoDTO.getModifyId();
        this.modifyName = promotionInfoDTO.getModifyName();
        this.modifyTime = promotionInfoDTO.getModifyTime();
        this.buyerRuleId = promotionInfoDTO.getBuyerRuleId();
        this.buyerRuleDTO = promotionInfoDTO.getBuyerRuleDTO();
        this.buyerRuleDesc = promotionInfoDTO.getBuyerRuleDesc();
        this.sellerRuleId = promotionInfoDTO.getSellerRuleId();
        this.sellerRuleDTO = promotionInfoDTO.getSellerRuleDTO();
        this.sellerRuleDesc = promotionInfoDTO.getSellerRuleDesc();
        this.categoryItemRuleId = promotionInfoDTO.getCategoryItemRuleId();
        this.categoryItemRuleDTO = promotionInfoDTO.getCategoryItemRuleDTO();
        this.categoryItemRuleDesc = promotionInfoDTO.getCategoryItemRuleDesc();
        this.promotionAccumulatyList = promotionInfoDTO.getPromotionAccumulatyList();
        this.promotionStatusHistoryList = promotionInfoDTO.getPromotionStatusHistoryList();
    }
}
