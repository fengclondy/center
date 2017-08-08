package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.QQCustomerDAO;
import cn.htd.storecenter.dto.QQCustomerDTO;
import cn.htd.storecenter.service.QQCustomerService;

/**
 * QQ客服服务实现类
 */
@Service("qqCustomerService")
public class QQCustomerServiceImpl implements QQCustomerService {

	private final static Logger logger = LoggerFactory.getLogger(QQCustomerServiceImpl.class);

	@Resource
	private QQCustomerDAO qqCustomerDAO;

	/**
	 * 通过条件查找QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @param page
	 * @return DataGrid表格
	 */
	@Override
	public DataGrid<QQCustomerDTO> findQQCustomerByCondition(QQCustomerDTO qqCustomerDTO, Pager<QQCustomerDTO> page) {
		DataGrid<QQCustomerDTO> dataGrid = new DataGrid<QQCustomerDTO>();
		List<QQCustomerDTO> list = qqCustomerDAO.selectListByCondition(qqCustomerDTO, page);
		Long listSize = qqCustomerDAO.selectCountByCondition(qqCustomerDTO);
		dataGrid.setRows(list);
		dataGrid.setTotal(listSize);
		return dataGrid;
	}

	/**
	 * 通过条件查找QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return DataGrid表格
	 */
	@Override
	public List<QQCustomerDTO> searchQQCustomerByCondition(QQCustomerDTO qqCustomerDTO) {
		List<QQCustomerDTO> list = qqCustomerDAO.selectListByCondition(qqCustomerDTO, null);
		return list;
	}

	/**
	 * 新增QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 执行结果信息类
	 */
	@Override
	public ExecuteResult<String> addQQCustomer(QQCustomerDTO qqCustomerDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			qqCustomerDAO.addQQCustomer(qqCustomerDTO);
			executeResult.setResultMessage("新增QQ客服成功！");
		} catch (Exception e) {
			executeResult.setResultMessage("新增QQ客服失败！");
			e.printStackTrace();
			logger.error("【QQ客服】-【新增QQ客服】出现异常！");
		}
		return executeResult;
	}

	/**
	 * 删除QQ客服
	 *
	 * @return 执行结果信息类
	 */
	@Override
	public ExecuteResult<String> deleteQQCustomer(QQCustomerDTO dto) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			qqCustomerDAO.deleteQQCustomer(dto);
			executeResult.setResultMessage("删除QQ客服成功！");
		} catch (Exception e) {
			executeResult.setResultMessage("删除QQ客服失败！");
			e.printStackTrace();
			logger.error("【QQ客服】-【新增QQ客服】出现异常！");
		}
		return executeResult;
	}

	/**
	 * 修改QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 执行结果信息类
	 */
	@Override
	public ExecuteResult<String> modifyQQCustomer(QQCustomerDTO qqCustomerDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			qqCustomerDAO.modifyQQCustomer(qqCustomerDTO);
			executeResult.setResultMessage("修改QQ客服成功！");
		} catch (Exception e) {
			executeResult.setResultMessage("修改QQ客服失败！");
			e.printStackTrace();
			logger.error("【QQ客服】-【修改QQ客服】出现异常！");
		}
		return executeResult;
	}

	/**
	 * 校验QQ唯一性
	 * 
	 * @param customerQQNumber
	 * @return
	 */
	@Override
	public boolean verifyQQCustomer(Long customerId, String customerQQNumber) {
		return qqCustomerDAO.verifyQQCustomer(customerId, customerQQNumber) > 0 ? true : false;
	}

	/**
	 * 设置默认客服
	 *
	 */
	@Override
	public ExecuteResult<String> setDefaultCustomer(QQCustomerDTO dto) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			qqCustomerDAO.clearDefaultCustomer(
					StringUtils.isNotEmpty(dto.getShopId().toString()) ? dto.getShopId().toString() : null);
			qqCustomerDAO.setDefaultCustomer(dto);
			executeResult.setResultMessage("设置默认客服成功！");
		} catch (Exception e) {
			executeResult.setResultMessage("设置默认客服失败！");
			e.printStackTrace();
			logger.error("【QQ客服】-【修改QQ客服】出现异常！");
		}
		return executeResult;

	}
}
