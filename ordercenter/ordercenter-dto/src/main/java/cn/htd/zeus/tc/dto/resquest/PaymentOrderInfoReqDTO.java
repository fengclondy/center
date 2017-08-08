package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class PaymentOrderInfoReqDTO extends GenricReqDTO implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = -4578565788625583879L;

	@NotEmpty(message = "rechargeOrderNo不能为空")
	private String rechargeOrderNo;

	private String voucherNo;

	private String companyCode;

	private Date timestamp;

	private String resultCode;

	private Integer paymentResultStatus;

	private String resultMessage;

	private String requestNo;

	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getPaymentResultStatus() {
		return paymentResultStatus;
	}

	public void setPaymentResultStatus(Integer paymentResultStatus) {
		this.paymentResultStatus = paymentResultStatus;
	}

}