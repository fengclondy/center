package cn.htd.goodscenter.service.middleware;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.SpringApplicationContextHolder;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.test.common.CommonTest;

public class MiddlewareInterfaceUtilTest extends CommonTest{
	
    @Resource
	private ItemSpuMapper itemSpuMapper;

	@Test
	public void testMiddleWareUtil_getToken(){
		String accessToken=MiddlewareInterfaceUtil.getAccessToken();
		System.out.println(accessToken);
		Assert.assertNotNull(accessToken);
	}
	
	@Test
	public void testQueryTotalStock_getTotalStock(){
		String accessToken=MiddlewareInterfaceUtil.getAccessToken();
		String itemCodeStr="17323,19010,39291,14156";
		String param="?supplierCode=0801&productCodes="+itemCodeStr+"&token="+accessToken;
		String responseJson=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_ITEM_STOCK_URL+param,Boolean.TRUE );
		Map map=(Map)JSONObject.toBean(JSONObject.fromObject(responseJson),Map.class);
		List list=(List)map.get("data");
		System.out.println(list);
	}
	
	@Test
	public void testGetMiddlewarePath(){
		String path=SpringApplicationContextHolder.getBean("middlewarePath");
		System.out.println(path);
	}
	
	@Test
	public void testQueryItemSpuBySpuCode(){
		try{
			ItemSpu spu=itemSpuMapper.queryItemSpuBySpuCode("123");
			System.out.println(spu);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMiddleWareUtil_getItemPrice(){
		String floorPrice=MiddlewareInterfaceUtil.findItemFloorPrice("0832", "20006447");
		System.out.println(floorPrice);
		Assert.assertNotNull(floorPrice);
	}
}
