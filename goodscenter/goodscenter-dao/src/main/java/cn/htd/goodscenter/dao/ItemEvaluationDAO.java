package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemEvaluation;
import cn.htd.goodscenter.dto.ItemEvaluationDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryInDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryOutDTO;
import cn.htd.goodscenter.dto.ItemEvaluationReplyDTO;
import cn.htd.goodscenter.dto.ItemEvaluationTotalQueryDTO;

/**
 * <p>
 * Description: [商品评价DAO]
 * </p>
 */
public interface ItemEvaluationDAO extends BaseDAO<ItemEvaluation> {
	/**
	 * 
	 * <p>
	 * Discription:[添加商品评价]
	 * </p>
	 */
	void insertItemEvaluation(ItemEvaluationDTO itemEvaluationDTO);

	/**
	 * <p>
	 * Discription:[根据评价信息唯一标识，修改评价状态]
	 * </p>
	 * 
	 * @param itemId    唯一标识
	 * @param flag      标记：0在用；1删除；2屏蔽
	 */
	void updFlagItemEvaluationById(@Param("itemId") Long itemId, @Param("flag") Integer flag);

	/**
	 * 
	 * <p>
	 * Discription:[添加商品评价回复]
	 * </p>
	 */
	void insertItemEvaluationReply(ItemEvaluationReplyDTO itemEvaluationReplyDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询评价记录]
	 * </p>
	 */
	List<ItemEvaluationQueryOutDTO> queryItemEvaluationList(@Param("entity") ItemEvaluationQueryInDTO entity, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[查询总条数]
	 * </p>
	 */
	Long queryCount(@Param("entity") ItemEvaluationQueryInDTO entity);

	/**
	 * 
	 * <p>
	 * Discription:[统计 对店铺/对买家 的评价]
	 * </p>
	 */
	List<ItemEvaluationTotalQueryDTO> queryItemEvaluationTotal(@Param("entity") ItemEvaluationQueryInDTO entity);

	/**
	 * 查询卖家回复集合
	 */

	List<ItemEvaluationReplyDTO> queryItemEvaluationReplyList(@Param("itemEvaluationReplyDTO") ItemEvaluationReplyDTO itemEvaluationReplyDTO, @Param("page") Pager page);

	/**
	 * 查询卖家回复总数
	 */
	Long queryTotal(@Param("itemEvaluationReplyDTO") ItemEvaluationReplyDTO itemEvaluationReplyDTO);
	
	
	List<ItemEvaluationQueryOutDTO> querySkuEvaluationBySyncTime(@Param("syncTime") Date syncTime);

}
