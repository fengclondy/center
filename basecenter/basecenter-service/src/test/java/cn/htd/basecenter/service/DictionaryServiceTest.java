package cn.htd.basecenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryServiceTest {

	private BaseDictionaryService dictionaryservice;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		dictionaryservice = (BaseDictionaryService) ctx.getBean("baseDictionaryService");
	}

	// @Test
	// public void testDictionaryadd() {
	// Date date = new Date();
	// BaseDictionaryDTO dictionaryDTO = new BaseDictionaryDTO();
	// dictionaryDTO.setName("尚橙");
	// dictionaryDTO.setCode("shangcheng");
	// dictionaryDTO.setType("物流");
	// dictionaryDTO.setStatus(1);
	// dictionaryDTO.setCreatedTime(date);
	// dictionaryDTO.setCreater("asdf");
	// dictionaryDTO.setRemark("楼层模板一");
	// dictionaryDTO.setParameter1("1111111");
	// dictionaryservice.addDictionary(dictionaryDTO);
	// }
	//
	// @Test
	// public void testQueryDictionaryByCode() {
	// String code = null;
	// String type = "物流";
	// ExecuteResult<List<BaseDictionaryDTO>> result =
	// dictionaryservice.queryDictionaryByCode(code, type);
	//
	// }
	//
	// @Test
	// public void testQueryDictionaryBy() {
	// BaseDictionaryDTO dictionaryDTO = new BaseDictionaryDTO();
	// Pager pager = new Pager();
	// pager.setPage(1);
	// pager.setRows(1000);
	// dictionaryDTO.setType("wuliu");
	// dictionaryDTO.setParameter1("货到付款");
	//
	// DataGrid<BaseDictionaryDTO> result =
	// dictionaryservice.queryDictionaryByCondition(dictionaryDTO, null);
	// for (BaseDictionaryDTO element : result.getRows()) {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>" + element.getType());
	// }
	// }
	//
	// @Test
	// public void testUdpDictionary() {
	// Date date = new Date();
	// BaseDictionaryDTO dictionaryDTO = new BaseDictionaryDTO();
	// dictionaryDTO.setId(5);
	// dictionaryDTO.setName("asweaaa");
	// dictionaryDTO.setCode("shangcheng");
	// dictionaryDTO.setType("sadw");
	// dictionaryDTO.setStatus(1);
	// dictionaryDTO.setChangeTime(date);
	// dictionaryDTO.setChanger("23ar");
	// dictionaryDTO.setParameter1("货到付款");
	// dictionaryservice.updDictionary(dictionaryDTO);
	//
	// }
	//
	// @Test
	// public void testDelteDictionary() {
	// BaseDictionaryDTO dictionaryDTO = new BaseDictionaryDTO();
	// dictionaryDTO.setId(156);
	// dictionaryservice.delDictionary(dictionaryDTO);
	// }
	
	@Test
	public void testAddItemUnit(){
		dictionaryservice.addSingleItemUnit("测试7", 598L, "张小龙");
	}
}
