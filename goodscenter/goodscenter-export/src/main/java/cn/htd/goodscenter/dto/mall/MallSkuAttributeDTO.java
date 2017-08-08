package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;

/**
 * 商品sku销售属性
 */
public class MallSkuAttributeDTO implements Serializable {

    private Long attributeId; // 属性id

    private String attributeName; // 属性名称

    private Long attributeValueId; // 属性值id

    private String attributeValueName; // 属性值名称

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Long getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Long attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getAttributeValueName() {
        return attributeValueName;
    }

    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    @Override
    public String toString() {
        return "ItemSkuAttribute{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeValueId=" + attributeValueId +
                ", attributeValueName='" + attributeValueName + '\'' +
                '}';
    }
}
