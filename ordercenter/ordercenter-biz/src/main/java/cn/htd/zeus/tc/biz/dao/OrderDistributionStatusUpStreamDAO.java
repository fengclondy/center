package cn.htd.zeus.tc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;

@Repository("cn.htd.zeus.tc.biz.dao.OrderDistributionStatusUpStreamDAO")
public interface OrderDistributionStatusUpStreamDAO {
	
	public OrderCompensateERPDMO selectTradeOrderErpDistribution();
}
