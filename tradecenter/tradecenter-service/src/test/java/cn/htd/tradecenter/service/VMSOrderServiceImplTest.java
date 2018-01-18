package cn.htd.tradecenter.service;

import cn.htd.common.Pager;
import cn.htd.tradecenter.common.utils.PaySDK;
import cn.htd.tradecenter.common.utils.SettlementUtils;
import cn.htd.tradecenter.dao.TradeOrderSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDetailDAO;
import cn.htd.tradecenter.dao.TradeSettlementWithdrawDAO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;
import cn.htd.tradecenter.service.convert.TradeSettlementConvert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

public class VMSOrderServiceImplTest {

    private VMSOrderService vmsOrderService;

    ApplicationContext ctx;

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        vmsOrderService = (VMSOrderService) ctx.getBean("vmsOrderService");
    }

    @Test
    public void queryVMSpendingOrderByConditionTest(){
        VenusTradeOrdersQueryInDTO conditionDTO = new VenusTradeOrdersQueryInDTO();
        Pager<VenusTradeOrdersQueryInDTO> pager = new Pager<VenusTradeOrdersQueryInDTO>();
        vmsOrderService.queryVMSpendingOrderByCondition(conditionDTO, pager);
    }
}
