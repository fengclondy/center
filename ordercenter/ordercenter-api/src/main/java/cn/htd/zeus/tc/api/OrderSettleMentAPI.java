package cn.htd.zeus.tc.api;

import java.util.List;

import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.response.OrderFreightInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

public interface OrderSettleMentAPI {

	public OrderSettleMentResDTO getOrderSettleMentInfo(OrderSettleMentReqDTO orderSettleMentReqDTO);

	public int getJDproductStock(String outerSkuId, String sellerCode, String messageId);

	public OtherCenterResDTO<String> getJDproductStock4xj(String outerSkuId, String sellerCode, String messageId);

	public OrderFreightInfoDTO queryGoodsFreight4seckill(List<OrderCreateItemListInfoReqDTO> orderItemList,
			String cityCode);

}
