package cn.htd.pricecenter.dao;

import cn.htd.pricecenter.domain.ItemSkuTerminalPrice;

public interface ItemSkuTerminalPriceMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(ItemSkuTerminalPrice record);

    int insertSelective(ItemSkuTerminalPrice record);

    ItemSkuTerminalPrice selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(ItemSkuTerminalPrice record);

    int updateByPrimaryKey(ItemSkuTerminalPrice record);
}