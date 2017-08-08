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
import cn.htd.marketcenter.dao.PromotionBuyerDetailDefineDAO;
import cn.htd.marketcenter.dao.PromotionBuyerRuleDefineDAO;
import cn.htd.marketcenter.dao.PromotionRulesDefineDAO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDefineDTO;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;
import cn.htd.marketcenter.service.PromotionBuyerRuleDefineService;

@Service("promotionBuyerRuleDefineService")
public class PromotionBuyerRuleDefineServiceImpl implements PromotionBuyerRuleDefineService {

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionRulesDefineDAO promotionRulesDefineDAO;

	@Resource
	private PromotionBuyerRuleDefineDAO promotionBuyerRuleDefineDAO;

	@Resource
	private PromotionBuyerDetailDefineDAO promotionBuyerDetailDefineDAO;

	/**
	 * 增加买家规则
	 */
	@Override
	public ExecuteResult<String> addPromotionBuyerRule(PromotionBuyerRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		String describe = "";
		String targetBuyerLevelStr = "";
		long count = 0;
		try {
			if (dto == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "会员规则不能为空");
			}
			count = promotionRulesDefineDAO.queryCountByName(dto.getRuleName());
			if (count > 0) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NAME_REPEAT, "会员规则名称和其他规则重复");
			}
			dto.setRuleType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_RULE_TYPE,
					DictionaryConst.OPT_PROMOTION_RULE_TYPE_BUYER));
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
					DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT).equals(dto.getRuleTargetType())) {
				if (dto.getBuyerDetailList() == null || dto.getBuyerDetailList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "指定会员时无会员详细信息");
				}
				dto.setTargetBuyerLevel("");
				dto.setRuleDescribe(
						dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_BUYER_RULE, dto.getRuleTargetType()));
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
					DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(dto.getRuleTargetType())) {
				if (dto.getTargetBuyerLevelList() == null || dto.getTargetBuyerLevelList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "等级会员未选择等级信息");
				}
				for (String level : dto.getTargetBuyerLevelList()) {
					describe += "," + dictionary.getNameByValue(DictionaryConst.TYPE_MEMBER_GRADE, level);
					targetBuyerLevelStr += "," + level;
				}
				dto.setRuleDescribe(describe.substring(1));
				dto.setTargetBuyerLevel(targetBuyerLevelStr.substring(1));
			}
			promotionRulesDefineDAO.add(dto);
			promotionBuyerRuleDefineDAO.add(dto);
			if (dto.getBuyerDetailList() != null && !dto.getBuyerDetailList().isEmpty()) {
				for (PromotionBuyerDetailDefineDTO detailDto : dto.getBuyerDetailList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionBuyerDetailDefineDAO.add(detailDto);
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

	/**
	 * 修改买家规则
	 */
	@Override
	public ExecuteResult<String> updatePromotionBuyerRule(PromotionBuyerRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionBuyerRuleDefineDTO buyerRuleDTO = null;
		String describe = "";
		String targetBuyerLevelStr = "";
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(dto.getRuleId());
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			buyerRuleDTO = promotionBuyerRuleDefineDAO.queryById(dto.getRuleId());
			if (buyerRuleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
					DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT).equals(dto.getRuleTargetType())) {
				dto.setTargetBuyerLevel("");
				dto.setRuleDescribe(
						dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_BUYER_RULE, dto.getRuleTargetType()));
				if (dictionary
						.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
								DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT)
						.equals(buyerRuleDTO.getRuleTargetType())) {
					if (dto.getBuyerDetailList() != null && !dto.getBuyerDetailList().isEmpty()) {
						promotionBuyerDetailDefineDAO.deleteByRuleId(dto);
					}
				} else {
					if (dto.getBuyerDetailList() == null || dto.getBuyerDetailList().isEmpty()) {
						throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "指定会员时无会员详细信息");
					}
				}
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
					DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(dto.getRuleTargetType())) {
				if (dto.getTargetBuyerLevelList() == null || dto.getTargetBuyerLevelList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "等级会员未选择等级信息");
				}
				for (String level : dto.getTargetBuyerLevelList()) {
					describe += "," + dictionary.getNameByValue(DictionaryConst.TYPE_MEMBER_GRADE, level);
					targetBuyerLevelStr += "," + level;
				}
				dto.setRuleDescribe(describe.substring(1));
				dto.setTargetBuyerLevel(targetBuyerLevelStr.substring(1));
				promotionBuyerDetailDefineDAO.deleteByRuleId(dto);
			}
			promotionRulesDefineDAO.update(dto);
			promotionBuyerRuleDefineDAO.update(dto);
			if (dto.getBuyerDetailList() != null && !dto.getBuyerDetailList().isEmpty()) {
				for (PromotionBuyerDetailDefineDTO detailDto : dto.getBuyerDetailList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionBuyerDetailDefineDAO.add(detailDto);
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
	public ExecuteResult<PromotionBuyerRuleDefineDTO> queryPromotionBuyerRule(Long ruleId) {
		ExecuteResult<PromotionBuyerRuleDefineDTO> result = new ExecuteResult<PromotionBuyerRuleDefineDTO>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionBuyerRuleDefineDTO dto = null;
		String[] buyerLevelArr = null;
		List<String> buyerLevelList = new ArrayList<String>();
		List<PromotionBuyerDetailDefineDTO> buyerDetailList = null;
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(ruleId);
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			dto = promotionBuyerRuleDefineDAO.queryById(ruleId);
			dto.setRuleDefine(ruleDTO);
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
					DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(dto.getRuleTargetType())) {
				if (!StringUtils.isEmpty(dto.getTargetBuyerLevel())) {
					buyerLevelArr = dto.getTargetBuyerLevel().split(",");
					for (String level : buyerLevelArr) {
						buyerLevelList.add(level);
					}
					dto.setTargetBuyerLevelList(buyerLevelList);
				}
			} else {
				buyerDetailList = promotionBuyerDetailDefineDAO.queryBuyerDetailList(ruleId);
				dto.setBuyerDetailList(buyerDetailList);
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

	/**
	 * 根据ID删除记录 其实是修改删除标志
	 */
	@Override
	public ExecuteResult<String> deleteBuyerDetailList(PromotionBuyerDetailDefineDTO detailDto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			promotionBuyerDetailDefineDAO.deleteDetailList(detailDto);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

}
