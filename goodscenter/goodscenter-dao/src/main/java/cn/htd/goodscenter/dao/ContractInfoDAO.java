package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.ContractInfoDTO;

/**
 * <p>
 * Description: [协议dao]
 * </p>
 */

public interface ContractInfoDAO extends BaseDAO<ContractInfoDTO> {

	public List<ContractInfoDTO> queryPage(@Param("pager") Pager pager, @Param("contractInfo") ContractInfoDTO contractInfo);

	public Long queryPageCount(@Param("contractInfo") ContractInfoDTO contractInfo);

	public ContractInfoDTO findById(Long id);

	public Integer update(ContractInfoDTO contractInfo);

	public void delete(@Param("codes") List<String> codes);

	public List<Map<String, Object>> findAll();

	public ContractInfoDTO findBycontractInfoDTO(@Param("contractInfo") ContractInfoDTO contractInfo);

	public List<ContractInfoDTO> queryContractInfoPager(@Param("pager") Pager pager, @Param("contractInfo") ContractInfoDTO contractInfo);

	public Long queryContractInfoPagerCount(@Param("contractInfo") ContractInfoDTO contractInfo);

}
