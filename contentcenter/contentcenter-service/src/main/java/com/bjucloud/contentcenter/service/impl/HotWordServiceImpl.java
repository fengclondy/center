package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.HotWordDAO;
import com.bjucloud.contentcenter.dto.HotWordDTO;
import com.bjucloud.contentcenter.service.HotWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("hotWordService")
public class HotWordServiceImpl implements HotWordService {
	@Resource
	private HotWordDAO hotWordDAO;

	@Override
	public DataGrid<HotWordDTO> datagrid(HotWordDTO dto, Pager page) {
		DataGrid<HotWordDTO> dg = new DataGrid<HotWordDTO>();

		List<HotWordDTO> list = this.hotWordDAO.selectListByCondition(dto, page);
		Long total = this.hotWordDAO.selectCountByCondition(dto);

		dg.setTotal(total);
		dg.setRows(list);
		return dg;
	}

	@Override
	public ExecuteResult<String> saveHotWord(HotWordDTO hotWordDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (hotWordDTO == null){
				result.addErrorMessage("参数不为空! ");
			}
			Long id = hotWordDTO.getId();
			if (id != null){
				hotWordDAO.update(hotWordDTO);
			}else {
				hotWordDAO.insert(hotWordDTO);
			}
			result.setResultMessage("1");
			result.setResult("0");
		}catch (Exception e){
			result.addErrorMessage("执行方法【saveHotWord】报错：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public DataGrid<HotWordDTO> queryByName(String name) {
		DataGrid<HotWordDTO> result = new DataGrid<HotWordDTO>();
		try {
			List<HotWordDTO> hotWordDTOs = hotWordDAO.selectByName(name);
			result.setRows(hotWordDTOs);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
	@Override
	public DataGrid<HotWordDTO> queryBySortNum(Long sortNum) {
		DataGrid<HotWordDTO> result = new DataGrid<HotWordDTO>();
		try {
			List<HotWordDTO> hotWordDTOs = hotWordDAO.selectBySortNum(sortNum);
			result.setRows(hotWordDTOs);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<HotWordDTO> queryById(Long id) {
		ExecuteResult<HotWordDTO> result = new ExecuteResult<HotWordDTO>();
		try {
			HotWordDTO subAdDTO = hotWordDAO.selectById(id);
			result.setResult(subAdDTO);
		}catch (Exception e){
			result.addErrorMessage("执行方法【queryById】报错：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}


	@Override
	public ExecuteResult<String> delete(Long id) {
		this.hotWordDAO.delete(id);
		return new ExecuteResult<String>();
	}

}
