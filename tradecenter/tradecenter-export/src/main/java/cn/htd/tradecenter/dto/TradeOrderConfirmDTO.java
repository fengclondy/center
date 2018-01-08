package cn.htd.tradecenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TradeOrderConfirmDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4148112163278956917L;

	/**
	 * 消息编码
	 */
	@NotBlank(message = "messageId不能为空")
	private String messageId;

	/**
	 * 订单编号
	 */
	@NotBlank(message = "订单编号不能为空")
	private String orderNo;

	/**
	 * 订单modifyTime做乐观排他用
	 */
	@NotBlank(message = "订单更新时间不能为空")
	private String modifyTimeStr;

	/**
	 * 操作者ID
	 */
	@NotNull(message = "操作者ID不能为空")
	private Long operatorId;

	/**
	 * 操作者名称
	 */
	@NotBlank(message = "操作者名称不能为空")
	@Length(max = 255, message = "操作者名称超长")
	private String operatorName;

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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

	/**
	 * @return the modifyTimeStr
	 */
	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	/**
	 * @param modifyTimeStr
	 *            the modifyTimeStr to set
	 */
	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName
	 *            the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
