package cn.htd.membercenter.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberJDAddress;

public class ConsigneeAddressServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsigneeAddressServiceTest.class);
	ApplicationContext ctx = null;
	ConsigneeAddressService consigneeAddressService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		consigneeAddressService = (ConsigneeAddressService) ctx.getBean("consigneeAddressService");
	}

	/*
	 * @Test public void selectConsigAddressID() { MemberConsigAddressDTO meu =
	 * consigneeAddressService .selectConsigAddressID((long) 1);
	 * System.out.println(meu.getConsigneeAddress()); }
	 */

	/*
	 * @Test public void updateConsigAddressID() { MemberConsigAddressDTO
	 * memberConsigAddressDto = new MemberConsigAddressDTO();
	 * memberConsigAddressDto.setAddressId((long) 1);
	 * memberConsigAddressDto.setConsigneeName("哈哈"); ExecuteResult<String> res
	 * = consigneeAddressService
	 * .updateConsigAddressInfo(memberConsigAddressDto);
	 * Assert.assertTrue(res.isSuccess()); }
	 */

	@Test
	public void saveConsigAddressID() {
		MemberConsigAddressDTO memberConsigAddressDto = new MemberConsigAddressDTO();
		memberConsigAddressDto.setMemberId((long) 17620);
		memberConsigAddressDto.setConsigneeName("hehe");
		memberConsigAddressDto.setConsigneeMobile("18015119170");
		memberConsigAddressDto.setDefaultFlag("1");
		ExecuteResult<String> res = consigneeAddressService.insertAddress(memberConsigAddressDto);
		Assert.assertTrue(res.isSuccess());
	}

	public void deleteConsigAddressID() {
		// ExecuteResult<String> res = consigneeAddressService
		// .deleteConsigAddressInfo(Long.valueOf("11"),Long.valueOf("1213"),"nihaoa");
		// ExecuteResult<MemberConsigAddressDTO> res = consigneeAddressService
		// .selectChannelAddressDTO("我是消息ID", "HTD10000001", "3010");
		Pager page = new Pager();
		page.setPage(1);
		page.setRows(20);// 写死默认显示0-20
		/*
		 * ExecuteResult<DataGrid<MemberConsigAddressDTO>> res =
		 * consigneeAddressService.selectConsigAddressIds(page,
		 * Long.valueOf("100002746")); Long size = res.getResult().getTotal();
		 */
		String sort = "default_flag desc,modify_time desc";
		ExecuteResult<List<MemberConsigAddressDTO>> res = consigneeAddressService
				.selectAddressCityList(Long.valueOf("10086"), "3201", "32", sort);
		if (res.isSuccess()) {
			Assert.assertTrue(res.isSuccess());
		}

	}

	/*
	 * @Test public void deleteConsigAddressIDs() { String ids = "1,2,4";
	 * ExecuteResult<String> res = consigneeAddressService
	 * .deleteConsigAddressInfoBatch(ids); Assert.assertTrue(res.isSuccess()); }
	 */

	public void getAllJDAddressForPageTest() {
		Pager<MemberJDAddress> page = new Pager<MemberJDAddress>();
		page.setPage(1);
		page.setRows(10);// 写死默认显示0-10
		MemberJDAddress dto = new MemberJDAddress();
		dto.setCompanyName("南京");
		ExecuteResult<DataGrid<MemberJDAddress>> res = consigneeAddressService.getAllJDAddressForPage(page, dto);
		if (res.isSuccess()) {
			Assert.assertTrue(res.isSuccess());
		}

	}
}
