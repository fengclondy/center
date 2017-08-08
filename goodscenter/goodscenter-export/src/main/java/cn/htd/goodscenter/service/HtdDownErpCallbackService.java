package cn.htd.goodscenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.middleware.indto.DownErpCallbackInDTO;

/**
 * 下行ERP回调接口
 * 
 * @author zhangxiaolong
 *
 */
public interface HtdDownErpCallbackService {
	ExecuteResult<String> itemDownErpCallback(List<DownErpCallbackInDTO> downErpCallbackList);

	ExecuteResult<String> brandDownErpCallback(DownErpCallbackInDTO downErpCallbackInDTO);
}
