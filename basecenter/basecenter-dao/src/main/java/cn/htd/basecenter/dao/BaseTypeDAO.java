package cn.htd.basecenter.dao;

import cn.htd.basecenter.domain.BaseType;
import cn.htd.basecenter.dto.BaseTypeDTO;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseTypeDAO extends BaseDAO<BaseType> {

	/**
	 * 检查输入Type的唯一性
	 */
	public long checkBaseTypeUniqueness(BaseTypeDTO typeDTO);
}
