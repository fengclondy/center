package cn.htd.goodscenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.CategoryAttrDAO;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.domain.ItemCategory;
import cn.htd.goodscenter.dto.*;
import cn.htd.goodscenter.service.ItemAttributeExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dao.ItemAttributeDAO;
import cn.htd.goodscenter.dao.ItemAttributeValueDAO;

@Service("itemAttributeExportService")
public class ItemAttributeExportServiceImpl implements ItemAttributeExportService {
	private static final Logger logger = LoggerFactory.getLogger(ItemAttributeExportServiceImpl.class);

	@Resource
	private ItemAttributeDAO itemAttributeDAO;

	@Resource
	private ItemAttributeValueDAO itemAttributeValueDAO;

	@Resource
	private CategoryAttrDAO categoryAttrDAO;

	@Resource
	private ItemCategoryDAO itemCategoryDAO;

	@Resource
	private ItemCategoryService itemCategoryService;

	@Override
	public ExecuteResult<ItemAttrDTO> modifyItemAttribute(ItemAttrDTO itemAttr) {
		ExecuteResult<ItemAttrDTO> result = new ExecuteResult<ItemAttrDTO>();
		if (itemAttr.getId() == null) {
			result.addErrorMessage("属性ID不能为空！");
			return result;
		}
		if (StringUtils.isBlank(itemAttr.getName())) {
			result.addErrorMessage("属性名不能为空！");
			return result;
		}
		if (itemAttr.getModifyId() == null) {
			result.addErrorMessage("更新人ID不能为空！");
			return result;
		}
		if (itemAttr.getModifyName() == null) {
			result.addErrorMessage("更新人名称不能为空！");
			return result;
		}
		try {
			this.itemAttributeDAO.update(itemAttr);
			// 【搜索引擎需要】更新属性是，更新类目更新时间
			this.updateCategoryModifyTimeByAttrId(itemAttr.getId());
			result.setResult(itemAttr);
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrDTO> addItemAttribute(ItemAttrDTO itemAttr) {
		ExecuteResult<ItemAttrDTO> result = new ExecuteResult<ItemAttrDTO>();
		if (StringUtils.isBlank(itemAttr.getName())) {
			result.addErrorMessage("属性名不能为空！");
			return result;
		}
		this.itemAttributeDAO.add(itemAttr);
		result.setResult(itemAttr);
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrDTO> deleteItemAttribute(ItemAttrDTO itemAttr) {
		ExecuteResult<ItemAttrDTO> result = new ExecuteResult<ItemAttrDTO>();
		ItemAttrDTO param = new ItemAttrDTO();
		param.setId(itemAttr.getId());
		param.setStatus(0);
		param.setModifyId(itemAttr.getModifyId());
		param.setModifyName(itemAttr.getModifyName());
		this.itemAttributeDAO.update(param);
		result.setResult(itemAttr);
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrValueDTO> modifyItemAttrValue(ItemAttrValueDTO itemAttrValue) {
		ExecuteResult<ItemAttrValueDTO> result = new ExecuteResult<ItemAttrValueDTO>();
		if (itemAttrValue.getId() == null) {
			result.addErrorMessage("属性值ID不能为空！");
			return result;
		}
		if (StringUtils.isBlank(itemAttrValue.getName())) {
			result.addErrorMessage("属性值名称不能为空！");
			return result;
		}
		if (itemAttrValue.getModifyId() == null) {
			result.addErrorMessage("更新人ID不能为空！");
			return result;
		}
		if (StringUtils.isBlank(itemAttrValue.getModifyName())) {
			result.addErrorMessage("更新人名称不能为空！");
			return result;
		}
		try {
			this.itemAttributeValueDAO.update(itemAttrValue);
			this.updateCategoryModifyTimeByAttrValueId(itemAttrValue.getId());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrValueDTO> addItemAttrValue(ItemAttrValueDTO itemAttrValue) {
		ExecuteResult<ItemAttrValueDTO> result = new ExecuteResult<ItemAttrValueDTO>();
		if (itemAttrValue.getAttrId() == null) {
			result.addErrorMessage("属性ID不能为空！");
			return result;
		}
		if (StringUtils.isBlank(itemAttrValue.getName())) {
			result.addErrorMessage("属性值名称不能为空！");
			return result;
		}
		this.itemAttributeValueDAO.add(itemAttrValue);
		result.setResult(itemAttrValue);
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrValueDTO> deleteItemAttrValue(ItemAttrValueDTO itemAttrValue) {
		ExecuteResult<ItemAttrValueDTO> result = new ExecuteResult<ItemAttrValueDTO>();
		ItemAttrValueDTO param = new ItemAttrValueDTO();
		param.setId(itemAttrValue.getId());
		param.setStatus(0);
		this.itemAttributeValueDAO.update(param);
		return result;
	}

	@Override
	public ExecuteResult<List<ItemAttrDTO>> addItemAttrValueBack(CatAttrSellerDTO inDTO, int operator) {
		ExecuteResult<List<ItemAttrDTO>> result = new ExecuteResult<List<ItemAttrDTO>>();
		ExecuteResult<DataGrid<CategoryAttrDTO>> executeResult = null;
		DataGrid<CategoryAttrDTO> dataGridca = null;
		List<ItemAttrDTO> list = new ArrayList<ItemAttrDTO>();
		if (operator == 1) {// 商家
			ExecuteResult<List<ItemAttrDTO>> catRe = itemCategoryService.queryCatAttrSellerList(inDTO);
			if (!catRe.isSuccess()) {
				return catRe;
			}
			List<ItemAttrDTO> dbAttrs = catRe.getResult();
			ItemAttrDTO attr = null;
			for (ItemAttrDTO ca : dbAttrs) {
				attr = new ItemAttrDTO();
				attr.setName(ca.getName());
				attr.setCreateId(inDTO.getCreateId());
				attr.setCreateName(inDTO.getCreateName());
				attr.setModifyId(inDTO.getCreateId());
				attr.setModifyName(inDTO.getModifyName());
				attr.setCreateTime(new Date());
				attr.setModifyTime(new Date());
				this.itemAttributeDAO.add(attr);
				List<ItemAttrValueDTO> listav = ca.getValues();
				ItemAttrValueDTO attrValue = null;
				ItemAttrValueDTO dbValue = null;
				List<ItemAttrValueDTO> reValues = new ArrayList<ItemAttrValueDTO>();
				for (int i = 0; i < listav.size(); i++) {
					dbValue = listav.get(i);
					attrValue = new ItemAttrValueDTO();
					attrValue.setAttrId(attr.getId());
					attrValue.setName(dbValue.getName());
					attrValue.setCreateId(inDTO.getCreateId());
					attrValue.setCreateName(inDTO.getCreateName());
					attrValue.setModifyId(inDTO.getModifyId());
					attrValue.setModifyName(inDTO.getModifyName());
					attrValue.setCreateTime(new Date());
					attrValue.setModifyTime(new Date());
					this.itemAttributeValueDAO.add(attrValue);
					reValues.add(attrValue);
				}
				attr.setValues(reValues);
				list.add(attr);
			}
		} else if (operator == 2) {// 平台
			executeResult = itemCategoryService.queryCategoryAttrList(inDTO.getCid(), inDTO.getAttrType());
			if (executeResult.isSuccess()) {
				dataGridca = executeResult.getResult();
			}
			if (null == dataGridca) {
				dataGridca = new DataGrid<CategoryAttrDTO>();
			}
			for (CategoryAttrDTO ca : dataGridca.getRows()) {
				ItemAttrDTO itemAttr = new ItemAttrDTO();
				itemAttr.setName(ca.getAttrAttrName());
				this.itemAttributeDAO.add(itemAttr);

				List<ItemAttrValueDTO> listav = new ArrayList<ItemAttrValueDTO>();
				for (CategoryAttrDTO cavalue : ca.getValueList()) {
					ItemAttrValueDTO itemAttrValue = new ItemAttrValueDTO();
					itemAttrValue.setName(cavalue.getAttrValueName());
					itemAttrValue.setAttrId(itemAttr.getId());
					this.itemAttributeValueDAO.add(itemAttrValue);
					listav.add(itemAttrValue);
				}
				itemAttr.setValues(listav);
				list.add(itemAttr);
			}
		}

		result.setResult(list);
		return result;
	}

	@Override
	public ExecuteResult<String> deleteByAttrId(Long attrId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			this.itemAttributeValueDAO.deleteByAttrId(attrId);
			result.setResult("success");
		}catch (Exception e){
			logger.error(" deleteByAttrId error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> delete(Long id) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			this.itemAttributeDAO.delete(id);
			result.setResult("success");
		}catch (Exception e){
			logger.error(" delete error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteItemAttrByAttrId(Long arrId,Long cid,Long shopId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			if(arrId == null || arrId == 0){
				result.setResult("arrId不能为空");
				return result;
			}
			if(cid == null || cid == 0){
				result.setResult("cid不能为空");
				return result;
			}
			if(shopId == null || shopId == 0){
				result.setResult("shopId不能为空");
				return result;
			}
			this.itemAttributeDAO.delteItemAttrByAttrId(arrId,cid,shopId);
			this.itemAttributeDAO.delete(arrId);
			this.itemAttributeValueDAO.deleteByAttrId(arrId);
			result.setResult("success");
		}catch (Exception e){
			logger.error(" delete error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteAttrValueById(Long id) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try{
			this.itemAttributeValueDAO.delete(id);
			result.setResult("success");
		}catch (Exception e){
			logger.error(" delete error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<List<ItemAttrValueDTO>> queryByAttrId(Long attrId) {
		ExecuteResult<List<ItemAttrValueDTO>> result = new ExecuteResult<List<ItemAttrValueDTO>>();
		try{
			List<ItemAttrValueDTO> list = itemAttributeValueDAO.selectByAttrId(attrId);
			result.setResult(list);
			result.setResultMessage("success");
		}catch (Exception e){
			logger.error(" queryByAttrId error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 搜索需求
	 * 更新类目的更新时间 根据属性id
	 * @param attrId 属性id
	 */
	private void updateCategoryModifyTimeByAttrId(Long attrId) {
		logger.error("【搜索引擎需要】, 根据属性ID更新类目时间, attrId : {}", attrId);
		try {
			List<Long> cids = this.categoryAttrDAO.queryCidByAttributeId(attrId);
			for (Long cid : cids) {
				ItemCategoryDTO itemCategory = new ItemCategoryDTO();
				itemCategory.setCategoryCid(cid);
				itemCategory.setCategoryModifyId(Constants.SYSTEM_CREATE_ID);
				itemCategory.setCategoryModifyName(Constants.SYSTEM_CREATE_NAME);
				this.itemCategoryDAO.updateBySelect(itemCategory);
			}
		} catch (Exception e) {
			logger.error("【搜索引擎需要】, 根据属性ID更新类目时间出错：", e);
		}
	}

	/**
	 * 更新类目的更新时间 根据属性值id
	 * @param attrValueId 属性值id
	 */
	private void updateCategoryModifyTimeByAttrValueId(Long attrValueId) {
		logger.error("【搜索引擎需要】, 根据属性值ID更新类目时间, attrId : {}", attrValueId);
		try {
			List<Long> cids = this.categoryAttrDAO.queryCidByAttributeValueId(attrValueId);
			for (Long cid : cids) {
				ItemCategoryDTO itemCategory = new ItemCategoryDTO();
				itemCategory.setCategoryCid(cid);
				itemCategory.setCategoryModifyId(Constants.SYSTEM_CREATE_ID);
				itemCategory.setCategoryModifyName(Constants.SYSTEM_CREATE_NAME);
				this.itemCategoryDAO.updateBySelect(itemCategory);
			}
		} catch (Exception e) {
			logger.error("【搜索引擎需要】, 根据属性ID更新类目时间出错：", e);
		}
	}
}
