package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tangjiayong on 2017/8/21.
 *
 * 中奖纪录查询DTO
 */
public class PromotionAwardReqDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String promotionId;//促销活动编码

    private String levelCode;//层级编码

    private String promotionType;//活动类型 1：优惠券，2：秒杀，21：扭蛋机，22：砍价，23：总部秒杀

    private String promotionName;//促销活动名称

    private String buyerCode;//会员编码

    private String buyerName;//会员名称

    private String buyerTelephone;//会员电话号码

    private String sellerCode;//归属会员店编号

    private String rewardType;//奖励类型 1:发券，2：实物奖品，3：话费，4：汇金币

    private String relevanceBargainCode;//关联砍价发起编号

    private String rewardName;//奖品名称

    private Date winningTime;//中奖时间

    private Long createId;//创建人ID

    private String createName;//创建人名称

    private Date createTime;//创建时间

    private Long modifyId;//更新ID

    private String modifyName;//更新名称

    private Date modifyTime;//更新时间

    //用于分页
    private Integer page;

    private Integer pageSize;

    //用于搜索
    private String winningStartTime;//中奖开始时间

    private String winningEdnTime;//中奖结束时间

    public String getWinningStartTime() {
        return winningStartTime;
    }

    public void setWinningStartTime(String winningStartTime) {
        this.winningStartTime = winningStartTime;
    }

    public String getWinningEdnTime() {
        return winningEdnTime;
    }

    public void setWinningEdnTime(String winningEdnTime) {
        this.winningEdnTime = winningEdnTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public Date getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(Date winningTime) {
        this.winningTime = winningTime;
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
