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

import org.hibernate.validator.constraints.NotBlank;

/**
 * VMS新增订单行DTO
 * 
 * @author jiangkun
 *
 */
public class VenusConfirmTradeOrderItemDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 订单行编号
	 */
	@NotBlank(message = "订单行编号不能为空")
	private String orderItemNo;

	/**
	 * 订单行modifyTime做乐观排他用
	 */
	@NotBlank(message = "订单行更新时间不能为空")
	private String modifyTimeStr;

	/**
	 * 订单行拆单详细列表
	 */
	@NotNull(message = "订单行拆单信息不能为空")
	@Valid
	private List<VenusConfirmTradeOrderItemWarehouseDTO> itemWarehouseDTOList;

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

	public List<VenusConfirmTradeOrderItemWarehouseDTO> getItemWarehouseDTOList() {
		return itemWarehouseDTOList;
	}

	public void setItemWareHouseDTOList(List<VenusConfirmTradeOrderItemWarehouseDTO> itemWarehouseDTOList) {
		this.itemWarehouseDTOList = itemWarehouseDTOList;
	}
}
