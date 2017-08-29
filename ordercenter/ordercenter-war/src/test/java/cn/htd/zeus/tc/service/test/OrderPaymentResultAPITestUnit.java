package cn.htd.zeus.tc.service.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderPaymentResultAPI;
import cn.htd.zeus.tc.biz.dmo.OrderPayResultInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultDMO;
import cn.htd.zeus.tc.biz.dmo.OrderPaymentResultListDMO;
import cn.htd.zeus.tc.biz.service.OrderPaymentResultService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class OrderPaymentResultAPITestUnit {

	@Resource
	private OrderPaymentResultService orderPaymentResultService;
	
	@Resource
	private OrderPaymentResultAPI orderPaymentResultAPI;

    @Before
    public void setUp() throws Exception
    {
    }

    /*
     * 查询支付结果
     */
    @Test
    @Rollback(false)
    public void testSelectPaymentResultByTradeNo()
    {
    	try {
    		OrderPaymentResultReqDTO reqDTO = new  OrderPaymentResultReqDTO();
    		String batchPayInfos = "[{\"amount\":\"0.00\",\"distributionStatus\":\"PROFIT_INIT\",\"merchantOrderNo\":\"60017082810291800164\",\"tradeStatus\":\"SUCCESS\",\"tradeType\":\"BALANCE_PAY\"}]";
    		reqDTO.setBatchPayInfos(batchPayInfos);
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			OrderPaymentResultListResDTO resultDMO = orderPaymentResultAPI.selectPaymentResultByTradeNo(reqDTO);
			//OrderPaymentResultListDMO resultDMO = orderPaymentResultAPI.selectPaymentResultByTradeNo(reqDTO);
			/*String resultCode = resultDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());*/
			System.out.println(JSONObject.toJSONString(resultDMO));
    	} catch (Exception e) {
		}
    }

    /*
     * 支付回调—更新订单状态、订单行状态、订单状态履历表、订单行状态履历表、新增分销单表、新增京东表
     */
    @Test
    @Rollback(false)
    public void testUpdateOrderStatusByTradeNo()
    {
    	try {
    		OrderPayResultInfoDMO reqDTO = new  OrderPayResultInfoDMO();
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setPayStatus("1");
			reqDTO.setPayType("1");
			reqDTO.setOrderPayAmount(new BigDecimal("100.00"));
			reqDTO.setOrderNo("10017022310373900382");
			reqDTO.setBuyerCode("HTD_13125455");
			OrderPaymentResultDMO resultDMO = orderPaymentResultService.updateOrderStatusByTradeNo(reqDTO);
			String resultCode = resultDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

}
