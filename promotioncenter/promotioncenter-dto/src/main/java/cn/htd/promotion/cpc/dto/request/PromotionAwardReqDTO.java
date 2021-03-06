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

    private String sellerName;//归属会员店名称

    private String sellerAddress;//会员店地址

    private String belongSuperiorName;//归属上级公司名称

    private String rewardType;//奖励类型 1:发券，2：实物奖品，3：话费，4：汇金币

    private String relevanceBargainCode;//关联砍价发起编号

    private String rewardName;//奖品名称

    private String awardValue;//奖品值

    private Date winningTime;//中奖时间

    private String winnerName;//中奖人姓名

    private String winningContact;//中奖人联系方式

    private String chargeTelephone;//充值号码

    private String logisticsStatus;//物流状态：00-待发货，01-已发货

    private String logisticsCompany;//物流编号

    private String logisticsNo;//物流编号

    private Long createId;//创建人ID

    private String createName;//创建人名称

    private Date createTime;//创建时间

    private Long modifyId;//更新ID

    private String modifyName;//更新名称

    private Date modifyTime;//更新时间

    //用于秒杀订单
    private String orderStatus;//订单状态

    private String orderNo;//订单编号


    //用于分页
    private Integer page;

    private Integer pageSize;

    //用于搜索
    private String winningStartTime;//中奖开始时间

    private String winningEndTime;//中奖结束时间

    //用于区分来源
    private String source;

    //关联优惠券编号
    private String relevanceCouponCode;
    
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

    public String getWinningStartTime() {
        return winningStartTime;
    }

    public void setWinningStartTime(String winningStartTime) {
        this.winningStartTime = winningStartTime;
    }

    public String getWinningEndTime() {
        return winningEndTime;
    }

    public void setWinningEndTime(String winningEndTime) {
        this.winningEndTime = winningEndTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	public String getRelevanceCouponCode() {
		return relevanceCouponCode;
	}

	public void setRelevanceCouponCode(String relevanceCouponCode) {
		this.relevanceCouponCode = relevanceCouponCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
    
    
}
