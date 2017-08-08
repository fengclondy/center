package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ContractMatDTO;

import org.apache.ibatis.annotations.Param;

public interface ContractMatDAO extends BaseDAO<ContractMatDTO> {

	public List<Map> queryPage(@Param("pager") Pager pager, @Param("contractMat") ContractMatDTO contractMat);

	public Long queryPageCount(@Param("contractMat") ContractMatDTO contractMat);

	public ContractMatDTO findById(Long id);

	public Integer update(ContractMatDTO contractMat);

	public void delete(@Param("codes") List<String> codes);

	public List<ContractMatDTO> findAll(@Param("contractMat") ContractMatDTO contractMat);

	public ContractMatDTO findByContractMatDTO(@Param("contractMat") ContractMatDTO contractMat);
}
