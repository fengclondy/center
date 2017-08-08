package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionCategoryDetailDefineDAO;
import cn.htd.marketcenter.dao.PromotionCategoryItemRuleDefineDAO;
import cn.htd.marketcenter.dao.PromotionItemDetailDefineDAO;
import cn.htd.marketcenter.dao.PromotionRulesDefineDAO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;
import cn.htd.marketcenter.service.PromotionCategoryItemRuleDefineService;

@Service("promotionCategoryItemRuleDefineService")
public class PromotionCategoryItemRuleDefineServiceImpl implements PromotionCategoryItemRuleDefineService {

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionRulesDefineDAO promotionRulesDefineDAO;

	@Resource
	private PromotionCategoryItemRuleDefineDAO promotionCategoryItemRuleDefineDAO;

	@Resource
	private PromotionCategoryDetailDefineDAO promotionCategoryDetailDefineDAO;

	@Resource
	private PromotionItemDetailDefineDAO promotionItemDetailDefineDAO;

	@Override
	public ExecuteResult<String> addPromotionCategoryItemRule(PromotionCategoryItemRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		long count = 0;
		try {
			if (dto == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "类目商品规则不能为空");
			}
			count = promotionRulesDefineDAO.queryCountByName(dto.getRuleName());
			if (count > 0) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NAME_REPEAT, "类目商品规则名称和其他规则重复");
			}
			dto.setRuleType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_RULE_TYPE,
					DictionaryConst.OPT_PROMOTION_RULE_TYPE_ITEM_CATEGORY));
			dto.setRuleDescribe(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
					dto.getRuleTargetType()));
			if (dictionary
					.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
							DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY)
					.equals(dto.getRuleTargetType())) {
				if (dto.getCategoryList() == null || dto.getCategoryList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "类目规则未指定类目品牌信息");
				}
				dto.setTargetItemLimit("");
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
					DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM).equals(dto.getRuleTargetType())) {
				if (dto.getItemList() == null || dto.getItemList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "商品规则未指定商品详细信息");
				}
			}
			promotionRulesDefineDAO.add(dto);
			promotionCategoryItemRuleDefineDAO.add(dto);
			if (dto.getCategoryList() != null && !dto.getCategoryList().isEmpty()) {
				for (PromotionCategoryDetailDefineDTO detailDto : dto.getCategoryList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setBrandIdList(StringUtils.join(detailDto.getBids(), ","));
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionCategoryDetailDefineDAO.add(detailDto);
				}
			}
			if (dto.getItemList() != null && !dto.getItemList().isEmpty()) {
				for (PromotionItemDetailDefineDTO detailDto : dto.getItemList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionItemDetailDefineDAO.add(detailDto);
				}
			}
		} catch (MarketCenterBusinessException mcbe) {
			result.setCode(mcbe.getCode());
			result.addErrorMessage(mcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updatePromotionCategoryItemRule(PromotionCategoryItemRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionCategoryItemRuleDefineDTO categoryItemRuleDTO = null;
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(dto.getRuleId());
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			categoryItemRuleDTO = promotionCategoryItemRuleDefineDAO.queryById(dto.getRuleId());
			if (categoryItemRuleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			dto.setRuleDescribe(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
					dto.getRuleTargetType()));
			if (dictionary
					.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
							DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY)
					.equals(dto.getRuleTargetType())) {
				if (dto.getCategoryList() == null || dto.getCategoryList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "未选择类目品牌信息");
				}
				if (dictionary
						.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
								DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM)
						.equals(categoryItemRuleDTO.getRuleTargetType())) {
					promotionItemDetailDefineDAO.deleteByRuleId(dto);
				} else {
					promotionCategoryDetailDefineDAO.deleteByRuleId(dto);
				}
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
					DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM).equals(dto.getRuleTargetType())) {
				if (dto.getItemList() == null || dto.getItemList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "未导入商品列表");
				}
				if (dictionary
						.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
								DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY)
						.equals(categoryItemRuleDTO.getRuleTargetType())) {
					promotionCategoryDetailDefineDAO.deleteByRuleId(dto);
				} else {
					promotionItemDetailDefineDAO.deleteByRuleId(dto);
				}
			}
			promotionRulesDefineDAO.update(dto);
			promotionCategoryItemRuleDefineDAO.update(dto);
			if (dto.getCategoryList() != null && !dto.getCategoryList().isEmpty()) {
				for (PromotionCategoryDetailDefineDTO detailDto : dto.getCategoryList()) {
					if (detailDto.getBids() == null || detailDto.getBids().isEmpty()) {
						continue;
					}
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setBrandIdList(StringUtils.join(detailDto.getBids().toArray(), ","));
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionCategoryDetailDefineDAO.add(detailDto);
				}
			}
			if (dto.getItemList() != null && !dto.getItemList().isEmpty()) {
				for (PromotionItemDetailDefineDTO detailDto : dto.getItemList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionItemDetailDefineDAO.add(detailDto);
				}
			}
		} catch (MarketCenterBusinessException mcbe) {
			result.setCode(mcbe.getCode());
			result.addErrorMessage(mcbe.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<PromotionCategoryItemRuleDefineDTO> queryPromotionCategoryItemRule(Long ruleId) {
		ExecuteResult<PromotionCategoryItemRuleDefineDTO> result = new ExecuteResult<PromotionCategoryItemRuleDefineDTO>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionCategoryItemRuleDefineDTO dto = null;
		List<PromotionCategoryDetailDefineDTO> categoryDetailList = null;
		List<PromotionItemDetailDefineDTO> itemDetailList = null;
		String[] brandIdArr = null;
		List<Long> brandIdList = null;
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(ruleId);
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			dto = promotionCategoryItemRuleDefineDAO.queryById(ruleId);
			dto.setRuleDefine(ruleDTO);
			if (dictionary
					.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
							DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY)
					.equals(dto.getRuleTargetType())) {
				categoryDetailList = promotionCategoryDetailDefineDAO.queryCategoryDetailList(ruleId);
				if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
					for (PromotionCategoryDetailDefineDTO categoryDto : categoryDetailList) {
						brandIdArr = categoryDto.getBrandIdList().split(",");
						brandIdList = new ArrayList<Long>();
						for (String brandId : brandIdArr) {
							brandIdList.add(new Long(brandId));
						}
						categoryDto.setBids(brandIdList);
					}
				}
				dto.setCategoryList(categoryDetailList);
			} else {
				itemDetailList = promotionItemDetailDefineDAO.queryItemDetailList(ruleId);
				dto.setItemList(itemDetailList);
			}
			result.setResult(dto);
		} catch (MarketCenterBusinessException mcbe) {
			result.setCode(mcbe.getCode());
			result.addErrorMessage(mcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteItemDetailList(PromotionItemDetailDefineDTO detailDto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			promotionItemDetailDefineDAO.deleteDetailList(detailDto);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}
