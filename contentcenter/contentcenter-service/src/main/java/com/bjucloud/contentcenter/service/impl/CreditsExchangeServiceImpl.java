package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.CreditsExchangeDAO;
import com.bjucloud.contentcenter.dto.CreditsExchangeDTO;
import com.bjucloud.contentcenter.service.CreditsExchangeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: [积分兑换实现类]
 * </p>
 */
@Service("creditsExchangeService")
public class CreditsExchangeServiceImpl implements CreditsExchangeService {
	private final static Logger logger = LoggerFactory.getLogger(CreditsExchangeServiceImpl.class);

	@Resource
	private CreditsExchangeDAO creditsExchangeDao;

	@Override
	public ExecuteResult<String> update(CreditsExchangeDTO dto) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-update", dto);
		ExecuteResult<String> er = new ExecuteResult<String>();
		int count = 0;
		try {
			count = creditsExchangeDao.update(dto);
			if (count > 0) {
				er.setResultMessage("修改成功！");
				er.setResult(String.valueOf(count));
			}
		} catch (Exception e) {
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-update", e.getMessage());
			er.setResultMessage("修改成功！");
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<DataGrid<CreditsExchangeDTO>> queryCreditExchangeList(CreditsExchangeDTO dto, Pager pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-queryCreditExchangeList", dto);
		ExecuteResult<DataGrid<CreditsExchangeDTO>> result = new ExecuteResult<DataGrid<CreditsExchangeDTO>>();

		try {
			DataGrid<CreditsExchangeDTO> dataGrid = new DataGrid<CreditsExchangeDTO>();
			List<CreditsExchangeDTO> list = creditsExchangeDao.queryList(dto, pager);
			Long counbt = creditsExchangeDao.queryCount(dto);
			dataGrid.setRows(list);
			dataGrid.setTotal(counbt);
			result.setResult(dataGrid);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-queryCreditExchangeList", e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> delete(long id) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-delete", id);
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (creditsExchangeDao.delete(id) > 0) {
				er.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-delete", e.getMessage());
			er.setResultMessage("删除失败！");
			throw new RuntimeException();
		}
		return er;
	}

	@Override
	public ExecuteResult<CreditsExchangeDTO> insert(CreditsExchangeDTO dto) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-insert", dto);
		ExecuteResult<CreditsExchangeDTO> er = new ExecuteResult<CreditsExchangeDTO>();
		try {
			creditsExchangeDao.add(dto);
			er.setResult(dto);
			er.setResultMessage("添加成功！");
		} catch (Exception e) {
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-insert", e.getMessage());
			er.setResultMessage("添加失败！");
			throw new RuntimeException();
		}
		return er;
	}

	@Override
	public CreditsExchangeDTO selectById(Long id) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-selectById", id);
		CreditsExchangeDTO CreditsExchangeDTO = new CreditsExchangeDTO();
		try {
			CreditsExchangeDTO = creditsExchangeDao.queryById(id);
		} catch (Exception e) {
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-selectById", e.getMessage());
			throw new RuntimeException();
		}
		return CreditsExchangeDTO;
	}

	@Override
	public List<CreditsExchangeDTO> queryCreditsByCredits(CreditsExchangeDTO creditsExchangeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "CreditsExchangeServiceImpl-selectById", creditsExchangeDTO);
		List<CreditsExchangeDTO> listCreditsExchangeDTO = new ArrayList<CreditsExchangeDTO>();
		try {
			listCreditsExchangeDTO = creditsExchangeDao.queryCreditsById(creditsExchangeDTO);
		} catch (Exception e) {
			logger.info("\n 方法[{}]，异常：[{}]", "CreditsExchangeServiceImpl-queryCreditsByCredits", e.getMessage());
			throw new RuntimeException();
		}
		return listCreditsExchangeDTO;
	}
}
