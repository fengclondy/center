package cn.htd.usercenter.service;

import java.text.MessageFormat;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.FunctionDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.LoginParamDTO;
import cn.htd.usercenter.dto.LoginResDTO;
import cn.htd.usercenter.dto.MenuDTO;

@Ignore
public class UserExportServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExportServiceTest.class);
    ApplicationContext ctx = null;
    UserExportService userExportService = null;

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        userExportService = (UserExportService) ctx.getBean("userExportService");
    }

    @Test
    public void testLogin_Ok() {
        userExportService.activeEmployee("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("123456");
        ExecuteResult<LoginResDTO> res = userExportService.employeeLogin(paramDTO);
        Assert.assertTrue(res.isSuccess());
        System.out.println("loginResDTO:" + toJson(res.getResult()));

        LOGGER.info("loginResDTO:" + toJson(res.getResult()));
    }

    @Test
    public void testLogin_MobileOk() {
        userExportService.activeCustomer("13912345678");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("13912345678");
        paramDTO.setPassword("123456");
        paramDTO.setVMS(true);
        ExecuteResult<LoginResDTO> res = userExportService.customerLogin(paramDTO);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testLogin_PasswordErr() {
        userExportService.activeEmployee("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("1234567");
        ExecuteResult<LoginResDTO> res = userExportService.employeeLogin(paramDTO);
        Assert.assertEquals(MessageFormat.format("用户名或密码不正确（还有{0}次机会）", GlobalConstant.MAX_LOGIN_FAILED_COUNT - 1),
                res.getErrorMessages().get(0));
    }

    @Test
    public void testLogin_VMSErr() {
        userExportService.activeCustomer("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("1234567");
        paramDTO.setVMS(false);
        ExecuteResult<LoginResDTO> res = userExportService.customerLogin(paramDTO);
        Assert.assertEquals("无效用户", res.getErrorMessages().get(0));
    }

    @Test
    public void testLogin_NotUtilMaxFailedCountErr() {
        userExportService.activeEmployee("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("123456");
        ExecuteResult<LoginResDTO> res = userExportService.employeeLogin(paramDTO);
        paramDTO.setPassword("1234567");
        for (int i = 0; i < GlobalConstant.MAX_LOGIN_FAILED_COUNT - 1; i++) {
            res = userExportService.employeeLogin(paramDTO);
        }
        Assert.assertEquals("用户名或密码不正确（还有1次机会）", res.getErrorMessages().get(0));
    }

    @Test
    public void testLogin_OnMaxFailedCountErr() {
        userExportService.activeEmployee("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("123456");
        ExecuteResult<LoginResDTO> res = userExportService.employeeLogin(paramDTO);
        paramDTO.setPassword("1234567");
        for (int i = 0; i < GlobalConstant.MAX_LOGIN_FAILED_COUNT; i++) {
            res = userExportService.employeeLogin(paramDTO);
        }
        Assert.assertEquals("用户名或密码不正确（账号已被冻结）", res.getErrorMessages().get(0));
    }

    @Test
    public void testLogin_OverMaxFailedCountErr() {
        userExportService.activeEmployee("test");

        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("123456");
        ExecuteResult<LoginResDTO> res = userExportService.employeeLogin(paramDTO);
        paramDTO.setPassword("1234567");
        for (int i = 0; i < GlobalConstant.MAX_LOGIN_FAILED_COUNT; i++) {
            res = userExportService.employeeLogin(paramDTO);
        }

        paramDTO.setPassword("123456");
        res = userExportService.employeeLogin(paramDTO);
        Assert.assertEquals("账号已被冻结", res.getErrorMessages().get(0));
    }

    @Test
    public void testActiveUser_Ok() {
        ExecuteResult<Boolean> res = userExportService.activeEmployee("test");
        Assert.assertEquals("重置登录失败次数成功！", res.getResultMessage());
    }

    @Test
    public void testActiveUser_Err() {
        ExecuteResult<Boolean> res = userExportService.activeEmployee("test2");
        Assert.assertEquals("用户不存在！", res.getResultMessage());
    }

    @Test
    public void testValidTicket() {
        userExportService.activeEmployee("test");
        LoginParamDTO paramDTO = new LoginParamDTO();
        paramDTO.setUsername("test");
        paramDTO.setPassword("123456");
        String ticket = userExportService.employeeLogin(paramDTO).getResult().getTicket();
        ExecuteResult<LoginResDTO> res = userExportService.validTicket(ticket);
        Assert.assertNotNull(res.getResult());
    }

    @Test
    public void testGetUserMenus() {
        ExecuteResult<List<MenuDTO>> res = userExportService.getMenusByUser(1L, "HMS");
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testGetUserMenus_nodata() {
        ExecuteResult<List<MenuDTO>> res = userExportService.getMenusByUser(1L, "HSA");
        Assert.assertEquals("没有菜单权限", res.getErrorMessages().get(0));
    }

    @Test
    public void testGetUserPageFunctions() {
        ExecuteResult<List<FunctionDTO>> res = userExportService.getPageFunctionsByUser(1L, "HMS", "0101");
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testGetUserPageFunctions_nodata() {
        ExecuteResult<List<FunctionDTO>> res = userExportService.getPageFunctionsByUser(1L, "HMS", "0201");
        Assert.assertEquals("没有页面机能权限", res.getErrorMessages().get(0));
    }

    @Test
    public void testValidProcessPermission() {
        ExecuteResult<Boolean> res = userExportService.validProcessPermission(1L, "HMS", "/poseidon-web/productList");
        Assert.assertEquals("拥有权限，可以操作！", res.getResultMessage());
    }

    @Test
    public void testValidProcessPermission_invalidParam() {
        ExecuteResult<Boolean> res = userExportService.validProcessPermission(1L, "HMS", null);
        Assert.assertEquals("请确认调用参数！", res.getResultMessage());
    }

    @Test
    public void testValidProcessPermission_noPermission() {
        ExecuteResult<Boolean> res = userExportService.validProcessPermission(1L, "HMS",
                "/poseidon-web/substationManList");
        Assert.assertEquals("没有权限，不可操作！", res.getResultMessage());
    }

    @Test
    public void testValidProcessPermission_notTarget() {
        ExecuteResult<Boolean> res = userExportService.validProcessPermission(1L, "HMS", "/poseidon-web/login");
        Assert.assertEquals("非权限验证对象，可以操作！", res.getResultMessage());
    }

    @Test
    public void testGetInchargeCompanies() {
        ExecuteResult<List<String>> res = userExportService.getInchargeCompanies(1L);
        Assert.assertTrue(res.isSuccess());
        System.out.println("incharge companies:" + toJson(res.getResult()));
    }

    @Test
    public void testGetEmployeeByCompanyId() {
        ExecuteResult<List<EmployeeDTO>> res = userExportService.getEmployeeByCompany("0108");
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void testGetCompanysByParentCompany() {
        ExecuteResult<List<HTDCompanyDTO>> res = userExportService.getCompanysByParentCompany("0801", "南分");
        Assert.assertEquals(3, res.getResult().size());
    }

    private static ObjectMapper mapper = new ObjectMapper();

    private static String toJson(Object obj) {
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (Exception e) {
        }
        return jsonString;
    }
}
