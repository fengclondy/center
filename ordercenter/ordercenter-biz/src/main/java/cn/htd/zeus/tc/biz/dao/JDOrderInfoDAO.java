package cn.htd.zeus.tc.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;

@Repository("cn.htd.zeus.tc.biz.dao.JDOrderInfoDAO")
public interface JDOrderInfoDAO {
    int deleteByPrimaryKey(Long id);

    int insert(JDOrderInfoDMO record);

    int insertSelective(JDOrderInfoDMO record);

    JDOrderInfoDMO selectByPrimaryKey(Long id);

    int updateJDOrderInfoByOrderNo(JDOrderInfoDMO record);

    int updateByPrimaryKey(JDOrderInfoDMO record);
    
    /*
     * 预售下行查询接口
     */
    public List<JDOrderInfoDMO> selectERPOrderNOFromJDOrderInfo(Map paramMap);
    
    /*
     * 京东抛单查询接口
     */
    public List<JDOrderInfoDMO> selectJDOrderNOFromJDOrderInfo(Map paramMap);
    
    public int updateERPJDInfo(JDOrderInfoDMO jdOrderInfoDMO);
    
    public int updateJDInfo(JDOrderInfoDMO jdOrderInfoDMO);
    
    public List<JDOrderInfoDMO> selectPreSalesOrderExceptionOrdersList(Map paramMap);
}