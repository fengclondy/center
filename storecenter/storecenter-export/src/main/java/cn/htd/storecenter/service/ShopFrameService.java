package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopFrameDTO;

/**
 * <p>
 * Description: [店铺框架(自定义装修)接口]
 * </p>
 */
public interface ShopFrameService {

	/**
	 * <p>
	 * Discription:[添加框架]
	 * </p>
	 */
	ExecuteResult<ShopFrameDTO> addFrame(ShopFrameDTO frameDTO);

	/**
	 * <p>
	 * Discription:[更新]
	 * </p>
	 */
	ExecuteResult<Boolean> updateFrame(ShopFrameDTO frameDTO);

	/**
	 * <p>
	 * Discription:[删除]
	 * </p>
	 */
	ExecuteResult<Boolean> delFrameById(long id);

	/**
	 * <p>
	 * Discription:[查找]
	 * </p>
	 */
	ExecuteResult<ShopFrameDTO> findFrameById(long id);

	/**
	 * <p>
	 * Discription:[查找]
	 * </p>
	 */
	ExecuteResult<List<ShopFrameDTO>> findFrame(ShopFrameDTO frameDTO);
}
