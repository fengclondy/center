package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VoteActivityMemberResDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5654998071269663340L;

	private Long voteMemberId;

    private Long voteId;

    private String memberCode;

    private String memberName;

    private String vendorName;

    private String contactName;

    private String contactPhone;

    private Integer signStatus;

    private Integer auditStatus;

    private Date signUpTime;

    private String memberActivityDec;

    private Date memberVoteLastTime;

    private Integer deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    /***
     * 会员店投票报名相关图片集合
     */
    private List<VoteActivityMemberPictureResDTO> memberPictureResDTOList;

    public Long getVoteMemberId() {
        return voteMemberId;
    }

    public void setVoteMemberId(Long voteMemberId) {
        this.voteMemberId = voteMemberId;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Date signUpTime) {
        this.signUpTime = signUpTime;
    }

    public String getMemberActivityDec() {
        return memberActivityDec;
    }

    public void setMemberActivityDec(String memberActivityDec) {
        this.memberActivityDec = memberActivityDec == null ? null : memberActivityDec.trim();
    }

    public Date getMemberVoteLastTime() {
        return memberVoteLastTime;
    }

    public void setMemberVoteLastTime(Date memberVoteLastTime) {
        this.memberVoteLastTime = memberVoteLastTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
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

    public List<VoteActivityMemberPictureResDTO> getMemberPictureResDTOList() {
        return memberPictureResDTOList;
    }

    public void setMemberPictureResDTOList(List<VoteActivityMemberPictureResDTO> memberPictureResDTOList) {
        this.memberPictureResDTOList = memberPictureResDTOList;
    }
}