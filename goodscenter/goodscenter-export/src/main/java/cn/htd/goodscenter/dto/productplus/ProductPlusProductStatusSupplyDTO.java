package cn.htd.goodscenter.dto.productplus;

/**
 * 商品加 - 上下架修改【增量】-DTO
 */
public class ProductPlusProductStatusSupplyDTO {
    /** sku **/
    private String outerSkuId;// 外接商品编码 skuId

    private String outerItemStatus;// 外部商品状态 1：未上架，2：已上架

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    public String getOuterItemStatus() {
        return outerItemStatus;
    }

    public void setOuterItemStatus(String outerItemStatus) {
        this.outerItemStatus = outerItemStatus;
    }
}
