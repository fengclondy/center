package cn.htd.zeus.tc.biz.listenter;

import java.io.PrintWriter;
import java.io.StringWriter;
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
 *监听MQ,并处理中间件往MQ队列里存放的结果 (分销单号状态上行接口)
 */
@Component
public class OrderDistributionStatusUpStreamListenter implements MessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDistributionStatusUpStreamListenter.class);
	
	@Autowired
	private OrderDistributionStatusUpStreamService orderDistributionStatusUpStreamService;

	@Override
	public void onMessage(Message msg) {
		try {
			String messageBody = new String(msg.getBody(),"UTF-8");
			LOGGER.info("分销单号状态上行接口 -- 监听到中间件发送的mq对象信息为:"+messageBody);
			Map<String,Object> mqMap = JSONObject.parseObject(messageBody, Map.class);
			if(null != mqMap){
				String distributionId = mqMap.get("distributionCode")==null?"":mqMap.get("distributionCode").toString();
				String distributionStatu = mqMap.get("distributionStatu")==null?"":mqMap.get("distributionStatu").toString();//TODO 文档里是distributionStatu
				if(StringUtilHelper.isNotNull(distributionId,distributionStatu)){
					//写在service里
					orderDistributionStatusUpStreamService.orderDistributionStatusUpStream(distributionId, distributionStatu);
				}else{
					LOGGER.warn("中间件传入的字段有空值distributionId:"+distributionId+" distributionStatu"+distributionStatu);
				}
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error(w.toString());
		}
	}

}
