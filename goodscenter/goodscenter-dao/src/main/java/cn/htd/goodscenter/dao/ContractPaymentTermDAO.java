package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ContractPaymentTermDTO;

import org.apache.ibatis.annotations.Param;

public interface ContractPaymentTermDAO extends BaseDAO<ContractPaymentTermDTO> {

	public List<ContractPaymentTermDTO> queryPage(@Param("pager") Pager pager, @Param("contractPaymentTerm") ContractPaymentTermDTO contractPaymentTerm);

	public Long queryPageCount(@Param("contractPaymentTerm") ContractPaymentTermDTO contractPaymentTerm);

	public ContractPaymentTermDTO findById(Integer id);

	public Integer update(ContractPaymentTermDTO contractPaymentTerm);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public ContractPaymentTermDTO findByContractPaymentTermDTO(@Param("contractPaymentTerm") ContractPaymentTermDTO contractPaymentTerm);
}
