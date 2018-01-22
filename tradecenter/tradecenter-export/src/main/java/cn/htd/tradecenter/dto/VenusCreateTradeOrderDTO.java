/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VmsTradeOrderDTO.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: VMS新增订单信息DTO  
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
 * VMS新增订单DTO
 * 
 * @author jiangkun
 */
public class VenusCreateTradeOrderDTO extends CreateTradeOrderDTO implements Serializable {

	private static final long serialVersionUID = 14054232385081974L;

	/**
	 * 销售方式 1:正常销售，2:特价销售等。。
	 */
	@NotBlank(message = "销售方式不能为空")
	private String salesType;

	/**
	 * 销售部门编码 （VMS开单专用）
	 */
	@NotBlank(message = "销售部门不能为空")
	@Length(min = 1, max = 32, message = "销售部门超长")
	private String salesDepartmentCode;

    /**
     * 订单来源
     */
    @NotBlank(message = "订单来源不能为空")
    private String orderFrom;

	/**
	 * 订单行及仓库信息列表
	 */
	@NotNull(message = "订单行及仓库信息不能为空")
	@Valid
	private List<VenusCreateTradeOrderItemDTO> tradeItemDTOList;

	/**
	 * 返利单信息
	 */
	private VenusCreateTradeOrderRebateDTO rebateDTO;
	
	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	public List<VenusCreateTradeOrderItemDTO> getTradeItemDTOList() {
		return tradeItemDTOList;
	}

	public void setTradeItemDTOList(List<VenusCreateTradeOrderItemDTO> tradeItemDTOList) {
		this.tradeItemDTOList = tradeItemDTOList;
	}

	public VenusCreateTradeOrderRebateDTO getRebateDTO() {
		return rebateDTO;
	}

	public void setRebateDTO(VenusCreateTradeOrderRebateDTO rebateDTO) {
		this.rebateDTO = rebateDTO;
	}

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }
}
