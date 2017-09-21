package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.HTDCompanyDTO;

/**
 * 用户的处理接口类
 */
public interface HTDCompanyService {

	/**
     * 根查询所有的分部
     * 
     * @return
     */
	  public ExecuteResult<DataGrid<HTDCompanyDTO>> selectEmployeeByTypeOrComplayId(HTDCompanyDTO htdCompanyDTO);
	  
	  /**
	   * 根据分部名称模糊查询平台公司编码
	   * 
	   * @param name
	   * @return
	   */
	  public ExecuteResult<List<String>> selectSubCompaniesByParentName(String name);
	  
	  /**
	   *  根据平台公司编码查询分部名称
	   * 
	   * @param subComCodeList
	   * @return
	   */
	  public ExecuteResult<List<HTDCompanyDTO>> selectParentNameBySubCode(List<String> subComCodeList);

}
