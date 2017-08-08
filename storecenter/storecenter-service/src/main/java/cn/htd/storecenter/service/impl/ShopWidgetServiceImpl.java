package cn.htd.storecenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dao.ShopWidgetDAO;
import cn.htd.storecenter.dao.ShopWidgetSheetDAO;
import cn.htd.storecenter.dto.ShopWidgetDTO;
import cn.htd.storecenter.dto.ShopWidgetSheetDTO;
import cn.htd.storecenter.service.ShopWidgetService;

@Service("shopWidgetService")
public class ShopWidgetServiceImpl implements ShopWidgetService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopWidgetDAO widgetDAO;
	@Resource
	private ShopWidgetSheetDAO sheetDAO;

	@Override
	public ExecuteResult<Boolean> saveOrUpdateWidget(ShopWidgetDTO widgetDTO) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		try {
			// 确保准确， 先删后加
			this.deleteWidget(widgetDTO.getFrameId(), widgetDTO.getWidgetId());
			widgetDAO.insert(widgetDTO);
			List<ShopWidgetSheetDTO> sheets = widgetDTO.getSheets();
			for (int i = 0; i < sheets.size(); i++) {
				ShopWidgetSheetDTO sheet = sheets.get(i);
				sheet.setFrameId(widgetDTO.getFrameId());
				sheet.setWidgetId(widgetDTO.getWidgetId());
				sheet.setOrderNo(i);
				sheetDAO.insert(sheet);
			}
			er.setResult(true);
		} catch (Exception e) {
			er.getErrorMessages().add(e.getMessage());
			er.setResultMessage("保存或更新控件失败");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<Boolean> deleteWidget(long frameId, String widgetId) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		try {
			widgetDAO.delete(frameId, widgetId);
			sheetDAO.delete(frameId, widgetId);
			er.setResult(true);
		} catch (Exception e) {
			er.getErrorMessages().add(e.getMessage());
			er.setResultMessage("删除控件失败");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<List<ShopWidgetDTO>> findWidget(ShopWidgetDTO widgetDTO) {
		ExecuteResult<List<ShopWidgetDTO>> result = new ExecuteResult<List<ShopWidgetDTO>>();
		List<ShopWidgetDTO> widgetList = widgetDAO.selectListByCondition(widgetDTO, null);

		// 查询出所有sheet 然后代码归类， 有助于速度
		ShopWidgetSheetDTO sheetDTO = new ShopWidgetSheetDTO();
		sheetDTO.setFrameId(widgetDTO.getFrameId());
		List<ShopWidgetSheetDTO> sheetList = sheetDAO.selectListByCondition(sheetDTO, null);

		for (ShopWidgetDTO widget : widgetList) {
			widget.setSheets(new ArrayList<ShopWidgetSheetDTO>());
			for (ShopWidgetSheetDTO sheet : sheetList) {
				if (sheet.getWidgetId().equals(widget.getWidgetId())) {
					widget.getSheets().add(sheet);
				}
			}
		}

		result.setResult(widgetList);
		return result;
	}

	@Override
	public ExecuteResult<Boolean> deleteWidget(long frameId) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		try {
			widgetDAO.deleteByFrameId(frameId);
			sheetDAO.deleteByFrameId(frameId);
			er.setResult(true);
		} catch (Exception e) {
			er.getErrorMessages().add(e.getMessage());
			er.setResultMessage("删除控件失败");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

}
