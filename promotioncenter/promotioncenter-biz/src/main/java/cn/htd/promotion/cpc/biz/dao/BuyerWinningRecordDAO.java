package cn.htd.promotion.cpc.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;

/**
 * Created by tangjiayong on 2017/8/22.
 */
@Repository("cn.htd.promotion.cpc.biz.dao.buyerWinningDAO")
public interface BuyerWinningRecordDAO {

    /**
     * 根据条件查询中奖记录列表
     *
     * @param params
     * @param page
     * @return
     */
    public List<BuyerWinningRecordDMO> getAwardRecordByPromotionId(@Param("params") Map<String, Object> params,
            @Param("page") Pager<PromotionAwardReqDTO> page);

    /**
     * 根据条件查询中奖记录数
     *
     * @param params
     * @return
     */
    public long getTotalAwardRecord(@Param("params") Map<String, Object> params);

    /**
     * 更新中奖记录物流信息
     *
     * @param dto
     * @return
     */
    public int updateLogisticsInfo(PromotionAwardReqDTO dto);

    /**
     * 查询买家中奖
     *
     * @param buyerWinningRecordDMO
     * @return
     */
    public List<BuyerWinningRecordDMO> queryWinningRecord(BuyerWinningRecordDMO buyerWinningRecordDMO);

    /**
     * 添加粉丝中奖记录
     * @param buyerWinningRecordDMO
     * @return
     */
    public int addBuyerWinningRecord(BuyerWinningRecordDMO buyerWinningRecordDMO);

	public List<PromotionAwardDTO> query4Task(@Param("entity") PromotionAwardDTO condition,
            @Param("page") Pager<PromotionAwardDTO> pager);
}
