package cn.htd.goodscenter.dto.outdto;

/**
 * Created by thinkpad on 2016/12/28.
 */
public class ItemToDoCountDTO {
    private static final long serialVersionUID = 1L;

    private Long shopId; //店铺ID
    private Long sellerId;//卖家ID
    private Integer itemStatus;//商品状态
    private String statusName;//状态名称
    private Long count;//统计数量


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
