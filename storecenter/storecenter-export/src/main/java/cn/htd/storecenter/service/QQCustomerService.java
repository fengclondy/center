package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.QQCustomerDTO;

/**
 * QQ客服服务类
 * 
 * @author Klaus
 */
public interface QQCustomerService {

	/**
	 * 通过条件查找QQ分页数据客服
	 * 
	 * @param qqCustomerDTO
	 * @param pager
	 * @return DataGrid表格
	 */
	public DataGrid<QQCustomerDTO> findQQCustomerByCondition(QQCustomerDTO qqCustomerDTO, Pager<QQCustomerDTO> pager);

	/**
	 * 通过条件查找QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return DataGrid表格
	 */
	public List<QQCustomerDTO> searchQQCustomerByCondition(QQCustomerDTO qqCustomerDTO);

	/**
	 * 新增QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 执行结果信息类
	 */
	public ExecuteResult<String> addQQCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * 删除QQ客服
	 *
	 * @return 执行结果信息类
	 */
	public ExecuteResult<String> deleteQQCustomer(QQCustomerDTO dto);

	/**
	 * 修改QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 执行结果信息类
	 */
	public ExecuteResult<String> modifyQQCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * @param customerQQNumber
	 * @return
	 */
	public boolean verifyQQCustomer(Long customerId, String customerQQNumber);

	/**
	 * 设置默认客服
	 *
	 */
	public ExecuteResult<String> setDefaultCustomer(QQCustomerDTO dto);
}
