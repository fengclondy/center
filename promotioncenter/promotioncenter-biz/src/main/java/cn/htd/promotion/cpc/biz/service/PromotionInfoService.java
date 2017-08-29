package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

public interface PromotionInfoService {

	public DataGrid<PromotionInfoDTO> getPromotionGashaponByInfo(PromotionInfoReqDTO promotionInfoReqDTO,Pager<PromotionInfoReqDTO> pager);
}
