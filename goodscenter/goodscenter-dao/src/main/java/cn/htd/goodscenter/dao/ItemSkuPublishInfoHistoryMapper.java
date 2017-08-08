package cn.htd.goodscenter.dao;

import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;

import java.util.List;

public interface ItemSkuPublishInfoHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemSkuPublishInfoHistory record);

    int insertSelective(ItemSkuPublishInfoHistory record);

    ItemSkuPublishInfoHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemSkuPublishInfoHistory record);

    int updateByPrimaryKey(ItemSkuPublishInfoHistory record);

    List<ItemSkuPublishInfoHistory> select(ItemSkuPublishInfoHistory itemSkuPublishInfoHistory);

    ItemSkuPublishInfoHistory selectLastStockRecord(ItemSkuPublishInfoHistory itemSkuPublishInfoHistory);
}