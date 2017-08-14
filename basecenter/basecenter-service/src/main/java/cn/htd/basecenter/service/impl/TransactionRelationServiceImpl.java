package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.basecenter.common.constant.HelpDocConstants;
import cn.htd.basecenter.dao.TransactionRelationDAO;
import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.ImportResultDTO;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

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
	public ExecuteResult<TransactionRelationDTO> getSingleTransactionRelationByParams(
			TransactionRelationDTO transactionRelationDTO) {
		logger.info("TransactionRelationServiceImpl--->getTransactionRelationForPage--->transactionRelationDTO:" + JSONObject.toJSONString(transactionRelationDTO));
		
		ExecuteResult<TransactionRelationDTO> result = new ExecuteResult<TransactionRelationDTO>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			List<TransactionRelationDTO> transactionRelationList = transactionRelationDAO.getTransactionRelationByParams(transactionRelationDTO);
			if(null != transactionRelationList && transactionRelationList.size() == 1){
				TransactionRelationDTO transactionRelation = transactionRelationList.get(0);
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
		logger.info("TransactionRelationServiceImpl--->getTransactionRelationIsRelated--->parmas:" + buyerCode);
		TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
		transactionRelationDTO.setBuyerCode(buyerCode);
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		boolean flag = false;
		try {	
			List<TransactionRelationDTO> list  = transactionRelationDAO.getTransactionRelationByParams(transactionRelationDTO);
			if(CollectionUtils.isNotEmpty(list) && list.get(0) != null && "1".equals(list.get(0).getIsRelated())){
				flag = true; 
			}
			result.setResult(flag);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TransactionRelationServiceImpl-getTransactionRelationIsRelated", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		} 
		return result;
	}


	@Transactional
	@Override
	public ExecuteResult<List<String>> addTransactionRelations(List<TransactionRelationDTO> transactionRelationDTOs) {
		logger.info("TransactionRelationServiceImpl--->addTransactionRelation--->parmas:" + JSONObject.toJSONString(transactionRelationDTOs));	
		// 返回状态 ：0.失败  1.成功
		ExecuteResult<List<String>> result = new ExecuteResult<List<String>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		List<String> fails = new ArrayList<String>();
		try {	
			for(TransactionRelationDTO dto : transactionRelationDTOs){
				if(!(buyerIsExistCode(dto , null) || buyerNameIsExist(dto.getBuyerName() , null))){
					if(transactionRelationDAO.addTransactionRelation(dto) > 0){
						continue;
					}
				}
				fails.add(dto.getBuyerCode());
			}
			result.setResult(fails);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TransactionRelationServiceImpl-addTransactionRelation", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}


	@Override
	public ExecuteResult<?> deleteTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExecuteResult<DataGrid<TransactionRelation>> getTransactionRelationForPage(
			Pager<TransactionRelationDTO> page, TransactionRelationDTO transactionRelationDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExecuteResult<List<TransactionRelationDTO>> getTransactionRelationByParams(
			TransactionRelationDTO transactionRelationDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExecuteResult<Integer> cancleRelations(List<String> list) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ExecuteResult<ImportResultDTO> importTransactioRelation(
			List<TransactionRelationDTO> transactionRelationDTOs) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean buyerIsExistCode(TransactionRelationDTO dto , String id){
		boolean flag = false;
		TransactionRelationDTO params = new TransactionRelationDTO();
		List<TransactionRelationDTO> list = null;
		if(StringUtils.isEmpty(dto.getBuyerCode())){
			return flag;
		}
		params.setBuyerCode(dto.getBuyerCode());
		list = transactionRelationDAO.getTransactionRelationByParams(params);
		if(!CollectionUtils.isEmpty(list)){
			flag = true; 
			logger.info("buyerIsExist params : {buyerCode : " + params.getBuyerCode() + ", id : "+ id + 
					",list : " + list.get(0) + "}");
			if(StringUtils.isNotEmpty(id) && list.get(0) != null && id.equals(list.get(0).getId())){
				flag = false;
			}
		}
		return flag;
	}

	private boolean buyerNameIsExist(String buyerName , String id){
		if(StringUtils.isEmpty(buyerName)){
			return true;
		}
		TransactionRelationDTO params = new TransactionRelationDTO();
		params.setBuyerName(buyerName);
		List<TransactionRelationDTO> list = transactionRelationDAO.getTransactionRelationByParams(params);
		if(!CollectionUtils.isEmpty(list)){
			if(StringUtils.isNotEmpty(id) && list.get(0) != null && id.equals(list.get(0).getId())){
				return false; 
			}
			return true;
		}
		return false;
	}
}
