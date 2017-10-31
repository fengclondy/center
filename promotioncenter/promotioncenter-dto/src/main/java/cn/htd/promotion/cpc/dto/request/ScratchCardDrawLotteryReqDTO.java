/**
 *
 */
package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 抽奖活动信息DTO
 */
public class ScratchCardDrawLotteryReqDTO extends GenricReqDTO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6889503625008266495L;
	
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
     * 抽奖活动编码
     */
    @NotEmpty(message = "活动编码不能为空")
    private String promotionId;
    
    //是否使用同步，默认使用异步
    private boolean useSync = false;
    
	/**
	 * 订单号
	 */
	private String orderNo;

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
    
    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

	public boolean isUseSync() {
		return useSync;
	}

	public void setUseSync(boolean useSync) {
		this.useSync = useSync;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
