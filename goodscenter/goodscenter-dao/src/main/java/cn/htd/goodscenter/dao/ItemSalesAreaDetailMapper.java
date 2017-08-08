package cn.htd.goodscenter.dao;

import java.util.List;

import cn.htd.goodscenter.domain.ItemSalesAreaDetail;

public interface ItemSalesAreaDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemSalesAreaDetail record);

    int insertSelective(ItemSalesAreaDetail record);

    ItemSalesAreaDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemSalesAreaDetail record);

    int updateByPrimaryKey(ItemSalesAreaDetail record);
    
    List<ItemSalesAreaDetail> selectAreaDetailsBySalesAreaId(Long salesAreaId);
    
    List<ItemSalesAreaDetail> selectAreaDetailsBySalesAreaIdAll(Long salesAreaId);
    
    void deleteBySalesAreaId(Long salesAreaId);
    
    void batchInsertSalesAreaDetail(List<ItemSalesAreaDetail> list);
}