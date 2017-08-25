package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动
 * @author xmz
 *
 */
public class PromotonInfoResDTO implements Serializable{

	private static final long serialVersionUID = -2088054923297729091L;

	/**
	 * 促销活动id
	 */
	private String promotionId;
	/**
	 * 促销活动名称
	 */
	private String promotionName;
	/**
	 * 促销活动开始时间
	 */
    private Date effectiveTime;
    /**
     * 促销活动结束时间
     */
    private Date invalidTime;
    /**
     * 活动状态
     */
    private String status;
    /**
     * 参砍商品种类
     */
    private int bargainType;
    /**
     * 未砍商品数量
     */
    private int noBargainItemQTY;
    /**
     *  发起砍价人数
     */
    private int launchBargainQTY;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getBargainType() {
		return bargainType;
	}
	public void setBargainType(int bargainType) {
		this.bargainType = bargainType;
	}
	public int getNoBargainItemQTY() {
		return noBargainItemQTY;
	}
	public void setNoBargainItemQTY(int noBargainItemQTY) {
		this.noBargainItemQTY = noBargainItemQTY;
	}
	public int getLaunchBargainQTY() {
		return launchBargainQTY;
	}
	public void setLaunchBargainQTY(int launchBargainQTY) {
		this.launchBargainQTY = launchBargainQTY;
	}
}
