package cn.htd.pricecenter.dao;

import java.util.List;

import cn.htd.pricecenter.domain.ItemSkuBasePrice;

public interface ItemSkuBasePriceMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(ItemSkuBasePrice record);

    int insertSelective(ItemSkuBasePrice record);

    ItemSkuBasePrice selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(ItemSkuBasePrice record);

    int updateByPrimaryKey(ItemSkuBasePrice record);
    
    List<ItemSkuBasePrice> queryItemSkuBasePriceBySkuIdList(List<Long> list);

    ItemSkuBasePrice selectByItemCode(String itemCode);
}