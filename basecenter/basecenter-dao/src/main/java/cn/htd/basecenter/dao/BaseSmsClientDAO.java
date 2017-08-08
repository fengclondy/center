package cn.htd.basecenter.dao;

import java.util.List;

import cn.htd.basecenter.domain.BaseSmsClient;
import cn.htd.common.dao.orm.BaseDAO;

public interface BaseSmsClientDAO extends BaseDAO<BaseSmsClient> {

	/**
	 * 查询模版信息
	 * 
	 * @param condition
	 * @return
	 */
	public List<BaseSmsClient> queryTemplateByCode(BaseSmsClient condition);

}