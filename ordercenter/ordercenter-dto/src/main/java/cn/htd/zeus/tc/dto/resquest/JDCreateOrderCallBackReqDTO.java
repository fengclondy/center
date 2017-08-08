package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

/*
 *京东抛单回调DTO
 */
public class JDCreateOrderCallBackReqDTO extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 526358742192845076L;
	
	//订单号
	@NotEmpty(message = "orderNo不能为空")
	private String orderNo;
	
	//京东抛单状态
	@NotEmpty(message = "jdResultStatus不能为空")
	private Integer jdResultStatus;//0-未抛单 1-已抛单至MQ未返回  2-已抛单至MQ已返回
	
	//京东抛单结果返回码
	@NotEmpty(message = "jdResultCode不能为空")
	private String jdResultCode;
	
	//京东抛单返回结果描述
	private String jdResultMsg;
	
	//京东订单号
	@NotEmpty(message = "jdOrderId不能为空")
	private Long jdOrderId;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public Long getJdOrderId() {
		return jdOrderId;
	}

	public void setJdOrderId(Long jdOrderId) {
		this.jdOrderId = jdOrderId;
	}
}
