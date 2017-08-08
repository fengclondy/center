package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.TransactionRelation;
import com.bjucloud.contentcenter.dto.TransactionRelationDTO;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface TransactionRelationDAO extends BaseDAO<TransactionRelation>{
	
	/**
	 * 添加关联交易
	 * @param transactionRelationDTO
	 */
	int addTransactionRelation(TransactionRelationDTO transactionRelationDTOs);
	
	/**
	 * 删除关联交易
	 * @param transactionRelationDTO
	 */
	int deleteTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 更新关联交易
	 * @param helpDocCategoryDTO
	 */
	int updateTransactionRelation(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 未导入更新存在的关联交易
	 * @param helpDocCategoryDTO
	 */
	int updateTransactionRelationForImport(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 根据条件查询关联交易总数
	 * @param helpDocCategoryDTO
	 * @return
	 */
	int getTransactionRelationCount(TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 分页查询关联交易
	 * @param page
	 * @param transactionRelationDTO
	 * @return
	 */
	List<TransactionRelation> getTransactionRelationForPage(@Param("page")Pager<TransactionRelationDTO> page, @Param("dto")TransactionRelationDTO transactionRelationDTO);
	
	/**
	 * 根据查询关联交易
	 * @param transactionRelationDTO
	 * @return
	 */
	List<TransactionRelationDTO> getTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	int updateTransactionRelationForImportByBuyerName(TransactionRelationDTO transactionRelationDTO);
}
