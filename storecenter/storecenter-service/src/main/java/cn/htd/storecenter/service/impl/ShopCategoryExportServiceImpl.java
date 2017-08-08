package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopCategoryDAO;
import cn.htd.storecenter.dto.ShopCategoryDTO;
import cn.htd.storecenter.service.ShopCategoryExportService;

@Service("shopCategoryExportService")
public class ShopCategoryExportServiceImpl implements ShopCategoryExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopCategoryExportServiceImpl.class);

	@Resource
	private ShopCategoryDAO shopCategoryDAO;

	@Override
	public ExecuteResult<List<ShopCategoryDTO>> queryShopCategoryList(Long shopId, Integer status) {
		ExecuteResult<List<ShopCategoryDTO>> result = new ExecuteResult<List<ShopCategoryDTO>>();
		try {
			List<ShopCategoryDTO> list = shopCategoryDAO.selectByShopId(shopId, status);
			result.setResult(list);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> addShopCategory(List<ShopCategoryDTO> list) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if(list != null && list.size() > 0){
				for(ShopCategoryDTO shopCategoryDTO : list){
					shopCategoryDTO.setStatus("2");
					shopCategoryDAO.insert(shopCategoryDTO);
				}
				result.setResultMessage("success");
			}
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ShopCategoryDTO>> queryShopCategory(ShopCategoryDTO shopCategoryDTO, Pager<ShopCategoryDTO> page) {
		ExecuteResult<DataGrid<ShopCategoryDTO>> result = new ExecuteResult<DataGrid<ShopCategoryDTO>>();

		try {
			DataGrid<ShopCategoryDTO> dataGrid = new DataGrid<ShopCategoryDTO>();
			List<ShopCategoryDTO> list = shopCategoryDAO.selectListByCondition(shopCategoryDTO, page);
			Long count = shopCategoryDAO.selectCountByCondition(shopCategoryDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ShopCategoryDTO>> queryShopCategoryAll(ShopCategoryDTO shopCategoryDTO, Pager<ShopCategoryDTO> page) {
		ExecuteResult<DataGrid<ShopCategoryDTO>> result = new ExecuteResult<DataGrid<ShopCategoryDTO>>();
			logger.info("ShopCategoryyExportService【queryShopCategoryAll】 入参{}："+shopCategoryDTO.toString());
		try {
			DataGrid<ShopCategoryDTO> dataGrid = new DataGrid<ShopCategoryDTO>();
			List<ShopCategoryDTO> list = shopCategoryDAO.selectListByConditionAll(shopCategoryDTO, page);
			Long counbt = shopCategoryDAO.selectCountByConditionAll(shopCategoryDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(counbt);
			result.setResult(dataGrid);
			result.setResultMessage("success");
			logger.info("ShopCategoryyExportService【queryShopCategoryAll】 出参{}："+list.toString());
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopCategoryStatus(ShopCategoryDTO shopCategoryDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		try {
			Integer count = shopCategoryDAO.modifyShopCategoryStatus(shopCategoryDTO);
			result.setResult(count.toString());
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public List<ShopCategoryDTO> selectShopIdById(Long id) {
		List<ShopCategoryDTO> result = shopCategoryDAO.selectShopIdById(id);
		return result;
	}

	@Override
	public void updateStatusByIdAndShopId(ShopCategoryDTO dto) {
		shopCategoryDAO.updateStatusByIdAndShopId(dto);

	}

	/**
	 * 查询卖家对应的平台类目
	 */
	@Override
	public List<ShopCategoryDTO> getShopCategoryBysellerId(Long sellerId) {
		return shopCategoryDAO.getShopCategoryBysellerId(sellerId);
	}

	@Override
	public Long queryStayShopCateorCount(ShopCategoryDTO shopCategoryDTO) {
		return shopCategoryDAO.selectCountByCondition(shopCategoryDTO);
	}


	@Override
	public ExecuteResult<String> deleteByShopId(ShopCategoryDTO shopCategoryDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			shopCategoryDAO.deleteByShopId(shopCategoryDTO);
			result.setResultMessage("success");
		}catch(Exception e){
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

}
