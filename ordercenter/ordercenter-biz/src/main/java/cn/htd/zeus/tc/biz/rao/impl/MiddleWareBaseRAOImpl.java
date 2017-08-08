/**
 * 
 */
package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.zeus.tc.biz.rao.MiddleWareBaseRAO;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.common.middleware.MiddlewareHttpUrlConfig;
import cn.htd.zeus.tc.common.util.HttpUtil;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @author ly
 *
 */
@Service
public class MiddleWareBaseRAOImpl implements MiddleWareBaseRAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MiddleWareBaseRAOImpl.class);

	@Autowired
	private MiddlewareHttpUrlConfig middlewareHttpUrlConfig;

	/* (non-Javadoc)
	 * @see cn.htd.zeus.tc.biz.rao.MiddleWareBaseRAO#getMiddleWareToken()
	 */
	@Override
	public OtherCenterResDTO<String> getMiddleWareToken(String messageId) {
		String token = null;
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			String tokenUrl = middlewareHttpUrlConfig.getOrdercenterMiddleware4token();
			LOGGER.info("MessageId:{} 调用中间件获取TOKEN方法-开始",messageId);
			Long startTime = System.currentTimeMillis();
			String tokenResult = HttpUtil.sendGet(tokenUrl);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 调用中间件获取TOKEN方法-结束，token返回值为:{}，调用耗时{}",messageId,JSONObject.toJSONString(token),endTime-startTime);
			Map<String,String> resultMap = JSON.parseObject(tokenResult,new TypeReference<Map<String, String>>(){});
			if(resultMap != null)
			{
				token = resultMap.get("data");
			}
			if(!StringUtils.isEmpty(token))
			{
				otherCenterResDTO.setOtherCenterResult(token);
				otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				return otherCenterResDTO;
			}
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.MIDDLEWARE_GETTOKEN_FAIL.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.MIDDLEWARE_GETTOKEN_FAIL.getCode());
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MiddleWareBaseRAOImpl.queryStock4JD出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

}
