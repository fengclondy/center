package cn.htd.marketcenter.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/test.xml")
@TransactionConfiguration(transactionManager = "transactionManager",
        defaultRollback = false)
@Transactional
public class CommonTest {

    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);

    ApplicationContext act = null;
    private GeneratorUtils generator;
    private DictionaryUtils dictionary;

    @Before
    public void setUp() throws Exception {
        act = new ClassPathXmlApplicationContext("test.xml");
        generator = (GeneratorUtils) act.getBean("generatorUtils");
        dictionary = (DictionaryUtils) act.getBean("dictionaryUtils");
    }

    @Test
    public void generatePromotionIdTest() throws Exception {
        for (int i = 0; i < 110; i++) {
            System.out.println(generator.generatePromotionLevelCode("x"));
        }
    }

    @Test
    public void dictionaryTest() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<String>> workResultList = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            workResultList.add(executorService.submit(new TestRunableTask()));

        }
        for (Future<String> futureRst : workResultList) {
            futureRst.get();
        }
        logger.info("dictionaryTest---over");
    }

    public final class TestRunableTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            for (int j = 0; j < 1000; j++) {
                long startTime = System.currentTimeMillis();
                String validStatus =
                        dictionary.getValueByCode(DictionaryConst.TYPE_ITEM_UNIT, DictionaryConst.OPT_ITEM_UNIT_1);
                logger.info(validStatus + "---" + j + "---" + Thread.currentThread().getName() + "---" + (
                        System.currentTimeMillis() - startTime) + "ms");
            }
            logger.info(Thread.currentThread().getName() + "---over");
            return "";
        }
    }

}
