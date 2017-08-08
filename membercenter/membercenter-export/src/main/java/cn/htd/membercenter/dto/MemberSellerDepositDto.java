/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberCompanyInfoDto</p>
* @author youyajun
* @date 2016年12月12日
* <p>Description: 
*			商家信息（外部）导入
* </p>
 */
package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberSellerDepositDto implements Serializable {
	private static final long serialVersionUID = 1L;
	// 商家ID
	private Long sellerId;
	// 公司名称
	private String companyName;
	// 公司编号
	private String companyCode;
	// 保证金余额
	private BigDecimal depositBalance;
	// 变动类型
	private String changeType;
	// 变动金额
	private BigDecimal changeDeposit;
	// 变动后保证金余额
	private BigDecimal depositBalanceBack;
	// 备注
	private String remarks;
	// 更新人ID
	private Long createId;
	// 更新人名称
	private String createName;
	// 更新时间
	private Date createTime;
	// 变动类型名称
	private String changeTypeName;
	// 变动金额表示
	private String changeDepositName;
	// 更新时间表示用
	private String createTimeName;
	// 变动后保证金余额表示用
	private String depositBalanceBackNmae;

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

	public BigDecimal getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(BigDecimal depositBalance) {
		this.depositBalance = depositBalance;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public BigDecimal getChangeDeposit() {
		return changeDeposit;
	}

	public void setChangeDeposit(BigDecimal changeDeposit) {
		this.changeDeposit = changeDeposit;
	}

	public BigDecimal getDepositBalanceBack() {
		return depositBalanceBack;
	}

	public void setDepositBalanceBack(BigDecimal depositBalanceBack) {
		this.depositBalanceBack = depositBalanceBack;
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

	public String getChangeTypeName() {
		return changeTypeName;
	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public String getChangeDepositName() {
		return changeDepositName;
	}

	public void setChangeDepositName(String changeDepositName) {
		this.changeDepositName = changeDepositName;
	}

	public String getCreateTimeName() {
		return createTimeName;
	}

	public void setCreateTimeName(String createTimeName) {
		this.createTimeName = createTimeName;
	}

	public String getDepositBalanceBackNmae() {
		return depositBalanceBackNmae;
	}

	public void setDepositBalanceBackNmae(String depositBalanceBackNmae) {
		this.depositBalanceBackNmae = depositBalanceBackNmae;
	}
}
