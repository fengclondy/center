package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;

public interface EmployeeDAO extends BaseDAO<EmployeeDTO> {

	public int addEmployee(@Param("dto") EmployeeDTO dto);

	public int editEmployee(@Param("dto") EmployeeDTO dto);

	public List<EmployeeDTO> selectSubstationMan(@Param("substationId") int substationId, @Param("name") String name,
			@Param("pager") Pager pager);

	public long selectSubstationManCount(@Param("substationId") int substationId, @Param("name") String name);

	public List<EmployeeDTO> selectEmployeeBySubId(@Param("substationId") int substationId, @Param("name") String name,
			@Param("pager") Pager pager);

	public long selectEmployeeBySubIdCount(@Param("substationId") int substationId, @Param("name") String name);

	public EmployeeDTO getEmployeeInfo(@Param("userId") Long userId);

	public List<EmployeeDTO> selectAllMan(@Param("dto") EmployeeDTO dto, @Param("htdDto") HTDCompanyDTO htdDto,
			@Param("pager") Pager pager);

	public long selectAllManCount(@Param("dto") EmployeeDTO dto, @Param("htdDto") HTDCompanyDTO htdDto);

	public int updateTotalNumPower(@Param("inchargeCompanies") String inchargeCompanies, @Param("userid") Long userid,
			@Param("userId") Long userId);

	public List<EmployeeDTO> isRepeatLoginId(@Param("loginId") String loginId);

	public int deleteSubstationMan(@Param("userid") Long userid, @Param("userId") Long userId);

	public List<EmployeeDTO> selectIsHasMan(@Param("substationId") int substationId);

	public List<EmployeeDTO> selectEmployeeByCompanyId(@Param("companyId") String companyId);

	/**
	 * @param companyId
	 * @return
	 */
	public List<EmployeeDTO> queryJlList(@Param("companyId") String companyId);

	/**
	 * @param mobile
	 * @return
	 */
	public EmployeeDTO getEmployeeInfoByMobile(@Param("mobile") String mobile);

	public List<String> getAllGw();

	public List<EmployeeDTO> getEmployeeByName(@Param("name")String name);
}
