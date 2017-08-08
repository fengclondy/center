package cn.htd.storecenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.QQCustomerDTO;

/**
 * QQ客服DTO
 * 
 * @author Klaus
 */
public interface QQCustomerDAO extends BaseDAO<QQCustomerDTO> {

	/**
	 * 新增QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 受影响记录数
	 */
	public int addQQCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * 删除QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 受影响记录数
	 */
	public int deleteQQCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * 修改QQ客服
	 * 
	 * @param qqCustomerDTO
	 * @return 受影响记录数
	 */
	public int modifyQQCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * @param customerQQNumber
	 * @return
	 */
	public int verifyQQCustomer(@Param("customerId") Long customerId,
			@Param("customerQQNumber") String customerQQNumber);

	/**
	 * 设置默认客服
	 *
	 */
	public void setDefaultCustomer(QQCustomerDTO qqCustomerDTO);

	/**
	 * 清楚默认客服
	 * 
	 * @param shopId
	 */
	public void clearDefaultCustomer(@Param("shopId") String shopId);

}