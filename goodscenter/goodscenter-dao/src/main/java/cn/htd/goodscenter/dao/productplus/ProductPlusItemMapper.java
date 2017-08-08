package cn.htd.goodscenter.dao.productplus;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.productplus.JdProductQueryItemDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemDTO;

/**
 * 商品+商品dao
 * @author chenkang
 */
public interface ProductPlusItemMapper {
    /**
     * 查询外接商品列表
     * @param productPlusItemDTO 参数
     * @param pager 分页参数
     * @return 外接商品列表
     */
    List<ProductPlusItemDTO> selectProductPlusItemList(@Param("entity") ProductPlusItemDTO productPlusItemDTO, @Param("page") Pager pager);

    /**
     * 查询外接商品列表
     * @param productPlusItemDTO 参数
     * @return 外接商品列表
     */
    Long selectProductPlusItemListCount(@Param("entity") ProductPlusItemDTO productPlusItemDTO);

    /**
     * 查询外接商品详情
     * @param skuId
     * @return
     */
    ProductPlusItemDTO selectProductPlusItem(Long skuId);

    /**
     * 修改商品+
     * @param productPlusItemDTO
     */
    void updateProductPlusItem(ProductPlusItemDTO productPlusItemDTO);

    /**
     * 查询卖家外接商品列表
     * @param productPlusItemDTO 参数
     * @param pager 分页参数
     * @return 外接商品列表
     */
    List<ProductPlusItemDTO> selectSellerProductPlusItemList(@Param("entity") ProductPlusItemDTO productPlusItemDTO, @Param("page") Pager pager);

    /**
     * 查询外接商品列表
     * @param productPlusItemDTO 参数
     * @return 外接商品列表
     */
    Long selectSellerProductPlusItemListCount(@Param("entity") ProductPlusItemDTO productPlusItemDTO);

    /**
     * 查询京东商品数据-总数
     * @param skuCode
     * @param isPreSale
     * @param startTime
     * @param endTime
     * @return
     */
    int queryJdProductListCount(@Param("skuCode")String skuCode, @Param("isPreSale")Integer isPreSale,
                                @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    /**
     * 查询京东商品数据
     * @param skuCode
     * @param isPreSale
     * @param startTime
     * @param endTime
     * @param pager
     * @return
     */
    List<JdProductQueryItemDTO> queryJdProductList(@Param("skuCode")String skuCode, @Param("isPreSale")Integer isPreSale,
                            @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("page")Pager pager);

    Integer queryTotalProductPlusItemCount();
    
    List<Map> queryPagedProductPlusItem(Map param);
    
    List<Map> queryShieldSellerIdsByItemId(List<String> list);
}
