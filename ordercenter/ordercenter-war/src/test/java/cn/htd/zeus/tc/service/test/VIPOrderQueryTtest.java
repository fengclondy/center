package cn.htd.zeus.tc.service.test;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.api.OrderQueryAPI;
import cn.htd.zeus.tc.dto.response.OrdersQueryVIPListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrdersQueryVIPListReqDTO;

import com.alibaba.fastjson.JSONObject;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class VIPOrderQueryTtest {
	
	@Resource
	private OrderQueryAPI orderQueryAPI;
	
    @Before  
    public void setUp() throws Exception 
    {  
    }
    
    @Test
    @Rollback(false) 
    public void testOrderCreate()
    {
    	try {
    		OrdersQueryVIPListReqDTO request = new OrdersQueryVIPListReqDTO();
    		request.setEndTime("2017-03-22 14:08:20");
    		request.setStartTime("2016-03-22 14:08:20");
    		request.setPage(3);
    		request.setRows(5);
    		OrdersQueryVIPListResDTO res = orderQueryAPI.selectVipOrder(request);
    		System.out.println("vip::::::::::;;;"+JSONObject.toJSONString(res));
    		
    	} catch (Exception e) {
		}
    }
}