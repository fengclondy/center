package cn.htd.basecenter.dao;

import java.util.List;

import cn.htd.basecenter.domain.BasePlacardScope;
import cn.htd.common.dao.orm.BaseDAO;

public interface BasePlacardScopeDAO extends BaseDAO<BasePlacardScope> {

	/**
	 * 批量保存公告发送范围信息
	 * 
	 * @param scopeList
	 */
	public void addBasePlacardScopeList(List<BasePlacardScope> scopeList);

}
