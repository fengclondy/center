package cn.htd.zeus.tc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.TradeOrderVipDownInfoDMO;
@Repository("cn.htd.zeus.tc.dao.TradeOrderVipDownInfoDAO")
public interface TradeOrderVipDownInfoDAO {
    int deleteByPrimaryKey(Long id);

    int insert(TradeOrderVipDownInfoDMO record);

    int insertSelective(TradeOrderVipDownInfoDMO record);

    TradeOrderVipDownInfoDMO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeOrderVipDownInfoDMO record);

    int updateByPrimaryKey(TradeOrderVipDownInfoDMO record);
}