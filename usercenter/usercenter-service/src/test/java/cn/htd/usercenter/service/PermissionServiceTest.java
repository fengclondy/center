package cn.htd.usercenter.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.PermissionDTO;

@Ignore
public class PermissionServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceTest.class);
	ApplicationContext ctx = null;
	PermissionService permissionService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		permissionService = (PermissionService) ctx.getBean("permissionService");
	}

	// @Test
	// @Ignore
	// public void testAddProduct() {
	// PermissionDTO permissionDTO = new PermissionDTO();
	// permissionDTO.setProductId("ZT01");
	// permissionDTO.setPermissionId("0103");
	// permissionDTO.setName("ProductEdit");
	// permissionDTO.setLevel(1);
	// permissionDTO.setPageUrl("/productEdit");
	// permissionDTO.setFunctionSymbol("");
	// permissionDTO.setDisplayOrder(3);
	// permissionDTO.setDeletedFlag(0);
	// permissionDTO.setType("1");
	// permissionDTO.setParentId("01");
	// ExecuteResult<Boolean> res =
	// permissionService.addPermission(permissionDTO, "2");
	// Assert.assertTrue(res.isSuccess());
	// }

	@Test
	public void testQueryProduct() {
		ExecuteResult<List<PermissionDTO>> res = permissionService.queryPermissionByUserId(1l);
		Assert.assertTrue(res.isSuccess());
	}
}
