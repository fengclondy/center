package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * SKU - 按照颜色分组的图片DTO
 */
public class SkuColorGroupPictureDTO implements Serializable {

    private String color; // 颜色的属性名称

    private List<SkuPictureDTO> skuPictureDTOList; // sku图片集合

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<SkuPictureDTO> getSkuPictureDTOList() {
        return skuPictureDTOList;
    }

    public void setSkuPictureDTOList(List<SkuPictureDTO> skuPictureDTOList) {
        this.skuPictureDTOList = skuPictureDTOList;
    }
}
