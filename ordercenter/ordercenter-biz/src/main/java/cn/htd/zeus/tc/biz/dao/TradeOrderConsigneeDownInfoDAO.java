package cn.htd.zeus.tc.biz.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderConsigneeDownInfoDMO;

@Repository("cn.htd.zeus.tc.biz.dao.TradeOrderConsigneeDownInfoDAO")
public interface TradeOrderConsigneeDownInfoDAO {

    int insertTradeOrderConsigneeDownInfo(TradeOrderConsigneeDownInfoDMO record);

    List<TradeOrderConsigneeDownInfoDMO> selectTradeOrderConsigneeDownInfoList(Map paramMap);

    int updateTradeOrderConsigneeDownInfo(TradeOrderConsigneeDownInfoDMO record);
    
    TradeOrderConsigneeDownInfoDMO selectTradeOrderConsigneeDownInfo(TradeOrderConsigneeDownInfoDMO record);

}