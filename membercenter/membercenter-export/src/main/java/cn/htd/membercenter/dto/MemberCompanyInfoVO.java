package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/11/16.
 */
public class MemberCompanyInfoVO implements Serializable {
	private String id;
	private String member_id;
	private String belong_seller_id;
	private String belong_manager_id;
	private String cur_belong_seller_id;
	private String cur_belong_manager_id;

	private Integer buyer_seller_type;
	private String company_name;
	private String belong_company_name;// 归属公司名称
	private String belong_company_name_cur;// 当前归属公司名称
	private String company_code;
	private String artificial_person_name;
	private String artificial_person_mobile;
	private String artificial_person_idcard;
	private String artificial_person_pic_src;
	private String artificial_person_pic_back_src;
	private String artificial_person_idcard_pic_src;
	private String area_code;
	private String landline;
	private String fax_number;
	private String location_province;
	private String location_city;
	private String location_county;
	private String location_town;
	private String location_detail;
	private String create_id;
	private String create_name;
	private String create_time;
	private String modify_id;
	private String modify_name;
	private String modify_time;
	private Integer pagenum;
	private Integer startnum;
	private Integer perpage;

	public String getCur_belong_manager_id() {
		return cur_belong_manager_id;
	}

	public void setCur_belong_manager_id(String cur_belong_manager_id) {
		this.cur_belong_manager_id = cur_belong_manager_id;
	}

	public String getBelong_manager_id() {
		return belong_manager_id;
	}

	public void setBelong_manager_id(String belong_manager_id) {
		this.belong_manager_id = belong_manager_id;
	}

	public String getCur_belong_seller_id() {
		return cur_belong_seller_id;
	}

	public void setCur_belong_seller_id(String cur_belong_seller_id) {
		this.cur_belong_seller_id = cur_belong_seller_id;
	}

	public String getBelong_seller_id() {
		return belong_seller_id;
	}

	public void setBelong_seller_id(String belong_seller_id) {
		this.belong_seller_id = belong_seller_id;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}

	public Integer getPerpage() {
		return perpage;
	}

	public void setPerpage(Integer perpage) {
		this.perpage = perpage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Integer getBuyer_seller_type() {
		return buyer_seller_type;
	}

	public void setBuyer_seller_type(Integer buyer_seller_type) {
		this.buyer_seller_type = buyer_seller_type;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
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

	public void setArtificial_person_pic_back_src(
			String artificial_person_pic_back_src) {
		this.artificial_person_pic_back_src = artificial_person_pic_back_src;
	}

	public String getArtificial_person_idcard_pic_src() {
		return artificial_person_idcard_pic_src;
	}

	public void setArtificial_person_idcard_pic_src(
			String artificial_person_idcard_pic_src) {
		this.artificial_person_idcard_pic_src = artificial_person_idcard_pic_src;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getFax_number() {
		return fax_number;
	}

	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}

	public String getLocation_province() {
		return location_province;
	}

	public void setLocation_province(String location_province) {
		this.location_province = location_province;
	}

	public String getLocation_city() {
		return location_city;
	}

	public void setLocation_city(String location_city) {
		this.location_city = location_city;
	}

	public String getLocation_county() {
		return location_county;
	}

	public void setLocation_county(String location_county) {
		this.location_county = location_county;
	}

	public String getLocation_town() {
		return location_town;
	}

	public void setLocation_town(String location_town) {
		this.location_town = location_town;
	}

	public String getLocation_detail() {
		return location_detail;
	}

	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}

	public String getCreate_id() {
		return create_id;
	}

	public void setCreate_id(String create_id) {
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
