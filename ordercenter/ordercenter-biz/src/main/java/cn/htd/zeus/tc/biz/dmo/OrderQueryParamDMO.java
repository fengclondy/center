package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;
import java.util.List;

/**
 * 订单参数类
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: tradeOrdersParamDMO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class OrderQueryParamDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = -4666384401785345729L;

	private String buyerCode;//会员code

	private String buyerName;

	private String sellerCode;

	private String customerManagerID;

	private String orderNo;//订单号

	private String shopName;//卖家名称

	private String sellerName;

	private Date createOrderTimeFrom;//下单日期开始

	private Date createOrderTimeTo;//下单日期结束

	private Integer orderDeleteStatus;//是否删除

	private Integer isCancelOrder;//是否取消

	private List<?> orderStatus;//订单状态

	//private String orderItemNos;//订单行号,用逗号分隔
	
	private List<String> orderItemNoList;//订单行号集合

	private Date payTimeLimit;//支付限制时间
	
	private Integer isVipItem;//是否VIP套餐商品
	
	private String skuCode;//商品SKU编码
	
	private String orderFrom;

	private String orderErrorFlag;

	/**
	 *
	 */
	private Integer start;

	/**
	 *
	 */
	private Integer rows;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getCreateOrderTimeFrom() {
		return createOrderTimeFrom;
	}

	public void setCreateOrderTimeFrom(Date createOrderTimeFrom) {
		this.createOrderTimeFrom = createOrderTimeFrom;
	}

	public Date getCreateOrderTimeTo() {
		return createOrderTimeTo;
	}

	public void setCreateOrderTimeTo(Date createOrderTimeTo) {
		this.createOrderTimeTo = createOrderTimeTo;
	}

	public List<?> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<?> orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getCustomerManagerID() {
		return customerManagerID;
	}

	public void setCustomerManagerID(String customerManagerID) {
		this.customerManagerID = customerManagerID;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public List<String> getOrderItemNoList() {
		return orderItemNoList;
	}

	public void setOrderItemNoList(List<String> orderItemNoList) {
		this.orderItemNoList = orderItemNoList;
	}

	/*public String getOrderItemNos() {
		return orderItemNos;
	}

	public void setOrderItemNos(String orderItemNos) {
		this.orderItemNos = orderItemNos;
	}
*/
	public Integer getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(Integer orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
	}

	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}

	public Integer getIsVipItem() {
		return isVipItem;
	}

	public void setIsVipItem(Integer isVipItem) {
		this.isVipItem = isVipItem;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getOrderErrorFlag() {
		return orderErrorFlag;
	}

	public void setOrderErrorFlag(String orderErrorFlag) {
		this.orderErrorFlag = orderErrorFlag;
	}
}
