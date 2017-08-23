package cn.htd.promotion.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;

public interface BuyerBargainRecordAPI {
	
	/**
	 * 根据砍价编号查询砍价记录
	 * @param bargainCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<List<BuyerBargainRecordResDTO>> getBuyerBargainRecordByBargainCode(String bargainCode,String messageId);
	
	/**
	 * 插入砍价记录
	 * @param buyerBargainRecord
	 * @return
	 */
	public ExecuteResult<Boolean> insertBuyerBargainRecord(BuyerBargainRecordReqDTO buyerBargainRecord);
	
	/**
	 * 查询改用户是否已经帮忙砍过
	 * @param bargainCode
	 * @param bargainPersonCode
	 * @return
	 */
	public ExecuteResult<Boolean> getThisPersonIsBargain(String bargainCode ,String bargainPersonCode, String messageId);
	
	/**
	 * 根据活动id查询该活动参与人数
	 * @param promotionId
	 * @return
	 */
	public ExecuteResult<Integer> queryPromotionBargainJoinQTY(String promotionId, String messageId);

}
