package cn.htd.basecenter.service;

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.common.ExecuteResult;

public interface TransactionRelationService{
	
	
	/**
	 * 更新关联交易
	 * @param helpDocCategoryDTO
	 */
	ExecuteResult<?> updateTransactionRelation(TransactionRelationDTO transactionRelationDTO);
	
	
	/**
	 * 查询单个关联交易
	 * @param page
	 * @param helpDocCategoryDTO
	 * @return
	 */
	ExecuteResult<TransactionRelation> getSingleTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 查询会员是否关联供应商
	 * @param buyerCode
	 * @return
	 */
	ExecuteResult<Boolean> getTransactionRelationIsRelated(String buyerCode);
	
}
