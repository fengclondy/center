package cn.htd.goodscenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.TranslationInfoDAO;
import cn.htd.goodscenter.dao.TranslationMatDAO;
import cn.htd.goodscenter.dao.TranslationOrderDAO;
import cn.htd.goodscenter.dto.TranslationInfoDTO;
import cn.htd.goodscenter.dto.TranslationMatDTO;
import cn.htd.goodscenter.dto.TranslationOrderDTO;
import cn.htd.goodscenter.service.TranslationExportService;

/**
 * <p>
 * Description: [协议功能接口实现]
 * </p>
 */
@Service("translationExportService")
public class TranslationExportServiceImpl implements TranslationExportService {

	@Resource
	private TranslationInfoDAO translationInfoDAO;
	@Resource
	private TranslationMatDAO translationMatDAO;
	@Resource
	private TranslationOrderDAO translationOrderDAO;

	/**
	 * <p>
	 * Discription:[根据条件查询详情接口实现]
	 * </p>
	 */
	public ExecuteResult<TranslationInfoDTO> queryByTranslationInfo(TranslationInfoDTO dto) {
		ExecuteResult<TranslationInfoDTO> er = new ExecuteResult<TranslationInfoDTO>();
		TranslationInfoDTO translationInfoDTO = translationInfoDAO.findByTranslationInfoDTO(dto);
		try {
			if (translationInfoDTO != null) {
				er.setResult(translationInfoDTO);
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
	public ExecuteResult<DataGrid<TranslationInfoDTO>> queryTranslationInfoList(TranslationInfoDTO dto, Pager page) {
		ExecuteResult<DataGrid<TranslationInfoDTO>> er = new ExecuteResult<DataGrid<TranslationInfoDTO>>();
		DataGrid<TranslationInfoDTO> dg = new DataGrid<TranslationInfoDTO>();
		List<TranslationInfoDTO> list = translationInfoDAO.queryPage(page, dto);
		Long count = translationInfoDAO.queryPageCount(dto);
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
	 * Discription:[根据条件查询协议的列表包含物料名称等信息接口实现]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Map>> queryTranslationInfoPager(TranslationInfoDTO dto, Pager page) {
		ExecuteResult<DataGrid<Map>> er = new ExecuteResult<DataGrid<Map>>();
		DataGrid<Map> dg = new DataGrid<Map>();
		List<Map> translationInfoDTOs = translationInfoDAO.queryTranslationInfoPager(page, dto);
		Long count = translationInfoDAO.queryTranslationInfoPagerCount(dto);
		try {
			if (translationInfoDTOs != null) {
				dg.setRows(translationInfoDTOs);
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
	public ExecuteResult<TranslationMatDTO> queryByTranslationMat(TranslationMatDTO dto) {
		ExecuteResult<TranslationMatDTO> er = new ExecuteResult<TranslationMatDTO>();
		TranslationMatDTO translationMatDTO = translationMatDAO.findByTranslationMatDTO(dto);
		try {
			if (translationMatDTO != null) {
				er.setResult(translationMatDTO);
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
	public ExecuteResult<DataGrid<Map>> queryTranslationMatList(TranslationMatDTO dto, Pager page) {
		ExecuteResult<DataGrid<Map>> er = new ExecuteResult<DataGrid<Map>>();
		DataGrid<Map> dg = new DataGrid<Map>();
		List<Map> list = translationMatDAO.queryPage(page, dto);
		Long count = translationMatDAO.queryPageCount(dto);
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
	public ExecuteResult<TranslationOrderDTO> queryByTranslationOrder(TranslationOrderDTO dto) {
		ExecuteResult<TranslationOrderDTO> er = new ExecuteResult<TranslationOrderDTO>();
		TranslationOrderDTO translationOrderDTO = translationOrderDAO.findByTranslationOrderDTO(dto);
		try {
			if (translationOrderDTO != null) {
				er.setResult(translationOrderDTO);
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
	public ExecuteResult<DataGrid<TranslationOrderDTO>> queryTranslationOrderList(TranslationOrderDTO dto, Pager page) {
		ExecuteResult<DataGrid<TranslationOrderDTO>> er = new ExecuteResult<DataGrid<TranslationOrderDTO>>();
		DataGrid<TranslationOrderDTO> dg = new DataGrid<TranslationOrderDTO>();
		List<TranslationOrderDTO> list = translationOrderDAO.queryPage(page, dto);
		Long count = translationOrderDAO.queryPageCount(dto);
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
	public ExecuteResult<String> addTranslationInfo(TranslationInfoDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getTranslationNo())) {
				er.setResultMessage("询价编码不能为空");
				return er;
			}
			translationInfoDAO.insert(dto);
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
	public ExecuteResult<String> addTranslationMat(TranslationMatDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getTranslationNo())) {
				er.setResultMessage("询价编码不能为空");
				return er;
			}
			translationMatDAO.insert(dto);
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
	public ExecuteResult<String> addTranslationOrder(TranslationOrderDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			if (null == dto || StringUtils.isEmpty(dto.getTranslationNo())) {
				er.setResultMessage("协议编码不能为空");
				return er;
			}

			// 判断传递订单号是多个还是一个
			if (dto.getOrderNos() != null && dto.getOrderNos().size() > 0) {
				for (String orderNo : dto.getOrderNos()) {
					dto.setOrderNo(orderNo);
					translationOrderDAO.insert(dto);
				}
			} else {
				translationOrderDAO.insert(dto);
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
	 * Discription:[修改协议详情接口实现]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationInfo(TranslationInfoDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		TranslationInfoDTO translationInfoDTO = translationInfoDAO.findById(dto.getId());
		if (translationInfoDTO != null) {
			if (dto.getBeginDate() != null) {
				translationInfoDTO.setBeginDate(dto.getBeginDate());
			}
			if (dto.getTranslationName() != null) {
				translationInfoDTO.setTranslationName(dto.getTranslationName());
			}
			if (dto.getMatCd() != null) {
				translationInfoDTO.setMatCd(dto.getMatCd());
			}
			if (dto.getEndDate() != null) {
				translationInfoDTO.setEndDate(dto.getEndDate());
			}
			if (dto.getActiveFlag() != null) {
				translationInfoDTO.setActiveFlag(dto.getActiveFlag());
			}
			if (dto.getStatus() != null) {
				translationInfoDTO.setStatus(dto.getStatus());
			}
			if (dto.getMatAttribute() != null) {
				translationInfoDTO.setMatAttribute(dto.getMatAttribute());
			}
			if (dto.getCreateDate() != null) {
				translationInfoDTO.setCreateDate(dto.getCreateDate());
			}
			if (dto.getDeliveryDate() != null) {
				translationInfoDTO.setDeliveryDate(dto.getDeliveryDate());
			}
			if (dto.getQuantity() != null && dto.getQuantity() != 0) {
				translationInfoDTO.setQuantity(dto.getQuantity());
			}
			if (dto.getRemarks() != null) {
				translationInfoDTO.setRemarks(dto.getRemarks());
			}
			if (dto.getAnnex() != null) {
				translationInfoDTO.setAnnex(dto.getAnnex());
			}
			if (translationInfoDAO.update(translationInfoDTO) > 0) {
				er.setResult("修改成功");
			} else {
				er.setResult("修改失败");
			}
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[修改协议明细接口实现]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationMat(TranslationMatDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		TranslationMatDTO translationMatDTO = translationMatDAO.findById(dto.getId());
		try {
			if (translationMatDTO != null) {
				if (dto.getMatPrice() != null) {
					translationMatDTO.setMatPrice(dto.getMatPrice());
				}
				if (dto.getActiveFlag() != null) {
					translationMatDTO.setActiveFlag(dto.getActiveFlag());
				}
				if (dto.getBeginDate() != null) {
					translationMatDTO.setBeginDate(dto.getBeginDate());
				}
				if (dto.getEndDate() != null) {
					translationMatDTO.setEndDate(dto.getEndDate());
				}
				if (dto.getQuantity() != null) {
					translationMatDTO.setQuantity(dto.getQuantity());
				}
				if (translationMatDAO.update(translationMatDTO) > 0) {
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
	 * Discription:[修改协议明细]
	 * </p>
	 */
	public ExecuteResult<String> modifyTranslationOrder(TranslationOrderDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		TranslationOrderDTO translationOrderDTO = translationOrderDAO.findById(dto.getId());
		try {
			if (translationOrderDTO != null) {
				if (dto.getActiveFlag() != null) {
					translationOrderDTO.setActiveFlag(dto.getActiveFlag());
				}
				if (translationOrderDAO.update(translationOrderDTO) > 0) {
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
	 * Discription:[生成询价编码]
	 * </p>
	 */
	public ExecuteResult<String> createTranslationNo() {
		ExecuteResult<String> er = new ExecuteResult<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random ra = new Random();
		int b = (ra.nextInt() * 10000);
		String a = "QG" + sdf.format(new Date()) + String.valueOf(b);
		er.setResult(a);
		return er;
	}
}
