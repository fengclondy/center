package cn.htd.storecenter.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.dto.ShopInfoModifyInDTO;

public interface ShopExportService {

	// Redis店铺二级域名
	public static final String REDIS_SHOP_SECOND_DOMAIN = "B2B_MIDDLE_SHOP_SECOND_DOMAIN";
	
	/**
	 *
	 * <p>
	 * Discription:[添加店铺信息]
	 * </p>
	 */
	public ExecuteResult<String> saveShopInfo(ShopDTO shopDTO);

	/**
	 *
	 * <p>
	 * Discription:[查询店铺信息详情]
	 * </p>
	 */
	public ExecuteResult<ShopDTO> findShopInfoById(long id);

	/**
	 *
	 * <p>
	 * Discription:[根据条件查询店铺信息列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopDTO>> findShopInfoByCondition(ShopDTO shopDTO, Pager<ShopDTO> pager);

	/**
	 *
	 * <p>
	 * Discription:[根据店铺id修改新店铺申请审核状态]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopInfostatus(ShopDTO dto);

	/**
	 *
	 * <p>
	 * Discription:[批量查询操作]
	 * </p>
	 */
	public ExecuteResult<List<ShopDTO>> queryShopInfoByids(ShopAuditInDTO shopAudiinDTO);

	public ExecuteResult<List<ShopDTO>> queryShopByids(ShopAuditInDTO shopAudiinDTO);

	/**
	 *
	 * <p>
	 * Discription:[店铺信息修改--申请提交(需要审核的 插入明细表)]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopInfo(ShopDTO dto);

	/**
	 *
	 * <p>
	 * Discription:[直接修改店铺信息]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopInfoUpdate(ShopInfoModifyInDTO shopInfoModifyInDTO);


	/**
	 *
	 * <p>
	 * Discription:[通过店铺信息审核 和品牌 类目 审核]
	 * </p>
	 * 
	 * @param shopId
	 * @param status 2是通过，3是驳回
	 */
	public ExecuteResult<String> modifyShopInfoAndcbstatus(ShopDTO shopDTO);

	/**
	 *
	 * <p>
	 * Discription:[修改店铺状态为开通 店铺运行状态开启]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopStatus(ShopDTO dto);

	/**
	 *
	 * <p>
	 * Discription:[修改店铺状态为审核通过 店铺运行状态关闭]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopStatusBack(ShopDTO dto);

	/**
	 * ]
	 *
	 * <p>
	 * Discription:[根据品牌id查询店铺信息]
	 * </p>
	 * 
	 * @param brandId
	 * @param page
	 */
	public ExecuteResult<DataGrid<ShopDTO>> queryShopInfoByBrandId(Long brandId, Pager<ShopDTO> page);

	/**
	 *
	 * <p>
	 * Discription:[将一个店铺域名信息放入redis]
	 * </p>
	 * 
	 * @param shopUrl 二级域名
	 * @param shopId 店铺id
	 */
	public ExecuteResult<String> addSecondDomainToRedis(String shopUrl, Long shopId);

	/**
	 *
	 * <p>
	 * Discription:[将所有店铺域名放入redis]
	 * </p>
	 */
	public ExecuteResult<String> addSecondDomainToRedis();

	/**
	 * 
	 * <p>
	 * Discription:[统计根据对象查询不同状态下店铺数量 ]
	 * </p>
	 */
	public Long queryStayCheckCount(ShopDTO shopDTO);

	public List<ShopDTO> queryShopInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("page") Pager<ShopDTO> page);


	/**
	 *
	 * <p>
	 * Discription:[根据sellerId查询店铺列表 ]
	 * </p>
	 */
	public ExecuteResult<ShopDTO> queryBySellerId(Long sellerId);


}
