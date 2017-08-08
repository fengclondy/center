/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: SalemanDTO.java
 * Author:   Administrator
 * Date:     下午5:15:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 */

package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class SalemanDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerManagerCode;
	private String customerManagerName;

	/**
	 * @return the customerManagerCode
	 */
	public String getCustomerManagerCode() {
		return customerManagerCode;
	}

	/**
	 * @param customerManagerCode
	 *            the customerManagerCode to set
	 */
	public void setCustomerManagerCode(String customerManagerCode) {
		this.customerManagerCode = customerManagerCode;
	}

	/**
	 * @return the customerManagerName
	 */
	public String getCustomerManagerName() {
		return customerManagerName;
	}

	/**
	 * @param customerManagerName
	 *            the customerManagerName to set
	 */
	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

}
