package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ContractOrderDTO;

import org.apache.ibatis.annotations.Param;

public interface ContractOrderDAO extends BaseDAO<ContractOrderDTO> {

	public List<ContractOrderDTO> queryPage(@Param("pager") Pager pager, @Param("contractOrder") ContractOrderDTO contractOrder);

	public Long queryPageCount(@Param("contractOrder") ContractOrderDTO contractOrder);

	public ContractOrderDTO findById(Long id);

	public Integer update(ContractOrderDTO contractOrder);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public ContractOrderDTO findByContractOrderDTO(@Param("contractOrder") ContractOrderDTO contractOrder);
}
