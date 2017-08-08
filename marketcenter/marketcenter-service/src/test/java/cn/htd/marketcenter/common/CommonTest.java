package cn.htd.marketcenter.common;

import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.service.handle.BuyerCouponHandle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/test.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class CommonTest {
    ApplicationContext act = null;
    private GeneratorUtils generator;

    @Before
    public void setUp() throws Exception {
        act = new ClassPathXmlApplicationContext("test.xml");
        generator = (GeneratorUtils) act.getBean("generatorUtils");
    }

    @Test
    public void generatePromotionIdTest() throws Exception {
        for (int i = 0; i < 110; i ++) {
            System.out.println(generator.generatePromotionLevelCode("x"));
        }
    }
}
