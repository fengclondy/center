package cn.htd.goodscenter.dto.mall;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;

import java.io.Serializable;

/**
 * 商城商品详情- 库存查询（包含上下架信息）
 */
public class MallChoiceBoxProductOutDTO implements Serializable {

    private Integer isBoxProduct; //是否包厢商品 【内部供应商商品必传：0-大厅 1-包厢】

    private Integer isUpShelf; // 1 : 上架、0 : 下架

    private ItemSkuPublishInfo itemSkuPublishInfo; // 库存 注：可卖库存=可见库存-锁定库存

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

    public Integer getIsBoxProduct() {
        return isBoxProduct;
    }

    public void setIsBoxProduct(Integer isBoxProduct) {
        this.isBoxProduct = isBoxProduct;
    }
}
