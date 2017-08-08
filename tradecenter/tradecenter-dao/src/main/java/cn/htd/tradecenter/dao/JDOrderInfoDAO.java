package cn.htd.tradecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.domain.JDOrderInfo;

public interface JDOrderInfoDAO extends BaseDAO<JDOrderInfo> {

	/**
	 * 根据订单编号更新订单行预售下行状态
	 * 
	 * @param jdOrderInfo
	 */
	public int updateJDOrderErpInfoByOrderNo(JDOrderInfo jdOrderInfo);

}