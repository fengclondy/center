package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.dto.indto.QueryBrandErpExceptionInDto;
import cn.htd.goodscenter.dto.indto.QueryItemBrandInDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.BrandOfShopDTO;
import cn.htd.goodscenter.dto.ItemBrandDTO;

/**
 * <p>
 * Description: [类目品牌操作类]
 * </p>
 */
public interface ItemBrandDAO extends BaseDAO<ItemBrand> {
	/**
	 * <p>
	 * Discription:[根据品牌id查询品牌列表]
	 * </p>
	 */
	public List<ItemBrandDTO> queryBrandByIds(List<Long> brandIds);

	/**
	 * <p>
	 * Discription:[根据品牌id查询品牌分页]
	 * </p>
	 */
	public List<ItemBrandDTO> queryBrandByIdsWithPage(@Param("page") Pager<ItemBrandDTO> page, @Param("entity") ItemBrandDTO dto);

	/**
	 * <p>
	 * Discription:[查询品牌数据的数量]
	 * </p>
	 */
	public long queryBrandCounts(@Param("entity") ItemBrandDTO dto);

	/**
	 * <p>
	 * Discription:[查询所有品牌列表]
	 * </p>
	 */
	public List<ItemBrandDTO> queryItemBrandAllList(@Param("page") Pager page);

	/**
	 * <p>
	 * Discription:[根据类目查询类目下的品牌]
	 * </p>
	 */
	public List<ItemBrandDTO> queryBrandListByCategoryId(@Param("entity") BrandOfShopDTO brandOfShopDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询品牌列表]
	 * </p>
	 */
	public List<ItemBrandDTO> queryBrandList(@Param("entity") ItemBrandDTO itemBrandDTO, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[查询数量]
	 * </p>
	 */
	public Long queryCountBrandList(@Param("entity") ItemBrandDTO itemBrandDTO);

	/**
	 * 
	 * <p>
	 * Discription:[修改有效状态]
	 * </p>
	 */
	public void updateStatus(Long brandId);

	/**
	 * 
	 * <p>
	 * Discription:[物理删除类目品牌关系]
	 * </p>
	 */
	public void deleteItemBrand(@Param("cid") Long cid, @Param("brandId") Long brandId);

	/**
	 * <p>
	 * Discription:[根据品牌id查询出该品牌是否类目关联]
	 * </p>
	 */
	public List<ItemBrandDTO> queryBrandCategoryById(@Param("brandId") Long brandId);


	public List<ItemBrandDTO> selectByBrandName(@Param("brandName") String brandName);
	
	/***
	 * 用于搜索引擎同步数据
	 * @param syncTime
	 * @return
	 */
	public List<ItemBrand> queryBrandBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 查询下行erp失败的品牌
	 * @param paramMap
	 * @return
	 */
	List<ItemBrand> selectErpFailBrandList(Map<String, Object> paramMap);

	/**
	 * 查询品牌列表for定时任务
	 * @param paramMap 参数
	 * @return
	 */
	List<ItemBrand> selectBrandList(Map<String, Object> paramMap);

	void updateForTask(ItemBrand itemBrand);

	ItemBrand queryByName(String brandName);

	Long selectErpExceptionCount(@Param("queryBrandErpExceptionInDto") QueryBrandErpExceptionInDto queryBrandErpExceptionInDto);

	List<ItemBrand> selectErpExceptionList(@Param("page") Pager pager, @Param("queryBrandErpExceptionInDto") QueryBrandErpExceptionInDto queryBrandErpExceptionInDto);

	public List<ItemBrandDTO> queryBrandList4SuperBoss(@Param("entity") QueryItemBrandInDTO itemBrandDTO, @Param("page") Pager page);

	public Long queryCountBrandList4SuperBoss(@Param("entity") QueryItemBrandInDTO itemBrandDTO);

    List<ItemBrand> batchQueryBrandByName(@Param("bNameList")List<String> cNameList);
}
