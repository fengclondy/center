package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.ProvinceDTO;
import cn.htd.usercenter.dto.SubstationDTO;

public interface SubstationService {

	/**
	 * 根据分站名称查询分站列表
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<DataGrid<SubstationDTO>> querySubstationByName(SubstationDTO substationDTO, int page,
			int rows);

	/**
	 * 添加分站
	 * 
	 * @param name
	 * @param areas
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> addSubstation(SubstationDTO substationDTO, Long userId);

	/**
	 * 添加分站
	 * 
	 * @param name
	 * @param areas
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> updateSubstation(String name, String areas, int substationId, Long userId);

	ExecuteResult<Boolean> deleteSubstation(int substationId, Long userId);

	/**
	 * 根查询分站列表
	 * 
	 * @return
	 */
	public ExecuteResult<DataGrid<SubstationDTO>> selectAllSubstation();

	public ExecuteResult<DataGrid<SubstationDTO>> selectSubstationByCityCode(String code);

	public List<SubstationDTO> selectBySubstationId(int substationId);

	/**
	 * 判断分站名称是否重复
	 * 
	 * @param name
	 * @return
	 */
	public ExecuteResult<Boolean> isRepeat(SubstationDTO substationDTO);

	public List<ProvinceDTO> getProvinceList();

	public ProvinceDTO getProvince(String code);
}
