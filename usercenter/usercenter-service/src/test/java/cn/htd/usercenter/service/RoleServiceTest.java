package cn.htd.usercenter.service;

import org.junit.Before;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Ignore
public class RoleServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceTest.class);
    ApplicationContext ctx = null;
    RoleService roleService = null;

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        roleService = (RoleService) ctx.getBean("roleService");
    }

    // @Test
    // public void testAddRole() {
    // ExecuteResult<Boolean> res = roleService.addRole("4", "1", "test",
    // "1,2,3,4,5", "2");
    // Assert.assertTrue(res.isSuccess());
    // }

    // @Test
    // public void testQueryRole() {
    // ExecuteResult<DataGrid<RoleDto>> res =
    // roleService.queryRoleByIdAndName("", "", 1, 10);
    // Assert.assertTrue(res.isSuccess());
    // }
    //
    // @Test
    // public void testUpdateRole() {
    // ExecuteResult<Boolean> res = roleService.updateRole("2", "2",
    // "管理员", "6,7,8,9", "2");
    // Assert.assertTrue(res.isSuccess());
    // }
}
