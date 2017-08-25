package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainOverviewResDTO;
import cn.htd.promotion.cpc.dto.response.PromotonInfoResDTO;

public interface PromotionBargainInfoAPI {

	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(String messageId,
            String promotionId);
	
	public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page);
	
	public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(String sellerCode, Pager<String> page);
}
