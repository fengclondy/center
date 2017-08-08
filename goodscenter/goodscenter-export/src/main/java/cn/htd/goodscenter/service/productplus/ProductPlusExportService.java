package cn.htd.goodscenter.service.productplus;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.common.ProductChannel;
import cn.htd.goodscenter.domain.productplus.OuterChannelBrandMapping;
import cn.htd.goodscenter.domain.productplus.OuterChannelCategoryMapping;
import cn.htd.goodscenter.domain.productplus.SellerCategoryBrandShield;
import cn.htd.goodscenter.dto.productplus.JdProductQueryInDTO;
import cn.htd.goodscenter.dto.productplus.JdProductQueryOutDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusAccessInfoInDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusAccessInfoOutDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemUpShelfSettingDTO;
import cn.htd.goodscenter.dto.productplus.SellerCategoryBrandShieldDTO;
import cn.htd.goodscenter.dto.productplus.SellerOuterProductChannelDTO;
import cn.htd.goodscenter.dto.productplus.SellerOuterProductChannelImportResultDTO;

/**
 * 商品+服务
 * （外接商品服务）
 * @author chenakng
 * @
 */
public interface ProductPlusExportService {
    /**  运营中心 **/
    /**
     * 外接商品渠道一览
     * @return 外接商品渠道一览
     */
    ExecuteResult<List<ProductChannel>> queryOuterProductChannelList();

    /**
     * 根据外部渠道编码查询外部渠道信息
     * @param code
     * @return
     */
    ExecuteResult<ProductChannel> queryProductChannelByChannelCode(String code);

