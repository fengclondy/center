package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class SellerMeetingEvaluateDTO  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String meetingNo;

    private Long sellerId;

    private String sellerName;

    private String sellerCode;

    private String meetingTitle;

    private Date meetingStartTime;

    private Date meetingEndTime;

    private String meetingAddr;

    private Long memberId;

    private String memberCode;

    private String memberName;

    private String artificialPersonName;

    private Byte evaluate1;

    private Byte evaluate2;

    private Byte evaluate3;

    private Long createId;

    private String createName;

    private Date createTime;

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
        this.meetingNo = meetingNo == null ? null : meetingNo.trim();
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle == null ? null : meetingTitle.trim();
    }

    public Date getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(Date meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public Date getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(Date meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public String getMeetingAddr() {
        return meetingAddr;
    }

    public void setMeetingAddr(String meetingAddr) {
        this.meetingAddr = meetingAddr == null ? null : meetingAddr.trim();
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
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getArtificialPersonName() {
        return artificialPersonName;
    }

    public void setArtificialPersonName(String artificialPersonName) {
        this.artificialPersonName = artificialPersonName == null ? null : artificialPersonName.trim();
    }

    public Byte getEvaluate1() {
        return evaluate1;
    }

    public void setEvaluate1(Byte evaluate1) {
        this.evaluate1 = evaluate1;
    }

    public Byte getEvaluate2() {
        return evaluate2;
    }

    public void setEvaluate2(Byte evaluate2) {
        this.evaluate2 = evaluate2;
    }

    public Byte getEvaluate3() {
        return evaluate3;
    }

    public void setEvaluate3(Byte evaluate3) {
        this.evaluate3 = evaluate3;
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