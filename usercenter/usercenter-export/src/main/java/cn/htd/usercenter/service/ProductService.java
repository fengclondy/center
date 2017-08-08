package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.ProductDTO;

public interface ProductService {

	/**
	 * 添加产品信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<Boolean> addProduct(ProductDTO product, String userId);

	/**
	 * 根据产品编码和产品名称查询产品信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<ProductDTO>> queryProductByIdAndName(String productId, String name, int page,
			int rows);

	/**
	 * 根据产品编码和产品名称查询产品信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ProductDTO queryProductById(String productId);

	/**
	 * 查询产品信息生成下拉框
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public List<ProductDTO> queryProductList(String productId, String productName);

	/**
	 * 根据产品编码更新产品信息
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> updateProduct(ProductDTO product, String userId);

	/**
	 * 根据产品编码删除产品信息
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> deleteProduct(ProductDTO product, String userId);

	/**
	 * 校验产品编码是否使用
	 * 
	 * @param product
	 * @return
	 */
	public boolean checkProductId(ProductDTO product);
}
