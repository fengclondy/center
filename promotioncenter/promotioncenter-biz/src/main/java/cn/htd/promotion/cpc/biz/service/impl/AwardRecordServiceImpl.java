package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.AwardRecordDAO;
import cn.htd.promotion.cpc.biz.service.AwardRecordService;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjiayong on 2017/8/22.
 */
@Service("awardRecordService")
public class AwardRecordServiceImpl implements AwardRecordService {

    private static final Logger logger = LoggerFactory.getLogger(AwardRecordServiceImpl.class);

    @Resource
    AwardRecordDAO awardRecordDAO;

    @Override
    public DataGrid<PromotionAwardDTO> getAwardRecordByPromotionId(PromotionAwardReqDTO dto,String messageId) {
        logger.info("promotionId{}:AwardRecordServiceImpl.getAwardRecordByPromotionId（）方法,入参{}",messageId , JSON.toJSONString(dto));

        //分页
        Pager<PromotionAwardReqDTO> page = new Pager<PromotionAwardReqDTO>();
        page.setPage(dto.getPage());
        page.setRows(dto.getPageSize());

        if (dto.getPage() < 1) {
            page.setPage(1);
        }
        if (dto.getPageSize() < 1) {
            page.setRows(10);
        }

        // 开始时间
        String startTime = dto.getWinningStartTime();
        // 结束时间
        String endTime = dto.getWinningEndTime();

        if(null != startTime && !"".equals(startTime)){
            dto.setWinningStartTime(startTime + " 00:00:00");
        }
        if(null != endTime && !"".equals(endTime)){
            dto.setWinningEndTime(endTime + " 23:59:59");
        }

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("buyerCode",dto.getBuyerCode());
        param.put("buyerName",dto.getBuyerName());
        param.put("winningStartTime",dto.getWinningStartTime());
        param.put("winningEndTime",dto.getWinningEndTime());
        param.put("rewardType",dto.getRewardType());
        param.put("promotionId",dto.getPromotionId());

        DataGrid<PromotionAwardDTO> dataGrid = new DataGrid<PromotionAwardDTO>();
        try {
            List<PromotionAwardDTO> list = awardRecordDAO.getAwardRecordByPromotionId(param, page);
            long count = awardRecordDAO.getTotalAwardRecord(param);
            dataGrid.setTotal(count);
            dataGrid.setRows(list);
        } catch (Exception e) {
            logger.error("messageId{}:执行方法【getAwardRecordByPromotionId】报错：{}", messageId ,e.toString());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }
}
