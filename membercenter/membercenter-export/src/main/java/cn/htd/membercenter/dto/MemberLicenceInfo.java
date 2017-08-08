package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/11/17.
 */
public class MemberLicenceInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5463343528664164106L;
	private Long member_id;
    private String buyer_business_license_id;
    private String buyer_guarantee_license_pic_src;
    private String buyer_business_license_pic_src;
    private String certificate_type;
    private String unified_social_credit_code;
    private String business_license_id;
    private String business_license_pic_src;
    private String business_license_certificate_pic_src;
    private String organization_id;
    private String organization_pic_src;
    private String taxpayer_type;
    private String tax_man_id;
    private String taxpayer_code;
    private String tax_registration_certificate_pic_src;
    private String taxpayer_certificate_pic_src;
    private Long create_id;
    private String create_name;
    private String create_time;
    private Long modify_id;
    private String modify_name;
    private String modify_time;

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getBuyer_business_license_id() {
        return buyer_business_license_id;
    }

    public void setBuyer_business_license_id(String buyer_business_license_id) {
        this.buyer_business_license_id = buyer_business_license_id;
    }

    public String getBuyer_guarantee_license_pic_src() {
        return buyer_guarantee_license_pic_src;
    }

    public void setBuyer_guarantee_license_pic_src(String buyer_guarantee_license_pic_src) {
        this.buyer_guarantee_license_pic_src = buyer_guarantee_license_pic_src;
    }

    public String getBuyer_business_license_pic_src() {
        return buyer_business_license_pic_src;
    }

    public void setBuyer_business_license_pic_src(String buyer_business_license_pic_src) {
        this.buyer_business_license_pic_src = buyer_business_license_pic_src;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getUnified_social_credit_code() {
        return unified_social_credit_code;
    }

    public void setUnified_social_credit_code(String unified_social_credit_code) {
        this.unified_social_credit_code = unified_social_credit_code;
    }

    public String getBusiness_license_id() {
        return business_license_id;
    }

    public void setBusiness_license_id(String business_license_id) {
        this.business_license_id = business_license_id;
    }

    public String getBusiness_license_pic_src() {
        return business_license_pic_src;
    }

    public void setBusiness_license_pic_src(String business_license_pic_src) {
        this.business_license_pic_src = business_license_pic_src;
    }

    public String getBusiness_license_certificate_pic_src() {
        return business_license_certificate_pic_src;
    }

    public void setBusiness_license_certificate_pic_src(String business_license_certificate_pic_src) {
        this.business_license_certificate_pic_src = business_license_certificate_pic_src;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getOrganization_pic_src() {
        return organization_pic_src;
    }

    public void setOrganization_pic_src(String organization_pic_src) {
        this.organization_pic_src = organization_pic_src;
    }

    public String getTaxpayer_type() {
        return taxpayer_type;
    }

    public void setTaxpayer_type(String taxpayer_type) {
        this.taxpayer_type = taxpayer_type;
    }

    public String getTax_man_id() {
        return tax_man_id;
    }

    public void setTax_man_id(String tax_man_id) {
        this.tax_man_id = tax_man_id;
    }

    public String getTaxpayer_code() {
        return taxpayer_code;
    }

    public void setTaxpayer_code(String taxpayer_code) {
        this.taxpayer_code = taxpayer_code;
    }

    public String getTax_registration_certificate_pic_src() {
        return tax_registration_certificate_pic_src;
    }

    public void setTax_registration_certificate_pic_src(String tax_registration_certificate_pic_src) {
        this.tax_registration_certificate_pic_src = tax_registration_certificate_pic_src;
    }

    public String getTaxpayer_certificate_pic_src() {
        return taxpayer_certificate_pic_src;
    }

    public void setTaxpayer_certificate_pic_src(String taxpayer_certificate_pic_src) {
        this.taxpayer_certificate_pic_src = taxpayer_certificate_pic_src;
    }

    public Long getCreate_id() {
        return create_id;
    }

    public void setCreate_id(Long create_id) {
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
}
