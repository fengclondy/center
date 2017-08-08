package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.ProductDTO;

public interface ProductDAO extends BaseDAO<ProductDTO> {

	public void addProduct(@Param("product") ProductDTO product, @Param("userId") String userId);

	public void updateProduct(@Param("product") ProductDTO product, @Param("userId") String userId);

	public List<ProductDTO> queryProductByIdAndName(@Param("productId") String productId, @Param("name") String name,
			@Param("pager") Pager pager);

	public ProductDTO queryProductById(@Param("productId") String productId);

	public long queryProductByIdAndNameCount(@Param("productId") String productId, @Param("name") String name);

	public int queryCount4ProductUsedByRole(@Param("product") ProductDTO product);

	public void deleteProduct(@Param("product") ProductDTO product, @Param("userId") String userId);

	public int checkProductId(@Param("product") ProductDTO product);

}
