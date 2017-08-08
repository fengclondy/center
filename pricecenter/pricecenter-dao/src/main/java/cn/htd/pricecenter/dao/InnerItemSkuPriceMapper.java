package cn.htd.pricecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.pricecenter.domain.InnerItemSkuPrice;

public interface InnerItemSkuPriceMapper {
    int deleteByPrimaryKey(Long gradePriceId);

    int insert(InnerItemSkuPrice record);

    int insertSelective(InnerItemSkuPrice record);

    InnerItemSkuPrice selectByPrimaryKey(Long gradePriceId);

    int updateByPrimaryKeySelective(InnerItemSkuPrice record);

    int updateByPrimaryKey(InnerItemSkuPrice record);
    
    int insertBatch(List<InnerItemSkuPrice> list);

    List<InnerItemSkuPrice> selectAllInnerItemSkuPriceBySkuId(Long skuId);

    List<InnerItemSkuPrice> selectInnerItemSkuPriceBySkuId(@Param("skuId") Long skuId, @Param("isBoxFlag")Integer isBoxFlag);
    
    void txDeleteInnerItemSkuAreaPrice(@Param("areaCode") String areaCode,@Param("skuId") Long skuId);
    
    void txDeleteInnerItemSkuPrice(@Param("skuId") Long skuId,@Param("isBoxFlag") Integer isBoxFlag);

    InnerItemSkuPrice select(InnerItemSkuPrice innerItemSkuPrice);
}