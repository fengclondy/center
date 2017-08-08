package cn.htd.goodscenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.ItemDescribe;

/**商品描述DAO
 * Created by GZG on 2016/11/16.
 */
public interface ItemDescribeDAO {
    int deleteByPrimaryKey(Long desId);

    int insert(ItemDescribe record);

    int insertSelective(ItemDescribe record);

    ItemDescribe selectByPrimaryKey(Long desId);

    int updateByPrimaryKeySelective(ItemDescribe record);

    int updateByPrimaryKeyWithBLOBs(ItemDescribe record);

    int updateByPrimaryKey(ItemDescribe record);

    int updateByItemId(ItemDescribe record);

    ItemDescribe getDescByItemId(@Param("itemId") Long itemId);
    
    ItemDescribe getDescBySkuId(@Param("skuId") Long skuId);
}
