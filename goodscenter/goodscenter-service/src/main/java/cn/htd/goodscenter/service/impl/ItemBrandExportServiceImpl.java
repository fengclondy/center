package cn.htd.goodscenter.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.goodscenter.dto.indto.QueryBrandErpExceptionInDto;
import cn.htd.goodscenter.dto.indto.QueryItemBrandInDTO;
import cn.htd.middleware.common.message.erp.BrandMessage;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.HZSM;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemCategoryBrandDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.domain.ItemCategoryBrand;
import cn.htd.goodscenter.dto.BrandOfShopDTO;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 品牌接口实现
 *  @author chenakng
 */
@Service("itemBrandExportService")
public class ItemBrandExportServiceImpl implements ItemBrandExportService {
	@Resource
	private ItemBrandDAO itemBrandDAO;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemCategoryBrandDAO itemCategoryBrandDAO;
	@Autowired
	private AmqpTemplate amqpTemplate;
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ItemBrandExportServiceImpl.class);

	/**
	 * 添加品牌信息
	 *
	 * @param itemBrandDTO 品牌信息
	 * @return 添加结果，品牌ID
	 * @support
	 */
	@Transactional
	@Override
	public ExecuteResult<ItemBrandDTO> addItemBrand(ItemBrandDTO itemBrandDTO) {
		ExecuteResult<ItemBrandDTO> result = new ExecuteResult();
		try {
			// 校验入参
			if (itemBrandDTO.getBrandLogoUrl() == null || itemBrandDTO.getBrandLogoUrl().trim().equals("")) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("BrandLogoUrl"));
				return result;
			}
			if (itemBrandDTO.getThirdLevCid() == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ThirdLevCid"));
				return result;
			}
			// 校验同一类别下不能存在重名的品牌
			itemBrandDTO.setBrandName(StringUtils.trim(itemBrandDTO.getBrandName()));
			ItemCategoryBrand itemCategoryBrand = this.itemCategoryBrandDAO.queryCategoryBrandByBrandName(itemBrandDTO);
			if (itemCategoryBrand != null) {
				result.addErrorMessage("品牌名称重复不能添加！");
				return result;
			}
			// 添加品牌、和品类关系
			// 添加品牌表
			List<ItemBrandDTO> list = itemBrandDAO.selectByBrandName(StringUtils.trim(itemBrandDTO.getBrandName()));
			if (list != null && list.size() > 0) {
				result.addErrorMessage("品牌名称重复不能添加！");
				return result;
			}
			ItemBrand itemBrand = new ItemBrand();
			itemBrand.setBrandLogoUrl(itemBrandDTO.getBrandLogoUrl());
			itemBrand.setBrandName(itemBrandDTO.getBrandName());
			itemBrand.setBrandKey(HZSM.getWordStart(itemBrandDTO.getBrandName()));
			itemBrand.setBrandStatus(1);
			itemBrand.setErpStatus(ItemErpStatusEnum.WAITING.getCode());
			itemBrand.setCreateId(itemBrandDTO.getCreateId());
			itemBrand.setCreateName(itemBrandDTO.getCreateName());
			itemBrand.setCreateTime(new Date());
			itemBrandDAO.add(itemBrand);
			// 添加类目品牌关系
			ItemCategoryBrand itemCategoryBrandParam = new ItemCategoryBrand();
			itemCategoryBrandParam.setThirdLevCid(itemBrandDTO.getThirdLevCid());
			itemCategoryBrandParam.setBrandId(itemBrand.getBrandId()); // 品牌ID
			itemCategoryBrandParam.setCreateId(itemBrandDTO.getCreateId());
			itemCategoryBrandParam.setCreateName(itemBrandDTO.getCreateName());
			List<ItemCategoryBrand> dbIcb = this.itemCategoryBrandDAO.queryCategoryBrandByBrandId(itemCategoryBrandParam);
			if (dbIcb == null && dbIcb.size() > 0) {
				itemCategoryBrandDAO.add(itemCategoryBrandParam);
			}
			ExecuteResult downExecute = this.downItemBrand2Erp(itemBrand, 0);
			if (!downExecute.isSuccess()) {
				throw new Exception("品牌下行出现异常, 错误信息：" + downExecute.getErrorMessages());
			}
			// 返回品牌ID
			itemBrandDTO.setBrandId(itemBrand.getBrandId());
			result.setResult(itemBrandDTO);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<String> downItemBrand2Erp(ItemBrand itemBrand, Integer isUpdateFlag) {
		logger.info("品牌开始下行ERP, 下行信息 : {}, 是否更新 : {}", JSONObject.fromObject(itemBrand), isUpdateFlag);
		ExecuteResult<String> executeResult = new ExecuteResult<>();
		try {
			if (itemBrand == null) {
				executeResult.addErrorMessage("itemBrand is null");
				return executeResult;
			}
			if (itemBrand.getBrandId() == null) {
				executeResult.addErrorMessage("BrandId is null");
				return executeResult;
			}
			if (StringUtils.isEmpty(itemBrand.getBrandName())) {
				executeResult.addErrorMessage("BrandName is null");
				return executeResult;
			}
			if (itemBrand.getBrandStatus() == null) {
				executeResult.addErrorMessage("BrandStatus is null");
				return executeResult;
			}
			if (StringUtils.isEmpty(itemBrand.getBrandLogoUrl())) {
				executeResult.addErrorMessage("BrandLogoUrl is null");
				return executeResult;
			}
			// 更新为erp下行中
			ItemBrand brandUpdate = new ItemBrand();
			brandUpdate.setBrandId(itemBrand.getBrandId());
			brandUpdate.setBrandName(itemBrand.getBrandName());
			brandUpdate.setBrandStatus(itemBrand.getBrandStatus());
			brandUpdate.setBrandLogoUrl(itemBrand.getBrandLogoUrl());
			brandUpdate.setErpStatus(ItemErpStatusEnum.DOWNING.getCode());
			brandUpdate.setErpDownTime(new Date());
			brandUpdate.setModifyId(Constants.SYSTEM_CREATE_ID);
			brandUpdate.setModifyName(Constants.SYSTEM_CREATE_NAME);
			brandUpdate.setModifyTime(new Date());
			this.itemBrandDAO.update(brandUpdate);
			// 下行
			BrandMessage brandMessage = new BrandMessage();
			brandMessage.setBrandCode(String.valueOf(itemBrand.getBrandId()));
			brandMessage.setBrandName(itemBrand.getBrandName());
			brandMessage.setIsUpdateFlag(isUpdateFlag);
			brandMessage.setValidTags(itemBrand.getBrandStatus()); // 有效、无效
			logger.info("品牌下行抛送MQ, 下行信息 : {}", JSONObject.fromObject(brandMessage));
			MQSendUtil mqSendUtil = new MQSendUtil();
			mqSendUtil.setAmqpTemplate(amqpTemplate);
			mqSendUtil.sendToMQWithRoutingKey(brandMessage, MQRoutingKeyConstant.BRAND_DOWN_ERP_ROUTING_KEY);
		} catch (Exception e) {
			logger.error("品牌下行出现异常, 错误信息：", e);
			executeResult.addErrorMessage(e.getMessage());
		}

		return executeResult;
	}

	@Override
	public ExecuteResult<DataGrid<ItemBrand>> queryErpExceptionBrandList(Pager pager, QueryBrandErpExceptionInDto queryBrandErpExceptionInDto) {
		logger.info("查询品牌下行非成功的数据, 参数 : {}", pager);
		ExecuteResult<DataGrid<ItemBrand>> executeResult = new ExecuteResult<>();
		DataGrid<ItemBrand> dataGrid = new DataGrid<>();
		try {
			Long count = this.itemBrandDAO.selectErpExceptionCount(queryBrandErpExceptionInDto);
			if (count > 0) {
				List<ItemBrand> erpExceptionBrandList = this.itemBrandDAO.selectErpExceptionList(pager, queryBrandErpExceptionInDto);
				dataGrid.setRows(erpExceptionBrandList);
				dataGrid.setTotal(count);
			}
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResult(dataGrid);
		} catch (Exception e) {
			executeResult.addErrorMsg(e.getMessage());
			logger.error("查询品牌下行非成功的数据失败, 错误信息 : {}", e);
		}
		return executeResult;
	}

	/**
	 * 添加品牌、品类关系 (批量)
	 * @param itemBrandDTO
	 * @return
	 * @support
	 */
	@Override
	public ExecuteResult<ItemBrandDTO> addCategoryBrandBatch(ItemBrandDTO itemBrandDTO) {
		ExecuteResult<ItemBrandDTO> result = new ExecuteResult<ItemBrandDTO>();
		try {
			Long[] brandIds = itemBrandDTO.getBrandIds();
			if (brandIds == null) {// 校验品牌ID必填
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("brandIds"));
				return result;
			}
			ItemCategoryBrand itemCategoryBrand;
			for (int i = 0; i < brandIds.length; i++) {
				itemCategoryBrand = new ItemCategoryBrand();
				itemCategoryBrand.setThirdLevCid(itemBrandDTO.getThirdLevCid());
				itemCategoryBrand.setBrandId(brandIds[i]);
				itemCategoryBrand.setCreateId(itemBrandDTO.getCreateId());
				itemCategoryBrand.setCreateName(itemBrandDTO.getCreateName());
				List<ItemCategoryBrand> dbIcb = this.itemCategoryBrandDAO.queryCategoryBrandByBrandId(itemCategoryBrand);
				if (dbIcb == null || dbIcb.size() == 0 ) {
					itemCategoryBrandDAO.add(itemCategoryBrand);
				}
			}
		} catch (Exception e) {
			logger.error("addCategoryBrandBatch error:" + e.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询品牌列表
	 * @param page 分页信息
	 * @return
	 * @support
	 */
	@Override
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryItemBrandAllList(Pager page) {
		ExecuteResult<DataGrid<ItemBrandDTO>> executeResult = new ExecuteResult();
		DataGrid<ItemBrandDTO> data = new DataGrid();
		try {
			List<ItemBrandDTO> itemBrandDTOs = itemBrandDAO.queryItemBrandAllList(page);
			long count = itemBrandDAO.queryCount(null);
			data.setRows(itemBrandDTOs);
			data.setTotal(count);
			executeResult.setResult(data);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<ItemBrand> queryItemBrandById(Long id) {
		ExecuteResult<ItemBrand> result = new ExecuteResult();
		try {
			if (id == null || id <= 0) {
				result.addErrorMessage("id is empty");
				return result;
			}
			ItemBrand itemBrand = this.itemBrandDAO.queryById(id);
			if (itemBrand == null) {
				result.addErrorMessage("itemBrand is null");
				return result;
			}
			result.setResult(itemBrand);
		} catch (Exception e) {
			logger.error("queryItemBrandByIds error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemBrand> queryItemBrandByName(String brandName) {
		ExecuteResult<ItemBrand> result = new ExecuteResult();
		try {
			if (StringUtils.isEmpty(brandName)) {
				result.addErrorMessage("brandName is empty");
				return result;
			}
			ItemBrand itemBrand = this.itemBrandDAO.queryByName(StringUtils.trim(brandName));
			if (itemBrand == null) {
				result.addErrorMessage("itemBrand is null");
				return result;
			}
			result.setResult(itemBrand);
		} catch (Exception e) {
			logger.error("queryItemBrandByIds error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询品牌列表-带入参
	 * @param itemBrandDTO 查询参数
	 * @param page 分页信息
	 * @return
	 * @support
	 */
	@Override
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandList(ItemBrandDTO itemBrandDTO, Pager page) {
		ExecuteResult<DataGrid<ItemBrandDTO>> result = new ExecuteResult<DataGrid<ItemBrandDTO>>();
		try {
			DataGrid<ItemBrandDTO> dataGrid = new DataGrid<ItemBrandDTO>();
			List<ItemBrandDTO> list = itemBrandDAO.\(itemBrandDTO, page);
			Long count = itemBrandDAO.queryCountBrandList(itemBrandDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询品牌品类关系列表
	 * （根据三级类目查询品牌列表）
	 * @param brandOfShopDTO
	 * @return
	 * @support
	 */
	@Override
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandListByCategoryId(BrandOfShopDTO brandOfShopDTO) {
		ExecuteResult<DataGrid<ItemBrandDTO>> executeResult = new ExecuteResult<>();
		DataGrid<ItemBrandDTO> data = new DataGrid<ItemBrandDTO>();
		try {
			List<ItemBrandDTO> dtos = itemBrandDAO.queryBrandListByCategoryId(brandOfShopDTO);
			data.setRows(dtos);
			data.setTotal(Long.valueOf(dtos.size()));
			executeResult.setResult(data);
		} catch (Exception e) {
			logger.error("error:：", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 修改品牌信息
	 * @param itemBrandDTO 品牌信息
	 * @support 已经单元测试
	 * @return
	 */
	@Transactional
	@Override
	public ExecuteResult<ItemBrandDTO> modifyItemBrand(ItemBrandDTO itemBrandDTO) {
		ExecuteResult<ItemBrandDTO> result = new ExecuteResult();
		// 校验
		if (itemBrandDTO == null) { // 入参为空
			result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("ItemBrandDTO"));
			return result;
		}
		// BrandId 必传
		if (itemBrandDTO.getBrandId() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("BrandId"));
			return result;
		}
		// modify_id 必传
		if (itemBrandDTO.getModifyId() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyId"));
			return result;
		}
		// ModifyName 必传
		if (itemBrandDTO.getModifyName() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyName"));
			return result;
		}
		// BrandName非空
		if (StringUtils.isEmpty(itemBrandDTO.getBrandName())) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("BrandName"));
			return result;
		}
		// BrandLogoUrl非空
		if (StringUtils.isEmpty(itemBrandDTO.getBrandLogoUrl())) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("BrandLogoUrl"));
			return result;
		}
		try {
			ItemBrand brand = new ItemBrand();
			brand.setBrandId(itemBrandDTO.getBrandId());
			brand.setBrandLogoUrl(itemBrandDTO.getBrandLogoUrl());
			brand.setBrandName(itemBrandDTO.getBrandName());
			brand.setBrandStatus(1);
			brand.setModifyId(itemBrandDTO.getModifyId());
			brand.setModifyName(itemBrandDTO.getModifyName());
			brand.setModifyTime(new Date());
			ExecuteResult downExecute = this.downItemBrand2Erp(brand, 1);
			if (!downExecute.isSuccess()) {
				throw new Exception("品牌下行出现异常, 错误信息：" + downExecute.getErrorMessages());
			}
			result.setResult(itemBrandDTO);
		} catch (Exception e) {
			logger.error("modifyItemBrand error:：", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 删除品牌
	 * @param itemBrandDTO
	 * @return
	 * @support
	 */
	@Transactional
	@Override
	public ExecuteResult<String> deleteBrand(ItemBrandDTO itemBrandDTO) {
		ExecuteResult<String> result = new ExecuteResult();
		// 校验
		if (itemBrandDTO == null) { // 入参为空
			result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("ItemBrandDTO"));
			return result;
		}
		// BrandId 必传
		if (itemBrandDTO.getBrandId() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("BrandId"));
			return result;
		}
		// modify_id 必传
		if (itemBrandDTO.getModifyId() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyId"));
			return result;
		}
		// ModifyName 必传
		if (itemBrandDTO.getModifyName() == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyName"));
			return result;
		}
		try {
			List<ItemBrandDTO> listItemBrandDTO = itemBrandDAO.queryBrandCategoryById(itemBrandDTO.getBrandId());
			if (listItemBrandDTO.isEmpty()) {
				ItemBrand itemBrand = this.itemBrandDAO.queryById(itemBrandDTO.getBrandId());
				itemBrand.setBrandStatus(0);
				// 删除 & 下行
				ExecuteResult downExecute = this.downItemBrand2Erp(itemBrand, 1);
				if (!downExecute.isSuccess()) {
					throw new Exception("品牌下行出现异常, 错误信息：" + downExecute.getErrorMessages());
				}
			} else { // 和类目存在关联、不能删除
				result.addErrorMessage(ErrorCodes.E10304.getErrorMsg());
				return result;
			}
		} catch (Exception e) {
			logger.error("deleteBrand error::", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 删除品牌和品类关系
	 * @param cid 品类ID
	 * @param brandId 品牌ID
	 * @return
	 * @support
	 */
	@Override
	public ExecuteResult<String> deleteCategoryBrand(Long cid, Long brandId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (cid == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("cid"));
			return result;
		}
		if (brandId == null) {
			result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("brandId"));
			return result;
		}
		try {
			Item item = new Item();
			item.setCid(cid);
			item.setBrand(brandId);
			Long count = itemMybatisDAO.queryItemCountByCidAndBrandId(item);
			if (count == 0) {
				itemBrandDAO.deleteItemBrand(cid, brandId);
			} else {
				result.addErrorMessage(ErrorCodes.E10306.getErrorMsg());
				return result;
			}
		} catch (Exception e) {
			logger.error("deleteCategoryBrand error::", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public DataGrid<ItemBrandDTO> queryByBrandName(String brandName) {
		DataGrid<ItemBrandDTO> dataGrid = new DataGrid<ItemBrandDTO>();
		try{
			List<ItemBrandDTO> list = itemBrandDAO.selectByBrandName(brandName);
			dataGrid.setRows(list);
		}catch (Exception e){
			logger.error("queryByBrandName error::", e);
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<List<ItemBrandDTO>> queryItemBrandByIds(Long... ids) {
		ExecuteResult<List<ItemBrandDTO>> result = new ExecuteResult<List<ItemBrandDTO>>();
		try {
			if (ids == null || ids.length <= 0) {
				result.addErrorMessage("param is empty");
				return result;
			}
			List<ItemBrandDTO> brands = this.itemBrandDAO.queryBrandByIds(Arrays.asList(ids));
			result.setResult(brands);
		} catch (Exception e) {
			logger.error("queryItemBrandByIds error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public List<ItemBrand> queryBrandBySyncTime(Date syncTime) {
		return itemBrandDAO.queryBrandBySyncTime(syncTime);
	}


	@Override
	public ExecuteResult<DataGrid<ItemBrandDTO>> queryBrandList4SuperBoss(QueryItemBrandInDTO itemBrandDTO, Pager page) {
		ExecuteResult<DataGrid<ItemBrandDTO>> result = new ExecuteResult<DataGrid<ItemBrandDTO>>();
		try {
			if (itemBrandDTO.getStartTime() == null || itemBrandDTO.getEndTime() == null) {
				result.setResultMessage("startTime or　endTime　is null");
				result.addErrorMessage("startTime or　endTime　is null");
				return result;
			}
			DataGrid<ItemBrandDTO> dataGrid = new DataGrid<ItemBrandDTO>();
			List<ItemBrandDTO> list = itemBrandDAO.queryBrandList4SuperBoss(itemBrandDTO, page);
			Long count = itemBrandDAO.queryCountBrandList4SuperBoss(itemBrandDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}
}