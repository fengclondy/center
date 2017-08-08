package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.ItemSkuTotalStock;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelListOutDTO;

public interface ItemSkuTotalStockMapper {
    int deleteByPrimaryKey(Long skuId);

    int insert(ItemSkuTotalStock record);

    int insertSelective(ItemSkuTotalStock record);

    ItemSkuTotalStock selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(ItemSkuTotalStock record);

    int updateByPrimaryKey(ItemSkuTotalStock record);


    /**
     * 根据skuId查询sku实际库存量
     * @param skuId
     * @return
     */
    ItemSkuTotalStock  queryBySkuId(Long skuId);
    
    void updateItemTotalStockBySkuCodeAndSellerId(@Param("inventory") Long inventory,
    		@Param("modifyId") Long modifyId,
    		@Param("modifyName") String modifyName,
    		@Param("skuCode") String skuCode,
    		@Param("sellerId") String sellerId);
    
    /**
     * 根据sellerId查询库存预警统计数据
     * 
     * @param sellerId
     * @return
     */
    List<Map<String,Integer>> queryWarningStockLevelProductsData(@Param("sellerId") Long sellerId);
    
    /**
     * 根据sellerId 查询库存预警数据
     * 
     * @param sellerId
     * @return
     */
    List<VenusWarningStockLevelListOutDTO> queryWarningStockLevelProductsInfoList(@Param("sellerId") Long sellerId);

    /**
     * 卖家中心修改库存 add by jcf
     */
    int updateItemInventoryByItemId (ItemSkuTotalStock item);
}