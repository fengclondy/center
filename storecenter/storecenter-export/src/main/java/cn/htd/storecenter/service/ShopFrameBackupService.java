package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopFrameBackupDTO;

/**
 * <p>
 * Description: [店铺框架(自定义装修)备份接口]
 * </p>
 */
public interface ShopFrameBackupService {

	/**
	 * <p>
	 * Discription:[添加]
	 * </p>
	 */
	ExecuteResult<Boolean> addBackup(ShopFrameBackupDTO backupDTO);

	/**
	 * <p>
	 * Discription:[删除]
	 * </p>
	 */
	ExecuteResult<Boolean> delBackUpById(long id);

	/**
	 * <p>
	 * Discription:[通过id查找]
	 * </p>
	 */
	ExecuteResult<ShopFrameBackupDTO> findBackUpById(long id);

	/**
	 * <p>
	 * Discription:[查找]
	 * </p>
	 */
	ExecuteResult<List<ShopFrameBackupDTO>> findBackup(ShopFrameBackupDTO backupDTO);
}
