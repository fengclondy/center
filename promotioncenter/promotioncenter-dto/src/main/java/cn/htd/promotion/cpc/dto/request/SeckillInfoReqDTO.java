package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SeckillInfoReqDTO implements Serializable {

	private static final long serialVersionUID = -1034610190052639854L;
	/**
	 * 促销活动变动类型
	 */
	@NotEmpty(message = "会员促销活动处理类型为空")
	private String useType;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 锁定订单号
	 */
	private String seckillLockNo;
	/**
	 * 买家编号
	 */
	@NotEmpty(message = "买家编号不能为空")
	private String buyerCode;
	/**
	 * 促销活动编码
	 */
	@NotEmpty(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 商品购买数量
	 */
	private Integer count = new Integer(1);
	/**
	 * 操作人ID
	 */
	@NotNull(message = "操作人ID不能为空")
	private Long operaterId;
	/**
	 * 操作人名称
	 */
	@NotEmpty(message = "操作人名称不能为空")
	private String operaterName;
	
	/**
	 * 是否下单标记
	 */
	private boolean orderFlag;

	/**
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}

	/**
	 * @param useType
	 *            the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
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
	 * @return the seckillLockNo
	 */
	public String getSeckillLockNo() {
		return seckillLockNo;
	}

	/**
	 * @param seckillLockNo
	 *            the seckillLockNo to set
	 */
	public void setSeckillLockNo(String seckillLockNo) {
		this.seckillLockNo = seckillLockNo;
	}

	/**
	 * @return the buyerCode
	 */
	public String getBuyerCode() {
		return buyerCode;
	}

	/**
	 * @param buyerCode
	 *            the buyerCode to set
	 */
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	/**
	 * @return the promotionId
	 */
	public String getPromotionId() {
		return promotionId;
	}

	/**
	 * @param promotionId
	 *            the promotionId to set
	 */
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the operaterId
	 */
	public Long getOperaterId() {
		return operaterId;
	}

	/**
	 * @param operaterId
	 *            the operaterId to set
	 */
	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}

	/**
	 * @return the operaterName
	 */
	public String getOperaterName() {
		return operaterName;
	}

	/**
	 * @param operaterName
	 *            the operaterName to set
	 */
	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}

    public boolean isOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(boolean orderFlag) {
        this.orderFlag = orderFlag;
    }

	
}
