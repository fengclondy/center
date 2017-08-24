package cn.htd.promotion.cpc.biz.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

@Service("promotionInfoService")
public class PromotionInfoServiceImpl implements PromotionInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionInfoServiceImpl.class);
	
	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Override
	public DataGrid<PromotionInfoDTO> getPromotionGashaponByInfo(
			PromotionInfoReqDTO promotionInfoReqDTO, Pager<PromotionInfoReqDTO> pager) {
        DataGrid<PromotionInfoDTO> dataGrid = new DataGrid<PromotionInfoDTO>();
        try {
            List<PromotionInfoDTO> list = promotionInfoDAO.getPromotionGashaponByInfo(promotionInfoReqDTO, pager);
            long count = promotionInfoDAO.getTotalPromotionGashaponByInfo(promotionInfoReqDTO);
            dataGrid.setTotal(count);
            dataGrid.setRows(list);
        } catch (Exception e) {
        	LOGGER.error("执行方法【queryTradeSettlement】报错：{}", e);
        	System.out.println(e);
            throw new RuntimeException(e);
        }
        return dataGrid;
	}
}
