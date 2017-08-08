package cn.htd.tradecenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.domain.ExpressModel;
import cn.htd.tradecenter.dto.ExpressModelDTO;

public interface ExpressModelDAO extends BaseDAO<ExpressModel> {

	public ExpressModel selectByPrimaryKey(Long id);

	public ExpressModel selectSystemModel(Integer deliveryId);

	public List<ExpressModel> selectByName(ExpressModel expressModel);

	public List<ExpressModel> selectExpressModelList(@Param("expressModel") ExpressModelDTO expressModel,
			@Param("pager") Pager<ExpressModelDTO> pager);

	public Long queryPageCount(@Param("expressModel") ExpressModelDTO expressModel);

}