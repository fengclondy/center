package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;

public class RechargeOrderQueryReqDTO extends GenricReqDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8439328288057876135L;

	private String memberCode;

	private String orderNo;

	private Integer start;

	private Integer rows;

	private Date payTimeFrom;

	private Date payTimeTo;

	public Date getPayTimeFrom() {
		return payTimeFrom;
	}

	public void setPayTimeFrom(Date payTimeFrom) {
		this.payTimeFrom = payTimeFrom;
	}

	public Date getPayTimeTo() {
		return payTimeTo;
	}

	public void setPayTimeTo(Date payTimeTo) {
		this.payTimeTo = payTimeTo;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
