package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.IllegalCharacter;
import cn.htd.basecenter.dto.IllegalCharacterDTO;
import cn.htd.common.dao.orm.BaseDAO;

/**
 * <p>
 * 非法字符DAO层
 * </p>
 *
 **/

public interface IllegalCharacterDAO extends BaseDAO<IllegalCharacter> {

	/**
	 * 批量插入非法字符
	 * @param list
	 * @return
	 */
	public void addIllegalCharacterList(List<IllegalCharacterDTO> list);

	/**
	 * 校验非法字符唯一性(是否存在)
	 * @param id
	 * @param content
	 * @return
	 */
	public int verifyIllegalCharacterExistence(@Param("id") Long id, @Param("content") String content);
}
