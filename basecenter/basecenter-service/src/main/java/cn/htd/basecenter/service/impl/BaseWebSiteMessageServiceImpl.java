package cn.htd.basecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dao.BaseWebSiteMessageDAO;
import cn.htd.basecenter.domain.BaseWebsiteMessage;
import cn.htd.basecenter.dto.WebSiteMessageDTO;
import cn.htd.basecenter.service.BaseWebSiteMessageService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("baseWebSiteMessageService")
public class BaseWebSiteMessageServiceImpl implements BaseWebSiteMessageService {
	private final static Logger logger = LoggerFactory.getLogger(BaseWebSiteMessageServiceImpl.class);

	@Resource
	private BaseWebSiteMessageDAO baseWebSiteMessageDAO;

	@Override
	public ExecuteResult<String> addWebMessage(WebSiteMessageDTO webSiteMessageDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (null != webSiteMessageDTO.getId()) {
				baseWebSiteMessageDAO.update(webSiteMessageDTO);
				executeResult.setResult("修改成功！");
			} else {
				baseWebSiteMessageDAO.add(webSiteMessageDTO);
				executeResult.setResult("添加成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			executeResult.setResult("操作失败！");
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public int totalMessages(long userId, int messageType, int readFlag) {
		BaseWebsiteMessage baseWebsiteMessage = new BaseWebsiteMessage();
		baseWebsiteMessage.setWmFromUserid(userId);
		baseWebsiteMessage.setType(messageType);
		baseWebsiteMessage.setWmRead(readFlag);
		return baseWebSiteMessageDAO.totalMessages(baseWebsiteMessage);
	}

	@Override
	public ExecuteResult<String> modifyWebSiteMessage(String[] ids, String wmRead) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			for (String id : ids) {
				System.out.println(id + "++++++++++++" + wmRead);
				Long id_l = Long.parseLong(id);
				Integer wm = Integer.parseInt(wmRead);
				BaseWebsiteMessage baseWebsiteMessage = new BaseWebsiteMessage(id_l, wm);
				// baseWebSiteMessageDAO.modifyWebSiteMessage(id, wmRead);
				baseWebSiteMessageDAO.modifyWebSiteMessage(baseWebsiteMessage);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			executeResult.addErrorMsg(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public DataGrid<WebSiteMessageDTO> queryWebSiteMessageList(WebSiteMessageDTO webSiteMessageDTO,
			Pager<WebSiteMessageDTO> page) {
		DataGrid<WebSiteMessageDTO> dataGrid = new DataGrid<WebSiteMessageDTO>();
		try {
			List<WebSiteMessageDTO> webSiteMessageDTOs = baseWebSiteMessageDAO
					.queryWebSiteMessageList(webSiteMessageDTO, page);
			long num = baseWebSiteMessageDAO.queryCount(webSiteMessageDTO);
			dataGrid.setRows(webSiteMessageDTOs);
			dataGrid.setTotal(num);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<WebSiteMessageDTO> getWebSiteMessageInfo(WebSiteMessageDTO webSiteMessageDTO) {
		ExecuteResult<WebSiteMessageDTO> result = new ExecuteResult<WebSiteMessageDTO>();
		try {
			WebSiteMessageDTO wsmDTO = baseWebSiteMessageDAO.getWebSiteMessageInfo(webSiteMessageDTO);
			result.setResult(wsmDTO);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

}
