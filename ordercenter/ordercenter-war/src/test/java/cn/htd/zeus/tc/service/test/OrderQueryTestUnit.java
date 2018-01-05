package cn.htd.zeus.tc.service.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.api.OrderQueryAPI;
import cn.htd.zeus.tc.biz.dmo.OrderQueryListDMO;
import cn.htd.zeus.tc.biz.service.OrderQueryService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.OrderQueryDetailResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQuerySupprMangerReqDTO;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class OrderQueryTestUnit {

	@Resource
	private OrderQueryService orderQueryService;
	
	@Resource
	private OrderQueryAPI orderQueryAPI;

    @Before
    public void setUp() throws Exception
    {
    }

    /*
     * 呼叫中心查询集合
     */
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

    /*
     * 订单中心查询订单列表
     */
    @Test
    @Rollback(false)
    public void testSelectOrderByBuyerId()
    {
    	try {
			OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
			reqDTO.setBuyerCode("htd657126");
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setStart(0);
			reqDTO.setRows(10);
			OrdersQueryParamListResDTO orderQueryDMO = orderQueryAPI.selectOrderByBuyerId(reqDTO);
			System.out.println(JSONObject.toJSONString(orderQueryDMO));
		/*	String resultCode = orderQueryDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());*/
    	} catch (Exception e) {
		}
    }

    /*
     * 订单中心根据条件查询订单列表
     */
    @Test
    @Rollback(false)
    public void testSelectOrderByTradeOrdersParam()
    {

    	try {
			OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
			reqDTO.setBuyerCode("htd20070007");
			reqDTO.setMessageId("15150551594280040784193");
			reqDTO.setOrderNo("1018010414272447369");
			reqDTO.setOrderDeleteStatus(0);
			reqDTO.setStart(0);
			reqDTO.setShopName("");
			reqDTO.setRows(10);
			OrderQueryListDMO orderQueryDMO = orderQueryService.selectOrderByTradeOrdersParam(reqDTO);
			String resultCode = orderQueryDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

    /*
     * 查询订单数量
     */
    @Test
    @Rollback(false)
    public void testSelectOrderCountByBuyerId()
    {
    	try {
			OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
			reqDTO.setBuyerCode("HTD_13125455");
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setOrderDeleteStatus(0);
			reqDTO.setStart(0);
			reqDTO.setRows(10);
			OrderQueryPageSizeResDTO resultDMO = orderQueryService.selectOrderCountByBuyerId(reqDTO);
			String resultCode = resultDMO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }

    /*
     * 查询订单详情
     */
    @Test
    @Rollback(false)
    public void testSelectOrderByPrimaryKey()
    {
    	try {
			OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
			reqDTO.setOrderNo("60017082810291800164");
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setBuyerCode("htd657126");
			OrderQueryDetailResDTO resultDMO = orderQueryAPI.selectOrderByPrimaryKey(reqDTO);
			System.out.println(JSONObject.toJSONString(resultDMO));
			/*String resultCode = resultDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());*/
    	} catch (Exception e) {
		}
    }

    @Test
    @Rollback(false)
    public void queryListOrderByCondition()
    {
    	try {
    		OrderQuerySupprMangerReqDTO reqDTO = new  OrderQuerySupprMangerReqDTO();
    		List<?> list = null;
			reqDTO.setMessageId(GenerateIdsUtil.generateId(null));
			reqDTO.setOrderStatus(list);
			OrderQueryListDMO orderQueryDMO = orderQueryService.queryListOrderByCondition(reqDTO);
			String resultCode = orderQueryDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());
    	} catch (Exception e) {
		}
    }
}
