package cn.htd.goodscenter.service.mall;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.indto.QueryHotSellItemInDTO;
import cn.htd.goodscenter.dto.indto.QueryNewPublishItemInDTO;
import cn.htd.goodscenter.dto.mall.*;
import cn.htd.goodscenter.dto.outdto.HotSellItemOutDTO;
import cn.htd.goodscenter.dto.outdto.NewPublishItemOutDTO;

/**
 * 商品服务 - 商城
 * 相关
 * @author chenkang
 * @date 2017-01-09
 */
public interface MallItemExportService {
    /**
     * 进货单 ：商品信息
     * @param mallSkuInDTOList 进货单参数
     * @return
     */
    ExecuteResult<List<MallSkuOutDTO>> queryCartItemList(List<MallSkuInDTO> mallSkuInDTOList);

    /**
     * 商城 ：商品详情页
     * @param mallSkuInDTO
     * @return
     */
    ExecuteResult<MallSkuOutDTO> queryMallItemDetailPage(MallSkuInDTO mallSkuInDTO);

    /**
     * 订单：商品详情
     * @param mallSkuInDTO
     * @return
     */
    ExecuteResult<MallSkuOutDTO> queryMallItemDetail(MallSkuInDTO mallSkuInDTO);

    /**
     * 批量查询商品详情 (对queryMallItemDetail封装)
     * 
     * @param mallSkuInDTOs
     * @return
     */
    ExecuteResult<List<MallSkuOutDTO>> queryMallItemDetailList(List<MallSkuInDTO> mallSkuInDTOs);

    /**
     * 订单：查询商品基本信息和库存 (queryMallItemDetail 和 queryMallItemStockInfo)
     * 
     * @param mallSkuWithStockInDTO
     * @return
     */
    ExecuteResult<MallSkuWithStockOutDTO> queryMallItemDetailWithStock(MallSkuWithStockInDTO mallSkuWithStockInDTO);

    /**
     * 批量查询商品基本信息和库存（对queryMallItemDetailWithStock包装）
     * 
     * @param mallSkuWithStockInDTOList
     * @return
     */
    ExecuteResult<List<MallSkuWithStockOutDTO>> queryMallItemDetailWithStockList(List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList);

    /**
     * 查询商品上下架状态 & 库存 
     * 
     * @param mallSkuStockInDTO
     * @return
     */
    ExecuteResult<MallSkuStockOutDTO> queryMallItemStockInfo(MallSkuStockInDTO mallSkuStockInDTO);

    /**
     * 商城 ：秒杀商品详情页 商品基本信息；图片；
     * @param mallSkuInDTO
     * @return
     */
    ExecuteResult<MallSkuOutDTO> queryMallFlashBuyItemDetail(MallSkuInDTO mallSkuInDTO);

    /**
     * 商品收藏 - 列表信息
     * {
     *     商品基本信息；图片；上下架状态；库存
     *     价格: 展示销售价 或 阶梯价
     * }
     * @param skuId
     * @return
     */
    ItemFavouriteOutDTO queryFavouriteItemInfo(Long skuId);


    /**
     * 查询新品上架
     *
     * @param queryNewPublishItemInDTO
     * @return
     */
    ExecuteResult<List<NewPublishItemOutDTO>> queryNewPublishItemList(QueryNewPublishItemInDTO queryNewPublishItemInDTO);

    /**
     * 查询商品基本信息
     *
     * @param itemIdList
     * @return
     */
    ExecuteResult<List<MallSearchItemDTO>> queryMallSearchItemInfo(List<String> itemIdList);
    
    /**
     * 查询内部供应商商品销售区域
     * 
     * @param itemId
     * @param isBoxFlag
     * @return
     */
    ExecuteResult<ItemSalesAreaDTO> queryInnerSupplierItemSalesArea(Long itemId,Integer isBoxFlag);
    
    /**
     * 查询热销商品
     * 
     * @param queryHotSellItemInDTO
     * @return
     */
    ExecuteResult<List<HotSellItemOutDTO>> queryHotSellItemList(QueryHotSellItemInDTO queryHotSellItemInDTO);

    /**
     * 判断是否包厢商品，和库存信息接口
     * 
     * @param mallSkuStockInDTO
     * @return
     */
    ExecuteResult<MallChoiceBoxProductOutDTO> choiceMallItemBoxFlagAndStockInfo(final MallChoiceBoxProductInDTO mallSkuStockInDTO);

    /**
     * 是否包厢商品
     * 在销售区域上架
     * @param skuId
     * @param cityCode
     * @return
     */
    boolean isBoxProduct(Long skuId, String cityCode);

    /**
     * 是否区域商品
     * 在销售区域上架
     * @param skuId
     * @param cityCode
     * @return
     */
    boolean isAreaProduct(Long skuId, String cityCode);

    /**
     * 判断商品是否在区域销售
     * @param itemId
     * @param isBoxFlag
     * @param cityCode
     * @return
     */
    boolean isInSaleArea(Long itemId, Integer isBoxFlag, String cityCode);


    /**
     * <p>
         推荐商品
         A 推荐商品取运营中台推荐的0801的商品一款（取排序值最大的一款）和所有有包厢关系的供应商商品一款
         B 有包厢关系的供应商商品取值：优先展示最近采购过的商品，如无采购商品取最近上架的商品。
         C 所有商品排除无经营关系的包厢商品、不在会员注册地的区域商品、商品+商品、平台公司设置的秒杀商品。
         D 所有商品按上架时间降序排序
         E 平台公司商品价格：展示销售单价
         F 销量取商品总销售量
         G 商品图片、商品标题链接到商品详情页；供应商名称链接到供应商门户
     * </p>
     *
     * @param mallRecommendItemInDTO 入参
     * @param pager 分页
     */
    ExecuteResult<DataGrid<MallRecommendItemOutDTO>> queryRecommendItemList(MallRecommendItemInDTO mallRecommendItemInDTO, Pager pager);
}
