package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);
	
	@Override
	public ExecuteResult<String> validateLuckDrawPermission(
			ValidateLuckDrawReqDTO request, ExecuteResult<String> result) {
		String messageId = request.getMessageId();
		try{
			//TODO 查询redis 查到数据返回 有值
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResult("");//TODO pomotionId
			//TODO  查询redis 查到数据返回 空
			result.setCode(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION.getCode());
			result.setErrorMessage(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION.getMsg());
		}catch(Exception e){
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			result.setErrorMessage(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.validateLuckDrawPermission出现异常 OrgId：{}",
					messageId, request.getOrgId(), w.toString());
		}
	    
		return result;
	}

}
