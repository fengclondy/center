package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopFrameDAO;
import cn.htd.storecenter.dto.ShopFrameDTO;
import cn.htd.storecenter.service.ShopFrameService;
import cn.htd.storecenter.service.ShopWidgetService;

@Service("shopFrameService")
public class ShopFrameServiceImpl implements ShopFrameService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopFrameDAO dao;
	@Resource
	private ShopWidgetService shopWidgetService;

	@Override
	public ExecuteResult<ShopFrameDTO> addFrame(ShopFrameDTO frameDTO) {
		ExecuteResult<ShopFrameDTO> er = new ExecuteResult<ShopFrameDTO>();
		try {
			dao.insert(frameDTO);
			er.setResult(frameDTO);
		} catch (Exception e) {
			LOG.error("店铺框架插入错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<Boolean> updateFrame(ShopFrameDTO frameDTO) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		try {
			dao.update(frameDTO);
			er.setResult(true);
		} catch (Exception e) {
			LOG.error("店铺框架修改错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<Boolean> delFrameById(long id) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		try {
			dao.delete(id);
			// 安全起见， 把widget也删掉吧
			shopWidgetService.deleteWidget(id);
			er.setResult(true);
		} catch (Exception e) {
			LOG.error("店铺框架删除错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<ShopFrameDTO> findFrameById(long id) {
		ExecuteResult<ShopFrameDTO> er = new ExecuteResult<ShopFrameDTO>();
		try {
			er.setResult(dao.selectById(id));
		} catch (Exception e) {
			LOG.error("店铺框架搜索错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<List<ShopFrameDTO>> findFrame(ShopFrameDTO frameDTO) {
		ExecuteResult<List<ShopFrameDTO>> er = new ExecuteResult<List<ShopFrameDTO>>();
		try {
			er.setResult(dao.selectListByCondition(frameDTO, null));
		} catch (Exception e) {
			LOG.error("店铺框架搜索错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

}
