package cn.htd.basecenter.domain;

import java.util.Date;
import java.util.List;

public class BasePlacard {

	private Long placardId;

	private String sendType;

	private Long sendSellerId;

	private String title;

	private String content;

	private int isPublishImmediately;

	private int isValidForever;

	private Date publishTime;

	private Date expireTime;

	private Date applyTime;

	private String comment;

	private String picAttachment;

	private int hasUrl;

	private String url;

	private String status;

	private int isTop;

	private String placardScope;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	private List<Long> placardIdList;

	private String newStatus;
	
	private int hasRead;

	public Long getPlacardId() {
		return placardId;
	}

	public void setPlacardId(Long placardId) {
		this.placardId = placardId;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType == null ? null : sendType.trim();
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
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
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
		this.comment = comment == null ? null : comment.trim();
	}

	public String getPicAttachment() {
		return picAttachment;
	}

	public void setPicAttachment(String picAttachment) {
		this.picAttachment = picAttachment == null ? null : picAttachment.trim();
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
		this.url = url == null ? null : url.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}

	public String getPlacardScope() {
		return placardScope;
	}

	public void setPlacardScope(String placardScope) {
		this.placardScope = placardScope == null ? null : placardScope.trim();
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
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<Long> getPlacardIdList() {
		return placardIdList;
	}

	public void setPlacardIdList(List<Long> placardIdList) {
		this.placardIdList = placardIdList;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public int getHasRead() {
		return hasRead;
	}

	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}
}