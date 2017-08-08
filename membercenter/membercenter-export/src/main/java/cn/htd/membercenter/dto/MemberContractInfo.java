package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberContractInfo extends BaseDTO implements Serializable {

	private static final long serialVersionUID = -5300908530290453423L;

	private Long contractId;// 合同id
	private String contractCode;// 合同编号
	private String contractType;// 合同类型
	private String contractJssAddr;// 合同存储地址
	private Date contractStarttime;// 合同生效开始时间
	private Date contractEndtime;// 合同生效结束时间

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractJssAddr() {
		return contractJssAddr;
	}

	public void setContractJssAddr(String contractJssAddr) {
		this.contractJssAddr = contractJssAddr;
	}

	public Date getContractStarttime() {
		return contractStarttime;
	}

	public void setContractStarttime(Date contractStarttime) {
		this.contractStarttime = contractStarttime;
	}

	public Date getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(Date contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

}
