package cn.htd.basecenter.service;

import java.util.List;

import cn.htd.basecenter.dto.IllegalCharacterDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [非法字符接口]
 * </p>
 */

public interface IllegalCharacterService {

	/**
	 * 添加非法字符info
	 *
	 */
	public ExecuteResult<IllegalCharacterDTO> addIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO);

	/**
	 * 批量添加非法字符info list
	 *
	 */
	public ExecuteResult<IllegalCharacterDTO> addIllegalCharacterList(List<IllegalCharacterDTO> list);

	/**
	 * 修改非法字符info
	 *
	 */
	public ExecuteResult<IllegalCharacterDTO> updateIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO);

	/**
	 * 删除非法字符info
	 *
	 */
	public ExecuteResult<IllegalCharacterDTO> deleteIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO);

	/**
	 * 根据id查询非法字符info
	 *
	 */
	public ExecuteResult<IllegalCharacterDTO> queryIllegalCharacterById(Long id);

	/**
	 * 查询非法字符列表
	 *
	 */
	public DataGrid<IllegalCharacterDTO> queryIllegalCharacterList(IllegalCharacterDTO illegalCharacterDTO,
			Pager<IllegalCharacterDTO> pager);

	/**
	 * 校验非法字符唯一性(是否存在)
	 */
	public boolean verifyIllegalCharacter(Long id, String content);
}
