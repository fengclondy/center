package cn.htd.zeus.tc.dto;

import cn.htd.zeus.tc.dto.response.GenricResDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 根据买家查询最近其购买商品的大B （排除没有包厢关系的大B）
 */
public class OrderRecentQueryPurchaseRecordsOutDTO extends GenricResDTO implements Serializable {

    List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList;

    public List<OrderRecentQueryPurchaseRecordOutDTO> getOrderRecentQueryPurchaseRecordOutDTOList() {
        return orderRecentQueryPurchaseRecordOutDTOList;
    }

    public void setOrderRecentQueryPurchaseRecordOutDTOList(List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList) {
        this.orderRecentQueryPurchaseRecordOutDTOList = orderRecentQueryPurchaseRecordOutDTOList;
    }
}
