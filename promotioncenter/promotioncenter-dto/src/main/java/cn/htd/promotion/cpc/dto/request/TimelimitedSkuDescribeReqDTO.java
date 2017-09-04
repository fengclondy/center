package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.util.Date;

public class TimelimitedSkuDescribeReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4594492051017569870L;
	
	
	// ID
	private Long descId;
	// 促销活动编码
	private String promotionId;
	// 层级编码
	private String levelCode;
	// 商品图片URL
	private String pictureUrl;
	// 排序号,排序号最小的作为主图，从1开始
	private Integer sortNum;
	// 删除标记
	private Boolean deleteFlag;
	// 创建人ID
	private Long createId;
	// 创建人名称
	private String createName;
	// 创建时间
	private Date createTime;
	// 更新人ID
	private Long modifyId;
	// 更新人名称
	private String modifyName;
	// 更新时间
	private Date modifyTime;
	
	
	public Long getDescId() {
		return descId;
	}
	public void setDescId(Long descId) {
		this.descId = descId;
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
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
	

}