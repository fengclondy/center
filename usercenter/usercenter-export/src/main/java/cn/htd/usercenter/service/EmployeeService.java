package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;

public interface EmployeeService {

	/**
	 * 添加产品信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<Boolean> addSubstationMan(EmployeeDTO employeeDTO, long userId);

	public ExecuteResult<Boolean> editSubstationAdmin(int substationId, long manager, long userId);

	/**
	 * 根据分站人员名称查询
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<EmployeeDTO>> selectSubstationMan(String name, int page, int rows, long userId);

	/**
	 * 根据产品编码更新产品信息
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> editSubstationMan(EmployeeDTO employeeDTO, long userid, long userId);

	/**
	 * 根据userid获取相关信息
	 * 
	 * @param userId
	 * @return
	 */
	public EmployeeDTO getEmployeeInfo(long userId);

	/**
	 * 根据分站id查询该分站下面的人员
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<EmployeeDTO>> selectEmployeeBySubId(int substationId, String name, int page,
			int rows);

	/**
	 * 查询总部的人员
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<EmployeeDTO>> selectAllMan(EmployeeDTO employeeDTO, HTDCompanyDTO htdCompanyDTO,
			int page, int rows);

	/**
	 * 修改总部人员数字权限
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> updateTotalNumPower(String inchargeCompanies, long userid, long userId);

	/**
	 * 判断用户名是否重复
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> isRepeatLoginId(String loginId);

	/**
	 * 删除分站人员deleteSubstationAdminMan
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> deleteSubstationMan(long userid, long userId);

	/**
	 * 根据公司id查询客户经理
	 * 
	 * @param companyId
	 * @return
	 */
	public ExecuteResult<List<EmployeeDTO>> queryCustomerJLList(String companyId);

	/**
	 * 根据手机号获取相关信息
	 * 
	 * @param userId
	 * @return
	 */
	public EmployeeDTO getEmployeeInfoByMobile(String mobile);

	/**
	 * @param empNo
	 * @return
	 */
	public EmployeeDTO queryEmployeeByEmpNo(String empNo);
	
	/**
	 * 获得所有岗位
	 * @return
	 */
	public ExecuteResult<List<String>> getAllGw();
}
