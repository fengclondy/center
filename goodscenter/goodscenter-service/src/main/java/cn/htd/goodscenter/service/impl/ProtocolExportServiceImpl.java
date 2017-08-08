package cn.htd.goodscenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import cn.htd.goodscenter.dto.ContractOrderDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.ContractInfoDAO;
import cn.htd.goodscenter.dao.ContractMatDAO;
import cn.htd.goodscenter.dao.ContractOrderDAO;
import cn.htd.goodscenter.dao.ContractPaymentTermDAO;
import cn.htd.goodscenter.dto.ContractInfoDTO;
import cn.htd.goodscenter.dto.ContractMatDTO;
import cn.htd.goodscenter.dto.ContractPaymentTermDTO;
import cn.htd.goodscenter.service.ProtocolExportService;

/**
 * <p>
 * Description: [协议功能接口实现]
 * </p>
 */
@Service("protocolExportService")
public class ProtocolExportServiceImpl implements ProtocolExportService {

	@Resource
	private ContractInfoDAO contractInfoDAO;
	@Resource
	private ContractMatDAO contractMatDAO;
	@Resource
	private ContractOrderDAO contractOrderDAO;
	@Resource
	private ContractPaymentTermDAO contractPaymentTermDAO;

	/**
	 * <p>
	 * Discription:[根据条件查询详情接口实现]
	 * </p>
	 */
	public ExecuteResult<ContractInfoDTO> queryByContractInfo(ContractInfoDTO dto) {
		ExecuteResult<ContractInfoDTO> er = new ExecuteResult<ContractInfoDTO>();
		ContractInfoDTO contractInfoDTO = contractInfoDAO.findBycontractInfoDTO(dto);
		try {
			if (contractInfoDTO != null) {
				er.setResult(contractInfoDTO);
				er.setResultMessage("success");
			} else {
				er.setResultMessage("您要查询的数据不存在");
			}
		} catch (Exception e) {
			er.setResultMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表接口实现]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoList(ContractInfoDTO dto, Pager page) {
		ExecuteResult<DataGrid<ContractInfoDTO>> er = new ExecuteResult<DataGrid<ContractInfoDTO>>();
		DataGrid<ContractInfoDTO> dg = new DataGrid<ContractInfoDTO>();
		List<ContractInfoDTO> list = contractInfoDAO.queryPage(page, dto);
		Long count = contractInfoDAO.queryPageCount(dto);
		try {
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				er.setResult(dg);
			} else {
				er.setResultMessage("要查询的数据不存在");
			}

			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议的列表包含物料名称等信息]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractInfoDTO>> queryContractInfoPager(ContractInfoDTO dto, Pager page) {
		ExecuteResult<DataGrid<ContractInfoDTO>> er = new ExecuteResult<DataGrid<ContractInfoDTO>>();
		DataGrid<ContractInfoDTO> dg = new DataGrid<ContractInfoDTO>();
		List<ContractInfoDTO> list = contractInfoDAO.queryContractInfoPager(page, dto);
		Long count = contractInfoDAO.queryContractInfoPagerCount(dto);
		try {
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				er.setResult(dg);
			} else {
				er.setResultMessage("要查询的数据不存在");
			}

			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细详情接口实现]
	 * </p>
	 */
	public ExecuteResult<ContractMatDTO> queryByContractMat(ContractMatDTO dto) {
		ExecuteResult<ContractMatDTO> er = new ExecuteResult<ContractMatDTO>();
		ContractMatDTO contractMatDTO = contractMatDAO.findByContractMatDTO(dto);
		try {
			if (contractMatDTO != null) {
				er.setResult(contractMatDTO);
				er.setResultMessage("success");
			} else {
				er.setResultMessage("您要查询的数据不存在");
			}
		} catch (Exception e) {
			er.setResultMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议明细的列表接口实现]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryContractMatList(ContractMatDTO dto, Pager page) {
		ExecuteResult<DataGrid<Map>> er = new ExecuteResult<DataGrid<Map>>();
		DataGrid<Map> dg = new DataGrid<Map>();
		List<Map> list = contractMatDAO.queryPage(page, dto);
		Long count = contractMatDAO.queryPageCount(dto);
		try {
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				er.setResult(dg);
			} else {
				er.setResultMessage("要查询的数据不存在");
			}

			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单详情]
	 * </p>
	 */
	public ExecuteResult<ContractOrderDTO> queryByContractOrder(ContractOrderDTO dto) {
		ExecuteResult<ContractOrderDTO> er = new ExecuteResult<ContractOrderDTO>();
		ContractOrderDTO contractOrderDTO = contractOrderDAO.findByContractOrderDTO(dto);
		try {
			if (contractOrderDTO != null) {
				er.setResult(contractOrderDTO);
				er.setResultMessage("success");
			} else {
				er.setResultMessage("您要查询的数据不存在");
			}
		} catch (Exception e) {
			er.setResultMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议订单的列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractOrderDTO>> queryContractOrderList(ContractOrderDTO dto, Pager page) {
		ExecuteResult<DataGrid<ContractOrderDTO>> er = new ExecuteResult<DataGrid<ContractOrderDTO>>();
		DataGrid<ContractOrderDTO> dg = new DataGrid<ContractOrderDTO>();
		List<ContractOrderDTO> list = contractOrderDAO.queryPage(page, dto);
		Long count = contractOrderDAO.queryPageCount(dto);
		try {
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				er.setResult(dg);
			} else {
				er.setResultMessage("要查询的数据不存在");
			}

			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议账期接口实现]
	 * </p>
	 */
	public ExecuteResult<ContractPaymentTermDTO> queryByContractPaymentTerm(ContractPaymentTermDTO dto) {
		ExecuteResult<ContractPaymentTermDTO> er = new ExecuteResult<ContractPaymentTermDTO>();
		ContractPaymentTermDTO contractPaymentTermDTO = contractPaymentTermDAO.findByContractPaymentTermDTO(dto);
		try {
			if (contractPaymentTermDTO != null) {
				er.setResult(contractPaymentTermDTO);
				er.setResultMessage("success");
			} else {
				er.setResultMessage("您要查询的数据不存在");
			}
		} catch (Exception e) {
			er.setResultMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[根据条件查询协议账期的列表接口实现]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ContractPaymentTermDTO>> queryContractPaymentTermList(ContractPaymentTermDTO dto,
			Pager page) {
		ExecuteResult<DataGrid<ContractPaymentTermDTO>> er = new ExecuteResult<DataGrid<ContractPaymentTermDTO>>();
		DataGrid<ContractPaymentTermDTO> dg = new DataGrid<ContractPaymentTermDTO>();
		List<ContractPaymentTermDTO> list = contractPaymentTermDAO.queryPage(page, dto);
		Long count = contractPaymentTermDAO.queryPageCount(dto);
		try {
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				er.setResult(dg);
			} else {
				er.setResultMessage("要查询的数据不存在");
			}

			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[生成协议详情接口实现]
	 * </p>
	 */
	public ExecuteResult<String> addContractInfo(ContractInfoDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getContractNo())) {
				er.setResultMessage("协议编码不能为空");
				return er;
			}
			contractInfoDAO.insert(dto);
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[生成协议详情接口实现]
	 * </p>
	 */
	public ExecuteResult<String> addContractMat(ContractMatDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getContractNo())) {
				er.setResultMessage("协议编码不能为空");
				return er;
			}
			contractMatDAO.insert(dto);
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[生成协议订单]
	 * </p>
	 */
	public ExecuteResult<String> addContractOrder(ContractOrderDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getContractNo())) {
				er.setResultMessage("协议编码不能为空");
				return er;
			}
			// 判断传递订单号是多个还是一个
			if (dto.getOrderNos() != null && dto.getOrderNos().size() > 0) {
				for (String orderNo : dto.getOrderNos()) {
					dto.setOrderNo(orderNo);
					contractOrderDAO.insert(dto);
				}
			} else {
				contractOrderDAO.insert(dto);
			}
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[生成协议账期]
	 * </p>
	 */
	public ExecuteResult<String> addContractPaymentTerm(ContractPaymentTermDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getContractNo())) {
				er.setResultMessage("协议编码不能为空");
				return er;
			}
			// 判断传递订单号是多个还是一个
			if (dto.getPaymentDays() != null && 0L == dto.getPaymentDays()) {
				er.setResultMessage("账期不能为空");
				return er;
			}
			contractPaymentTermDAO.insert(dto);
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[修改协议详情接口实现]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractInfo(ContractInfoDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		ContractInfoDTO contractInfo = new ContractInfoDTO();
		contractInfo.setContractNo(dto.getContractNo());
		ContractInfoDTO contractInfoDTO = contractInfoDAO.findBycontractInfoDTO(contractInfo);
		try {
			if (contractInfoDTO != null) {
				if (dto.getBeginDate() != null) {
					contractInfoDTO.setBeginDate(dto.getBeginDate());
				}
				if (dto.getEndDate() != null) {
					contractInfoDTO.setEndDate(dto.getEndDate());
				}
				if (dto.getStatus() != null) {
					contractInfoDTO.setStatus(dto.getStatus());
				}
				if (dto.getActiveFlag() != null) {
					contractInfoDTO.setActiveFlag(dto.getActiveFlag());
				}
				if (dto.getUpdateBy() != null) {
					contractInfoDTO.setUpdateBy(dto.getUpdateBy());
				}
				if (dto.getUpdateDate() != null) {
					contractInfoDTO.setUpdateDate(dto.getUpdateDate());
				}
				if (dto.getRefusalReason() != null) {
					contractInfoDTO.setRefusalReason(dto.getRefusalReason());
				}
				if (contractInfoDAO.update(contractInfoDTO) > 0) {
					er.setResult("修改成功");
				} else {
					er.setResult("修改失败");
				}
			}
		} catch (Exception e) {
			er.setResult("error");
			throw new RuntimeException(e);
		}

		return er;
	}

	/**
	 * <p>
	 * Discription:[修改协议明细接口实现]
	 * </p>
	 */

	public ExecuteResult<String> modifyContractMat(ContractMatDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		ContractMatDTO contractMat = new ContractMatDTO();
		contractMat.setContractNo(dto.getContractNo());
		contractMat.setMatCd(dto.getMatCd());
		List<ContractMatDTO> contractMatDTOs = contractMatDAO.findAll(contractMat);
		for (ContractMatDTO contractMatDTO : contractMatDTOs) {
			try {
				if (dto.getMatPrice() != null) {
					contractMatDTO.setMatPrice(dto.getMatPrice());
				}
				if (dto.getActiveFlag() != null) {
					contractMatDTO.setActiveFlag(dto.getActiveFlag());
				}
				if (dto.getUpdateBy() != null) {
					contractMatDTO.setUpdateBy(dto.getUpdateBy());
				}
				if (dto.getUpdateDate() != null) {
					contractMatDTO.setUpdateDate(dto.getUpdateDate());
				}
				if (contractMatDAO.update(contractMatDTO) > 0) {
					er.setResult("修改成功");
				} else {
					er.setResult("修改失败");
				}
			} catch (Exception e) {
				er.setResult("error");
				throw new RuntimeException(e);
			}
		}

		return er;
	}

	/**
	 * <p>
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractPaymentTerm(ContractPaymentTermDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		ContractPaymentTermDTO contractPaymentTermDTO = new ContractPaymentTermDTO();
		contractPaymentTermDTO.setContractNo(dto.getContractNo());
		ContractPaymentTermDTO contractPaymentTerm = contractPaymentTermDAO
				.findByContractPaymentTermDTO(contractPaymentTermDTO);
		try {
			if (contractPaymentTerm != null) {
				if (dto.getActiveFlag() != null) {
					contractPaymentTerm.setActiveFlag(dto.getActiveFlag());
				}
				if (dto.getPaymentDays() != null) {
					contractPaymentTerm.setPaymentDays(dto.getPaymentDays());
				}
				if (dto.getPaymentType() != null) {
					contractPaymentTerm.setPaymentType(dto.getPaymentType());
				}
				if (contractPaymentTermDAO.update(contractPaymentTerm) > 0) {
					er.setResult("修改成功");
				} else {
					er.setResult("修改失败");
				}
			}
		} catch (Exception e) {
			er.setResult("error");
			throw new RuntimeException(e);
		}

		return er;
	}

	/**
	 * <p>
	 * Discription:[修改协议账期]
	 * </p>
	 */
	public ExecuteResult<String> modifyContractOrder(ContractOrderDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		ContractOrderDTO contractOrder = new ContractOrderDTO();
		contractOrder.setContractNo(dto.getContractNo());
		contractOrder.setOrderNo(dto.getOrderNo());
		ContractOrderDTO contractOrderDTO = contractOrderDAO.findByContractOrderDTO(contractOrder);
		try {
			if (contractOrderDTO != null) {
				if (dto.getActiveFlag() != null) {
					contractOrderDTO.setActiveFlag(dto.getActiveFlag());
				}
				if (dto.getState() != null) {
					contractOrderDTO.setState(dto.getState());
				}
				if (contractOrderDAO.update(contractOrderDTO) > 0) {
					er.setResult("修改成功");
				} else {
					er.setResult("修改失败");
				}
			}
		} catch (Exception e) {
			er.setResult("error");
			throw new RuntimeException(e);
		}

		return er;
	}

	/**
	 * <p>
	 * Discription:[生成协议编码]
	 * </p>
	 */
	public ExecuteResult<String> createContractNo() {
		ExecuteResult<String> er = new ExecuteResult<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random ra = new Random();
		int b = (ra.nextInt(10) * 10000);
		String a = "XY" + sdf.format(new Date()) + String.valueOf(b);
		er.setResult(a);
		return er;
	}
}