    /**
     * 查询外接商品类目【已映射】关系
     * 导出当前 ~
     * 导出全部 【不传入分页参数】
     * @param outerChannelCategoryMapping 外接渠道映射参数
     * @param pager 分页参数
     */
    ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryMappedOuterChannelCategoryList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager);

    /**
     * 查询外接商品类目【未映射】关系
     * 导出当前 ~
     * 导出全部 【不传入分页参数】
     * @param outerChannelCategoryMapping 外接渠道映射参数
     * @param pager 分页参数
     */
    ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryNoMappedOuterChannelCategoryList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager);


    /**
     * 查询 - 外接商品类目【ALL】映射关系
     * @param outerChannelCategoryMapping 外接渠道映射参数
     * @param pager 分页参数
     * @param isMapped 是否映射
     */
    ExecuteResult<DataGrid<OuterChannelCategoryMapping>> queryOuterChannelCategoryMappingList(OuterChannelCategoryMapping outerChannelCategoryMapping, Pager pager, boolean isMapped);

    /**
     * 保存/修改 - 外接商品类目映射关系
     * @param outerChannelCategoryMapping 参数
     * @return 返回主键
     */
    ExecuteResult<String> addOrModifyOuterChannelCategoryMapping(OuterChannelCategoryMapping outerChannelCategoryMapping);

    /**
     * 查询外接商品渠道品牌已映射关系
     *  导出当前 ~
     *  导出全部 【不传入分页参数】
     * @param outerChannelBrandMapping 参数
     * @param pager 分页信息
     *
     * @return
     */
    ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryMappedOuterChannelBrandList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager);

    /**
     * 查询外接商品渠道品牌未映射关系
     *  导出当前 ~
     *  导出全部 【不传入分页参数】
     * @param outerChannelBrandMapping 参数
     * @param pager 分页信息
     * @return
     */
    ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryNoMappedOuterChannelBrandList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager);

    /**
     * 查询外接商品渠道品牌映射关系
     * @param outerChannelBrandMapping 参数
     * @param pager 分页信息
     * @param isMapped 是否已映射
     * @return
     */
    ExecuteResult<DataGrid<OuterChannelBrandMapping>> queryOuterChannelBrandMappingList(OuterChannelBrandMapping outerChannelBrandMapping, Pager pager, boolean isMapped);

    /**
     * 保存、更新外接商品品牌映射关系
     * @param outerChannelBrandMapping 参数
     * @return 成功结果，成功返回主键
     */
    ExecuteResult<String> addOrModifyOuterChannelBrandMapping(OuterChannelBrandMapping outerChannelBrandMapping);

    /**
     * 查询商家接入外接商品渠道列表 for 运营中心查询商家接入
     *
     * @param sellerOuterProductChannelDTO 参数    公司名称(精确查询) + 商家编号（精确查询）+ 外部平台（必须传）
     * @param pager 分页信息
     * @retrun
     */
    ExecuteResult<DataGrid<SellerOuterProductChannelDTO>> querySellerOuterProductChannelList(SellerOuterProductChannelDTO sellerOuterProductChannelDTO, Pager pager);

    /**
     * 查询商家外接渠道接入详情
     * 传入主键ID
     * @param sellerOuterProductChannelDTO 参数
     * @retrun 商家外接渠道接入详情
     */
    ExecuteResult<SellerOuterProductChannelDTO> querySellerOuterProductChannelDetail(SellerOuterProductChannelDTO sellerOuterProductChannelDTO);

    /**
     * 商家接入 - 外部平台接入管理
     * 多个渠道以逗号隔开传入channelCode中。
     */
    ExecuteResult<String> addSellerOuterProductChannel(SellerOuterProductChannelDTO sellerOuterProductChannelDTO);

    /**
     * 批量导入-商家接入外接渠道
     */
    ExecuteResult<SellerOuterProductChannelImportResultDTO> batchAddSellerOuterProductChannel(List<SellerOuterProductChannelDTO> sellerOuterProductChannelDTOList);

    /**
     * 查询外接商品单品列表
     * 导出当前页
     * 导出全部 （page 传 null）
     * @param productPlusItemDTO 参数
     * @param pager 分页参数
     * @return 外接商品单品列表
     */
    ExecuteResult<DataGrid<ProductPlusItemDTO>> queryProductPlushItemList(ProductPlusItemDTO productPlusItemDTO, Pager pager);

    /**
     * 查询外接商品单品详情
     * 查询卖家外接商品单品详情
     * @param skuId 参数
     * @return 外接商品单品详情
     */
    ExecuteResult<ProductPlusItemDTO> queryProductPlushItemDetail(Long skuId);


    /**
     * 上架外接渠道商品
     * @param productPlusItemDTO 参数
     */
    ExecuteResult<String> upShelfProductPlushItem(ProductPlusItemDTO productPlusItemDTO);

    /**
     * 下架外接渠道商品
     * （注：下架只修改下架状态）
     * @param itemId 参数
     * @param itemId
     */
    ExecuteResult<String> downShelfProductPlushItem(Long itemId);

    /**
     * 修改外接渠道商品价格、浮动比例及预售信息
     * （在上架状态下，修改）
     * @param productPlusItemDTO 参数
     */
    ExecuteResult<String> modifyProductPlushItemPriceAndPreSaleInfo(ProductPlusItemDTO productPlusItemDTO);

    /**
     * 批量上架外接渠道商品
     * @param productPlusItemDTOs list中传item_id 和 sku_id
     * @param productPlusItemUpShelfSettingDTO 上架设置参数  注：百分比例请换算成小数
     */
    ExecuteResult<String> batchUpShelfProductPlushItem(List<ProductPlusItemDTO> productPlusItemDTOs, ProductPlusItemUpShelfSettingDTO productPlusItemUpShelfSettingDTO);

    /**
     * 批量下架外接渠道商品
     * @param itemIds 参数
     */
    ExecuteResult<String> batchDownShelfProductPlushItem(List<Long> itemIds);


    /**  vms **/

    /**
     * 查询卖家外接渠道商品列表
     * @param productPlusItemDTO
     * @return 卖家外接渠道商品列表
     */
    ExecuteResult<DataGrid<ProductPlusItemDTO>> querySellerProductPlusProductList(ProductPlusItemDTO productPlusItemDTO, Pager pager);

    /**
     * 查询未屏蔽的品类与品牌
     * （通过渠道编码查询已经映射过的品牌、品类）除去 (卖家已屏蔽的)
     */
    ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> querySellerNoShieldCategoryBrandList(SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, Pager pager);

    /**
     * 屏蔽外接品牌品类
     * @param sellerCategoryBrandShieldList 参数
     * @return
     */
    ExecuteResult<String> shieldCategoryBrand(List<SellerCategoryBrandShield> sellerCategoryBrandShieldList);

    /**
     * 取消屏蔽外接品牌品类
     * @param sellerCategoryBrandShieldList 参数
     * @return
     */
    ExecuteResult<String> cancelShieldCategoryBrand(List<SellerCategoryBrandShield> sellerCategoryBrandShieldList);

    /**
     * 查询已经屏蔽的品类与品牌
     */
    ExecuteResult<DataGrid<SellerCategoryBrandShieldDTO>> querySellerShieldCategoryBrandList(SellerCategoryBrandShieldDTO sellerCategoryBrandShieldDTO, Pager pager);

    /**
     * 查询接入商品+信息
     * 
     * @param productPlusAccessInfoInDTO
     * @return
     */
    ProductPlusAccessInfoOutDTO queryProductPlusAccessInfoList(ProductPlusAccessInfoInDTO productPlusAccessInfoInDTO);

    JdProductQueryOutDTO queryJdProduct4SupperBoss(JdProductQueryInDTO jdProductQueryInDTO);


    /**
     * 该商品加商品是否可以被这个大B售卖
     * 查询卖家是否接入该品牌、品类商品+商品 （提供商品+商品详情页是否展示）
     * 1. 查询大B卖家是否接入商品+
     * 2. 查询该品牌、品类是否被屏蔽，是否可卖
     *
     * @param sellerId 卖家大B id
     * @param productChannel 商品+渠道   3010
     * @param categoryId 商品品类
     * @param brandId 商品品牌
     * @return 是否可卖
     */
    ExecuteResult<Boolean> canProductPlusSaleBySeller(Long sellerId, String productChannel, Long categoryId, Long brandId);


    /**
     * 大B是否接入商品加
     * @param sellerId
     * @param productChannel
     * @return
     */
    ExecuteResult<Boolean> isSellerAccessProductPlus(Long sellerId, String productChannel);
    
}
