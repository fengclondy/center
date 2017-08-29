package cn.htd.promotion.cpc.biz.dao;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import cn.htd.promotion.cpc.dto.response.WinningRecordListResDTO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by tangjiayong on 2017/8/22.
 */
@Repository("cn.htd.promotion.cpc.biz.dao.AwardRecordDAO")
public interface AwardRecordDAO {

    List<PromotionAwardDTO> getAwardRecordByPromotionId (@Param("params") Map<String, Object> params , @Param("page") Pager<PromotionAwardReqDTO> page);

    long getTotalAwardRecord(@Param("params") Map<String, Object> params);

    int updateLogisticsInfo(PromotionAwardReqDTO dto);

	/**
	 * 查询买家中奖
	 * @param winningRecordReqDTO
	 * @return
	 */
	public List<BuyerWinningRecordDMO> queryWinningRecord(BuyerWinningRecordDMO buyerWinningRecordDMO);
}
