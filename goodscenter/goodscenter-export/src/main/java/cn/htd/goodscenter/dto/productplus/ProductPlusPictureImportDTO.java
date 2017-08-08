package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;

/**
 * 外接渠道商品图片导入DTO
 * @author chenkang
 */
public class ProductPlusPictureImportDTO implements Serializable {

    private String pictureUrl;// 图片url

    private Integer isFirst;//是否是主图

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }
}
