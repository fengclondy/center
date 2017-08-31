/**
 *
 */
package cn.htd.promotion.cpc.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * 抽奖活动信息DTO
 */
public class DrawLotteryReqDTO extends GenricReqDTO {

    /**
     * 抽奖活动卖家编码
     */
    @NotEmpty(message = "卖家编码不能为空")
    private String sellerCode;
    /**
     * 抽奖活动参与买家编码
     */
    @NotEmpty(message = "买家编码不能为空")
    private String buyerCode;
    /**
     * 买家首次登陆标记
     */
    @NotNull(message = "买家首次登陆标记不能为空")
    @Range(min=0, max=1, message = "买家首次登陆标记只能是0或1")
    private int isBuyerFirstLogin;
    /**
     * 抽奖活动编码
     */
    @NotEmpty(message = "活动编码不能为空")
    private String promotionId;

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public int getIsBuyerFirstLogin() {
        return isBuyerFirstLogin;
    }

    public void setIsBuyerFirstLogin(int isBuyerFirstLogin) {
        this.isBuyerFirstLogin = isBuyerFirstLogin;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
}
