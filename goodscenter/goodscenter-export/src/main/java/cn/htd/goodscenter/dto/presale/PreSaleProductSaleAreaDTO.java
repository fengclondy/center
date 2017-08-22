package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;

/**
 * Created by admin on 2017/8/14.
 */
public class PreSaleProductSaleAreaDTO implements Serializable {
    /**
     * 省编码
     */
    private String regionProvince;
    /**
     * 市编码
     */
    private String regionCity;
    /**
     * 区编码
     */
    private String regionCounty;
    /**
     * 销售区域中文
     */
    private String  region;

    public String getRegionProvince() {
        return regionProvince;
    }

    public void setRegionProvince(String regionProvince) {
        this.regionProvince = regionProvince;
    }

    public String getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(String regionCity) {
        this.regionCity = regionCity;
    }

    public String getRegionCounty() {
        return regionCounty;
    }

    public void setRegionCounty(String regionCounty) {
        this.regionCounty = regionCounty;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
