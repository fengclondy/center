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
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * VMS新增订单行DTO
 * 
 * @author jiangkun
 *
 */
public class VenusConfirmTradeOrderDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 订单编号
	 */
	@NotBlank(message = "订单编号不能为空")
	private String orderNo;

	/**
	 * 销售方式 1:正常销售，2:特价销售等。。
	 */
	@NotNull(message = "销售方式不能为空")
	private String salesType;

	/**
	 * 审核备注
	 */
	private String verifyComment;

	/**
	 * 订单modifyTime做乐观排他用
	 */
	@NotBlank(message = "订单更新时间不能为空")
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

	/**
	 * 订单行列表
	 */
	@NotNull(message = "订单行及仓库信息不能为空")
	@Valid
	private List<VenusConfirmTradeOrderItemDTO> orderItemList;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getVerifyComment() {
		return verifyComment;
	}

	public void setVerifyComment(String verifyComment) {
		this.verifyComment = verifyComment;
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

	public List<VenusConfirmTradeOrderItemDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<VenusConfirmTradeOrderItemDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
