package cn.htd.tradecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dao.ShopPreferentialWayDAO;
import cn.htd.tradecenter.dto.ShopPreferentialWayDTO;
import cn.htd.tradecenter.service.ShopPreferentialWayExportService;

@Service("shopPreferentialWayExportService")
public class ShopPreferentialWayExportServiceImpl implements ShopPreferentialWayExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopPreferentialWayDTO.class);
	@Resource
	private ShopPreferentialWayDAO shopPreferentialWayDAO;

	@Override
	public ExecuteResult<String> addShopPreferentialWay(ShopPreferentialWayDTO dto) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			shopPreferentialWayDAO.add(dto);
			ex.setResultMessage("添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex.setResultMessage("添加失败！");
			throw new RuntimeException();
		}
		return ex;
	}

	@Override
	public ExecuteResult<String> deleteShopPreferentialWay(ShopPreferentialWayDTO dto) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			Long id = dto.getId();
			if (shopPreferentialWayDAO.delete(id) > 0) {
				ex.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex.setResultMessage("删除失败！");
			throw new RuntimeException();
		}
		return ex;
	}

	@Override
	public ExecuteResult<String> updateShopPreferentialWay(ShopPreferentialWayDTO dto) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			if (shopPreferentialWayDAO.update(dto) > 0) {
				ex.setResultMessage("修改成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ex.setResultMessage("修改失败！");
			throw new RuntimeException();
		}
		return ex;
	}

	@Override
	public ExecuteResult<List<ShopPreferentialWayDTO>> queryShopPreferentialWay(ShopPreferentialWayDTO dto) {
		ExecuteResult<List<ShopPreferentialWayDTO>> ex = new ExecuteResult<List<ShopPreferentialWayDTO>>();
		try {
			List<ShopPreferentialWayDTO> list = shopPreferentialWayDAO.selectListByCondition(dto);
			ex.setResult(list);
			ex.setResultMessage("success");
		} catch (Exception e) {
			ex.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException();
		}
		return ex;
	}
}
