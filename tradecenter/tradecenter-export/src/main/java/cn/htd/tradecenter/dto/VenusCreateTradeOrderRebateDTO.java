/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VmsCreateTradeOrderRebateInDTO.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: VMS新增订单返利信息DTO  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * VMS新增返利单DTO
 * 
 * @author jiangkun
 *
 */
public class VenusCreateTradeOrderRebateDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 返利单号
	 */
	@NotBlank(message = "返利单号不能为空")
	private String rebateNo;

	/**
	 * 返利单代码
	 */
	@NotBlank(message = "返利单代码不能为空")
	private String rebateCode;

	/**
	 * 返利单余额
	 */
	@NotNull(message = "返利单余额不能为空")
	@DecimalMin("0")
	private BigDecimal rebateBalance;

	/**
	 * 使用返利总金额
	 */
	private BigDecimal useRebateAmount = BigDecimal.ZERO;

	/**
	 * @return the rebateNo
	 */
	public String getRebateNo() {
		return rebateNo;
	}

	/**
	 * @param rebateNo the rebateNo to set
	 */
	public void setRebateNo(String rebateNo) {
		this.rebateNo = rebateNo;
	}

	/**
	 * @return the rebateCode
	 */
	public String getRebateCode() {
		return rebateCode;
	}

	/**
	 * @param rebateCode the rebateCode to set
	 */
	public void setRebateCode(String rebateCode) {
		this.rebateCode = rebateCode;
	}

	/**
	 * @param rebateBalance the rebateBalance to set
	 */
	public BigDecimal getRebateBalance() {
		return rebateBalance;
	}

	/**
	 * @param rebateBalance the rebateBalance to set
	 */
	public void setRebateBalance(BigDecimal rebateBalance) {
		this.rebateBalance = rebateBalance;
	}

	/**
	 * @return the useRebateAmount
	 */
	public BigDecimal getUseRebateAmount() {
		return useRebateAmount;
	}

	/**
	 * @param useRebateAmount the useRebateAmount to set
	 */
	public void setUseRebateAmount(BigDecimal useRebateAmount) {
		this.useRebateAmount = useRebateAmount;
	}

}
