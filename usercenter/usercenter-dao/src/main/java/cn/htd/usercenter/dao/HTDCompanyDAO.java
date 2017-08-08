package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.HTDCompanyDTO;

public interface HTDCompanyDAO extends BaseDAO<HTDCompanyDTO> {

    public int insertHTDCompany(@Param("dto") HTDCompanyDTO dto);

    public int updateHTDCompany(@Param("dto") HTDCompanyDTO dto);

    public HTDCompanyDTO queryHTDCompanyById(@Param("companyId") String companyId);

    public List<HTDCompanyDTO> selectEmployeeByTypeOrComplayId(@Param("dto") HTDCompanyDTO dto);

    public List<HTDCompanyDTO> querySubCompanies(@Param("companyIds") String[] companyIds);

    public List<HTDCompanyDTO> querySubCompaniesByParentIdAndName(@Param("parentCompanyId") String parentCompanyId,
            @Param("name") String name);
}
