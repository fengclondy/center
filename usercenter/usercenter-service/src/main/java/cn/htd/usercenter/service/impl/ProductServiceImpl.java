package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dao.ProductDAO;
import cn.htd.usercenter.dto.ProductDTO;
import cn.htd.usercenter.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Resource
	private ProductDAO productDao;

	@Override
	public ExecuteResult<Boolean> addProduct(ProductDTO product, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = product.getProductId();
			String name = product.getName();
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)) {
				product.setDeletedFlag(GlobalConstant.DELETED_FLAG_NO);
				productDao.addProduct(product, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("保存成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写产品编码和产品名称信息！");
			}
		} catch (Exception e) {
			logger.error("ProductExportServiceImpl----->addProduct=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<ProductDTO>> queryProductByIdAndName(String productId, String name, int page,
			int rows) {
		ExecuteResult<DataGrid<ProductDTO>> rs = new ExecuteResult<DataGrid<ProductDTO>>();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		try {
			DataGrid<ProductDTO> dg = new DataGrid<ProductDTO>();
			List<ProductDTO> productList = productDao.queryProductByIdAndName(productId, name, pager);
			long count = productDao.queryProductByIdAndNameCount(productId, name);
			try {
				if (productList != null) {
					dg.setRows(productList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("ProductExportServiceImpl----->queryProductByIdAndName=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateProduct(ProductDTO product, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = product.getProductId();
			String name = product.getName();
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)) {
				productDao.updateProduct(product, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写产品名称！");
			}
		} catch (Exception e) {
			logger.error("ProductExportServiceImpl----->updateProduct=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public List<ProductDTO> queryProductList(String productId, String productName) {
		List<ProductDTO> productList = productDao.queryProductByIdAndName(productId, productName, new Pager());
		return productList;
	}

	@Override
	public ExecuteResult<Boolean> deleteProduct(ProductDTO product, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = product.getProductId();
			product.setDeletedFlag(GlobalConstant.DELETED_FLAG_YES);
			if (StringUtils.isNotBlank(productId)) {
				int count = productDao.queryCount4ProductUsedByRole(product);
				if (count == 0) {
					productDao.deleteProduct(product, userId);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("删除成功！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该产品已经绑定角色，请先删除对应角色信息");
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请选择要删除的产品");
			}
		} catch (Exception e) {
			logger.error("ProductExportServiceImpl----->deleteProduct=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ProductDTO queryProductById(String productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	public boolean checkProductId(ProductDTO product) {
		boolean flag = false;
		int count = productDao.checkProductId(product);
		if (count == 0) {
			flag = true;
		}
		return flag;
	}

}
