package cn.htd.zeus.tc.biz.listenter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.htd.zeus.tc.biz.service.OrderDistributionStatusUpStreamService;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

import com.alibaba.fastjson.JSONObject;

/*
 *监听MQ,并处理中间件往MQ队列里存放的结果 (商品+入库上行接口)
 */
@Component
public class JDOrderDistributionStatusUpStreamListenter implements MessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JDOrderDistributionStatusUpStreamListenter.class);
	
	@Autowired
	private OrderDistributionStatusUpStreamService orderDistributionStatusUpStreamService;

	@Override
	public void onMessage(Message msg) {
		try {
			String messageBody = new String(msg.getBody(),"UTF-8");
			LOGGER.info("商品+入库上行接口-- 监听到中间件发送的mq对象信息为:"+messageBody);
			Map<String,String> mqMap = JSONObject.parseObject(messageBody, Map.class);
			if(null != mqMap){
				//中间件给订单中心的全部是成功的
				String orderNo = mqMap.get("orderNo");
				if(StringUtilHelper.isNotNull(orderNo)){
					//写在service里
					orderDistributionStatusUpStreamService.jdOrderDistributionStatusUpStream(orderNo);
				}else{
					LOGGER.warn("商品+入库上行接口 数据有问题orderCode:"+orderNo);
				}
			}else{
				LOGGER.warn("商品+入库上行接口 数据有问题mqMap:"+mqMap);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
