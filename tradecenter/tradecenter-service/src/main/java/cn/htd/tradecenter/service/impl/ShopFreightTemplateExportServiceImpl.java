package cn.htd.tradecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dao.ShopFreightTemplateDAO;
import cn.htd.tradecenter.dto.ShopDeliveryTypeDTO;
import cn.htd.tradecenter.dto.ShopFreightTemplateDTO;
import cn.htd.tradecenter.dto.ShopPreferentialWayDTO;
import cn.htd.tradecenter.service.ShopDeliveryTypeExportService;
import cn.htd.tradecenter.service.ShopFreightTemplateExportService;
import cn.htd.tradecenter.service.ShopPreferentialWayExportService;

@Service("shopFreightTemplateExportService")
public class ShopFreightTemplateExportServiceImpl implements ShopFreightTemplateExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopFreightTemplateExportServiceImpl.class);

	@Resource
	private ShopFreightTemplateDAO shopFreightTemplateDAO;
	@Resource
	private ShopDeliveryTypeExportService shopDeliveryTypeExportService;
	@Resource
	private ShopPreferentialWayExportService shopPreferentialWayExportService;

	@Override
	public ExecuteResult<ShopFreightTemplateDTO> update(ShopFreightTemplateDTO dto) {
		ExecuteResult<ShopFreightTemplateDTO> er = new ExecuteResult<ShopFreightTemplateDTO>();
		try {
			int count = shopFreightTemplateDAO.update(dto);
			if (count > 0) {
				er.setResultMessage("修改成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			er.setResultMessage("修改成功！");
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<DataGrid<ShopFreightTemplateDTO>> queryShopFreightTemplateList(ShopFreightTemplateDTO dto,
																						Pager<ShopFreightTemplateDTO> pager) {
		ExecuteResult<DataGrid<ShopFreightTemplateDTO>> result = new ExecuteResult<DataGrid<ShopFreightTemplateDTO>>();

		try {
			DataGrid<ShopFreightTemplateDTO> dataGrid = new DataGrid<ShopFreightTemplateDTO>();
			List<ShopFreightTemplateDTO> list = shopFreightTemplateDAO.queryList(dto, pager);
			Long counbt = shopFreightTemplateDAO.queryCount(dto);
			dataGrid.setRows(list);
			dataGrid.setTotal(counbt);
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
	public ExecuteResult<String> deleteShopFreightTemplateById(long id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (shopFreightTemplateDAO.delete(id) > 0) {
				er.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			er.setResultMessage("删除失败！");
			throw new RuntimeException();
		}
		return er;
	}

	@Override
	public ExecuteResult<ShopFreightTemplateDTO> addShopFreightTemplate(ShopFreightTemplateDTO dto) {
		ExecuteResult<ShopFreightTemplateDTO> er = new ExecuteResult<ShopFreightTemplateDTO>();
		try {
			shopFreightTemplateDAO.add(dto);
			er.setResult(dto);
			er.setResultMessage("添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			er.setResultMessage("添加失败！");
			throw new RuntimeException();
		}
		return er;
	}

	//当选择为自定义模板的时候用下方法
	@Override
	public ExecuteResult<ShopFreightTemplateDTO> addShopFreightTemplateForSeller(ShopFreightTemplateDTO dto) {
		ExecuteResult<ShopFreightTemplateDTO> er = new ExecuteResult<ShopFreightTemplateDTO>();
		try {
			shopFreightTemplateDAO.addForSeller(dto);
			er.setResult(dto);
			er.setResultMessage("添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			er.setResultMessage("添加失败！");
			throw new RuntimeException();
		}
		return er;
	}
	@Override
	public ShopFreightTemplateDTO queryShopFreightTemplateById(Long id) {
		ShopFreightTemplateDTO shopFreightTemplateDTO = new ShopFreightTemplateDTO();
		try {
			shopFreightTemplateDTO = shopFreightTemplateDAO.queryById(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		}
		return shopFreightTemplateDTO;
	}

	@Override
	public ExecuteResult<ShopFreightTemplateDTO> queryById(Long shopFreightTemplateId) {
		logger.info("\n 方法[{}]，入参：[{}]", "ShopFreightTemplateServiceImpl-queryById", shopFreightTemplateId);
		ExecuteResult<ShopFreightTemplateDTO> executeResult = new ExecuteResult<ShopFreightTemplateDTO>();
		try {
			ShopFreightTemplateDTO shopFreightTemplateDTO = shopFreightTemplateDAO.queryById(shopFreightTemplateId);
			if (shopFreightTemplateDTO != null) {
				if (shopFreightTemplateDTO.getPostageFree() == 1) {// 自定义运费
					ShopDeliveryTypeDTO shopDeliveryTypeDTO = new ShopDeliveryTypeDTO();
					shopDeliveryTypeDTO.setTemplateId(shopFreightTemplateDTO.getId());
					shopDeliveryTypeDTO.setDeleteFlag(Byte.valueOf("0"));// 未删除
					ExecuteResult<List<ShopDeliveryTypeDTO>> deliveryTypeResult = shopDeliveryTypeExportService
							.queryShopDeliveryType(shopDeliveryTypeDTO);
					if (deliveryTypeResult.isSuccess() && deliveryTypeResult.getResult() != null
							&& deliveryTypeResult.getResult().size() > 0) {
						shopFreightTemplateDTO.setShopDeliveryTypeList(deliveryTypeResult.getResult());
					}
				}
				// 查询优惠
				ShopPreferentialWayDTO shopPreferentialWayDTO = new ShopPreferentialWayDTO();
				shopPreferentialWayDTO.setTemplateId(shopFreightTemplateDTO.getId());
				shopPreferentialWayDTO.setDeleteFlag(Byte.valueOf("0"));// 未删除
				ExecuteResult<List<ShopPreferentialWayDTO>> preferentialWayResult = shopPreferentialWayExportService
						.queryShopPreferentialWay(shopPreferentialWayDTO);
				if (preferentialWayResult.isSuccess() && preferentialWayResult.getResult() != null
						&& preferentialWayResult.getResult().size() > 0) {
					shopFreightTemplateDTO.setShopPreferentialWayList(preferentialWayResult.getResult());
				}
			}
			executeResult.setResult(shopFreightTemplateDTO);
		} catch (Exception e) {
			executeResult.addErrorMessage(e.getMessage());
			logger.info("\n 方法[{}]，异常：[{}]", "ShopFreightTemplateServiceImpl-queryById", e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<ShopFreightTemplateDTO> queryByRegionIdAndTemplateId(Long regionId,
																			  Long shopFreightTemplateId) {
		logger.info("\n 方法[{}]，入参：[{},{}]", "ShopFreightTemplateServiceImpl-queryByRegionIdAndTemplateId", regionId,
				shopFreightTemplateId);
		ExecuteResult<ShopFreightTemplateDTO> executeResult = new ExecuteResult<ShopFreightTemplateDTO>();
		ShopFreightTemplateDTO templateDTO = null;
		try {
			ExecuteResult<ShopFreightTemplateDTO> shopFreightTemplateExecuteResult = this
					.queryById(shopFreightTemplateId);
			if (shopFreightTemplateExecuteResult.isSuccess() && shopFreightTemplateExecuteResult.getResult() != null) {
				templateDTO = shopFreightTemplateExecuteResult.getResult();
				if (templateDTO.getPostageFree() == 1) {// 买家承担运费
					// 查询运费策略
					ExecuteResult<List<ShopDeliveryTypeDTO>> shopDeliveryTypeExecuteResult = shopDeliveryTypeExportService
							.queryByRegionIdAndTemplateId(regionId, templateDTO.getId());
					if (shopDeliveryTypeExecuteResult.isSuccess() && shopDeliveryTypeExecuteResult.getResult() != null
							&& shopDeliveryTypeExecuteResult.getResult().size() > 0) {
						List<ShopDeliveryTypeDTO> deliveryTypeDTOs = shopDeliveryTypeExecuteResult.getResult();
						templateDTO.setShopDeliveryTypeList(deliveryTypeDTOs);
					}
					// =================优惠计算开始=================
					ShopPreferentialWayDTO inDTO = new ShopPreferentialWayDTO();
					inDTO.setTemplateId(templateDTO.getId());
					inDTO.setDeleteFlag(Byte.valueOf("0"));// 未删除
					// 查询优惠策略
					ExecuteResult<List<ShopPreferentialWayDTO>> shopPreferentialWayResult = shopPreferentialWayExportService
							.queryShopPreferentialWay(inDTO);
					templateDTO.setShopPreferentialWayList(shopPreferentialWayResult.getResult());
				}
				executeResult.setResult(templateDTO);
			}
		} catch (Exception e) {
			executeResult.addErrorMessage(e.getMessage());
			logger.info("\n 方法[{}]，异常：[{}]", "ShopFreightTemplateServiceImpl-queryByRegionIdAndTemplateId",
					e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<ShopFreightTemplateDTO> copy(Long shopFreightTemplateId) {
		ExecuteResult<ShopFreightTemplateDTO> result = new ExecuteResult<ShopFreightTemplateDTO>();
		logger.info("\n 方法[{}]，入参：[{}]", "ShopFreightTemplateServiceImpl-copy", shopFreightTemplateId);
		try {
			// 查询运费模版
			ExecuteResult<ShopFreightTemplateDTO> queryResult = this.queryById(shopFreightTemplateId);
			if (queryResult.isSuccess() && queryResult.getResult() != null) {
				ShopFreightTemplateDTO oldShopFreightTemplateDTO = queryResult.getResult();
				// 复制模版
				ShopFreightTemplateDTO newShopFreightTemplateDTO = new ShopFreightTemplateDTO();
				BeanUtils.copyProperties(oldShopFreightTemplateDTO, newShopFreightTemplateDTO);
				newShopFreightTemplateDTO.setId(null);
				newShopFreightTemplateDTO.setCreateTime(new Date());
				//newShopFreightTemplateDTO.setUpdateTime(new Date());
				newShopFreightTemplateDTO.setShopDeliveryTypeList(null);
				newShopFreightTemplateDTO.setShopPreferentialWayList(null);
				ExecuteResult<ShopFreightTemplateDTO> er = this.addShopFreightTemplate(newShopFreightTemplateDTO);
				if (er.isSuccess()) {
					if (oldShopFreightTemplateDTO.getShopDeliveryTypeList() != null
							&& oldShopFreightTemplateDTO.getShopDeliveryTypeList().size() > 0) {
						List<ShopDeliveryTypeDTO> deliveryTypeDTOs = new ArrayList<ShopDeliveryTypeDTO>();
						// 复制运送方式
						for (ShopDeliveryTypeDTO oldDeliveryTypeDTO : oldShopFreightTemplateDTO
								.getShopDeliveryTypeList()) {
							ShopDeliveryTypeDTO newShopDeliveryTypeDTO = new ShopDeliveryTypeDTO();
							BeanUtils.copyProperties(oldDeliveryTypeDTO, newShopDeliveryTypeDTO);
							newShopDeliveryTypeDTO.setId(null);
							newShopDeliveryTypeDTO.setTemplateId(newShopFreightTemplateDTO.getId());
							newShopDeliveryTypeDTO.setCreateTime(new Date());
							newShopDeliveryTypeDTO.setUpdateTime(new Date());
							ExecuteResult<String> executeResult = shopDeliveryTypeExportService
									.addShopDeliveryType(newShopDeliveryTypeDTO);
							if (executeResult.isSuccess()) {
								deliveryTypeDTOs.add(newShopDeliveryTypeDTO);
							} else {
								result.getErrorMessages().addAll(er.getErrorMessages());
								break;
							}
						}
						newShopFreightTemplateDTO.setShopDeliveryTypeList(deliveryTypeDTOs);
					}
					if (oldShopFreightTemplateDTO.getShopPreferentialWayList() != null
							&& oldShopFreightTemplateDTO.getShopPreferentialWayList().size() > 0) {
						List<ShopPreferentialWayDTO> preferentialWayDTOs = new ArrayList<ShopPreferentialWayDTO>();
						// 复制优惠方式
						for (ShopPreferentialWayDTO oldPreferentialWayDTO : oldShopFreightTemplateDTO
								.getShopPreferentialWayList()) {
							ShopPreferentialWayDTO newShopPreferentialWayDTO = new ShopPreferentialWayDTO();
							BeanUtils.copyProperties(oldPreferentialWayDTO, newShopPreferentialWayDTO);
							newShopPreferentialWayDTO.setId(null);
							newShopPreferentialWayDTO.setTemplateId(newShopFreightTemplateDTO.getId());
							newShopPreferentialWayDTO.setCreateTime(new Date());
							newShopPreferentialWayDTO.setModifyTime(new Date());
							ExecuteResult<String> executeResult = shopPreferentialWayExportService
									.addShopPreferentialWay(newShopPreferentialWayDTO);
							if (executeResult.isSuccess()) {
								preferentialWayDTOs.add(newShopPreferentialWayDTO);
							} else {
								result.getErrorMessages().addAll(er.getErrorMessages());
								break;
							}
						}
						newShopFreightTemplateDTO.setShopPreferentialWayList(preferentialWayDTOs);
					}
					result.setResult(newShopFreightTemplateDTO);
				} else {
					result.getErrorMessages().addAll(er.getErrorMessages());
				}
			}
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.info("\n 方法[{}]，异常：[{}]", "ShopFreightTemplateServiceImpl-copy", e.getMessage());
		}

		return result;
	}
}