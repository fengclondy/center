package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.SubstationDTO;
import cn.htd.usercenter.dto.UserDTO;

public interface SubstationDAO extends BaseDAO<UserDTO> {

	public List<SubstationDTO> querySubstationByName(@Param("substationDTO") SubstationDTO substationDTO,
			@Param("pager") Pager pager);

	public long querySubstationByNameCount(@Param("substationDTO") SubstationDTO substationDTO);

	public void addSubstation(@Param("substationDTO") SubstationDTO substationDTO, @Param("userId") Long userId);

	public void updateSubstation(@Param("name") String name, @Param("areas") String areas,
			@Param("substationId") int substationId, @Param("deletedFlag") int deletedFlag,
			@Param("userId") Long userId);

	public List<SubstationDTO> querySubstationById(@Param("substationId") int substationId);

	public List<SubstationDTO> selectSubstation();

	public int updateManger(@Param("userId") Long userId, @Param("substationId") int substationId);

	public List<SubstationDTO> selectSubstationByManger(@Param("userid") Long userid);

	public int updateMangerIsNull(@Param("substationId") int substationId, @Param("userId") Long userId);

	public int updateSubstationFlag(@Param("substationId") int substationId, @Param("userId") Long userId);

	public List<SubstationDTO> isRepeat(@Param("substationDTO") SubstationDTO substationDTO);

	public List<SubstationDTO> isHasUsed(@Param("cityCode") String cityCode,
			@Param("substationDTO") SubstationDTO substationDTO);

	/**
	 * @param code
	 * @return
	 */
	public List<SubstationDTO> selectSubstationByCityCode(@Param("code") String code);

}
