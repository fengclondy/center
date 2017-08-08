package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thinkpad on 2016/11/15.
 */
public class MemberStatusVO implements Serializable {
	private static final long serialVersionUID = 6898081696749827492L;

	private String mall_account;// 商城帐号
	private Integer member_type;// 会员类型：1：非会员，3：担保会员 ，2：正式会员
	private String create_time;// 创建时间（申请时间）
	private String company_name;// 原公司名称
	private String artificial_person_name;// 原法人名称
	private String artificial_person_idcard;// 原身份证号
	private String artificial_person_pic_src;// 原身份证正面照
	private String artificial_person_pic_back_src;// 原身份证反面照
	private String artificial_person_idcard_pic_src;// 原手持身份证照
	private String buyer_business_license_pic_src;// 会员营业执照电子版图片地址（买家）//公司营业执照
	private String buyer_business_license_pic_src_new;// 营业执照变更证明 \现营业执照照片
	private String buyer_guarantee_license_pic_src;// 供应商担保证明电子版图片地址（买家）
	private String buyer_guarantee_license_pic_src_new;// 现担保证明
	private String member_id; // 会员ID
	private String member_code; // 会员编码
	private String contact_mobile_new;// 新手机号码
	private String company_name_new;// 公司名称（新）
	private String artificial_person_name_new;// 公司法人（新）

	// 会员状态表
	private String status_id;// 状态信息ID
	private List<String> info_type_list;// 信息类型集合
	private String info_type;// 信息类型1：找回密码未变更联系人信息审核，2：找回密码变更联系人信息审核，3：会员手机号码更改审核，4：支付系统同步状态，5：ERP下行状态，
								// 11：会员注册运营审核，12：会员注册供应商审核，13：非会员注册审核，14：非会员转会员审核，15：会员信息修改审核，16：会员金融信息审核，
								// 21：商家注册审核，22：商家合同审核，23：商家店铺审核，24：商家信息修改审核',
	private String verify_status;// 审核状态/系统同步状态 1：待审核，2：已通过，3：被驳回
	private String verify_time;// '0000-00-00 00:00:00' comment '审核时间',
	private String verify_id; // '审核信息ID',
	private String sync_error_msg;// 同步错误信息(驳回原因)
	private String create_id;// 创建人ID
	private String create_name;// 创建人名称
	private String modify_id;// 更新人ID
	private String modify_name;// 更新人名称
	private String modify_time;// 更新人时间
	private Integer perpage;// 每页多大字段
	private Integer pagenum;// 第几页
	private Integer startnum;// 分页开始字段
	private String contact_mobile;// 联系人电话
	private String belong_seller_id;// 归属商家ID
	private String seller_type;// 商家类型 1:内部供应商，2:外部供应商
	private String cur_belong_seller_id;
	private List<String> verify_status_list;

	private Integer can_mall_login;// 是否登陆商城
	private Integer has_guarantee_license;// 是否有担保证明
	private Integer has_business_license;// 是否有营业执照
	private String belong_company_name;// 归属公司名称
	private String belong_company_name_cur;// 当前归属公司名称

	private Integer buyer_seller_type;// 会员/商家类型 1：会员，2：商家
	private Integer checkstatus;// 是否审核通过 1：审核通过 0：审核不通过

	public List<String> getInfo_type_list() {
		return info_type_list;
	}

	public void setInfo_type_list(List<String> info_type_list) {
		this.info_type_list = info_type_list;
	}

	public String getMall_account() {
		return mall_account;
	}

	public void setMall_account(String mall_account) {
		this.mall_account = mall_account;
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

	public String getArtificial_person_pic_src() {
		return artificial_person_pic_src;
	}

	public void setArtificial_person_pic_src(String artificial_person_pic_src) {
		this.artificial_person_pic_src = artificial_person_pic_src;
	}

	public String getArtificial_person_pic_back_src() {
		return artificial_person_pic_back_src;
	}

	public void setArtificial_person_pic_back_src(String artificial_person_pic_back_src) {
		this.artificial_person_pic_back_src = artificial_person_pic_back_src;
	}

	public String getArtificial_person_idcard_pic_src() {
		return artificial_person_idcard_pic_src;
	}

	public void setArtificial_person_idcard_pic_src(String artificial_person_idcard_pic_src) {
		this.artificial_person_idcard_pic_src = artificial_person_idcard_pic_src;
	}

	public String getBuyer_business_license_pic_src() {
		return buyer_business_license_pic_src;
	}

	public void setBuyer_business_license_pic_src(String buyer_business_license_pic_src) {
		this.buyer_business_license_pic_src = buyer_business_license_pic_src;
	}

	public String getBuyer_business_license_pic_src_new() {
		return buyer_business_license_pic_src_new;
	}

	public void setBuyer_business_license_pic_src_new(String buyer_business_license_pic_src_new) {
		this.buyer_business_license_pic_src_new = buyer_business_license_pic_src_new;
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

	public String getMember_code() {
		return member_code;
	}

	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}

	public String getContact_mobile_new() {
		return contact_mobile_new;
	}

	public void setContact_mobile_new(String contact_mobile_new) {
		this.contact_mobile_new = contact_mobile_new;
	}

	public String getCompany_name_new() {
		return company_name_new;
	}

	public void setCompany_name_new(String company_name_new) {
		this.company_name_new = company_name_new;
	}

	public String getArtificial_person_name_new() {
		return artificial_person_name_new;
	}

	public void setArtificial_person_name_new(String artificial_person_name_new) {
		this.artificial_person_name_new = artificial_person_name_new;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getVerify_id() {
		return verify_id;
	}

	public void setVerify_id(String verify_id) {
		this.verify_id = verify_id;
	}

	public String getCreate_id() {
		return create_id;
	}

	public void setCreate_id(String create_id) {
		this.create_id = create_id;
	}

	public String getModify_id() {
		return modify_id;
	}

	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
	}

	public Integer getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(Integer checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getInfo_type() {
		return info_type;
	}

	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}

	public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getVerify_time() {
		return verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public String getSync_error_msg() {
		return sync_error_msg;
	}

	public void setSync_error_msg(String sync_error_msg) {
		this.sync_error_msg = sync_error_msg;
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

	public List<String> getVerify_status_list() {
		return verify_status_list;
	}

	public void setVerify_status_list(List<String> verify_status_list) {
		this.verify_status_list = verify_status_list;
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

	public Integer getPerpage() {
		return perpage;
	}

	public void setPerpage(Integer perpage) {
		this.perpage = perpage;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getBelong_seller_id() {
		return belong_seller_id;
	}

	public void setBelong_seller_id(String belong_seller_id) {
		this.belong_seller_id = belong_seller_id;
	}

	public String getSeller_type() {
		return seller_type;
	}

	public void setSeller_type(String seller_type) {
		this.seller_type = seller_type;
	}

	public String getCur_belong_seller_id() {
		return cur_belong_seller_id;
	}

	public void setCur_belong_seller_id(String cur_belong_seller_id) {
		this.cur_belong_seller_id = cur_belong_seller_id;
	}

	public Integer getMember_type() {
		return member_type;
	}

	public void setMember_type(Integer member_type) {
		this.member_type = member_type;
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

	public Integer getBuyer_seller_type() {
		return buyer_seller_type;
	}

	public void setBuyer_seller_type(Integer buyer_seller_type) {
		this.buyer_seller_type = buyer_seller_type;
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
}
