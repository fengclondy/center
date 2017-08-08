package cn.htd.goodscenter.service.util;

import org.junit.Assert;
import org.junit.Test;

import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.goodscenter.test.common.CommonTest;

public class ItemCodeGeneratortest extends CommonTest{
 
	@Test
	public void testGenerateItemCode(){
	 String itemCode= ItemCodeGenerator.generateItemCode();
	 System.out.println(itemCode);
	 Assert.assertNotNull(itemCode);
    }
	
	@Test
	public void testgenerateSkuCode(){
	  String skuCode=ItemCodeGenerator.generateItemCode();
	  System.out.println(skuCode);
	  Assert.assertNotNull(skuCode);
   }
}
