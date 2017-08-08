package cn.htd.storecenter.service;

import java.util.List;
import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopBrandDTO;

/**
 * 
 * <p>
 * Description: [店铺品牌]
 * </p>
 */
public interface ShopBrandExportService {
	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID 状态查询 店铺品牌]
	 * </p>
	 */
	public ExecuteResult<List<ShopBrandDTO>> queryShopBrandList(Long shopId, Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[添加店铺品牌]
	 * </p>
	 */
	public ExecuteResult<String> addShopCategoryAndBrand(ShopBrandDTO shopBrandDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺品牌店铺运行状态为开通]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopBrandDTO>> queryShopBrand(ShopBrandDTO shopBrandDTO, Pager<ShopBrandDTO> page);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺品牌所有]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopBrandDTO>> queryShopBrandAll(ShopBrandDTO shopBrandDTO, Pager<ShopBrandDTO> page);

	/**
	 * 
	 * <p>
	 * Discription:[店铺品牌状态修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopBrandStatus(ShopBrandDTO shopBrandDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据id查找品牌所有信息]
	 * </p>
	 */
	public List<ShopBrandDTO> selectBrandIdById(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[根据BrandId修改曾经被驳回的品牌的status的值为5]
	 * </p>
	 */
	public void updateStatusByIdAndBrandId(ShopBrandDTO dto);

	/**
	 * 
	 * <p>
	 * Discription:[统计 根据实体不同的状态记录数量]
	 * </p>
	 */
	public Long queryStayShopBrandCount(ShopBrandDTO shopBrandDTO);

	/**
	 *
	 * <p>
	 * Discription:[保存运营人员调整的申请经营类目品牌信息]
	 *
	 * </p>
	 */
	public ExecuteResult<String> modifyShopBrandsByCondition(Map<String, List<Long>> maps, ShopBrandDTO dto);


	ExecuteResult<String> saveAllBrand(List<ShopBrandDTO> list);

	ExecuteResult<String> addBrand(ShopBrandDTO dto);

	ExecuteResult<String> delBrandByShopId(long shopId);
}
