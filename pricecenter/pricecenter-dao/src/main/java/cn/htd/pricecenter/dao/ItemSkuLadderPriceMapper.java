package cn.htd.pricecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.ExecuteResult;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

public interface ItemSkuLadderPriceMapper {
    int deleteByPrimaryKey(Long ladderId);

    int insert(ItemSkuLadderPrice record);

    int insertSelective(ItemSkuLadderPrice record);

    ItemSkuLadderPrice selectByPrimaryKey(Long ladderId);

    int updateByPrimaryKeySelective(ItemSkuLadderPrice record);

    int updateByPrimaryKey(ItemSkuLadderPrice record);

    List<ItemSkuLadderPrice> getSkuLadderPrice(Long skuId);

    int updateDeleteFlagByItemId(@Param("entity") ItemSkuLadderPrice record);

    public ExecuteResult<String> deleteItemSkuLadderPrice(Long skuId);

    public List<ItemSkuLadderPrice> selectPriceBySellerIdAndSkuId(@Param("sellerId") Long sellerId, @Param("skuId") Long skuId);

    public int deleteLadderPriceBySkuId(ItemSkuLadderPrice itemSkuLadderPrice);
    
    String queryMinLadderPriceByItemId(Long itemId);
}