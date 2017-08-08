package cn.htd.storecenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;

public interface ShopInfoDAO extends BaseDAO<ShopDTO> {

	/**
	 *
	 * <p>
	 * Discription:[修改店铺状态]
	 * </p>
	 */
	public void modifyShopInfostatus(ShopDTO shopDTO);

	/**
	 *
	 * <p>
	 * Discription:[查询店铺信息]
	 * </p>
	 */
	public List<ShopDTO> queryShopInfoByIds(@Param("entity") ShopAuditInDTO shopAudiinDTO);

	public List<ShopDTO> queryShopByIds(@Param("entity") ShopAuditInDTO shopAudiinDTO);

	/**
	 *
	 * <p>
	 * Discription:[修改店铺信息]
	 * </p>
	 */
	public void updateShopInfo(ShopModifyDetailDTO shopModifyDetailDTO);

	/**
	 *
	 * <p>
	 * Discription:[获取店铺ID方法]
	 * </p>
	 */
	public Long getShopId();


	/**
	 *
	 * <p>
	 * Discription:[根据品牌Id 查询店铺信息]
	 * </p>
	 */
	public List<ShopDTO> queryShopInfoByBrandId(@Param("brandId") Long brandId, @Param("page") Pager<ShopDTO> page);

	/**
	 *
	 * <p>
	 * Discription:[根据品牌Id 查询店铺信息条数]
	 * </p>
	 */
	public Long queryCountShopInfoByBrandId(Long brandId);

	public List<ShopDTO> queryShopInfoBySyncTime(@Param("syncTime") Date syncTime, @Param("page") Pager<ShopDTO> page);


	/**
	 *
	 * <p>
	 * Discription:[根据sellerId 查询店铺列表]
	 * </p>
	 */

	ShopDTO selectBySellerId(Long sellerId);
}