package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberBackupContactInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long contactId;// 联系人ID

	private Long memberId;// 会员id

	private String contactName;// 联系人姓名

	private String contactMobile;// 联系人电话号码

	private String contactIdcard;// 联系人证件号码

	private String contactIdcardPicSrc;// 联系人身份证正面电子版图片地址

	private String contactIdcardPicBackSrc;// 联系人身份证背面电子版图片地址

	private String contactPersonIdcardPicSrc;// 联系人手持身份证电子版图片地址

	private String status;// 状态 1：待审核，2：审核驳回，3：审核通过，4：启用，9：禁用

	private Long createId;// 创建人ID

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人姓名

	private Date modifyTime;// 更新时间

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactIdcard() {
		return contactIdcard;
	}

	public void setContactIdcard(String contactIdcard) {
		this.contactIdcard = contactIdcard == null ? null : contactIdcard
				.trim();
	}

	public String getContactIdcardPicSrc() {
		return contactIdcardPicSrc;
	}

	public void setContactIdcardPicSrc(String contactIdcardPicSrc) {
		this.contactIdcardPicSrc = contactIdcardPicSrc == null ? null
				: contactIdcardPicSrc.trim();
	}

	public String getContactIdcardPicBackSrc() {
		return contactIdcardPicBackSrc;
	}

	public void setContactIdcardPicBackSrc(String contactIdcardPicBackSrc) {
		this.contactIdcardPicBackSrc = contactIdcardPicBackSrc == null ? null
				: contactIdcardPicBackSrc.trim();
	}

	public String getContactPersonIdcardPicSrc() {
		return contactPersonIdcardPicSrc;
	}

	public void setContactPersonIdcardPicSrc(String contactPersonIdcardPicSrc) {
		this.contactPersonIdcardPicSrc = contactPersonIdcardPicSrc == null ? null
				: contactPersonIdcardPicSrc.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
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
}