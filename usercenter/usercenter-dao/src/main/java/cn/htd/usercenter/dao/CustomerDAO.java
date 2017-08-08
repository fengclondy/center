package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.CustomerDTO;

public interface CustomerDAO extends BaseDAO<CustomerDTO> {
	public int deleteByPrimaryKey(Long userId);

	public int addCustomer(@Param("dto") CustomerDTO record);

	public int updateByUid(@Param("dto") CustomerDTO record);

	public List<CustomerDTO> isRepeatMobile(@Param("mobile") String mobile, @Param("userid") long userid);

	/**
	 * @param name
	 * @param companyId
	 * @param pager
	 * @return
	 */
	public List<CustomerDTO> selectList(@Param("name") String name, @Param("companyId") long companyId,
			@Param("pager") Pager pager);

	/**
	 * @return
	 */
	public List<CustomerDTO> isRepeatdc(@Param("userid") long userid, @Param("companyId") long companyId);

	/**
	 * @param userid
	 * @param userId2
	 */
	public void deleteCustomer(@Param("userid") long userid, @Param("userId") long userId);

	/**
	 * @param name
	 * @param companyId
	 * @return
	 */
	public long selectAllCount(@Param("name") String name, @Param("companyId") long companyId);

	/**
	 * @param userId
	 * @return
	 */
	public CustomerDTO getCustomerInfo(@Param("userId") Long userId);

	/**
	 * @param customerDTO
	 */
	public void updatePermByUid(@Param("dto") CustomerDTO record);

	/**
	 * @return
	 */
	public String genVMSMemberCode();

	/**
	 * @return
	 */
	public String genVMSPTMemberCode();

	/**
	 * @return
	 */
	public String genVMSOUTMemberCode();

}