package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class DrawLotteryWinningReqDTO extends DrawLotteryResultReqDTO{

    /**
     * 抽奖人名称
     */
    @NotEmpty(message = "抽奖人名称不能为空")
    private String buyerName;
    /**
     * 抽奖人电话号码
     */
    @NotEmpty(message = "抽奖人电话号码不能为空")
    private String buyerTelephone;
    /**
     * 会员店名称
     */
    @NotEmpty(message = "会员店名称不能为空")
    private String sellerName;
    /**
     * 会员店地址
     */
    @NotEmpty(message = "会员店地址不能为空")
    private String sellerAddress;
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
}
