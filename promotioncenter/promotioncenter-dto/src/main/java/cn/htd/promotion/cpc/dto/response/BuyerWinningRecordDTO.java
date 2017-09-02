package cn.htd.promotion.cpc.dto.response;

import java.util.Date;

import cn.htd.promotion.cpc.dto.response.GenricResDTO;

public class BuyerWinningRecordDTO extends GenricResDTO {

    /**
     * 中奖记录ID
     */
    private Long id;
    /**
     * 促销活动ID
     */
    private String promotionId;
    /**
     * 促销活动层级编码
     */
    private String levelCode;
    /**
     * 促销活动类型
     */
    private String promotionType;
    /**
     * 促销活动名称
     */
    private String promotionName;
    /**
     * 中奖会员编码
     */
    private String buyerCode;
    /**
     * 中奖会员名称
     */
    private String buyerName;
    /**
     * 中奖会员联系电话
     */
    private String buyerTelephone;
    /**
     * 卖家编码
     */
    private String sellerCode;
    /**
     * 卖家名称
     */
    private String sellerName;
    /**
     * 卖家地址
     */
    private String sellerAddress;
    /**
     * 卖家归属上级公司名称
     */
    private String belongSuperiorName;
    /**
     * 奖品类型
     */
    private String rewardType;
    /**
     * 关联砍价发起编码
     */
    private String relevanceBargainCode;
    /**
     * 奖品名称
     */
    private String rewardName;
    /**
     * 奖金面值
     */
    private String awardValue;
    /**
     * 中奖时间
     */
    private Date winningTime;
    /**
     * 得奖者姓名
     */
    private String winnerName;
    /**
     * 得奖人联系方式
     */
    private String winningContact;
    /**
     * 话费充值手机号码
     */
    private String chargeTelephone;
    /**
     * 物流状态
     */
    private String logisticsStatus;
    /**
     * 物流公司名称
     */
    private String logisticsCompany;
    /**
     * 物流单号
     */
    private String logisticsNo;
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
     * 更新时间
     */
    private Date modifyTime;

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

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTelephone() {
        return buyerTelephone;
    }

    public void setBuyerTelephone(String buyerTelephone) {
        this.buyerTelephone = buyerTelephone;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getBelongSuperiorName() {
        return belongSuperiorName;
    }

    public void setBelongSuperiorName(String belongSuperiorName) {
        this.belongSuperiorName = belongSuperiorName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRelevanceBargainCode() {
        return relevanceBargainCode;
    }

    public void setRelevanceBargainCode(String relevanceBargainCode) {
        this.relevanceBargainCode = relevanceBargainCode;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue;
    }

    public Date getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(Date winningTime) {
        this.winningTime = winningTime;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinningContact() {
        return winningContact;
    }

    public void setWinningContact(String winningContact) {
        this.winningContact = winningContact;
    }

    public String getChargeTelephone() {
        return chargeTelephone;
    }

    public void setChargeTelephone(String chargeTelephone) {
        this.chargeTelephone = chargeTelephone;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
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

    public void setBuyerWinningRecordByPromoitonInfo(PromotionInfoDTO promoitonInfoDTO) {
        this.promotionId = promoitonInfoDTO.getPromotionId();
        this.promotionType = promoitonInfoDTO.getPromotionType();
        this.promotionName = promoitonInfoDTO.getPromotionName();
    }
    public void setBuyerWinningRecordByAwardInfo(PromotionAwardInfoDTO awardInfoDTO) {
        setBuyerWinningRecordByPromoitonInfo(awardInfoDTO);
        this.levelCode = awardInfoDTO.getLevelCode();
        this.rewardType = awardInfoDTO.getAwardType();
        this.rewardName = awardInfoDTO.getAwardName();
        this.awardValue = awardInfoDTO.getAwardValue();
    }
}
