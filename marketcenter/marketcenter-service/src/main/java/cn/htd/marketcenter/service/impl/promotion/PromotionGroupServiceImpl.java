package cn.htd.marketcenter.service.impl.promotion;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionGroupDAO;
import cn.htd.marketcenter.domain.PromotionGroupSignup;
import cn.htd.marketcenter.dto.PromotionGroupSignUpDTO;
import cn.htd.marketcenter.dto.PromotionGroupSignUpRepairDTO;
import cn.htd.marketcenter.service.PromotionGroupService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionGroupService")
public class PromotionGroupServiceImpl implements PromotionGroupService {
	
	protected static transient Logger logger = LoggerFactory.getLogger(PromotionGroupServiceImpl.class);
	
	@Resource
	private PromotionGroupDAO promotionGroupDAO;

	@Override
	public ExecuteResult<Boolean> insertPromotionGroupSignUpInfo(PromotionGroupSignUpDTO PromotionGroupSignUpDTO) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			if (PromotionGroupSignUpDTO == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "供应商规则不能为空");
			}
//			List<PromotionGroupSignup> groupList = promotionGroupDAO
//					.queryPromotionGroupSignUpCount(PromotionGroupSignUpDTO);
//			if (groupList.size() < MarketCenterCodeConst.GROUP_LIMIT_PRODUCT_NUM) {
				promotionGroupDAO.insertPromotionGroupSignUpInfo(PromotionGroupSignUpDTO);
				result.setResult(true);
//			}
//			else {
//				result.setResult(false);
//				result.addErrorMessage("该用户报名次数已经达到报名上限");
//			}
		} catch (MarketCenterBusinessException mbe) {
			result.setResult(false);
			result.addErrorMessage(mbe.getCode());
		} catch (Exception e) {
			result.setResult(false);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<PromotionGroupSignUpDTO> queryPromotionGroupSignUpInfo(
			PromotionGroupSignUpDTO PromotionGroupSignUpDTO) {
		ExecuteResult<PromotionGroupSignUpDTO> result = new ExecuteResult<PromotionGroupSignUpDTO>();
		try {
			List<PromotionGroupSignup> groupList = promotionGroupDAO
					.queryPromotionGroupSignUpList(PromotionGroupSignUpDTO);
			if (CollectionUtils.isNotEmpty(groupList)) {
				int productNum = 0;
				for (PromotionGroupSignup group : groupList) {
					productNum += group.getProductNum();
				}
				PromotionGroupSignUpDTO dto = new PromotionGroupSignUpDTO();
				dto.setProductNum(productNum);
				dto.setPromotionId(PromotionGroupSignUpDTO.getPromotionId());
				result.setResult(dto);
			} else {
				PromotionGroupSignUpDTO dto = new PromotionGroupSignUpDTO();
				dto.setProductNum(0);
				result.setResult(dto);
			}
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> repairPromotionGroupSignUpInfo(List<PromotionGroupSignUpRepairDTO> promotionGroupSignUpRepairDTOList) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(CollectionUtils.isEmpty(promotionGroupSignUpRepairDTOList)){
			result.setCode(MarketCenterCodeConst.PARAMETER_ERROR);
			result.addErrorMessage("入参为空");
			return result;
		}
		
		if(promotionGroupSignUpRepairDTOList.size()>10000){
			result.setCode(MarketCenterCodeConst.PARAMETER_ERROR);
			result.addErrorMessage("入参大于10000条记录");
			return result;
		}
		int affectCount=0;
		try{
			for(PromotionGroupSignUpRepairDTO promotionGroupSignUpRepairDTO:promotionGroupSignUpRepairDTOList){
				if(promotionGroupSignUpRepairDTO==null||
						StringUtils.isEmpty(promotionGroupSignUpRepairDTO.getMemberCode())||
						StringUtils.isEmpty(promotionGroupSignUpRepairDTO.getMemberCode())||
						StringUtils.isEmpty(promotionGroupSignUpRepairDTO.getMobile())){
					logger.info("PromotionGroupSignUpRepairDTO has empty field {}", JSONObject.toJSONString(promotionGroupSignUpRepairDTO));
				}
				//根据手机号和用户名称去更新
				int n=promotionGroupDAO.repairPromotionGroupSignupInfo(promotionGroupSignUpRepairDTO);
				affectCount+=n;
				logger.info("PromotionGroupSignUpRepairDTO has update", JSONObject.toJSONString(promotionGroupSignUpRepairDTO));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		result.setResult("执行成功，影响"+affectCount+"条记录");
		return result;
	}

}
