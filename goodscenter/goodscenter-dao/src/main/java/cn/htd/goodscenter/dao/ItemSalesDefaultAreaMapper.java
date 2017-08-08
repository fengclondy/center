package cn.htd.goodscenter.dao;

import java.util.List;

import cn.htd.goodscenter.domain.ItemSalesDefaultArea;

public interface ItemSalesDefaultAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemSalesDefaultArea record);

    int insertSelective(ItemSalesDefaultArea record);

    ItemSalesDefaultArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemSalesDefaultArea record);

    int updateByPrimaryKey(ItemSalesDefaultArea record);
    
    List<ItemSalesDefaultArea> selectDefaultSalesAreaBySellerId(Long sellerId);
    
    void deleteBySellerId(Long sellerId);
}