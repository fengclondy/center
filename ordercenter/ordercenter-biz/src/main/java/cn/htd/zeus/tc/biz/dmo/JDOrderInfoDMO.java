package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class JDOrderInfoDMO {

	private Long id;

    private String orderNo;

    private String orderItemNo;

    private String jdorderNo;

    private Integer erpResultStatus;

    private String erpResultCode;

    private String erpResultMsg;

    private Integer jdResultStatus;

    private String jdResultCode;

    private String jdResultMsg;

    private String erpLastMessageId;

    private String jdLastEssageId;

    private Integer jdSendCount;

    private Integer erpBookSendCount;

    private Date jdLastSendTime;

    private Date erpLastSendTime;

    private String orderItemNos;//订单行号--多个订单行用逗号分隔
    
    private Integer orderType;//订单类型

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getJdorderNo() {
		return jdorderNo;
	}

	public void setJdorderNo(String jdorderNo) {
		this.jdorderNo = jdorderNo;
	}

	public Integer getErpResultStatus() {
		return erpResultStatus;
	}

	public void setErpResultStatus(Integer erpResultStatus) {
		this.erpResultStatus = erpResultStatus;
	}

	public String getErpResultCode() {
		return erpResultCode;
	}

	public void setErpResultCode(String erpResultCode) {
		this.erpResultCode = erpResultCode;
	}

	public String getErpResultMsg() {
		return erpResultMsg;
	}

	public void setErpResultMsg(String erpResultMsg) {
		this.erpResultMsg = erpResultMsg;
	}

	public Integer getJdResultStatus() {
		return jdResultStatus;
	}

	public void setJdResultStatus(Integer jdResultStatus) {
		this.jdResultStatus = jdResultStatus;
	}

	public String getJdResultCode() {
		return jdResultCode;
	}

	public void setJdResultCode(String jdResultCode) {
		this.jdResultCode = jdResultCode;
	}

	public String getJdResultMsg() {
		return jdResultMsg;
	}

	public void setJdResultMsg(String jdResultMsg) {
		this.jdResultMsg = jdResultMsg;
	}

	public String getErpLastMessageId() {
		return erpLastMessageId;
	}

	public void setErpLastMessageId(String erpLastMessageId) {
		this.erpLastMessageId = erpLastMessageId;
	}

	public String getJdLastEssageId() {
		return jdLastEssageId;
	}

	public void setJdLastEssageId(String jdLastEssageId) {
		this.jdLastEssageId = jdLastEssageId;
	}

	public Integer getJdSendCount() {
		return jdSendCount;
	}

	public void setJdSendCount(Integer jdSendCount) {
		this.jdSendCount = jdSendCount;
	}

	public Integer getErpBookSendCount() {
		return erpBookSendCount;
	}

	public void setErpBookSendCount(Integer erpBookSendCount) {
		this.erpBookSendCount = erpBookSendCount;
	}

	public Date getJdLastSendTime() {
		return jdLastSendTime;
	}

	public void setJdLastSendTime(Date jdLastSendTime) {
		this.jdLastSendTime = jdLastSendTime;
	}

	public Date getErpLastSendTime() {
		return erpLastSendTime;
	}

	public void setErpLastSendTime(Date erpLastSendTime) {
		this.erpLastSendTime = erpLastSendTime;
	}

	public String getOrderItemNos() {
		return orderItemNos;
	}

	public void setOrderItemNos(String orderItemNos) {
		this.orderItemNos = orderItemNos;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}