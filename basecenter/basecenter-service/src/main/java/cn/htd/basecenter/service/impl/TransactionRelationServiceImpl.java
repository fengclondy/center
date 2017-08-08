package cn.htd.basecenter.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.basecenter.common.constant.HelpDocConstants;
import cn.htd.basecenter.dao.TransactionRelationDAO;
import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.ExecuteResult;
import com.alibaba.fastjson.JSONObject;

@Service("transactionRelationService")
public class TransactionRelationServiceImpl implements TransactionRelationService{

	private final static Logger logger = LoggerFactory.getLogger(TransactionRelationServiceImpl.class);

	@Resource
	private TransactionRelationDAO transactionRelationDAO;
	

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<?> updateTransactionRelation(TransactionRelationDTO transactionRelationDTO) {
		logger.info("TransactionRelationServiceImpl--->updateTransactionRelation--->parmas:" + JSONObject.toJSONString(transactionRelationDTO));
		
		// 返回状态 ：0.失败  1.成功
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {	
			transactionRelationDAO.updateTransactionRelation(transactionRelationDTO);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TransactionRelationServiceImpl-updateTransactionRelation", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}

	
	@Override
	public ExecuteResult<TransactionRelation> getSingleTransactionRelationByParams(
			TransactionRelationDTO transactionRelationDTO) {
		logger.info("TransactionRelationServiceImpl--->getTransactionRelationForPage--->transactionRelationDTO:" + JSONObject.toJSONString(transactionRelationDTO));
		
		ExecuteResult<TransactionRelation> result = new ExecuteResult<TransactionRelation>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			List<TransactionRelation> transactionRelationList = transactionRelationDAO.getTransactionRelationByParams(transactionRelationDTO);
			if(null != transactionRelationList && transactionRelationList.size() == 1){
				TransactionRelation transactionRelation = transactionRelationList.get(0);
				result.setResult(transactionRelation);
			}else{
				logger.info("TransactionRelationServiceImpl--->getSingleTransactionRelationByParams--->transactionRelationList is more then one!!!  - transactionRelationList size:" + transactionRelationList.size());
				result.setResult(null);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TransactionRelationServiceImpl-getTransactionRelationForPage", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<Boolean> getTransactionRelationIsRelated(String buyerCode) {
		logger.info("TransactionRelationServiceImpl--->getTransactionRelationIsRelated--->buyerCode:" + buyerCode);
		
		TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
		transactionRelationDTO.setBuyerCode(buyerCode);
		
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {	
			List<TransactionRelation> transactionRelationList  = transactionRelationDAO.getTransactionRelationByParams(transactionRelationDTO);
			
			if(null != transactionRelationList && transactionRelationList.size() == 1){
				TransactionRelation transactionRelation = transactionRelationList.get(0);
				if(null != transactionRelation){
					result.setResult(transactionRelation.getIsRelated());
				}else{
					logger.info("TransactionRelationServiceImpl--->getSingleTransactionRelationByParams--->transactionRelation is null!!!");
					result.setResult(Boolean.FALSE);
				}
				
			}else{
				logger.info("TransactionRelationServiceImpl--->getSingleTransactionRelationByParams--->transactionRelationList is more then one!!!  - transactionRelationList size:" + transactionRelationList.size());
				result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
				result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
				result.setResult(Boolean.FALSE);
			}
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TransactionRelationServiceImpl-getTransactionRelationIsRelated", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		} 
		return result;
	}



}
