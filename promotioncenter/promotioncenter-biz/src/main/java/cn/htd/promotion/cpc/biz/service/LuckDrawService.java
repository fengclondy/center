package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;

public interface LuckDrawService {

	/**
	 * 判断汇掌柜首页是否要显示扭蛋机图标
	 * 
	 * @param request
	 * @return
	 */
	public ExecuteResult<String> validateLuckDrawPermission(
			ValidateLuckDrawReqDTO request, ExecuteResult<String> result);
}
