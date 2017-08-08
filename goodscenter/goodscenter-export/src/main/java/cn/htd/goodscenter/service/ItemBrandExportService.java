package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.BrandOfShopDTO;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.dto.indto.QueryBrandErpExceptionInDto;
import cn.htd.goodscenter.dto.indto.QueryItemBrandInDTO;

/**
 * 品牌接口
 */
public interface ItemBrandExportService {
	/**
	 * <p>
	 * Discription:[品牌添加]
	 * </p>
	 */
	public ExecuteResult<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO);

	/**
	 * <p>
	 * Discription:[品牌信息修改]
	 * </p>
	 */
	public ExecuteResult<ItemBrandDTO> modifyItemBrand(ItemBrandDTO itemBrandDTO);

	/**
	 * <p>
	 * Discription:[查询所有品牌列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryItemBrandAllList(Pager page);

	/**
	 *
	 * <p>
	 * Discription:[根据ID组查询品牌]
	 * </p>
	 */
	public ExecuteResult<ItemBrand> queryItemBrandById(Long id);

	/**
	 *
	 * <p>
	 * Discription:[根据ID组查询品牌]
	 * </p>
	 */
	public ExecuteResult<ItemBrand> queryItemBrandByName(String brandName);

	/**
	 * 
	 * <p>
	 * Discription:[根据ID组查询品牌]
	 * </p>
	 */
	public ExecuteResult<List<ItemBrandDTO>> queryItemBrandByIds(Long... ids);

	/**
	 * <p>
	 * Discription:[根据三级类目查询品牌列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandListByCategoryId(BrandOfShopDTO brandOfShopDTO);

	/**
	 * 
	 * <p>
	 * Discription:[批量添加品牌和类目关系]
	 * </p>
	 */
	public ExecuteResult<ItemBrandDTO> addCategoryBrandBatch(ItemBrandDTO brand);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandList(ItemBrandDTO itemBrandDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[删除类目品牌关系]
	 * </p>
	 */
	public ExecuteResult<String> deleteCategoryBrand(Long cid, Long brandId);

	/**
	 *
	 * <p>
	 * Discription:[根据品牌名称查询记录]
	 * </p>
	 */
	public DataGrid<ItemBrandDTO> queryByBrandName(String brandName);

	/**
	 * 删除品牌，之前的删除品牌有问题，只判断了商品下有没有品牌，并没有判断和类目关系
	 * @param itemBrandDTO
	 */
	public ExecuteResult<String> deleteBrand(ItemBrandDTO itemBrandDTO);
	
	/***
	 * 用于搜索引擎同步数据
	 * @param syncTime
	 * @return
	 */
	public List<ItemBrand> queryBrandBySyncTime(Date syncTime);

	/**
	 * 品牌下行ERP
	 * @param itemBrand 下行品牌对象
	 * @param isUpdateFlag 是否更新； 0 : 新增； 1：更新
	 */
	public ExecuteResult<String> downItemBrand2Erp(ItemBrand itemBrand, Integer isUpdateFlag);

	/**
	 * 查询下行erp未成功的数据。
	 * @param pager 分页信息
	 * @return
	 */
	public ExecuteResult<DataGrid<ItemBrand>> queryErpExceptionBrandList(Pager pager, QueryBrandErpExceptionInDto queryBrandErpExceptionInDto);

	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandList4SuperBoss(QueryItemBrandInDTO itemBrandDTO, Pager page);
}
