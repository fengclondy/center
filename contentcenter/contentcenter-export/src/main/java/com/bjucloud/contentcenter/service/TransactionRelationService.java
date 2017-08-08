package com.bjucloud.contentcenter.service;

import java.util.List;

import com.bjucloud.contentcenter.domain.TransactionRelation;
import com.bjucloud.contentcenter.dto.ImportResultDTO;
import com.bjucloud.contentcenter.dto.TransactionRelationDTO;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public interface TransactionRelationService{

	

	/**
	 * 添加关联交易
	 * @param helpDocCategoryDTO
	 */
	ExecuteResult<List<String>> addTransactionRelations(List<TransactionRelationDTO> transactionRelationDTOs);
	
	/**
	 * 删除关联交易
	 * @param transactionRelationDTO
	 * @return
	 */
	ExecuteResult<?> deleteTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 更新关联交易
	 * @param helpDocCategoryDTO
	 */
	ExecuteResult<?> updateTransactionRelation(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 分页查询关联交易
	 * @param page
	 * @param helpDocCategoryDTO
	 * @return
	 */
	ExecuteResult<DataGrid<TransactionRelation>> getTransactionRelationForPage(Pager<TransactionRelationDTO> page,TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 分页查询关联交易
	 * @param page
	 * @param helpDocCategoryDTO
	 * @return
	 */
	ExecuteResult<List<TransactionRelationDTO>> getTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 查询会员是否关联供应商
	 * @param transactionRelationDTO
	 * @return
	 */
	ExecuteResult<Boolean> getTransactionRelationIsRelated(String buyerCode);
	
	/**
	 * 批量取消关联交易
	 * @param list
	 * @return
	 */
	ExecuteResult<Integer>  cancleRelations(List<String> list);
	
	/**
	 * 导入存储数据
	 * @param transactionRelationDTOs
	 * @return
	 */
	ExecuteResult<ImportResultDTO> importTransactioRelation(List<TransactionRelationDTO> transactionRelationDTOs);
}
