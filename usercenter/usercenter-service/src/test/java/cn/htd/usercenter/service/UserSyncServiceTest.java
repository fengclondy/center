package cn.htd.usercenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.OAWorkerDTO;

@Ignore
public class UserSyncServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSyncService.class);
    ApplicationContext ctx = null;
    UserSyncService userSyncService = null;

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        userSyncService = (UserSyncService) ctx.getBean("userSyncService");
    }

    @Test
    public void testSyncHTDCompany() {
        HTDCompanyDTO dto = new HTDCompanyDTO();
        dto.setCompanyId("0801");
        dto.setName("汇通达网络股份有限公司");
        dto.setType("0");
        dto.setParentCompanyId("");

        ExecuteResult<Boolean> res = userSyncService.syncHTDCompany(dto);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testSyncOAEmployee() {
        OAWorkerDTO workerDTO = new OAWorkerDTO();
        workerDTO.setWorkCode("test");
        workerDTO.setLastName("测试用户");
        workerDTO.setSubCompanyCode("0801");
        workerDTO.setPassword("e10adc3949ba59abbe56e057f20f883e");
        workerDTO.setEmail("test@htd.cn");
        workerDTO.setMobile("13912345678");
        workerDTO.setSuperior("");
        workerDTO.setIsCM(1);
        workerDTO.setStatus(1);

        ExecuteResult<Boolean> res = userSyncService.syncOAWorker(workerDTO);
        Assert.assertTrue(res.isSuccess());
    }
}
