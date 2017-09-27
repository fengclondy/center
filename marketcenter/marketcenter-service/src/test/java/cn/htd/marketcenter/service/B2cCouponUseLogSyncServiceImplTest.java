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
import cn.htd.marketcenter.dto.B2cCouponUseLogSyncDTO;

import com.alibaba.fastjson.JSONObject;

public class B2cCouponUseLogSyncServiceImplTest extends CommonTest{
	@Resource
	private B2cCouponUseLogSyncService b2cCouponUseLogSyncService;
	
	@Test
	@Rollback(false) 
	public void saveB2cCouponInfoSync() throws ParseException {
		B2cCouponUseLogSyncDTO b2cCouponUseLogSyncDTO = new B2cCouponUseLogSyncDTO();
		b2cCouponUseLogSyncDTO.setB2cActivityCode("f23423");
		b2cCouponUseLogSyncDTO.setB2cBuyerCode("23423");
		b2cCouponUseLogSyncDTO.setB2cBuyerCouponCode("1132345657i559");
		b2cCouponUseLogSyncDTO.setB2cCouponUsedAmount(new BigDecimal(12));
		b2cCouponUseLogSyncDTO.setUseType("1");
		b2cCouponUseLogSyncDTO.setCreateId(32544L);
		b2cCouponUseLogSyncDTO.setCreateName("fafasd");
		b2cCouponUseLogSyncDTO.setMessageId("1234454576544fa");
		b2cCouponUseLogSyncDTO.setB2cOrderNo("1235545655676");
		//b2cCouponUseLogSyncDTO.setMemberCode("htd_345433");
		b2cCouponUseLogSyncDTO.setMemberId("1");
		ExecuteResult<String> res = b2cCouponUseLogSyncService.saveB2cCouponUseLogSync(b2cCouponUseLogSyncDTO);
		System.out.print(JSONObject.toJSONString(res));
	}
	
}
