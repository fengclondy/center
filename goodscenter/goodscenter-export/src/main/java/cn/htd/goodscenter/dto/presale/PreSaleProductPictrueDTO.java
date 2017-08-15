package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;

/**
 * Created by admin on 2017/8/14.
 */
public class PreSaleProductPictrueDTO implements Serializable {

    private String imageType; // 主图：PRIMARY

    private String url; // 地址

    private String sort; // 排序

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
