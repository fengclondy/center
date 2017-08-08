package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.OrderFeightPromotionDMO;
import cn.htd.zeus.tc.biz.dmo.OrderFreightCalcuRuleDMO;
import cn.htd.zeus.tc.biz.dmo.OrderFreightInfoDMO;

@Repository("cn.htd.zeus.tc.biz.dao.OrderFreightDAO")
public interface OrderFreightDAO {

	public OrderFreightInfoDMO queryOrderFreightInfoByTemplateId(long templateId);

	public OrderFeightPromotionDMO queryOrderFreightPromotionInfoByTemplateId(long templateId);

	public List<OrderFreightCalcuRuleDMO> queryOrderFreightCalcuRuleByTemplateId(long templateId);

}
