package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.bill.TraSetComOpeDTO;

public interface TraSetComOpeDAO extends BaseDAO<TraSetComOpeDAO>{

	public List<TraSetComOpeDTO> queryTraSetComOpeByParams(TraSetComOpeDTO dto);
	
	public int addTraSetComOpe(TraSetComOpeDTO dto);
	
	public int updateTraSetComOpe(TraSetComOpeDTO dto);
}
