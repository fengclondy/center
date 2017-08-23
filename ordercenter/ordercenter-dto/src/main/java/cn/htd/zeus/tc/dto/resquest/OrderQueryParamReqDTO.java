package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 根据条件查询订单输入参数DTO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderQueryReqDTO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 */
public class OrderQueryParamReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2965872780264767995L;

	/**
	 * 会员code
	 */
	private String buyerCode;
	
	/**
	 * 会员id
	 */
	private Long buyerId;
	
	/**
	 * 卖家编号
	 */
	private String sellerCode;

	/**
	 * 卖家名称
	 */
	private String sellerName;

	private String buyerName;

	private String buyerPhone;

	private Integer orderDeleteStatus;

	private Integer isCancelOrder;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 下单日期开始
	 */
	private Date createOrderTimeFrom;

	/**
	 * 下单日期结束
	 */
	private Date createOrderTimeTo;

	/**
	 * 订单状态
	 */
	private List<?> orderStatus;

	/**
	 *
	 */
	@NotEmpty(message = "start不能为空")
	private Integer start;

	/**
	 *
	 */
	@NotEmpty(message = "rows不能为空")
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

	public Integer getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(Integer orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
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

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
}
