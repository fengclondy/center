package cn.htd.promotion.cpc.api;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;

public interface LuckDrawAPI {

	/**
	 * 判断汇掌柜首页是否要显示扭蛋机图标
	 * @param request
	 * @return
	 */
	public ExecuteResult<String> validateLuckDrawPermission(ValidateLuckDrawReqDTO request);
	
}
