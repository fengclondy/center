package cn.htd.basecenter.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.dto.IllegalCharacterDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;

public class IllegalCharacterServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(IllegalCharacterServiceTest.class);
	private IllegalCharacterService illegalCharacterService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		illegalCharacterService = (IllegalCharacterService) ctx.getBean("illegalCharacterService");
	}

	@Test
	public void testAddIllegalCharacter() {
		IllegalCharacterDTO illegalCharacterDTO = new IllegalCharacterDTO();
		illegalCharacterDTO.setContent("中国");
		illegalCharacterDTO.setCreateId(new Long("1"));
		ExecuteResult<IllegalCharacterDTO> result = illegalCharacterService.addIllegalCharacter(illegalCharacterDTO);
		LOGGER.info("操作结果{}", JSON.toJSON(result));
	}

	@Test
	public void testAddIllegalCharacterList() {
		List<IllegalCharacterDTO> list = new ArrayList<IllegalCharacterDTO>();
		for (int i = 0; i < 20; i++) {
			IllegalCharacterDTO illegalCharacterDTO = new IllegalCharacterDTO();
			illegalCharacterDTO.setContent("批量增加" + i);
			illegalCharacterDTO.setCreateId(new Long("1"));
			list.add(illegalCharacterDTO);
		}

		ExecuteResult<IllegalCharacterDTO> result = illegalCharacterService.addIllegalCharacterList(list);
		LOGGER.info("操作结果{}", JSON.toJSON(result));
	}

	@Test
	public void testUpdateIllegalCharacter() {
		IllegalCharacterDTO illegalCharacterDTO = new IllegalCharacterDTO();
		illegalCharacterDTO.setContent("美国");
		illegalCharacterDTO.setCreateId(new Long("2"));
		illegalCharacterDTO.setId(1L);
		ExecuteResult<IllegalCharacterDTO> result = illegalCharacterService.updateIllegalCharacter(illegalCharacterDTO);
		LOGGER.info("操作结果{}", JSON.toJSON(result));
	}

	@Test
	public void testQueryIllegalCharacterById() {
		ExecuteResult<IllegalCharacterDTO> result = illegalCharacterService.queryIllegalCharacterById(1L);
		LOGGER.info("操作结果{}", JSON.toJSON(result));
	}

	@Test
	public void testQueryIllegalCharacterList() {
		IllegalCharacterDTO illegalCharacterDTO = new IllegalCharacterDTO();
		illegalCharacterDTO.setDeleteFlag(YesNoEnum.NO.getValue());
		DataGrid<IllegalCharacterDTO> dataGrid = illegalCharacterService.queryIllegalCharacterList(illegalCharacterDTO,
				null);
		LOGGER.info("操作结果{}", JSON.toJSON(dataGrid));
	}

	/**
	 *
	 * <p>
	 * Discription:[校验非法字符唯一性(是否存在)测试]
	 * </p>
	 */
	@Test
	public void testVerifyIllegalCharacter() {
		// Long id = 171L;//新增时不传，修改时传
		String content = "法轮功";
		boolean b = illegalCharacterService.verifyIllegalCharacter(null, content);
		LOGGER.info("操作方法{}，结果信息{}", "VerifyIllegalCharacter", b);
	}

}
