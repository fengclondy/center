package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;

/**
 * 预售商品属性DTO
 */
public class PreSaleProductAttributeDTO implements Serializable {
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValue;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
