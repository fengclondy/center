package cn.htd.storecenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopBrandDAO;
import cn.htd.storecenter.dao.ShopCategoryDAO;
import cn.htd.storecenter.dao.ShopInfoDAO;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.dto.ShopCategoryDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopBrandExportService;

@Service("shopBrandExportService")
public class ShopBrandExportServiceImpl implements ShopBrandExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopBrandExportServiceImpl.class);

	@Resource
	private ShopBrandDAO shopBrandDAO;
	@Resource
	private ShopCategoryDAO shopCategoryDAO;
	@Resource
	private ShopInfoDAO shopInfoDAO;

	@Override
	public ExecuteResult<List<ShopBrandDTO>> queryShopBrandList(Long shopId, Integer status) {
		ExecuteResult<List<ShopBrandDTO>> result = new ExecuteResult<List<ShopBrandDTO>>();
		try {
			List<ShopBrandDTO> list = shopBrandDAO.selectByShopId(shopId, status);
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
	public ExecuteResult<String> addShopCategoryAndBrand(ShopBrandDTO shopBrandDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			ShopCategoryDTO queryCategoryDto = new ShopCategoryDTO();
			queryCategoryDto.setSellerId(shopBrandDTO.getSellerId());
			queryCategoryDto.setCid(shopBrandDTO.getCategoryId());
			queryCategoryDto.setStatus("2");
			List<ShopCategoryDTO> list = shopCategoryDAO.selectListByCondition(queryCategoryDto,null);
			if (list.size() == 0 || list == null){
				ShopDTO shopDTO = shopInfoDAO.selectBySellerId(shopBrandDTO.getSellerId());
				if(shopDTO != null){
					ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
					shopCategoryDTO.setCid(shopBrandDTO.getCategoryId());
					shopCategoryDTO.setStatus("2");
					shopCategoryDTO.setSellerId(shopBrandDTO.getSellerId());
					shopCategoryDTO.setCategoryLevel(3);
					shopCategoryDTO.setParentCid(shopBrandDTO.getParentCid());
					shopCategoryDTO.setShopId(shopDTO.getShopId());
					shopCategoryDTO.setComment("");
					shopCategoryDTO.setCreateId(shopBrandDTO.getCreateId());
					shopCategoryDTO.setCreateName(shopBrandDTO.getCreateName());
					shopCategoryDTO.setModifyId(shopBrandDTO.getModifyId());
					shopCategoryDTO.setModifyName(shopBrandDTO.getModifyName());
					shopCategoryDAO.insert(shopCategoryDTO);
				}
			}
			ShopBrandDTO queryBrandDto = new ShopBrandDTO();
			queryBrandDto.setSellerId(shopBrandDTO.getSellerId());
			queryBrandDto.setBrandId(shopBrandDTO.getBrandId());
			queryBrandDto.setCategoryId(shopBrandDTO.getCategoryId());
			queryBrandDto.setStatus("2");
			List<ShopBrandDTO> brandList = shopBrandDAO.selectListByConditionAll(queryBrandDto,null);
			if(brandList.size() == 0 || brandList == null){
				ShopDTO shopDTO = shopInfoDAO.selectBySellerId(shopBrandDTO.getSellerId());
				if(shopDTO != null){
					shopBrandDTO.setShopId(shopDTO.getShopId());
					shopBrandDTO.setStatus("2");
					shopBrandDAO.insert(shopBrandDTO);
				}
			}
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
	public ExecuteResult<DataGrid<ShopBrandDTO>> queryShopBrand(ShopBrandDTO shopBrandDTO, Pager<ShopBrandDTO> page) {
		ExecuteResult<DataGrid<ShopBrandDTO>> result = new ExecuteResult<DataGrid<ShopBrandDTO>>();

		try {
			DataGrid<ShopBrandDTO> dataGrid = new DataGrid<ShopBrandDTO>();
			List<ShopBrandDTO> list = shopBrandDAO.selectListByCondition(shopBrandDTO, page);
			Long count = shopBrandDAO.selectCountByCondition(shopBrandDTO);
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
	public ExecuteResult<String> modifyShopBrandStatus(ShopBrandDTO shopBrandDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			Integer count = shopBrandDAO.modifyShopCategoryStatus(shopBrandDTO);
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
	public ExecuteResult<DataGrid<ShopBrandDTO>> queryShopBrandAll(ShopBrandDTO shopBrandDTO, Pager<ShopBrandDTO> page) {
		ExecuteResult<DataGrid<ShopBrandDTO>> result = new ExecuteResult<DataGrid<ShopBrandDTO>>();
		logger.info("ShopBrandExportService【queryShopBrandAll】 入参{}："+shopBrandDTO.toString());
		try {
			DataGrid<ShopBrandDTO> dataGrid = new DataGrid<ShopBrandDTO>();
			List<ShopBrandDTO> list = shopBrandDAO.selectListByConditionAll(shopBrandDTO, page);
			Long counbt = shopBrandDAO.selectCountByConditionAll(shopBrandDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(counbt);
			result.setResult(dataGrid);
			result.setResultMessage("success");
			logger.info("ShopBrandExportService【queryShopBrandAll】 出参{}："+list.toString());
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public List<ShopBrandDTO> selectBrandIdById(Long id) {
		List<ShopBrandDTO> result = shopBrandDAO.selectBrandIdById(id);
		return result;
	}

	@Override
	public void updateStatusByIdAndBrandId(ShopBrandDTO dto) {
		shopBrandDAO.updateStatusByIdAndBrandId(dto);
	}

	@Override
	public Long queryStayShopBrandCount(ShopBrandDTO shopBrandDTO) {
		return shopBrandDAO.selectCountByCondition(shopBrandDTO);
	}

	// 保存运营人员调整的申请经营类目品牌信息
	// map中String为三级类目ID,List中为brandId
	public ExecuteResult<String> modifyShopBrands(Map<String, List<Long>> maps, ShopBrandDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			Set<String> keys = maps.keySet();
			if (keys != null && keys.size() > 0) {
				for (String cid : keys) {
					if (StringUtils.isBlank(cid)) {
						continue;
					}
					dto.setCategoryId(Long.valueOf(cid));
					dto.setStatus("1");
					// 根据shopid，sellerid，categoryId检索所有dto
					List<ShopBrandDTO> brandDTOList = shopBrandDAO.selectListByConditionAll(dto, null);
					if (brandDTOList != null && brandDTOList.size() > 0) {
						for (ShopBrandDTO brandDTO : brandDTOList) {
							shopBrandDAO.delete(brandDTO.getId());
						}
					}
					List<Long> brandlist = maps.get(cid);
					if (brandlist != null && brandlist.size() > 0) {
						for (Long brandId : brandlist) {
							dto.setBrandId(brandId);
							shopBrandDAO.insert(dto);
						}
					}
				}
			}
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	// 保存运营人员调整的申请经营类目品牌信息
	// map中String为三级类目ID,List中为brandId集合
	@Override
	public ExecuteResult<String> modifyShopBrandsByCondition(Map<String, List<Long>> maps, ShopBrandDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			List<Long> oldBrandList = null;
			List<Long> newBrandList = null;
			Set<String> cids = maps.keySet();
			if (cids != null && cids.size() > 0) {
				for (String cid : cids) {
					List<Long> brandIds = maps.get(cid);
					newBrandList = new ArrayList<Long>();
					for (Long brandId : brandIds) {
						newBrandList.add(brandId);
					}
					dto.setCategoryId(Long.valueOf(cid));
					dto.setStatus("1");
					// 根据shopid，sellerid，categoryId查询此商家此类目下所有品牌
					List<ShopBrandDTO> shopBrandDTOs = shopBrandDAO.selectListByConditionAll(dto, null);
					if (shopBrandDTOs != null && shopBrandDTOs.size() > 0) {
						oldBrandList = new ArrayList<Long>();
						for (ShopBrandDTO shopBrandDTO : shopBrandDTOs) {
							oldBrandList.add(shopBrandDTO.getBrandId());
						}
						for (Long oldBrandId : oldBrandList) {
							for (Long newBrandId : newBrandList) {
								if (oldBrandId == newBrandId) {
									oldBrandList.remove(oldBrandId);
									newBrandList.remove(newBrandId);
								}
							}
						}
					}
					if (oldBrandList != null && oldBrandList.size() > 0) {
						for (Long brandId : oldBrandList) {
							dto.setBrandId(brandId);
							List<ShopBrandDTO> delShopBrandDTO = shopBrandDAO.selectListByConditionAll(dto, null);
							shopBrandDAO.delete(delShopBrandDTO.get(0).getId());
						}
					}
					if (newBrandList != null && newBrandList.size() > 0) {
						for (Long brandId : newBrandList) {
							dto.setBrandId(brandId);
							shopBrandDAO.insert(dto);
						}
					}
				}
			}
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
	public ExecuteResult<String> saveAllBrand(List<ShopBrandDTO> list) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			if(list != null && list.size() > 0){
				for(ShopBrandDTO shopBrandDTO : list){
					if (shopBrandDTO.getBrandIds() != null && shopBrandDTO.getBrandIds().size() > 0){
						for(Long brandId : shopBrandDTO.getBrandIds()){
							shopBrandDTO.setBrandId(brandId);
							shopBrandDTO.setStatus("2");
							shopBrandDAO.insert(shopBrandDTO);
						}
					}
				}
				result.setResultMessage("success");
			}
		}catch (Exception e){
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> addBrand(ShopBrandDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			shopBrandDAO.insert(dto);
			result.setResultMessage("success");
		}catch (Exception e){
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}

		return result;
	}

	@Override
	public ExecuteResult<String> delBrandByShopId(long shopId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			shopBrandDAO.deleteByShopId(shopId);
			result.setResultMessage("success");
		}catch (Exception e){
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.error(e.getMessage());
		}

		return result;
	}
}
