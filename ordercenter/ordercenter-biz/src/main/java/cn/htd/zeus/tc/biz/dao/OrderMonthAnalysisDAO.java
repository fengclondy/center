package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;

@Repository("cn.htd.zeus.tc.dao.OrderMonthAnalysisDAO")
public interface OrderMonthAnalysisDAO {

	public List<OrderSalesMonthInfoDMO> queryShopInfo();

	public OrderSalesMonthInfoDMO queryOrderMonthAnalysisInfo(
			@Param("sellerCode") String sellerCode, @Param("firstDay") String firstDay,
			@Param("lastDay") String lastDay);

	public void insertOrderMonthAnalysisInfo(
			@Param("orderSalesMonthInfoDMO") OrderSalesMonthInfoDMO orderSalesMonthInfoDMO);

	public int updateOrderMonthAnalysisInfo(
			@Param("orderSalesMonthInfoDMO") OrderSalesMonthInfoDMO orderSalesMonthInfoDMO);

}
