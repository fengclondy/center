/**
 * 
 */
package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;


/**
 * @author ly
 *
 */
public class OrderCancelInfoReqDTO  extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172330166415670419L;


	/**
	 * 订单取消人ID
	 */
	private String orderCancelMemberId = null;

	/**
	 * 订单取消人名称
	 */
	private String orderCancelMemberName = null;

	/**
	 * 订单取消原因
	 */
	private String orderCancelReason = null;
	
	/**
	 * 订单号
	 */
	private String orderNo = null;
	
	/**
	 * 会员code编码
	 */
	private String memberCode = null;
	
	/**
	 * 订单行订单号
	 */
	private String 	orderItemNo = null;
	
	/**
	 * 订单删除状态 1-删除 2-彻底删除 3-还原
	 */
	private String isDeleteStatus = null;


	public String getOrderCancelMemberId() {
		return orderCancelMemberId;
	}

	public void setOrderCancelMemberId(String orderCancelMemberId) {
		this.orderCancelMemberId = orderCancelMemberId;
	}

	public String getOrderCancelMemberName() {
		return orderCancelMemberName;
	}

	public void setOrderCancelMemberName(String orderCancelMemberName) {
		this.orderCancelMemberName = orderCancelMemberName;
	}

	public String getOrderCancelReason() {
		return orderCancelReason;
	}

	public void setOrderCancelReason(String orderCancelReason) {
		this.orderCancelReason = orderCancelReason;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getIsDeleteStatus() {
		return isDeleteStatus;
	}

	public void setIsDeleteStatus(String isDeleteStatus) {
		this.isDeleteStatus = isDeleteStatus;
	}
	
}
