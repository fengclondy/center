package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopFrameBackupDAO;
import cn.htd.storecenter.dao.ShopFrameDAO;
import cn.htd.storecenter.dao.ShopWidgetDAO;
import cn.htd.storecenter.dao.ShopWidgetSheetDAO;
import cn.htd.storecenter.dto.ShopFrameBackupDTO;
import cn.htd.storecenter.service.ShopFrameBackupService;

@Service("shopFrameBackupService")
public class ShopFrameBackupServiceImpl implements ShopFrameBackupService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopFrameBackupDAO dao;
	@Resource
	private ShopFrameDAO frameDAO;
	@Resource
	private ShopWidgetDAO widgetDAO;
	@Resource
	private ShopWidgetSheetDAO sheetDAO;

	@Override
	public ExecuteResult<Boolean> addBackup(ShopFrameBackupDTO backupDTO) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			dao.insert(backupDTO);
			result.setResult(true);
		} catch (Exception e) {
			LOG.error("店铺装修备份插入错误！", e);
			result.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<Boolean> delBackUpById(long id) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			dao.delete(id);
			result.setResult(true);
		} catch (Exception e) {
			LOG.error("店铺装修备份删除错误！", e);
			result.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<ShopFrameBackupDTO> findBackUpById(long id) {
		ExecuteResult<ShopFrameBackupDTO> result = new ExecuteResult<ShopFrameBackupDTO>();
		try {
			result.setResult(dao.selectById(id));
		} catch (Exception e) {
			LOG.error("店铺装修备份查找错误！", e);
			result.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<List<ShopFrameBackupDTO>> findBackup(ShopFrameBackupDTO backupDTO) {
		ExecuteResult<List<ShopFrameBackupDTO>> result = new ExecuteResult<List<ShopFrameBackupDTO>>();
		try {
			result.setResult(dao.selectListByCondition(backupDTO, null));
		} catch (Exception e) {
			LOG.error("店铺装修备份查找错误！", e);
			result.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

}
