package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lih
 * Date: 2017/3/31
 * Time: 11:51
 */
public class FreightTemplateAddressDTO implements Serializable{
    private static final long serialVersionUID = -3156213853943757620L;

    private String code;

    private Integer level;

    private String name;

    private List<FreightTemplateAddressDTO> freightTemplateAddressDTOs = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FreightTemplateAddressDTO> getFreightTemplateAddressDTOs() {
        return freightTemplateAddressDTOs;
    }

    public void setFreightTemplateAddressDTOs(List<FreightTemplateAddressDTO> freightTemplateAddressDTOs) {
        this.freightTemplateAddressDTOs = freightTemplateAddressDTOs;
    }
}
