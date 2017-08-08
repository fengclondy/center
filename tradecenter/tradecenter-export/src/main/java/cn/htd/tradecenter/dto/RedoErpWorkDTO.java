/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VmsCreateTradeOrderItemInDTO.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: VMS新增订单行信息DTO  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * ERP下行处理重处理DTO
 * 
 * @author jiangkun
 *
 */
public class RedoErpWorkDTO implements Serializable {

	private static final long serialVersionUID = -4575514794499324196L;

	/**
	 * 订单编号
	 */
	@NotBlank(message = "订单编号不能为空")
	private String orderNo;

	/**
	 * 订单行编号
	 */
	@NotNull(message = "订单行遍号不能为空")
	private String orderItemNo;
	/**
	 * 订单行modifyTime做乐观排他用
	 */
	@NotBlank(message = "订单行更新时间不能为空")
	private String modifyTimeStr;

	/**
	 * 操作者ID
	 */
	@NotNull(message = "操作者ID不能为空")
	private Long operatorId;

	/**
	 * 操作者名称
	 */
	@NotBlank(message = "操作者名称不能为空")
	@Length(max = 255, message = "操作者名称超长")
	private String operatorName;

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

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
