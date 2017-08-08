package cn.htd.usercenter.dto;

import java.io.Serializable;
import java.util.List;

public class ProvinceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;// 省份编码

    private String name;// 省份名称

    private List<CityDTO> cities;// 下级市

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
