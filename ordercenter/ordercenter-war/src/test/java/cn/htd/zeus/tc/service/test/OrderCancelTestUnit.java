package cn.htd.zeus.tc.service.test;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderCancelService;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class OrderCancelTestUnit {
	
	@Resource
	private OrderCancelService orderCancelService;
	
    @Before  
    public void setUp() throws Exception 
    {  
    }
    
    @Test
    @Rollback(false) 
    public void testOrderCancel()
    {
    	try {
			OrderCancelInfoReqDTO orderCancelInfoReqDTO = new OrderCancelInfoReqDTO();
			orderCancelInfoReqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			orderCancelInfoReqDTO.setOrderCancelMemberId("13125455");
			orderCancelInfoReqDTO.setMemberCode("HTD_13125455");
			orderCancelInfoReqDTO.setOrderCancelReason("取消原因");
			orderCancelInfoReqDTO.setOrderCancelMemberName("二娃");
			orderCancelInfoReqDTO.setOrderNo("10017022217190000378");
			TradeOrdersDMO tradeOrdersDMO = orderCancelService.orderCancel(orderCancelInfoReqDTO);
			String resultCode = tradeOrdersDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());  
    	} catch (Exception e) {
		}
    }
    @Test
    @Rollback(false) 
    public void testOrderDelete()
    {
    	try {
			OrderCancelInfoReqDTO orderCancelInfoReqDTO = new OrderCancelInfoReqDTO();
			orderCancelInfoReqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			orderCancelInfoReqDTO.setMemberCode("HTD_13125455");
			orderCancelInfoReqDTO.setOrderNo("10017022217190000378");
			orderCancelInfoReqDTO.setIsDeleteStatus(OrderStatusEnum.ORDER_RESTORE_DELETE_STATUS.getCode());
		/*	TradeOrdersDMO tradeOrdersDMO = orderCancelService.ordeDelete(orderCancelInfoReqDTO);
			String resultCode = tradeOrdersDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());  */
    	} catch (Exception e) {
		}
    }
}
