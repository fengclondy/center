package cn.htd.basecenter.dao;

import java.util.List;

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.common.dao.orm.BaseDAO;

public interface TransactionRelationDAO extends BaseDAO<TransactionRelation>{
	
	
	/**
	 * 更新关联交易
	 * @param helpDocCategoryDTO
	 */
	void updateTransactionRelation(TransactionRelationDTO transactionRelationDTO);
	
	
	/**
	 * 根据查询关联交易
	 * @param transactionRelationDTO
	 * @return
	 */
	List<TransactionRelation> getTransactionRelationByParams(TransactionRelationDTO transactionRelationDTO);
	
	
}
