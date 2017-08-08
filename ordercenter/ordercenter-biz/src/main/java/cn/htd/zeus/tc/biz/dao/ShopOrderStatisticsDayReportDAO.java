package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.ShopOrderStatisticsDayReportDMO;

@Repository("cn.htd.zeus.tc.biz.dao.ShopOrderStatisticsDayReportDAO")
public interface ShopOrderStatisticsDayReportDAO {

	int insertSelective(ShopOrderStatisticsDayReportDMO record);

	List<ShopOrderStatisticsDayReportDMO> selectByConditions(
			ShopOrderStatisticsDayReportDMO shopOrderStatisticsDayReportDMO);

	int updateByUniIndex(ShopOrderStatisticsDayReportDMO record);
}