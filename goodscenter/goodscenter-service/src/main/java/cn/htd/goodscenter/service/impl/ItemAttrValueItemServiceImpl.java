package cn.htd.goodscenter.service.impl;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dao.ItemAttrValueItemDAO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemAttrValueItemDTO;
import cn.htd.goodscenter.service.ItemAttrValueItemExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("itemAttrValueItemExportService")
public class ItemAttrValueItemServiceImpl implements ItemAttrValueItemExportService {
	private static final Logger logger = LoggerFactory.getLogger(ItemAttrValueItemServiceImpl.class);
	@Resource
	private ItemAttrValueItemDAO itemAttrValueItemDAO;

	@Override
	public ExecuteResult<String> addItemAttrValueItem(List<ItemAttrValueItemDTO> itemAttrValueItemList) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			for (ItemAttrValueItemDTO itemAttrValueItemDTO : itemAttrValueItemList) {
				itemAttrValueItemDAO.add(itemAttrValueItemDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" addItemAttrValueItem error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> modifyItemAttrValueItem(List<ItemAttrValueItemDTO> itemAttrValueItemList) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		try {
			itemAttrValueItemDAO.updatestatus(itemAttrValueItemList.get(0).getItemId());
			for (ItemAttrValueItemDTO itemAttrValueItemDTO : itemAttrValueItemList) {

				itemAttrValueItemDAO.add(itemAttrValueItemDTO);
			}
		} catch (Exception e) {
			logger.error(" modifyItemAttrValueItem error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<String> deleteItemAttrValueItem(Long... valueId) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		try {
			for (int i = 0; i < valueId.length; i++) {
				itemAttrValueItemDAO.updatestatusbyValueId(valueId[i]);
			}
		} catch (Exception e) {
			logger.error(" modifyItemAttrValueItem error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public ExecuteResult<List<ItemAttrDTO>> queryItemAttrValueItem(ItemAttrValueItemDTO itemAttrValueItemDTO) {
		ExecuteResult<List<ItemAttrDTO>> result = new ExecuteResult<List<ItemAttrDTO>>();
		try {
			List<ItemAttrDTO> attrlist = itemAttrValueItemDAO.queryAttrList(itemAttrValueItemDTO);
			for (ItemAttrDTO itemAttr : attrlist) {
				itemAttrValueItemDTO.setAttrId(itemAttr.getId());
				List<ItemAttrValueDTO> valueList = itemAttrValueItemDAO.queryValueList(itemAttrValueItemDTO);
				itemAttr.setValues(valueList);
			}
			result.setResult(attrlist);
		} catch (Exception e) {
			logger.error(" modifyItemAttrValueItem error:：" + e.getMessage());
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}

}
