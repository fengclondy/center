package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/11/16.
 */
public class MemberBaseInfoVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 86788251962178318L;
	private Long id;// 会员id
	private String member_code;// 会员编码
	private String company_leagal_persion_flag;// 法人/自然人标记 1：法人，2：自然人
	private Integer is_buyer;// 是否是买家',
	private Integer is_seller;// 是否是商家',
	private Integer can_mall_login;// 是否登陆商城',
	private Integer has_guarantee_license;// 是否有担保证明',
	private Integer has_business_license;// 是否有营业执照',
	private String regist_time;// 注册时间（会员/非会员/商家注册时间）'
	private String account_type;// '成为会员标记
								// (会员身份标识，只有注册为会员和非会员升级为会员审核通过时，此字段才为1，商家注册开通会员等流程此标记都为0）',
	private String become_member_time;// 成为会员时间(注册为会员，或者非会员升级为会员，审核完了时间）
	private Integer is_center_store;// 是否中心店',
	private String upgrade_center_store_time;// 升级为中心店时间
	private String upgrade_seller_time;// 升级为商家时间（会员升级为商家时间）
	private String seller_type;// 商家类型 1:内部供应商，2:外部供应商
	private Integer is_generation;// 是否是二代
	private String industry_category;// 发展行业
	private Integer is_diff_industry;// 是否异业
	private String contact_name;// 联系人姓名
	private String contact_mobile;// 联系人手机号码
	private String contact_email;// 联系人邮箱
	private String contact_idcard;// 联系人身份证号
	private String contact_pic_src;// 联系人身份证电子版图片地址
	private String contact_pic_back_src;// 联系人身份证电子版图片地址(反面)
	private Integer is_phone_authenticated;// 是否手机号已验证
	private Integer is_real_name_authenticated;// 是否实名验证标记
	private String cooperate_vendor;// 合作供应商
	private String regist_from;// 注册来源
	private Integer promotion_person;// 推广人编号
	private Integer belong_seller_id;// 归属商家ID
	private String belong_manager_id;// 归属商家客户经理ID
	private Integer cur_belong_seller_id;// 当前归属商家ID
	private String cur_belong_manager_id;// 当前商家客户经理ID
	private String status;// 状态 空白：审核中未成为正式会员，0：无效，1：有效
	private Integer create_id;// 创建人ID
	private String create_name;// 创建人名称
	private String create_time;// 创建时间
	private Integer modify_id;// 更新人ID
	private String modify_name;// 更新人名称
	private String modify_time;// 更新人时间

	private Integer member_type;// 会员类型：1：非会员，3：担保会员 ，2：正式会员

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany_leagal_persion_flag() {
		return company_leagal_persion_flag;
	}

	public void setCompany_leagal_persion_flag(String company_leagal_persion_flag) {
		this.company_leagal_persion_flag = company_leagal_persion_flag;
	}

	public Integer getIs_buyer() {
		return is_buyer;
	}

	public void setIs_buyer(Integer is_buyer) {
		this.is_buyer = is_buyer;
	}

	public Integer getIs_seller() {
		return is_seller;
	}

	public void setIs_seller(Integer is_seller) {
		this.is_seller = is_seller;
	}

	public Integer getCan_mall_login() {
		return can_mall_login;
	}

	public void setCan_mall_login(Integer can_mall_login) {
		this.can_mall_login = can_mall_login;
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

	public String getRegist_time() {
		return regist_time;
	}

	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getBecome_member_time() {
		return become_member_time;
	}

	public void setBecome_member_time(String become_member_time) {
		this.become_member_time = become_member_time;
	}

	public Integer getIs_center_store() {
		return is_center_store;
	}

	public void setIs_center_store(Integer is_center_store) {
		this.is_center_store = is_center_store;
	}

	public String getUpgrade_center_store_time() {
		return upgrade_center_store_time;
	}

	public void setUpgrade_center_store_time(String upgrade_center_store_time) {
		this.upgrade_center_store_time = upgrade_center_store_time;
	}

	public String getUpgrade_seller_time() {
		return upgrade_seller_time;
	}

	public void setUpgrade_seller_time(String upgrade_seller_time) {
		this.upgrade_seller_time = upgrade_seller_time;
	}

	public String getSeller_type() {
		return seller_type;
	}

	public void setSeller_type(String seller_type) {
		this.seller_type = seller_type;
	}

	public Integer getIs_generation() {
		return is_generation;
	}

	public void setIs_generation(Integer is_generation) {
		this.is_generation = is_generation;
	}

	public String getIndustry_category() {
		return industry_category;
	}

	public void setIndustry_category(String industry_category) {
		this.industry_category = industry_category;
	}

	public Integer getIs_diff_industry() {
		return is_diff_industry;
	}

	public void setIs_diff_industry(Integer is_diff_industry) {
		this.is_diff_industry = is_diff_industry;
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

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_idcard() {
		return contact_idcard;
	}

	public void setContact_idcard(String contact_idcard) {
		this.contact_idcard = contact_idcard;
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

	public Integer getIs_phone_authenticated() {
		return is_phone_authenticated;
	}

	public void setIs_phone_authenticated(Integer is_phone_authenticated) {
		this.is_phone_authenticated = is_phone_authenticated;
	}

	public Integer getIs_real_name_authenticated() {
		return is_real_name_authenticated;
	}

	public void setIs_real_name_authenticated(Integer is_real_name_authenticated) {
		this.is_real_name_authenticated = is_real_name_authenticated;
	}

	public String getCooperate_vendor() {
		return cooperate_vendor;
	}

	public void setCooperate_vendor(String cooperate_vendor) {
		this.cooperate_vendor = cooperate_vendor;
	}

	public String getRegist_from() {
		return regist_from;
	}

	public void setRegist_from(String regist_from) {
		this.regist_from = regist_from;
	}

	public Integer getPromotion_person() {
		return promotion_person;
	}

	public void setPromotion_person(Integer promotion_person) {
		this.promotion_person = promotion_person;
	}

	public Integer getBelong_seller_id() {
		return belong_seller_id;
	}

	public void setBelong_seller_id(Integer belong_seller_id) {
		this.belong_seller_id = belong_seller_id;
	}

	public String getBelong_manager_id() {
		return belong_manager_id;
	}

	public void setBelong_manager_id(String belong_manager_id) {
		this.belong_manager_id = belong_manager_id;
	}

	public Integer getCur_belong_seller_id() {
		return cur_belong_seller_id;
	}

	public void setCur_belong_seller_id(Integer cur_belong_seller_id) {
		this.cur_belong_seller_id = cur_belong_seller_id;
	}

	public String getCur_belong_manager_id() {
		return cur_belong_manager_id;
	}

	public void setCur_belong_manager_id(String cur_belong_manager_id) {
		this.cur_belong_manager_id = cur_belong_manager_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreate_id() {
		return create_id;
	}

	public void setCreate_id(Integer create_id) {
		this.create_id = create_id;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getModify_id() {
		return modify_id;
	}

	public void setModify_id(Integer modify_id) {
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

	public String getMember_code() {
		return member_code;
	}

	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}

	public Integer getMember_type() {
		return member_type;
	}

	public void setMember_type(Integer member_type) {
		this.member_type = member_type;
	}
}
