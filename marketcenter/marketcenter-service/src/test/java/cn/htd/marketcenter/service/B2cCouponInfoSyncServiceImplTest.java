package cn.htd.marketcenter.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.CommonTest;
import cn.htd.marketcenter.dto.B2cCouponInfoSyncDTO;

import com.alibaba.fastjson.JSONObject;

public class B2cCouponInfoSyncServiceImplTest extends CommonTest{
	@Resource
	private B2cCouponInfoSyncService b2cCouponInfoSyncService;
	
	@Test
	@Rollback(false) 
	public void saveB2cCouponInfoSync() throws ParseException {
		B2cCouponInfoSyncDTO b2cCouponInfoSyncDTO = new B2cCouponInfoSyncDTO();
		b2cCouponInfoSyncDTO.setB2cActivityCode("f23423");
		b2cCouponInfoSyncDTO.setCouponDescribe("优惠券描述");
		b2cCouponInfoSyncDTO.setCouponName("优惠券名称");
		b2cCouponInfoSyncDTO.setCouponProvideType("3");
	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date1 =  sf.parse("2017-08-25 12:12:12");
		b2cCouponInfoSyncDTO.setCouponStartTime(date1);
		Date date2 =  sf.parse("2017-10-25 12:12:13");
		b2cCouponInfoSyncDTO.setCouponEndTime(date2);
		b2cCouponInfoSyncDTO.setCouponType("1");
		b2cCouponInfoSyncDTO.setDiscountPercent(10);
		b2cCouponInfoSyncDTO.setDiscountThreshold(new BigDecimal(100));
		b2cCouponInfoSyncDTO.setCreateId(1335L);
		b2cCouponInfoSyncDTO.setCreateName("张丁");
		b2cCouponInfoSyncDTO.setMessageId("2332432432432432");
		ExecuteResult<String> res = b2cCouponInfoSyncService.saveB2cCouponInfoSync(b2cCouponInfoSyncDTO);
		System.out.print(JSONObject.toJSONString(res));
	}
	
}
