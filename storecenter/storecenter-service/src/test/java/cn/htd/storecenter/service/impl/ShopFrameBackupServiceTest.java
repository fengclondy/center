package cn.htd.storecenter.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopFrameBackupDTO;
import cn.htd.storecenter.service.ShopFrameBackupService;

public class ShopFrameBackupServiceTest {

	private ShopFrameBackupService shopFrameBackupService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopFrameBackupService = (ShopFrameBackupService) ctx.getBean("shopFrameBackupService");
	}

	@Test
	public void testAdd() {
		ShopFrameBackupDTO backupDTO = new ShopFrameBackupDTO();
		backupDTO.setFrameId(1l);
		backupDTO.setShopId(10000l);
		backupDTO.setName("test");
		backupDTO.setRemark("adfdfrrrrrrrrrffffffssssssssscddddddddddddddddddddddddddddd");
		ExecuteResult<Boolean> er = shopFrameBackupService.addBackup(backupDTO);
		Assert.assertNotNull(er.getResult());
	}

	@Test
	public void findBackup() {
		ShopFrameBackupDTO backupDTO = new ShopFrameBackupDTO();
		ExecuteResult<List<ShopFrameBackupDTO>> er = shopFrameBackupService.findBackup(backupDTO);
		Assert.assertNotNull(er.getResult());
	}
}
