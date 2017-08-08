package cn.htd.membercenter.service.listenter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.alibaba.fastjson.JSONObject;

import cn.htd.membercenter.service.PayInfoService;

public class YijiToCenterListenter implements MessageListener {
	protected static transient Logger logger = LoggerFactory.getLogger(YijiToCenterListenter.class);

	@Resource
	PayInfoService payInfoService;

	@Override
	public void onMessage(Message message) {
		logger.info("支付实名信息修改上行同步,传入参数:" + message.toString());
		JSONObject obj = JSONObject.parseObject(new String(message.getBody()));
		String accoutNo = obj.getString("userId");
		String notifyTypeEnum = obj.getString("notifyTypeEnum");
		payInfoService.yijiRealNameModify(accoutNo, notifyTypeEnum);
	}
}