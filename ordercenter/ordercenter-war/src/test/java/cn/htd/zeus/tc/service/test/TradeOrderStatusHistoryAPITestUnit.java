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
import cn.htd.zeus.tc.biz.dmo.OrderQueryListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderStatusHistoryListDMO;
import cn.htd.zeus.tc.biz.service.OrderQueryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class TradeOrderStatusHistoryAPITestUnit {

	@Resource
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	@Resource
	private OrderQueryService orderQueryService;


    @Before
    public void setUp() throws Exception
    {
    }

    /*
     * 根据订单号查询订单状态履历集合
     */
    @Test
    @Rollback(false)
    public void testSelectHistoryByOrderNo()
    {
    	try {
    		TradeOrderStatusHistoryReqDTO reqDTO = new  TradeOrderStatusHistoryReqDTO();
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setOrderNo("10017022310373900382");
			TradeOrderStatusHistoryListDMO resultDMO = tradeOrderStatusHistoryService.selectHistoryByOrderNo(reqDTO);
			String resultCode = resultDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

    /*
    * 根据订单号查询订单行状态履历集合
    */
   @Test
   @Rollback(false)
   public void testSelectOrderItemsHistoryByOrderNo()
   {
   	try {
   		TradeOrderStatusHistoryReqDTO reqDTO = new  TradeOrderStatusHistoryReqDTO();
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setOrderNo("10017022310373900382");
			TradeOrderItemsStatusHistoryListDMO resultDMO = tradeOrderStatusHistoryService.selectOrderItemsHistoryByOrderNo(reqDTO);
			String resultCode = resultDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
   	} catch (Exception e) {
		}
   }

   /*
   * 确认收货
   */
  @Test
  @Rollback(false)
  public void testUpdateOrderStatus()
  {
  	try {
  			UpdateOrderStatusReqDTO reqDTO = new  UpdateOrderStatusReqDTO();
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setOrderNo("10017022310373900382");
			reqDTO.setOrderStatus("61");
			reqDTO.setOrderStatusText("买家收货");
			UpdateOrderStatusResDTO resultDMO = tradeOrderStatusHistoryService.updateOrderStatus(reqDTO);
			String resultCode = resultDMO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
  	} catch (Exception e) {
		}
  }

  @Test
  @Rollback(false)
  public void testQueryListOrder()
  {
  	try {
			OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
			reqDTO.setBuyerCode("HTD_13125455");
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setStart(1);
			reqDTO.setRows(10);
			OrderQueryListDMO orderQueryDMO = orderQueryService.queryListOrder(reqDTO);
			String resultCode = orderQueryDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
  	} catch (Exception e) {
		}
  }

}
