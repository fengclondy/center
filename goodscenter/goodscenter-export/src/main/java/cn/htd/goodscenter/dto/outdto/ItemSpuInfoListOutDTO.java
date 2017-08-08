package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

/**
 * 模板列表dto
 */
public class ItemSpuInfoListOutDTO implements Serializable {
    /**
     * 商品模板名称
     */
    private String spuName;
    /**
     * 商品模板编码
     */
    private String spuCode;
    /**
     * 型号
     */
    private String modelType;
    /**
     * 商品三级类目ID
     */
    private Long cid;
    /**
     * 商品三级类目名称
     */
    private String cName;
    /**
     * 商品品牌ID
     */
    private Long brandId;
    /**
     * 商品品牌名称
     */
    private String brandName;
    /**
     * 商品主图url (不含域名)
     */
    private String pictureUrl;

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
