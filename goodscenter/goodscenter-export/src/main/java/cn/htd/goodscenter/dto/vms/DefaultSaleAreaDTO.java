package cn.htd.goodscenter.dto.vms;

import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;

import java.util.List;

public class DefaultSaleAreaDTO {

    //销售区域
    private ItemSalesArea itemSaleArea;

    private List<ItemSalesAreaDetail> itemSaleAreaDetailList;

    public ItemSalesArea getItemSaleArea() {
        return itemSaleArea;
    }

    public void setItemSaleArea(ItemSalesArea itemSaleArea) {
        this.itemSaleArea = itemSaleArea;
    }

    public List<ItemSalesAreaDetail> getItemSaleAreaDetailList() {
        return itemSaleAreaDetailList;
    }

    public void setItemSaleAreaDetailList(List<ItemSalesAreaDetail> itemSaleAreaDetailList) {
        this.itemSaleAreaDetailList = itemSaleAreaDetailList;
    }
}
