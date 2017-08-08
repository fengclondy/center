package cn.htd.goodscenter.dto.mall;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;

import java.io.Serializable;

/**
 * 商城商品详情- 库存查询（包含上下架信息）
 */
public class MallSkuStockOutDTO implements Serializable {

    private String skuCode; //商品sku编码

    private Integer isBoxFlag; //是否包厢商品 【内部供应商商品必传：0-大厅 1-包厢】

    /** 上下架 **/
    private Integer isUpShelf;// 1 : 上架、0 : 下架

    private ItemSkuPublishInfo itemSkuPublishInfo; // 【只有内部供应商和外部供应商有库存】区域或者包厢的库存信息 注：如果为null，说明没有库存信息，或者区域和包厢的已经下架

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public Integer getIsUpShelf() {
        return isUpShelf;
    }

    public void setIsUpShelf(Integer isUpShelf) {
        this.isUpShelf = isUpShelf;
    }

    public ItemSkuPublishInfo getItemSkuPublishInfo() {
        return itemSkuPublishInfo;
    }

    public void setItemSkuPublishInfo(ItemSkuPublishInfo itemSkuPublishInfo) {
        this.itemSkuPublishInfo = itemSkuPublishInfo;
    }

    @Override
    public String toString() {
        return "MallSkuStockOutDTO{" +
                "skuCode='" + skuCode + '\'' +
                ", isBoxFlag=" + isBoxFlag +
                ", isUpShelf=" + isUpShelf +
                ", itemSkuPublishInfo=" + itemSkuPublishInfo +
                '}';
    }
}
