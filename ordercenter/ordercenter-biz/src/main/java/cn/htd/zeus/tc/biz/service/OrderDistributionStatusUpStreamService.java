package cn.htd.zeus.tc.biz.service;

public interface OrderDistributionStatusUpStreamService {
	
	/*
	 * 订单中心监听中间件后保存订单相关的状态
	 * @param distributionId
	 * @param distributionStatus
	 * @return void
	 */
	public void orderDistributionStatusUpStream(String distributionId,String distributionStatus);
	
	/*
	 * JD订单中心监听中间件后保存订单相关的状态
	 */
	public void jdOrderDistributionStatusUpStream(String orderNo);
}
