package cn.htd.membercenter.dto;

import java.io.Serializable;
/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerFinanceDTO</p>
* @author root
* @date 2016年12月27日
* <p>Description: 
*			买家中心   金融
* </p>
 */
public class MemberBuyerFinanceDTO implements Serializable{

	private static final long serialVersionUID = 5057269299368224928L;
	
	private Long memberId;
	private String artificialPersonName;//法人姓名
	private String artificialPersonIdcard;//法人身份证号
	private String artificialPersonPicSrc ;// '法人身份证电子版图片地址',
    private String artificialPersonPicBackSrc ;//'法人身份证电子版图片地址(反面)',
    private String artificialPersonIdcardPicSrc ;//'法人手持身份证电子版图片地址',
    
    private Long contactId;//'联系人ID',
    private String contactName ;//'联系人姓名',
    private String contactMobile;// '联系人手机号码',
    private String contactIdcard;// '联系人身份证号',
    private String contactIdcardPicSrc;// '联系人身份证正面电子版图片地址',
    private String contactIdcardPicBackSrc;// '联系人身份证背面电子版图片地址',
    private String contactPersonIdcardPicSrc;//'联系人手持身份证电子版图片地址',
    private String status;// '状态 1：待审核，2：审核驳回，3：审核通过(页面用不到)，4：启用(有效)，9：禁用(无效)',
    private String remark;//审核意见
    
    private Long createId;//创建人id
    private String createName;//创建人名称
    private Long modifyId;//修改人id
    private String modifyName;//修改人名称 
    
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getArtificialPersonName() {
		return artificialPersonName;
	}
	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}
	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}
	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}
	public String getArtificialPersonPicSrc() {
		return artificialPersonPicSrc;
	}
	public void setArtificialPersonPicSrc(String artificialPersonPicSrc) {
		this.artificialPersonPicSrc = artificialPersonPicSrc;
	}
	public String getArtificialPersonPicBackSrc() {
		return artificialPersonPicBackSrc;
	}
	public void setArtificialPersonPicBackSrc(String artificialPersonPicBackSrc) {
		this.artificialPersonPicBackSrc = artificialPersonPicBackSrc;
	}
	public String getArtificialPersonIdcardPicSrc() {
		return artificialPersonIdcardPicSrc;
	}
	public void setArtificialPersonIdcardPicSrc(String artificialPersonIdcardPicSrc) {
		this.artificialPersonIdcardPicSrc = artificialPersonIdcardPicSrc;
	}
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
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
		this.contactIdcard = contactIdcard;
	}
	public String getContactIdcardPicSrc() {
		return contactIdcardPicSrc;
	}
	public void setContactIdcardPicSrc(String contactIdcardPicSrc) {
		this.contactIdcardPicSrc = contactIdcardPicSrc;
	}
	public String getContactIdcardPicBackSrc() {
		return contactIdcardPicBackSrc;
	}
	public void setContactIdcardPicBackSrc(String contactIdcardPicBackSrc) {
		this.contactIdcardPicBackSrc = contactIdcardPicBackSrc;
	}
	public String getContactPersonIdcardPicSrc() {
		return contactPersonIdcardPicSrc;
	}
	public void setContactPersonIdcardPicSrc(String contactPersonIdcardPicSrc) {
		this.contactPersonIdcardPicSrc = contactPersonIdcardPicSrc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
