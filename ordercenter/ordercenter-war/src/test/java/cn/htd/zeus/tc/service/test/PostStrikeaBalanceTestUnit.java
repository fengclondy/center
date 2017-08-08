package cn.htd.zeus.tc.service.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.service.PostStrikeaBalanceService;
import cn.htd.zeus.tc.common.enums.MiddleWareEnum;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class PostStrikeaBalanceTestUnit {

	@Resource
	private PostStrikeaBalanceService postStrikeaBalanceService;

	private static final byte ordertype = 0;

	DecimalFormat df1 = new DecimalFormat("0.0000");

    @Before
    public void setUp() throws Exception
    {
    }

    /*
     * 呼叫中心查询集合
     */
    @Test
    @Rollback(false)
    public void executeDownPostStrikeaBalance()
    {
    	try {
    		PayOrderInfoDMO task = new  PayOrderInfoDMO();
			task.setProductCode(MiddleWareEnum.PRODUCTCODE_RECHARGE.getCode());
			task.setDownOrderNo("40017030717271640734");
			task.setOrderType(ordertype);
			task.setMemberCode("HTD10000001");
			task.setDepartmentCode("0832");
			task.setBrandCode("00028");
			task.setClassCode("50");
			task.setPayType(new Byte(MiddleWareEnum.PAY_CODE_PLUS.getCode().toString()));
			task.setDepartmentCode("0832");
			task.setAmount(new BigDecimal("100"));

			PayOrderInfoDMO[] ss = {task};
			postStrikeaBalanceService.executeDownPostStrikeaBalance(ss);
    	} catch (Exception e) {
		}
    }

}
