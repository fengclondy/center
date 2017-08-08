package cn.htd.usercenter.service;

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

}
