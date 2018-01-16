package cn.htd.marketcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.TimelimitedSkuCountDTO;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lh on 2016/12/08
 */
public class TimelimitedSkuInfo4VMSServiceImplTest {
    ApplicationContext ctx;
    private TimelimitedSkuInfo4VMSService timelimitedSkuInfo4VMSService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        timelimitedSkuInfo4VMSService = (TimelimitedSkuInfo4VMSService) ctx.getBean("timelimitedSkuInfo4VMSService");
    }

    @Test
    public void getSkuTimelimitedAllCountTest() {
        ExecuteResult<TimelimitedSkuCountDTO> result =
                timelimitedSkuInfo4VMSService.getSkuTimelimitedAllCount("1234567890", "1000057381");
        System.out.println("aaaaa:" + JSON.toJSONString(result));
    }
}
