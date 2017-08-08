package cn.htd.zeus.tc.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCreateInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderCompensateERPService;
import cn.htd.zeus.tc.biz.service.OrderCreateService;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class FiveOneTestUnit {
	
	@Resource
	private OrderCompensateERPService orderCompensateERPService;
	
    @Before  
    public void setUp() throws Exception 
    {  
    }
    
    @Test
    @Rollback(false) 
    public void testOrderCreate()
    {
    	try {

    		OrderCompensateERPCallBackReqDTO reqInfo = new OrderCompensateERPCallBackReqDTO();
    		reqInfo.setBrandCode("77772");	
    		reqInfo.setCategroyCode("15");
    		reqInfo.setErrormessage("错误");
    		reqInfo.setJL_ComPanyCode("21");
    		reqInfo.setResult("1");
    		reqInfo.setOrderCode("14906891467770012128410");
    		OrderCompensateERPCallBackDMO resDMO = orderCompensateERPService.executeOrderCompensateERPCallBack(reqInfo);
    		System.out.println("res::::::::::;;;"+JSONObject.toJSONString(resDMO));
    		
    	} catch (Exception e) {
		}
    }
}
