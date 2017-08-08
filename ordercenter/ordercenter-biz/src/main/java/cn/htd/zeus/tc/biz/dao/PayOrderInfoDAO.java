package cn.htd.zeus.tc.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;

@Repository("cn.htd.zeus.tc.dao.PayOrderInfoDAO")
public interface PayOrderInfoDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PayOrderInfoDMO record);

    int insertSelective(PayOrderInfoDMO record);

    PayOrderInfoDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayOrderInfoDMO record);
    
    int updateByDownOrderNo(PayOrderInfoDMO record);

    int updateByPrimaryKey(PayOrderInfoDMO record);

    int updateByRechargeOrderNo(PayOrderInfoDMO record);
    
    int updatePayorderinfoByOrderNoBrandIdClassCode(PayOrderInfoDMO record);

    PayOrderInfoDMO selectByRechargeOrderNo(String rechargeOrderNo);

    /*
	 * 查询收付款待下行信息表
	 */
	public List<PayOrderInfoDMO> selectPayOrderFromPayOrderInfo(Map paramMap);
	
	/*
	 * 更新下行次数
	 */
	public int updateByRechargeOrderNoLockNo(PayOrderInfoDMO record);
	
	/*
	 * 查询收付款订单状态
	 */
	public PayOrderInfoDMO selectPayOrderByOrderNo(PayOrderInfoDMO record);
	
	
	public List<PayOrderInfoDMO> selectBrandCodeAndClassCodeByOrderNo(String orderNo);
	
	public PayOrderInfoDMO selectPayOrderInfoByOrderNo(PayOrderInfoDMO record);
	
	public List<PayOrderInfoDMO> selectPostStrikeaExceptionOrdersList(Map paramMap);
}