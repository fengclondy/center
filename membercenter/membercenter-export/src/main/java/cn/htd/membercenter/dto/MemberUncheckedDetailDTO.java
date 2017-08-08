package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.domain.VerifyDetailInfo;

public class MemberUncheckedDetailDTO implements Serializable {

	private static final long serialVersionUID = 4420948993236066878L;
	private Long member_id;// 会员id
	private String company_name;// 公司名称
	private String location_detail; // 所在地-详细
	private String location_addr; // 所在地址
	private String invoice_notify; // 普通发票抬头
	private String tax_man_id; // 纳税人识别号
	private String bank_name; // 开户行名称
	private String bank_account; // 银行账号
	private String belong_seller_name;// 归属公司名称 需调用用户系统服务
	private String belong_manager_name;// 归属客户经理名称 需调用用户系统服务
	private String belong_manager_id;// 归属客户经理id
	private String cur_belong_seller_name;// 当前归属公司名称 需调用用户系统服务
	private String cur_belong_manager_name;// 当前归属客户经理名称 需调用用户系统服务
	private String cur_belong_manager_id;// 当前归属客户经理id
	private String contact_pic_src;// 联系人身份证电子版图片地址
	private String contact_pic_back_src;// 联系人身份证电子版图片地址(反面)
	private String buyer_business_license_pic_src;// 会员营业执照电子版图片地址
	private String buyer_guarantee_license_pic_src;// 供应商担保证明电子版图片地址
	private String buyer_business_license_id;// 营业执照号
	private String artificial_person_name;// 法人姓名
	private String artificial_person_mobile;// 法人手机号码
	private String contact_name;// 联系人姓名
	private String contact_mobile;// 联系人手机号码
	private Long status_id;// 状态信息ID
	private String verify_status;// 审核状态/系统同步状态 1：待审核，2：已通过，3：被驳回
	private Long verify_id;// 审核信息ID
	private String sync_error_msg;// 同步错误信息(驳回原因)
	private Long belong_seller_id;
	private Long cur_belong_seller_id;
	private String modify_id;// 更新人ID
	private String modify_name;// 更新人名称
	private String modify_time;// 更新人时间
	private List<VerifyDetailInfo> verifyDetailInfoList;

	private String artificial_person_idcard;// 身份证号
	private String artificial_person_pic_src;// 身份照片
	private String artificial_person_idcard_pic_src;// 手持身份证照

	private String industry_category;// 发展行业

	/**
	 * @return the belong_seller_id
	 */
	public Long getBelong_seller_id() {
		return belong_seller_id;
	}

	/**
	 * @param belong_seller_id
	 *            the belong_seller_id to set
	 */
	public void setBelong_seller_id(Long belong_seller_id) {
		this.belong_seller_id = belong_seller_id;
	}

	/**
	 * @return the cur_belong_seller_id
	 */
	public Long getCur_belong_seller_id() {
		return cur_belong_seller_id;
	}

	/**
	 * @param cur_belong_seller_id
	 *            the cur_belong_seller_id to set
	 */
	public void setCur_belong_seller_id(Long cur_belong_seller_id) {
		this.cur_belong_seller_id = cur_belong_seller_id;
	}

	public String getArtificial_person_idcard() {
		return artificial_person_idcard;
	}

	public void setArtificial_person_idcard(String artificial_person_idcard) {
		this.artificial_person_idcard = artificial_person_idcard;
	}

	public String getArtificial_person_pic_src() {
		return artificial_person_pic_src;
	}

	public void setArtificial_person_pic_src(String artificial_person_pic_src) {
		this.artificial_person_pic_src = artificial_person_pic_src;
	}

	public String getArtificial_person_idcard_pic_src() {
		return artificial_person_idcard_pic_src;
	}

	public void setArtificial_person_idcard_pic_src(String artificial_person_idcard_pic_src) {
		this.artificial_person_idcard_pic_src = artificial_person_idcard_pic_src;
	}

	public String getArtificial_person_pic_back_src() {
		return artificial_person_pic_back_src;
	}

	public void setArtificial_person_pic_back_src(String artificial_person_pic_back_src) {
		this.artificial_person_pic_back_src = artificial_person_pic_back_src;
	}

