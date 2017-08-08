package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.ShopSalesAnalysisDayReportDMO;

@Repository("cn.htd.zeus.tc.biz.dao.ShopSalesAnalysisDayReportDAO")
public interface ShopSalesAnalysisDayReportDAO {

    int insertSelective(ShopSalesAnalysisDayReportDMO record);

    List<ShopSalesAnalysisDayReportDMO> selectByConditions(ShopSalesAnalysisDayReportDMO shopSalesAnalysisDayReportDMO);

    int updateByUniIndex(ShopSalesAnalysisDayReportDMO record);
}