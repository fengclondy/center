/**
 * 
 */
package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.rao.ErpMiddleWareOrderRAO;
import cn.htd.zeus.tc.biz.rao.MiddleWareBaseRAO;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.util.HttpUtil;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author ly
 *
 */
@Service
public class ErpMiddleWareOrderRAOImpl implements ErpMiddleWareOrderRAO {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErpMiddleWareOrderRAOImpl.class);

	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;
	
	@Autowired
	private MiddleWareBaseRAO middleWareBaseRAO;
	
	@Override
	public OtherCenterResDTO<String> erpBalanceUnlock(String messageId, String lockBalanceCode) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			otherCenterResDTO = middleWareBaseRAO.getMiddleWareToken(messageId);
			if(ResultCodeEnum.SUCCESS.getCode().equals(otherCenterResDTO.getOtherCenterResponseCode()))
			{
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				String token = otherCenterResDTO.getOtherCenterResult();
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("merchOrderNo", lockBalanceCode);
				paramMap.put("token", token);
				Long startTime = System.currentTimeMillis();
				LOGGER.info("MessageId {} 调用中间件http接口：调用ERP余额解锁接口信息=======erpBalanceUnlock开始：{}",messageId,
						JSONObject.fromObject(paramMap));
				String url = middlewareHttpUrlConfig.getOrdercenterMiddleware4unLockBalance();
				String result = HttpUtil.sendPost(url, paramMap);
				Long endTime = System.currentTimeMillis();
				LOGGER.info("MessageId {}调用中间件http接口：调用ERP余额解锁接口信息=======erpBalanceUnlock结束：{} 调用接口耗时{}",messageId, result,endTime - startTime);
				Map<String,String> resultMap = JSON.parseObject(result,new TypeReference<Map<String, String>>(){});
				if(resultMap != null)
				{
					String resultCode = resultMap.get("code");
					if("1".equals(resultCode))
					{
						otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
						otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
						return otherCenterResDTO;
					}
				}
				otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_UNLOCK_FAIL.getMsg());
				otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ORDERCANCEL_ORDER_IS_UNLOCK_FAIL.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法ErpMiddleWareOrderRAOImpl.erpBalanceUnlock出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

}
