package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

/**
 * 订单行状态履历DMO
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: TradeOrderItemsStatusHistoryDMO.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class TradeOrderItemsStatusHistoryDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = 5276146695789966225L;

	/**
	 * 主键
	 */
    private Long id;

    /**
     * 订单行号
     */
    private String orderItemNo;

    /**
     * 订单行状态
     */
    private String orderItemStatus;

    /**
     * 订单行状态说明
     */
    private String orderItemStatusText;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建人名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderItemNo() {
        return orderItemNo;
    }

    public void setOrderItemNo(String orderItemNo) {
        this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
    }

    public String getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(String orderItemStatus) {
        this.orderItemStatus = orderItemStatus == null ? null : orderItemStatus.trim();
    }

    public String getOrderItemStatusText() {
        return orderItemStatusText;
    }

    public void setOrderItemStatusText(String orderItemStatusText) {
        this.orderItemStatusText = orderItemStatusText == null ? null : orderItemStatusText.trim();
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
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}