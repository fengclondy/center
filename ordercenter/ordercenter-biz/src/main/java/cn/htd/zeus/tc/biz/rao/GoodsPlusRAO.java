package cn.htd.zeus.tc.biz.rao;

import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;

public interface GoodsPlusRAO {

	public OtherCenterResDTO<String> queryStock4JD(BatchGetStockReqDTO batchGetStockReqDTO,
			String messageId);

	public OtherCenterResDTO<String> queryProductStock4JD(BatchGetStockReqDTO batchGetStockReqDTO,
			String messageId);

	public OtherCenterResDTO<String> queryAccountAmount4JD(String messageId);
}
