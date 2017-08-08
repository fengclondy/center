package cn.htd.tradecenter.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;
import cn.htd.tradecenter.dao.ExpressModelDAO;
import cn.htd.tradecenter.domain.ExpressModel;
import cn.htd.tradecenter.dto.ExpressModelDTO;
import cn.htd.tradecenter.service.ExpressModelExportService;

/**
 * <p>
 * Description: [快递单打印实现service]
 * </p>
 */
@Service("expressModelExportService")
public class ExpressModelExportServiceImpl implements ExpressModelExportService {

	@Resource
	private ExpressModelDAO expressModelDAO;

	/**
	 * 
	 * <p>
	 * Discription:[根据参数查询唯一的快递单数据]
	 * </p>
	 */
	@Override
	public ExecuteResult<ExpressModelDTO> getExpressModelByID(ExpressModelDTO expressModelDto) {
		ExecuteResult<ExpressModelDTO> er = new ExecuteResult<ExpressModelDTO>();
		try {
			if (null == expressModelDto) {
				er.setResultMessage("查询参数不能为空");
				return er;
			}
			ExpressModel expressModel = expressModelDAO.selectByPrimaryKey(expressModelDto.getId());
			if (null == expressModel) {
				return er;
			}
			er.setResult(this.getDto(expressModel));
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据模版名称查询模版信息]
	 * </p>
	 */
	@Override
	public ExecuteResult<List<ExpressModelDTO>> getExpressModelByName(ExpressModelDTO expressModelDto) {
		ExecuteResult<List<ExpressModelDTO>> er = new ExecuteResult<List<ExpressModelDTO>>();
		try {
			if (null == expressModelDto) {
				er.setResultMessage("查询参数不能为空");
				return er;
			}
			List<ExpressModel> expressModel = expressModelDAO.selectByName(this.getDomain(expressModelDto));
			if (null == expressModel) {
				return er;
			}
			List<ExpressModelDTO> list = new ArrayList<ExpressModelDTO>();
			for (ExpressModel em : expressModel) {
				list.add(this.getDto(em));
			}
			er.setResult(list);
			er.setResultMessage("success");
		} catch (Exception e) {
			er.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据条件查询快递单列表]
	 * </p>
	 */
	@Override
	public ExecuteResult<DataGrid<ExpressModelDTO>> getExpressModelList(ExpressModelDTO expressModelDto,
			Pager<ExpressModelDTO> pager) {
		ExecuteResult<DataGrid<ExpressModelDTO>> exlist = new ExecuteResult<DataGrid<ExpressModelDTO>>();
		DataGrid<ExpressModelDTO> exDataGrid = new DataGrid<ExpressModelDTO>();
		try {
			if (null == expressModelDto) {
				exlist.setResultMessage("查询参数不能为空");
				return exlist;
			}

			// 获取数据总数
			Long count = expressModelDAO.queryPageCount(expressModelDto);
			if (count > 0) {
				// 获取数据信息
				List<ExpressModel> emList = expressModelDAO.selectExpressModelList(expressModelDto, pager);
				List<ExpressModelDTO> list = new ArrayList<ExpressModelDTO>();
				for (ExpressModel em : emList) {
					list.add(this.getDto(em));
				}
				exDataGrid.setRows(list);
				if (null != pager) {
					exDataGrid.setPages(pager.getPage());
				}
				exDataGrid.setTotal(count);
				exlist.setResult(exDataGrid);
			}
			exlist.setResultMessage("success");
		} catch (Exception e) {
			exlist.setResultMessage("error");
			throw new RuntimeException(e);
		}

		return exlist;
	}

	/**
	 * 
	 * <p>
	 * Discription:[新增快递单数据]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addExpressModel(ExpressModelDTO expressModelDto) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			if (null == expressModelDto) {
				ex.setResultMessage("保存对象不能为空");
				return ex;
			}
			expressModelDAO.insert(this.getDomain(expressModelDto));
			ex.setResultMessage("success");
		} catch (Exception e) {
			ex.setResultMessage("error");
			throw new RuntimeException(e);
		}

		return ex;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据快递单id，删除]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> deteleExpressModel(String emid) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			if (null == emid || StringUtils.isEmpty(emid)) {
				ex.setResultMessage("参数不能为空");
				return ex;
			}
			expressModelDAO.delete(Long.valueOf(emid));
			ex.setResultMessage("success");
		} catch (Exception e) {
			ex.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return ex;
	}

	/**
	 * 
	 * <p>
	 * Discription:[修改快递单]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyExpressModel(ExpressModelDTO expressModelDto) {
		ExecuteResult<String> ex = new ExecuteResult<String>();
		try {
			if (null == expressModelDto || 0 == expressModelDto.getId()) {
				ex.setResultMessage("快递单修改不能为空");
				return ex;
			}
			expressModelDAO.update(this.getDomain(expressModelDto));
			ex.setResultMessage("success");
		} catch (Exception e) {
			ex.setResultMessage("error");
			throw new RuntimeException(e);
		}
		return ex;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据快递公司，获取模版]
	 * </p>
	 */
	@Override
	public ExpressModelDTO getExpressSystemModel(Integer deliveryId) {
		ExpressModel expressModel = expressModelDAO.selectSystemModel(deliveryId);
		try {
			return this.getDto(expressModel);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Discription:[dto转化为实体]
	 * </p>
	 * 
	 * @param expressModelDto
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private ExpressModel getDomain(ExpressModelDTO expressModelDto)
			throws IllegalAccessException, InvocationTargetException {
		ExpressModel ex = new ExpressModel();
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		BeanUtils.copyProperties(ex, expressModelDto);
		return ex;
	}

	/**
	 * 
	 * <p>
	 * Discription:[实体转化为dto]
	 * </p>
	 * 
	 * @param expressModel
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private ExpressModelDTO getDto(ExpressModel expressModel) throws IllegalAccessException, InvocationTargetException {
		ExpressModelDTO ex = new ExpressModelDTO();
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		BeanUtils.copyProperties(ex, expressModel);
		return ex;
	}

}
