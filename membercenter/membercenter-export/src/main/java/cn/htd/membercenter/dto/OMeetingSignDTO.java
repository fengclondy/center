package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class OMeetingSignDTO implements Serializable {

	private static final long serialVersionUID = 6819827953777449611L;

	private Long id;
	private String meetingNo;// 会议序号
	private Long sellerId;// 平台公司id
	private String sellerName;// 平台公司名称
	private String sellerCode;// 平台公司帐号
	private String meetingTitle;// 标题
	private String meetingStateTime;// 会议开始时间
	private String meetingEndTime;// 会议结束时间
	private String meetingAddr;// 会议地点
	private Long memberId;// 会员ID
	private String memberCode;// 会员店帐号
	private String memberName;// 会员店名称
	private String artificialPersonName;// 会员店法人姓名
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private Date createTime;// 创建时间
	
	private int pageNo;
	private int pageSize;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeetingNo() {
		return meetingNo;
	}

	public void setMeetingNo(String meetingNo) {
		this.meetingNo = meetingNo;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}


	public String getMeetingStateTime() {
		return meetingStateTime;
	}

	public void setMeetingStateTime(String meetingStateTime) {
		this.meetingStateTime = meetingStateTime;
	}

	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	public String getMeetingAddr() {
		return meetingAddr;
	}

	public void setMeetingAddr(String meetingAddr) {
		this.meetingAddr = meetingAddr;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
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

}
