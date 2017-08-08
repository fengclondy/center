package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopCategoryDTO;

/**
 * 
 * <p>
 * Description: [店铺经营类目]
 * </p>
 */
public interface ShopCategoryExportService {
	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID查询店铺经营类目]
	 * </p>
	 */
	public ExecuteResult<List<ShopCategoryDTO>> queryShopCategoryList(Long shopId, Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[添加平台经营类目]
	 * </p>
	 */
	public ExecuteResult<String> addShopCategory(List<ShopCategoryDTO> list);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺类目 店铺运行状态未开通]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopCategoryDTO>> queryShopCategory(ShopCategoryDTO shopCategoryDTO, Pager<ShopCategoryDTO> page);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺类目所有]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopCategoryDTO>> queryShopCategoryAll(ShopCategoryDTO shopCategoryDTO, Pager<ShopCategoryDTO> page);

	/**
	 * 
	 * <p>
	 * Discription:[店铺经营类目状态修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopCategoryStatus(ShopCategoryDTO shopCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据id查找类目所有信息]
	 * </p>
	 */
	public List<ShopCategoryDTO> selectShopIdById(Long Id);

	/**
	 * 
	 * <p>
	 * Discription:[根据shopId修改曾经被驳回的类目的status的值为-1]
	 * </p>
	 */
	public void updateStatusByIdAndShopId(ShopCategoryDTO dto);

	/**
	 * @Title: getShopCategory
	 * @Description: 根据卖家ID查询对应平台三级类目
	 * @return 设定文件
	 * @return ShopCategoryDTO 返回类型
	 */
	public List<ShopCategoryDTO> getShopCategoryBysellerId(Long sellerId);

	/**
	 * 
	 * <p>
	 * Discription:[统计 查询不同条件下的商品类目数量]
	 * </p>
	 * 
	 * @param shopCategoryDTO
	 */
	public Long queryStayShopCateorCount(ShopCategoryDTO shopCategoryDTO);

	/**
	 *
	 * <p>
	 * Discription:[根据shopId删除店铺类目]
	 * </p>
	 *
	 * @param shopCategoryDTO
	 */
	public ExecuteResult<String> deleteByShopId(ShopCategoryDTO shopCategoryDTO);
}
