package cn.htd.marketcenter.service.impl.promotion;

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
import cn.htd.marketcenter.dao.PromotionRulesDefineDAO;
import cn.htd.marketcenter.dao.PromotionSellerDetailDefineDAO;
import cn.htd.marketcenter.dao.PromotionSellerRuleDefineDAO;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDefineDTO;
import cn.htd.marketcenter.service.PromotionSellerRuleDefineService;

/**
 * Created by thinkpad on 2016/12/1.
 */
@Service("promotionSellerRuleDefineService")
public class PromotionSellerRuleDefineServiceImpl implements PromotionSellerRuleDefineService {

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionRulesDefineDAO promotionRulesDefineDAO;

	@Resource
	private PromotionSellerRuleDefineDAO promotionSellerRuleDefineDAO;

	@Resource
	private PromotionSellerDetailDefineDAO promotionSellerDetailDefineDAO;

	@Override
	public ExecuteResult<String> addPromotionSellerRule(PromotionSellerRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		long count = 0;
		try {
			if (dto == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "供应商规则不能为空");
			}
			count = promotionRulesDefineDAO.queryCountByName(dto.getRuleName());
			if (count > 0) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NAME_REPEAT, "供应商规则名称和其他规则重复");
			}
			dto.setRuleType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_RULE_TYPE,
					DictionaryConst.OPT_PROMOTION_RULE_TYPE_SELLER));
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
					DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART).equals(dto.getRuleTargetType())) {
				if (dto.getSellerDetailList() == null || dto.getSellerDetailList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "部分供应商时未导入供应商详细列表信息");
				}
				dto.setTargetSellerType("");
				dto.setRuleDescribe(
						dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_SELLER_RULE, dto.getRuleTargetType()));
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
					DictionaryConst.OPT_PROMOTION_SELLER_RULE_APPIONT).equals(dto.getRuleTargetType())) {
				if (StringUtils.isEmpty(dto.getTargetSellerType())) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "指定供应商未选择供应商类型信息");
				}
				dto.setRuleDescribe(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
						dto.getTargetSellerType()));
			}
			promotionRulesDefineDAO.add(dto);
			promotionSellerRuleDefineDAO.add(dto);
			if (dto.getSellerDetailList() != null && !dto.getSellerDetailList().isEmpty()) {
				for (PromotionSellerDetailDefineDTO detailDto : dto.getSellerDetailList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionSellerDetailDefineDAO.add(detailDto);
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
	public ExecuteResult<String> updatePromotionSellerRule(PromotionSellerRuleDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionSellerRuleDefineDTO sellerRuleDTO = null;
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(dto.getRuleId());
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			sellerRuleDTO = promotionSellerRuleDefineDAO.queryById(dto.getRuleId());
			if (sellerRuleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
					DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART).equals(dto.getRuleTargetType())) {
				dto.setTargetSellerType("");
				dto.setRuleDescribe(
						dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_SELLER_RULE, dto.getRuleTargetType()));
				if (dictionary
						.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
								DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART)
						.equals(sellerRuleDTO.getRuleTargetType())) {
					if (dto.getSellerDetailList() != null && !dto.getSellerDetailList().isEmpty()) {
						promotionSellerDetailDefineDAO.deleteByRuleId(dto);
					}
				} else {
					if (dto.getSellerDetailList() == null || dto.getSellerDetailList().isEmpty()) {
						throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "部分供应商时未导入供应商详细列表信息");
					}
				}
			} else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
					DictionaryConst.OPT_PROMOTION_SELLER_RULE_APPIONT).equals(dto.getRuleTargetType())) {
				if (StringUtils.isEmpty(dto.getTargetSellerType())) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "指定供应商未选择供应商类型信息");
				}
				dto.setRuleDescribe(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
						dto.getTargetSellerType()));
				promotionSellerDetailDefineDAO.deleteByRuleId(dto);
			}
			promotionRulesDefineDAO.update(dto);
			promotionSellerRuleDefineDAO.update(dto);
			if (dto.getSellerDetailList() != null && !dto.getSellerDetailList().isEmpty()) {
				for (PromotionSellerDetailDefineDTO detailDto : dto.getSellerDetailList()) {
					detailDto.setRuleId(dto.getRuleId());
					detailDto.setCreateId(dto.getCreateId());
					detailDto.setCreateName(dto.getCreateName());
					promotionSellerDetailDefineDAO.add(detailDto);
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
	public ExecuteResult<PromotionSellerRuleDefineDTO> queryPromotionSellerRule(Long ruleId) {
		ExecuteResult<PromotionSellerRuleDefineDTO> result = new ExecuteResult<PromotionSellerRuleDefineDTO>();
		PromotionRulesDefineDTO ruleDTO = null;
		PromotionSellerRuleDefineDTO dto = null;
		List<PromotionSellerDetailDefineDTO> sellerDetailList = null;
		try {
			ruleDTO = promotionRulesDefineDAO.queryById(ruleId);
			if (ruleDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则不存在");
			}
			if (YesNoEnum.YES.getValue() == ruleDTO.getDeleteFlag()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.RULE_NOT_EXIST, "该规则已被删除");
			}
			dto = promotionSellerRuleDefineDAO.queryById(ruleId);
			dto.setRuleDefine(ruleDTO);
			if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
					DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART).equals(dto.getRuleTargetType())) {
				sellerDetailList = promotionSellerDetailDefineDAO.querySellerDetailList(ruleId);
				dto.setSellerDetailList(sellerDetailList);
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
	public ExecuteResult<String> deleteSellerDetailList(PromotionSellerDetailDefineDTO detailDto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			promotionSellerDetailDefineDAO.deleteDetailList(detailDto);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}
