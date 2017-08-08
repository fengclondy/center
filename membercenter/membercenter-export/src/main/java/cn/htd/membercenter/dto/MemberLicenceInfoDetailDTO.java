package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.domain.VerifyDetailInfo;

public class MemberLicenceInfoDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 7170589504889608363L;
	
	private String mall_account;//商城帐号
	private String member_type;//会员类型
	private String create_time;//创建时间（申请时间）
	private String company_name_new;//公司名称（新）  去掉
	private String company_name;//原公司名称\公司名称
	private String contact_mobile;//联系人手机号
	private String contact_mobile_new;//联系人手机号(新)
	private String artificial_person_name_new;//公司法人（新）  
	private String artificial_person_name;//原法人名称\法人姓名
	private String artificial_person_idcard;//原身份证号
	private String artificial_person_idcard_new;//身份证号(新)  去掉
	private String artificial_person_mobile_new;//新手机号码
	private String artificial_person_mobile;//原手机号
	private String artificial_person_pic_src_new;//身份证正面照(新)  去掉
	private String artificial_person_pic_src;//原身份证正面照
	private String artificial_person_pic_back_src_new;//身份证反面照(新)   去掉
	private String artificial_person_pic_back_src;//原身份证反面照
	private String artificial_person_idcard_pic_src_new;//手持身份证照(新)   去掉
	private String artificial_person_idcard_pic_src;//原手持身份证照
	private String business_license_certificate_pic_src;//营业执照变更证明    
	private String buyer_business_license_pic_src;//会员营业执照电子版图片地址（买家）//公司营业执照
	private String buyer_business_license_pic_src_new;//会员营业执照电子版图片地址（买家）//现公司营业执照
	private String buyer_guarantee_license_pic_src;//供应商担保证明电子版图片地址（买家）
	private String buyer_guarantee_license_pic_src_new;//现担保证明
	
	private String info_type;//信息类型
	private String member_id;   //会员ID
    private String member_code; //会员编码
    private String verify_id;//审核ID
    private Long   status_id;//状态信息ID
    private String verify_status;//审核状态
    private String sync_error_msg;//同步错误信息  (驳回原因)
    private String modify_id;//更新人ID
    private String modify_name;//更新人名称
    private String modify_time;//更新人时间
    private List<VerifyDetailInfo> verifyDetailInfoList;//审批详细信息
    
    
    
	public String getBuyer_business_license_pic_src_new() {
		return buyer_business_license_pic_src_new;
	}
	public void setBuyer_business_license_pic_src_new(String buyer_business_license_pic_src_new) {
		this.buyer_business_license_pic_src_new = buyer_business_license_pic_src_new;
	}
	public String getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public String getContact_mobile_new() {
		return contact_mobile_new;
	}
	public void setContact_mobile_new(String contact_mobile_new) {
		this.contact_mobile_new = contact_mobile_new;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}
	public String getModify_id() {
		return modify_id;
	}
	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
	}
	public String getModify_name() {
		return modify_name;
	}
	public void setModify_name(String modify_name) {
		this.modify_name = modify_name;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getMall_account() {
		return mall_account;
	}
	public void setMall_account(String mall_account) {
		this.mall_account = mall_account;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCompany_name_new() {
		return company_name_new;
	}
	public void setCompany_name_new(String company_name_new) {
		this.company_name_new = company_name_new;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getArtificial_person_name_new() {
		return artificial_person_name_new;
	}
	public void setArtificial_person_name_new(String artificial_person_name_new) {
		this.artificial_person_name_new = artificial_person_name_new;
	}
	public String getArtificial_person_name() {
		return artificial_person_name;
	}
	public void setArtificial_person_name(String artificial_person_name) {
		this.artificial_person_name = artificial_person_name;
	}
	public String getArtificial_person_idcard() {
		return artificial_person_idcard;
	}
	public void setArtificial_person_idcard(String artificial_person_idcard) {
		this.artificial_person_idcard = artificial_person_idcard;
	}
	public String getArtificial_person_idcard_new() {
		return artificial_person_idcard_new;
	}
	public void setArtificial_person_idcard_new(String artificial_person_idcard_new) {
		this.artificial_person_idcard_new = artificial_person_idcard_new;
	}
	
	public String getArtificial_person_mobile_new() {
		return artificial_person_mobile_new;
	}
	public void setArtificial_person_mobile_new(String artificial_person_mobile_new) {
		this.artificial_person_mobile_new = artificial_person_mobile_new;
	}
	public String getArtificial_person_mobile() {
		return artificial_person_mobile;
	}
	public void setArtificial_person_mobile(String artificial_person_mobile) {
		this.artificial_person_mobile = artificial_person_mobile;
	}
	public String getArtificial_person_pic_src_new() {
		return artificial_person_pic_src_new;
	}
	public void setArtificial_person_pic_src_new(String artificial_person_pic_src_new) {
		this.artificial_person_pic_src_new = artificial_person_pic_src_new;
	}
	public String getArtificial_person_pic_src() {
		return artificial_person_pic_src;
	}
	public void setArtificial_person_pic_src(String artificial_person_pic_src) {
		this.artificial_person_pic_src = artificial_person_pic_src;
	}
	public String getArtificial_person_pic_back_src_new() {
		return artificial_person_pic_back_src_new;
	}
	public void setArtificial_person_pic_back_src_new(String artificial_person_pic_back_src_new) {
		this.artificial_person_pic_back_src_new = artificial_person_pic_back_src_new;
	}
	public String getArtificial_person_pic_back_src() {
		return artificial_person_pic_back_src;
	}
	public void setArtificial_person_pic_back_src(String artificial_person_pic_back_src) {
		this.artificial_person_pic_back_src = artificial_person_pic_back_src;
	}
	public String getArtificial_person_idcard_pic_src_new() {
		return artificial_person_idcard_pic_src_new;
	}
	public void setArtificial_person_idcard_pic_src_new(String artificial_person_idcard_pic_src_new) {
		this.artificial_person_idcard_pic_src_new = artificial_person_idcard_pic_src_new;
	}
	public String getArtificial_person_idcard_pic_src() {
		return artificial_person_idcard_pic_src;
	}
	public void setArtificial_person_idcard_pic_src(String artificial_person_idcard_pic_src) {
		this.artificial_person_idcard_pic_src = artificial_person_idcard_pic_src;
	}
	
	public String getBusiness_license_certificate_pic_src() {
		return business_license_certificate_pic_src;
	}
	public void setBusiness_license_certificate_pic_src(String business_license_certificate_pic_src) {
		this.business_license_certificate_pic_src = business_license_certificate_pic_src;
	}
	public String getBuyer_business_license_pic_src() {
		return buyer_business_license_pic_src;
	}
	public void setBuyer_business_license_pic_src(String buyer_business_license_pic_src) {
		this.buyer_business_license_pic_src = buyer_business_license_pic_src;
	}
	public String getBuyer_guarantee_license_pic_src() {
		return buyer_guarantee_license_pic_src;
	}
	public void setBuyer_guarantee_license_pic_src(String buyer_guarantee_license_pic_src) {
		this.buyer_guarantee_license_pic_src = buyer_guarantee_license_pic_src;
	}
	public String getBuyer_guarantee_license_pic_src_new() {
		return buyer_guarantee_license_pic_src_new;
	}
	public void setBuyer_guarantee_license_pic_src_new(String buyer_guarantee_license_pic_src_new) {
		this.buyer_guarantee_license_pic_src_new = buyer_guarantee_license_pic_src_new;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getVerify_id() {
		return verify_id;
	}
	public void setVerify_id(String verify_id) {
		this.verify_id = verify_id;
	}
	public Long getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Long status_id) {
		this.status_id = status_id;
	}
	public String getSync_error_msg() {
		return sync_error_msg;
	}
	public void setSync_error_msg(String sync_error_msg) {
		this.sync_error_msg = sync_error_msg;
	}
	public List<VerifyDetailInfo> getVerifyDetailInfoList() {
		return verifyDetailInfoList;
	}
	public void setVerifyDetailInfoList(List<VerifyDetailInfo> verifyDetailInfoList) {
		this.verifyDetailInfoList = verifyDetailInfoList;
	}
    
	
}
