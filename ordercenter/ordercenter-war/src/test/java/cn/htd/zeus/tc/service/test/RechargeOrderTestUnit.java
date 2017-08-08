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

import cn.htd.zeus.tc.biz.dmo.RechargeOrderListDMO;
import cn.htd.zeus.tc.biz.service.RechargeOrderService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderReqDTO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class RechargeOrderTestUnit {

	@Resource
	private RechargeOrderService rechargeOrderService;

    @Before
    public void setUp() throws Exception
    {
    }

    /*
     * 呼叫中心查询集合
     */
    @Test
    @Rollback(false)
    public void testInsertRechargeOrder()
    {
    	try {
    		RechargeOrderReqDTO rechargeOrderReqDTO = new  RechargeOrderReqDTO();
			rechargeOrderReqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			rechargeOrderReqDTO.setAmount(new BigDecimal(300));
			rechargeOrderReqDTO.setRechargeNo("40017030717451267747");
			rechargeOrderReqDTO.setOperaterCode("HTD10000001");
			rechargeOrderReqDTO.setOperaterName("测试");
			rechargeOrderReqDTO.setPayCode("ERP_PAY");
			rechargeOrderReqDTO.setPayStatus("SUCCESS");
			rechargeOrderReqDTO.setSupplierCode("0832");
			rechargeOrderReqDTO.setDepartmentCode("0832");
			EmptyResDTO rechargeOrderResDTO = rechargeOrderService.insertRechargeOrder(rechargeOrderReqDTO);
			String resultCode = rechargeOrderResDTO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

    @Test
    @Rollback(false)
    public void testSelectRechargeOrder()
    {
    	try {
    		RechargeOrderQueryReqDTO reqDTO = new  RechargeOrderQueryReqDTO();
    		reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
    		reqDTO.setMemberCode("HTD10000001");
    		RechargeOrderListDMO rechargeOrderResDTO = rechargeOrderService.selectRechargeOrder(reqDTO);
			String resultCode = rechargeOrderResDTO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

    @Test
    @Rollback(false)
    public void testUpdatePaymentOrderInfo()
    {
    	try {
    		PaymentOrderInfoReqDTO reqDTO = new  PaymentOrderInfoReqDTO();
    		reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
    		reqDTO.setPaymentResultStatus(1);
    		reqDTO.setRechargeOrderNo("4001703071724150740");
    		reqDTO.setResultCode("1");
    		reqDTO.setResultMessage("成功");
    		reqDTO.setTimestamp(DateUtil.getSystemTime());
    		/*EmptyResDTO rechargeOrderResDTO = rechargeOrderService.updatePaymentOrderInfo(reqDTO);
			String resultCode = rechargeOrderResDTO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());*/
    	} catch (Exception e) {
		}
    }
}
