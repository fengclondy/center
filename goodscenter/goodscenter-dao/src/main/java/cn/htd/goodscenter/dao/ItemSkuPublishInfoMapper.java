package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.dto.indto.QueryItemStockDetailInDTO;
import cn.htd.goodscenter.dto.outdto.QueryItemStockDetailOutDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;

public interface ItemSkuPublishInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemSkuPublishInfo record);

    int insertSelective(ItemSkuPublishInfo record);

    ItemSkuPublishInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemSkuPublishInfo record);

    int updateByPrimaryKey(ItemSkuPublishInfo record);
    
    VenusItemSkuPublishInfoDetailOutDTO queryPublishInfoDetailBySkuId(QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO);
    
    ItemSkuPublishInfo selectByItemSkuAndShelfType(@Param("skuId") Long skuId,@Param("shelfType") String shelfType,@Param("isVisible") String isVisible);

    ItemSkuPublishInfo selectBySkuCodeAndShelfType(@Param("skuCode") String skuCode, @Param("isBoxFlag") Integer isBoxFlag);

    List<ItemSkuPublishInfo> queryItemSkuShelfStatus(@Param("skuId") Long skuId);
    


    /**
     * 根据skuId获取商品sku库存
     * @param skuId
     * @return
     */
    List<ItemSkuPublishInfo> queryBySkuId(Long skuId);

    /**
     * 根据skuId更新商品sku库存
     * @param itemSkuPublishInfo
     * @return
     */
    int updateBySkuId(ItemSkuPublishInfo itemSkuPublishInfo);
    
    /**
     * 根据skuId查询上架商品个数
     * 
     * @param skuId
     * @return
     */
    Integer queryItemSkuOnShelvedCount(Long skuId);

    int add(ItemSkuPublishInfo itemSkuPublishInfo);
    
    /**
     * 查询待自动上架商品
     * 
     * @param paramMap
     * @return
     */
    List<ItemSkuPublishInfo> queryWaitingAutoUpShelfItem(Map paramMap);
    
    /**
     *查询待自动下架商品
     *    
     * @param paramMap
     * @return
     */
    List<ItemSkuPublishInfo> queryWaitingAutoDownShelfItem(Map paramMap);
    
    void updateDisplayQuantityByPk(Map paramMap);
    
    void updateVisibleStateByPk(Map paramMap);

    QueryItemStockDetailOutDTO queryItemStockDetailInDTO(@Param("param")QueryItemStockDetailInDTO queryItemStockDetailInDTO );
}