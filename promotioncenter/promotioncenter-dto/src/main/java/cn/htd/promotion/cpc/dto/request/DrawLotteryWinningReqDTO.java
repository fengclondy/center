package cn.htd.promotion.cpc.dto.request;

public class DrawLotteryWinningReqDTO extends DrawLotteryResultReqDTO {

    /**
     * 抽奖人名称
     */
    private String buyerName;
    /**
     * 抽奖人电话号码
     */
    private String buyerTelephone;
    /**
     * 会员店名称
     */
    private String sellerName;
    /**
     * 会员店地址
     */
    private String sellerAddress;
    /**
     * 归属平台公司名称
     */
    private String belongsSuperiorName;
    /**
     * 中奖人姓名
     */
    private String winnerName;
    /**
     * 中奖人联系方式
     */
    private String winningContact;
    /**
     * 充值电话号码
     */
    private String chargeTelephone;
    /**
     * 卷号（红包雨使用）
     */
    private String relevanceCouponCode;
    /**
     * 活动类型（红包雨使用）
     */
    private String promotionType;
    /**
     * 活动名称（红包雨使用）
     */
    private String promotionName;
    

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

    public String getBelongsSuperiorName() {
        return belongsSuperiorName;
    }

    public void setBelongsSuperiorName(String belongsSuperiorName) {
        this.belongsSuperiorName = belongsSuperiorName;
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

	public String getRelevanceCouponCode() {
		return relevanceCouponCode;
	}

	public void setRelevanceCouponCode(String relevanceCouponCode) {
		this.relevanceCouponCode = relevanceCouponCode;
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
	
}
