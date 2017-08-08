package cn.htd.usercenter.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.util.StringUtils;

public class HTDCompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyId; // 公司ID
    private String name; // 公司名
    private String type; // 公司类型
    private String parentCompanyId; // 所属公司ID
    private String addressProvince; // 公司地址-省
    private String addressCity; // 公司地址-市
    private String addressDistrict; // 公司地址-区
    private String addressTown; // 公司地址-县
    private String area;//所属区域

    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

    public boolean equals(HTDCompanyDTO target) {
        if (target == null) {
            return false;
        }

        if (nvl(this.getCompanyId()).equals(nvl(target.getCompanyId()))
                && nvl(this.getName()).equals(nvl(target.getName()))
                && nvl(this.getType()).equals(nvl(target.getType()))
                && nvl(this.getParentCompanyId()).equals(nvl(target.getParentCompanyId()))
                && nvl(this.getArea()).equals(nvl(target.getArea()))
                && nvl(this.getAddressProvince()).equals(nvl(target.getAddressProvince()))
                && nvl(this.getAddressCity()).equals(nvl(target.getAddressCity()))
                && nvl(this.getAddressDistrict()).equals(nvl(target.getAddressDistrict()))
                && nvl(this.getAddressTown()).equals(nvl(target.getAddressTown()))) {
            return true;
        } else {
            return false;
        }
    }

    private static final String nvl(String str) {
        return StringUtils.isEmpty(str) ? "" : str;
    }
}
