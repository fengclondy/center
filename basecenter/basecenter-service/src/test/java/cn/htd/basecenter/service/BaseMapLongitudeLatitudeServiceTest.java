package cn.htd.basecenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.basecenter.domain.BaseMapLongitudeLatitude;
import cn.htd.common.ExecuteResult;

public class BaseMapLongitudeLatitudeServiceTest {
	 	ApplicationContext ctx;
	    
	 	private BaseMapLongitudeLatitudeService baseMapLongitudeLatitudeService;


	    @Before
	    public void setUp() throws Exception {
	        ctx = new ClassPathXmlApplicationContext("test.xml");
	        baseMapLongitudeLatitudeService = (BaseMapLongitudeLatitudeService) ctx.getBean("baseMapLongitudeLatitudeService");
	    }
	    
	    @Test
	    public void testAddMapLongitudeAndLatitude(){
	    	BaseMapLongitudeLatitude baseMapLongitudeLatitude=new BaseMapLongitudeLatitude();
	    	baseMapLongitudeLatitude.setAreaCode("11");
	    	baseMapLongitudeLatitude.setType(1L);
	    	
	    	baseMapLongitudeLatitude.setData("sdfds sdfd sdfd 123");
	    	
	    	baseMapLongitudeLatitudeService.addMapLongitudeAndLatitude(baseMapLongitudeLatitude);
	    }
	    
	    @Test
	    public void testQueryMapLongitudeAndLatitudeByAreaCode(){
	    	ExecuteResult<BaseMapLongitudeLatitude> r=baseMapLongitudeLatitudeService.queryMapLongitudeAndLatitudeByAreaCode(1, "11");
	    	
	    	Assert.assertTrue(r.isSuccess());
	    	
	    	System.out.println(r.getResult().getData());
	    	
	    }
}
