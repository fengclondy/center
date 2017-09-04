package cn.htd.promotion.cpc.biz.dmo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class BuyerUseTimelimitedLogDMO implements Serializable {

	private static final long serialVersionUID = 7093820631512207342L;

	private Long id;

	private String buyerCode;

	private String promotionId;

	private String levelCode;

	private String seckillLockNo;

	private String orderNo;

	private String useType;

	private Integer usedCount;

	private int hasReleasedStock;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;
	/**
	 * 当前任务类型的任务队列数量
	 */
	private int taskQueueNum;
	/**
	 * 当前调度服务器，分配到的可处理队列
	 */
	private List<String> taskIdList;
	/**
	 * 释放锁定库存的间隔（单位：小时）
	 */
	private String releaseStockInterval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getSeckillLockNo() {
		return StringUtils.isEmpty(seckillLockNo) ? "" : seckillLockNo;
	}

	public void setSeckillLockNo(String seckillLockNo) {
		this.seckillLockNo = seckillLockNo;
	}

	public String getOrderNo() {
		return StringUtils.isEmpty(orderNo) ? "" : orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	/**
	 * @return the hasReleasedStock
	 */
	public int getHasReleasedStock() {
		return hasReleasedStock;
	}

	/**
	 * @param hasReleasedStock
	 *            the hasReleasedStock to set
	 */
	public void setHasReleasedStock(int hasReleasedStock) {
		this.hasReleasedStock = hasReleasedStock;
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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getTaskQueueNum() {
		return taskQueueNum;
	}

	public void setTaskQueueNum(int taskQueueNum) {
		this.taskQueueNum = taskQueueNum;
	}

	public List<String> getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}

	public String getReleaseStockInterval() {
		return releaseStockInterval;
	}

	public void setReleaseStockInterval(String releaseStockInterval) {
		this.releaseStockInterval = releaseStockInterval;
	}
}
