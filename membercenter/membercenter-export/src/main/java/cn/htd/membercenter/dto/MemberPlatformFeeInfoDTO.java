/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberPlatformFeeInfoDTO</p>
* @author youyajun
* @date 2016年12月14日
* <p>Description: 
*			商家平台使用费信息（外部专用）导入
* </p>
 */
package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberPlatformFeeInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 商家ID
	private Long sellerId;
	// 公司名称
	private String companyName;
	// 公司编号
	private String companyCode;
	// 缴费金额
	private BigDecimal fee;
	// 备注
	private String remarks;
	// 更新人ID
	private Long createId;
	// 更新人名称
	private String createName;
	// 更新时间
	private Date createTime;
	// 缴费金额名称
	private String feeName;
	// 变化类型名称
	private String depositName;
	// 更新时间名称
	private String createTimeName;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getDepositName() {
		return depositName;
	}

	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}

	public String getCreateTimeName() {
		return createTimeName;
	}

	public void setCreateTimeName(String createTimeName) {
		this.createTimeName = createTimeName;
	}
}
