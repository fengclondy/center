package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公告信息
 */
public class BasePlacardDTO implements Serializable {

	private static final long serialVersionUID = 6593096669524893752L;

	/**
	 * 公告ID
	 */
	private Long placardId;

	/**
	 * 公告编号
	 */
	private String placardCode;

	/**
	 * 发送方类型
	 */
	private String sendType;

	/**
	 * 发送商家ID
	 */
	private Long sendSellerId = null;

	/**
	 * 主题
	 */
	private String title = "";

	/**
	 * 内容
	 */
	private String content = "";

	/**
	 * 是否立即生效
	 */
	private int isPublishImmediately = 0;

	/**
	 * 是否永久有效
	 */
	private int isValidForever = 0;
	/**
	 * 发布时间
	 */
	private Date publishTime;

	/**
	 * 失效时间
	 */
	private Date expireTime;

	/**
	 * 申请时间
	 */
	private Date applyTime;

	/**
	 * 备注
	 */
	private String comment = "";

	/**
	 * 图片附件
	 */
	private String picAttachment = "";

	/**
	 * 是否有超链接
	 */
	private int hasUrl = 0;
	/**
	 * 超链接
	 */
	private String url = "";

	/**
	 * 状态
	 */
	private String status = "";

	/**
	 * 状态名称
	 */
	private String statusName = "";

	/**
	 * 是否置顶
	 */
	private int isTop = 0;

	/**
	 * 创建者ID
	 */
	private Long createId;
	/**
	 * 创建者名称
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者ID
	 */
	private Long modifyId;
	/**
	 * 更新者名称
	 */
	private String modifyName;
	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * 公告对象DTO
	 */
	private List<BasePlacardScopeDTO> placardScopeList;

	/**
	 * 商家公告通知会员ID列表
	 */
	private List<Long> buyerIdList;

	public Long getPlacardId() {
		return placardId;
	}

	public void setPlacardId(Long placardId) {
		this.placardId = placardId;
	}

	public String getPlacardCode() {
		return placardCode;
	}

	public void setPlacardCode(String placardCode) {
		this.placardCode = placardCode;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Long getSendSellerId() {
		return sendSellerId;
	}

	public void setSendSellerId(Long sendSellerId) {
		this.sendSellerId = sendSellerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsPublishImmediately() {
		return isPublishImmediately;
	}

	public void setIsPublishImmediately(int isPublishImmediately) {
		this.isPublishImmediately = isPublishImmediately;
	}

	public int getIsValidForever() {
		return isValidForever;
	}

	public void setIsValidForever(int isValidForever) {
		this.isValidForever = isValidForever;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPicAttachment() {
		return picAttachment;
	}

	public void setPicAttachment(String picAttachment) {
		this.picAttachment = picAttachment;
	}

	public int getHasUrl() {
		return hasUrl;
	}

	public void setHasUrl(int hasUrl) {
		this.hasUrl = hasUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
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

	public List<BasePlacardScopeDTO> getPlacardScopeList() {
		return placardScopeList;
	}

	public void setPlacardScopeList(List<BasePlacardScopeDTO> placardScopeList) {
		this.placardScopeList = placardScopeList;
	}

	public List<Long> getBuyerIdList() {
		return buyerIdList;
	}

	public void setBuyerIdList(List<Long> buyerIdList) {
		this.buyerIdList = buyerIdList;
	}
}
