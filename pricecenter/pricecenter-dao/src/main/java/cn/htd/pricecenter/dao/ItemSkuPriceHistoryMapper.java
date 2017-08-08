package cn.htd.pricecenter.dao;

import cn.htd.pricecenter.domain.ItemSkuPriceHistory;

public interface ItemSkuPriceHistoryMapper {
    int deleteByPrimaryKey(Long ladderId);

    int insert(ItemSkuPriceHistory record);

    int insertSelective(ItemSkuPriceHistory record);

    ItemSkuPriceHistory selectByPrimaryKey(Long ladderId);

    int updateByPrimaryKeySelective(ItemSkuPriceHistory record);

    int updateByPrimaryKey(ItemSkuPriceHistory record);
}