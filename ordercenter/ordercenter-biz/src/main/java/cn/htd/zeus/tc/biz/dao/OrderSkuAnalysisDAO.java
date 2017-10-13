package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderSkuAnalysisDMO;

@Repository("cn.htd.zeus.tc.dao.OrderSkuAnalysisDAO")
public interface OrderSkuAnalysisDAO {

	public List<OrderSkuAnalysisDMO> queryShopInfo();

	public List<OrderSkuAnalysisDMO> queryOrderSkuInfo(@Param("sellerCode") String sellerCode,
			@Param("lastDayStart") String lastDayStart, @Param("lastDayEnd") String lastDayEnd);

	public void insertOrderSkuInfo(
			@Param("orderSkuAnalysisDMO") OrderSkuAnalysisDMO orderSkuAnalysisDMO);

	public int updateOrderSkuInfo(
			@Param("orderSkuAnalysisDMO") OrderSkuAnalysisDMO orderSkuAnalysisDMO);

}
