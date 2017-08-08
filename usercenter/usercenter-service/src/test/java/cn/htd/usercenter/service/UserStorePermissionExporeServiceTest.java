package cn.htd.usercenter.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.UserMallResourceDTO;

/**
 * Created by thinkpad on 2017/2/6.
 */
@Ignore
public class UserStorePermissionExporeServiceTest {
	ApplicationContext ctx = null;
	UserStorePermissionExportService userStorePermissionExportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		userStorePermissionExportService = (UserStorePermissionExportService) ctx
				.getBean("userStorePermissionExportService");
	}

	@Test
	public void queryParentResourceListTest() {
		ExecuteResult<List<UserMallResourceDTO>> result = userStorePermissionExportService.queryParentResourceList(2,
				2);
		System.out.println("result----------" + JSON.toJSONString(result.getResult()));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void queryById(){
		ExecuteResult<UserMallResourceDTO> dto = userStorePermissionExportService.queryResourceNameById(20l);
		System.out.println(dto.getResult().getResourceName());
	}

}
