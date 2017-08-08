package cn.htd.goodscenter.service.util;

import javax.annotation.Resource;

import org.junit.Test;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.test.common.CommonTest;

public class DicUtilTest extends CommonTest{
	@Resource
	private DictionaryUtils dictionaryUtils;
	
	@Test
	public void testQueryUnitName(){
		String name=dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, "tai");
		System.out.println(name);
	}
}
