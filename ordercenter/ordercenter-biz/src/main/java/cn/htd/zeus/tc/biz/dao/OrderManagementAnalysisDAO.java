package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderManagementAnalysisDMO;

@Repository("cn.htd.zeus.tc.dao.OrderManagementAnalysisDAO")
public interface OrderManagementAnalysisDAO {

	public List<OrderManagementAnalysisDMO> queryShopInfo();

	public OrderManagementAnalysisDMO queryOrderManagermentInfo(
			@Param("sellerCode") String sellerCode, @Param("lastDayStart") String lastDayStart,
			@Param("lastDayEnd") String lastDayEnd);

	public void insertOrderManagementInfo(
			@Param("orderManagementAnalysisDMO") OrderManagementAnalysisDMO orderManagementAnalysisDMO);

	public int updateOrderManagementInfo(
			@Param("orderManagementAnalysisDMO") OrderManagementAnalysisDMO orderManagementAnalysisDMO);
}
