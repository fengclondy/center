package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;

public interface StockChangeService {

	public void checkAndChangeStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);

}
