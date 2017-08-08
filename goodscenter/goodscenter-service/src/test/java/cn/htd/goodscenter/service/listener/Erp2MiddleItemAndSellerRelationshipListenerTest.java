package cn.htd.goodscenter.service.listener;

import javax.annotation.Resource;

import cn.htd.goodscenter.dto.listener.ItemAndSellerRelationshipDTO;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import cn.htd.common.util.SpringApplicationContextHolder;
import cn.htd.goodscenter.test.common.CommonTest;
import org.springframework.amqp.core.Message;

import java.io.IOException;

public class Erp2MiddleItemAndSellerRelationshipListenerTest extends CommonTest{
	@Resource
	private Erp2MiddleItemRelationshipListener erp2MiddleItemAndSellerRelationshipListener;
	@Test
	public void testGetMiddleItemAndSellerRelationship(){
		erp2MiddleItemAndSellerRelationshipListener=SpringApplicationContextHolder.getBean(Erp2MiddleItemRelationshipListener.class);
		ItemAndSellerRelationshipDTO itemAndSellerRelationship = new ItemAndSellerRelationshipDTO();
		itemAndSellerRelationship.setProductCode("HP_0000009338");
		itemAndSellerRelationship.setSupplierCode("");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		String req = null;
		try {
			req = mapper.writeValueAsString(itemAndSellerRelationship);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Message message = new Message(req.getBytes(), null);
		erp2MiddleItemAndSellerRelationshipListener.onMessage(message);
	}
}
