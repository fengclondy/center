package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopBrandCategoryDAO;
import cn.htd.storecenter.dto.ShopBrandCategoryDTO;
import cn.htd.storecenter.service.ShopBrandCategoryService;

@Service("shopBrandCategoryService")
public class ShopBrandCategoryServiceImpl implements ShopBrandCategoryService {
	private final static Logger logger = LoggerFactory.getLogger(ShopBrandCategoryServiceImpl.class);

	@Resource
	private ShopBrandCategoryDAO shopBrandCategoryDAO;

	@Override
	public ExecuteResult<DataGrid<Long>> queryShopIdList(ShopBrandCategoryDTO shopBrandCategoryDTO) {
		DataGrid<Long> lon = new DataGrid<Long>();
		ExecuteResult<DataGrid<Long>> result = new ExecuteResult<DataGrid<Long>>();
		try {
			List<Long> list = shopBrandCategoryDAO.selectByBrandIdCategoryId(shopBrandCategoryDTO);
			lon.setRows(list);
			result.setResult(lon);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

}
