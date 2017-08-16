package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerBargainRecordDAO")
public interface BuyerBargainRecordDAO {

	public List<BuyerBargainRecordDMO> getBuyerBargainRecordByBargainCode(String bargainCode);
}
