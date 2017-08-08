package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberUncheckedDTO implements Serializable {
	
	private static final long serialVersionUID = -7581674523694251029L;
	
	private String company_name;//公司名称
    private String member_type;//会员类型
    private String contact_mobile;//联系人手机号码 （现改成法人手机号）
    private String belong_company_name;//归属公司名称
    private String belong_company_name_cur;//当前归属公司名称
    private String verify_status;//审核状态/系统同步状态  1：待审核，2：已通过，3：被驳回
    private Long verify_id;//审核信息ID
    private String info_type;//1：找回密码未变更联系人信息审核，2：找回密码变更联系人信息审核
    
    private Long member_id;//会员ID
    private String member_code;//'会员编码',
    private Long status_id;//状态信息ID
    private String create_time;//创建时间
    private Long modify_id;//更新人ID
    private String modify_name;//更新人名称
    private String modify_time;//更新人时间
    private Integer has_guarantee_license;//是否有担保证明
    private Integer has_business_license;//是否有营业执照
    private Integer can_mall_login;//是否登陆商城
	
    public Long getVerify_id() {
		return verify_id;
	}
	public void setVerify_id(Long verify_id) {
		this.verify_id = verify_id;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public String getBelong_company_name() {
		return belong_company_name;
	}
	public void setBelong_company_name(String belong_company_name) {
		this.belong_company_name = belong_company_name;
	}
	public String getBelong_company_name_cur() {
		return belong_company_name_cur;
	}
	public void setBelong_company_name_cur(String belong_company_name_cur) {
		this.belong_company_name_cur = belong_company_name_cur;
	}
	public String getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public Long getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Long status_id) {
		this.status_id = status_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Long getModify_id() {
		return modify_id;
	}
	public void setModify_id(Long modify_id) {
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
	public Integer getHas_guarantee_license() {
		return has_guarantee_license;
	}
	public void setHas_guarantee_license(Integer has_guarantee_license) {
		this.has_guarantee_license = has_guarantee_license;
	}
	public Integer getHas_business_license() {
		return has_business_license;
	}
	public void setHas_business_license(Integer has_business_license) {
		this.has_business_license = has_business_license;
	}
	public Integer getCan_mall_login() {
		return can_mall_login;
	}
	public void setCan_mall_login(Integer can_mall_login) {
		this.can_mall_login = can_mall_login;
	}
    
}
