package cn.htd.storecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopCategorySellerDTO;
import cn.htd.storecenter.dto.ShopCategorySellerQueryDTO;

public interface ShopCategorySellerExportService {

	/**
	 * <p>
	 * Discription:[店铺自定义分类修改]
	 * </p>
	 */
	public ExecuteResult<String> update(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[店铺自定义分类列表查询]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopCategorySellerDTO>> queryShopCategoryList(ShopCategorySellerDTO dto, Pager<ShopCategorySellerDTO> pager);

	/**
	 * <p>
	 * Discription:[店铺自定义分类单个删除]
	 * </p>
	 */
	public ExecuteResult<String> deleteById(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[店铺自定义分类添加]
	 * </p>
	 */
	public ExecuteResult<Long> addShopCategory(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[店铺自定义分类全部删除]
	 * </p>
	 */
	public ExecuteResult<String> deletes(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件查询店铺类目名称]
	 * </p>
	 */
	public String selectNameByCondition(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[根据条件判断店铺类目是否存在]
	 * </p>
	 */
	public Boolean isExist(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[根据cid修改删除标志]
	 * </p>
	 */
	public ExecuteResult<String> delete(ShopCategorySellerDTO dto);

	/**
	 * <p>
	 * Discription:[根据shopId查询所有子类目]
	 * </p>
	 */
	public DataGrid<ShopCategorySellerDTO> queryChildCategoryByShopId(ShopCategorySellerDTO dto,Integer count);


	/**
	 * <p>
	 * Discription:[根据条件查询或者添加类目]
	 * </p>
	 */
	public ExecuteResult<ShopCategorySellerQueryDTO> addOrQueryByCondition(ShopCategorySellerQueryDTO dto);


	public ExecuteResult<ShopCategorySellerDTO> queryParentCname(Long cid);

}
