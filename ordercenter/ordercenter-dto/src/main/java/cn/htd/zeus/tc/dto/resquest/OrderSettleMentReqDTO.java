package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;

public class OrderSettleMentReqDTO implements Serializable {

	private static final long serialVersionUID = 7065401528764482426L;

	// 买家会员编码
	@NotEmpty(message = "memberId不能为空")
	public String memberId;

	@NotNull(message = "sellerInfoList不能为空")
	@Valid
	public List<OrderSellerInfoDTO> sellerInfoList;

	@NotEmpty(message = "messageId不能为空")
	public String messageId;

	// 是否为秒杀活动 1：是 0：不是
	@NotEmpty(message = "isSeckill不能为空")
	public String isSeckill;

	// 买家所属城市站编码
	@NotEmpty(message = "citySiteCode不能为空")
	public String citySiteCode;

	// 秒杀活动编码
	private String promotionId;
	
	//是否有限时购商品  0:没有，1：有
	private int isHasLimitedTimePurchase;

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the sellerInfoList
	 */
	public List<OrderSellerInfoDTO> getSellerInfoList() {
		return sellerInfoList;
	}

	/**
	 * @param sellerInfoList
	 *            the sellerInfoList to set
	 */
	public void setSellerInfoList(List<OrderSellerInfoDTO> sellerInfoList) {
		this.sellerInfoList = sellerInfoList;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the isSeckill
	 */
	public String getIsSeckill() {
		return isSeckill;
	}

	/**
	 * @param isSeckill
	 *            the isSeckill to set
	 */
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}

	/**
	 * @return the citySiteCode
	 */
	public String getCitySiteCode() {
		return citySiteCode;
	}

	/**
	 * @param citySiteCode
	 *            the citySiteCode to set
	 */
	public void setCitySiteCode(String citySiteCode) {
		this.citySiteCode = citySiteCode;
	}

	/**
	 * @return the promotionId
	 */
	public String getPromotionId() {
		return promotionId;
	}

	/**
	 * @param promotionId
	 *            the promotionId to set
	 */
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public int getIsHasLimitedTimePurchase() {
		return isHasLimitedTimePurchase;
	}

	public void setIsHasLimitedTimePurchase(int isHasLimitedTimePurchase) {
		this.isHasLimitedTimePurchase = isHasLimitedTimePurchase;
	}

}
