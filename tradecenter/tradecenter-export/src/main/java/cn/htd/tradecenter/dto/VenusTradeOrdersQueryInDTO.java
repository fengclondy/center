package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class VenusTradeOrdersQueryInDTO implements Serializable {

	private static final long serialVersionUID = -6911305614814198858L;
	// ----------------------------VMS检索条件 start-----------------------
	/**
	 * 卖家编号
	 */
	@NotBlank(message = "卖家编号不能为空")
	private String sellerCode;
	/**
	 * 订单行状态
	 */
	private String orderStatus;
	/**
	 * 订单号、客户名称、联系电话
	 */
	private String searchStr;
	/**
	 * 客户类型
	 */
	private String buyerType;
	/**
	 * 下单开始时间
	 */
	private Date createStart;
	/**
	 * 下单结束时间
	 */
	private Date createEnd;
	/**
	 * 渠道编码
	 */
	private String channelCode;
	// ----------------------------VMS检索条件 end-----------------------
	/**
	 * 订单状态List
	 */
	private List<String> orderStatusList;
	/**
	 * 订单取消状态
	 */
	private int isCancelFlag = -1;
	/**
	 * 订单异常标志
	 */
	private int isErrorFlag = -1;

	private String thirdCategoryId;

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public Date getCreateStart() {
		return createStart;
	}

	public void setCreateStart(Date createStart) {
		this.createStart = createStart;
	}

	public Date getCreateEnd() {
		return createEnd;
	}

	public void setCreateEnd(Date createEnd) {
		this.createEnd = createEnd;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public List<String> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public int getIsCancelFlag() {
		return isCancelFlag;
	}

	public void setIsCancelFlag(int isCancelFlag) {
		this.isCancelFlag = isCancelFlag;
	}

	public int getIsErrorFlag() {
		return isErrorFlag;
	}

	public void setIsErrorFlag(int isErrorFlag) {
		this.isErrorFlag = isErrorFlag;
	}

	/**
	 * @return the thirdCategoryId
	 */
	public String getThirdCategoryId() {
		return thirdCategoryId;
	}

	/**
	 * @param thirdCategoryId
	 *            the thirdCategoryId to set
	 */
	public void setThirdCategoryId(String thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

}