	private String artificial_person_pic_back_src;// 身份证照背面

	public String getBelong_manager_id() {
		return belong_manager_id;
	}

	public void setBelong_manager_id(String belong_manager_id) {
		this.belong_manager_id = belong_manager_id;
	}

	public String getCur_belong_manager_id() {
		return cur_belong_manager_id;
	}

	public void setCur_belong_manager_id(String cur_belong_manager_id) {
		this.cur_belong_manager_id = cur_belong_manager_id;
	}

	public Long getVerify_id() {
		return verify_id;
	}

	public void setVerify_id(Long verify_id) {
		this.verify_id = verify_id;
	}

	public String getBuyer_business_license_id() {
		return buyer_business_license_id;
	}

	public void setBuyer_business_license_id(String buyer_business_license_id) {
		this.buyer_business_license_id = buyer_business_license_id;
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

	public List<VerifyDetailInfo> getVerifyDetailInfoList() {
		return verifyDetailInfoList;
	}

	public void setVerifyDetailInfoList(List<VerifyDetailInfo> verifyDetailInfoList) {
		this.verifyDetailInfoList = verifyDetailInfoList;
	}

	public Long getMember_id() {
		return member_id;
	}

	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLocation_detail() {
		return location_detail;
	}

	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}

	public String getInvoice_notify() {
		return invoice_notify;
	}

	public void setInvoice_notify(String invoice_notify) {
		this.invoice_notify = invoice_notify;
	}

	public String getTax_man_id() {
		return tax_man_id;
	}

	public void setTax_man_id(String tax_man_id) {
		this.tax_man_id = tax_man_id;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBelong_seller_name() {
		return belong_seller_name;
	}

	public void setBelong_seller_name(String belong_seller_name) {
		this.belong_seller_name = belong_seller_name;
	}

	public String getBelong_manager_name() {
		return belong_manager_name;
	}

	public void setBelong_manager_name(String belong_manager_name) {
		this.belong_manager_name = belong_manager_name;
	}

	public String getCur_belong_seller_name() {
		return cur_belong_seller_name;
	}

	public void setCur_belong_seller_name(String cur_belong_seller_name) {
		this.cur_belong_seller_name = cur_belong_seller_name;
	}

	public String getCur_belong_manager_name() {
		return cur_belong_manager_name;
	}

	public void setCur_belong_manager_name(String cur_belong_manager_name) {
		this.cur_belong_manager_name = cur_belong_manager_name;
	}

	public String getContact_pic_src() {
		return contact_pic_src;
	}

	public void setContact_pic_src(String contact_pic_src) {
		this.contact_pic_src = contact_pic_src;
	}

	public String getContact_pic_back_src() {
		return contact_pic_back_src;
	}

	public void setContact_pic_back_src(String contact_pic_back_src) {
		this.contact_pic_back_src = contact_pic_back_src;
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

	public String getArtificial_person_name() {
		return artificial_person_name;
	}

	public void setArtificial_person_name(String artificial_person_name) {
		this.artificial_person_name = artificial_person_name;
	}

	public String getArtificial_person_mobile() {
		return artificial_person_mobile;
	}

	public void setArtificial_person_mobile(String artificial_person_mobile) {
		this.artificial_person_mobile = artificial_person_mobile;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public Long getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Long status_id) {
		this.status_id = status_id;
	}

	public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getSync_error_msg() {
		return sync_error_msg;
	}

	public void setSync_error_msg(String sync_error_msg) {
		this.sync_error_msg = sync_error_msg;
	}

	/**
	 * @return the location_addr
	 */
	public String getLocation_addr() {
		return location_addr;
	}

	/**
	 * @param location_addr
	 *            the location_addr to set
	 */
	public void setLocation_addr(String location_addr) {
		this.location_addr = location_addr;
	}

	/**
	 * @return the industry_category
	 */
	public String getIndustry_category() {
		return industry_category;
	}

	/**
	 * @param industry_category
	 *            the industry_category to set
	 */
	public void setIndustry_category(String industry_category) {
		this.industry_category = industry_category;
	}

}
