package cn.htd.goodscenter.service.listener;

import javax.annotation.Resource;

import org.junit.Test;

import cn.htd.goodscenter.test.common.CommonTest;

public class Erp2MiddleItemListenerTest extends CommonTest{
	@Resource
	private Erp2MiddleItemListener erp2MiddleItemListener;
	@Test
	public void testOnMessage(){
		erp2MiddleItemListener.onMessage(null);
	}
}
