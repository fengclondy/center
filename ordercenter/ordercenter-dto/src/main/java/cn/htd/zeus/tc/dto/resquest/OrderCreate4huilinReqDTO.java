package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreate4huilinReqDTO implements Serializable {

	private static final long serialVersionUID = -7963788605565343410L;

	// 接口编号
	@NotEmpty(message = "messageId不能为空")
	private String messageId;

	// 会员编号
	@NotEmpty(message = "buyerId不能为空")
	private Long buyerId;

	// 买家留言
	@NotEmpty(message = "buyerRemarks不能为空")
	private String buyerRemarks;

	// 卖家编码
	@NotEmpty(message = "sellerId不能为空")
	private Long sellerId;

	// 订单来源
	@NotEmpty(message = "orderFrom不能为空")
	private String orderFrom;

	// 订单行集合
	@NotNull(message = "skuList不能为空")
	@Valid
	private List<OrderCreateSkuListInfoReqDTO> skuList;

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
	 * @return the buyerRemarks
	 */
	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	/**
	 * @param buyerRemarks
	 *            the buyerRemarks to set
	 */
	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}

	/**
	 * @return the buyerId
	 */
	public Long getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId
	 *            the buyerId to set
	 */
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the orderFrom
	 */
	public String getOrderFrom() {
		return orderFrom;
	}

	/**
	 * @param orderFrom
	 *            the orderFrom to set
	 */
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	/**
	 * @return the skuList
	 */
	public List<OrderCreateSkuListInfoReqDTO> getSkuList() {
		return skuList;
	}

	/**
	 * @param skuList
	 *            the skuList to set
	 */
	public void setSkuList(List<OrderCreateSkuListInfoReqDTO> skuList) {
		this.skuList = skuList;
	}

}
