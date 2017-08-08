package cn.htd.goodscenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.ItemSalesArea;

public interface ItemSalesAreaMapper {
    int deleteByPrimaryKey(Long salesAreaId);

    int insert(ItemSalesArea record);

    int insertSelective(ItemSalesArea record);

    ItemSalesArea selectByPrimaryKey(Long salesAreaId);

    int updateByPrimaryKeySelective(ItemSalesArea record);

    int updateByPrimaryKey(ItemSalesArea record);
    
    ItemSalesArea selectByItemId(@Param("itemId")Long itemId,@Param("shelfType") String shelfType);
}