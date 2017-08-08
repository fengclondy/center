package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopModifyDetailDAO;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;
import cn.htd.storecenter.emums.ShopModifyColumnEnum;
import cn.htd.storecenter.service.ShopModifyDetailExportService;

@Service("shopModifyDetailExportService")
public class ShopModifyDetailExportServiceImpl implements ShopModifyDetailExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopModifyDetailExportServiceImpl.class);

	@Resource
	private ShopModifyDetailDAO shopModifyDetailDAO;

	@Override
	public ExecuteResult<DataGrid<ShopModifyDetailDTO>> queryShopModifyDetail(ShopModifyDetailDTO shopModifyDetailDTO,
			Pager<ShopModifyDetailDTO> page) {

		ExecuteResult<DataGrid<ShopModifyDetailDTO>> result = new ExecuteResult<DataGrid<ShopModifyDetailDTO>>();
		try {
			DataGrid<ShopModifyDetailDTO> dataGrid = new DataGrid<ShopModifyDetailDTO>();
			List<ShopModifyDetailDTO> list = shopModifyDetailDAO.selectListByCondition(shopModifyDetailDTO, page);
			if(list != null && list.size() > 0){
				for(ShopModifyDetailDTO dto : list){
					if(dto.getBeforeChange() != null && !dto.getBeforeChange().equals("")){
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_NAME.getLabel())){
							dto.setBeforeChange(dto.getBeforeChange());
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_INTRODUCE.getLabel())){
							dto.setBeforeChange(dto.getBeforeChange());
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_STATUS.getLabel())){
							dto.setBeforeChange(dto.getBeforeChange().equals("1")?"已关闭":"已开通");
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_TYPE.getLabel())){
							if(dto.getBeforeChange().equals("1")){
								dto.setBeforeChange("品牌商");
							}else if(dto.getBeforeChange().equals("2")){
								dto.setBeforeChange("经销商");
							}else{
								dto.setBeforeChange("专营店");
							}
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.BUSINESS_TYPE.getLabel())){
							dto.setBeforeChange(dto.getBeforeChange().equals("1")?"自有品牌":"代理品牌");
						}
					}
					if(dto.getAfterChange() != null && !dto.getAfterChange().equals("")){
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_NAME.getLabel())){
							dto.setAfterChange(dto.getAfterChange());
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_INTRODUCE.getLabel())){
							dto.setAfterChange(dto.getAfterChange());
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_STATUS.getLabel())){
							dto.setAfterChange(dto.getAfterChange().equals("1")?"已关闭":"已开通");
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.SHOP_TYPE.getLabel())){
							if(dto.getAfterChange().equals("1")){
								dto.setAfterChange("品牌商");
							}else if(dto.getAfterChange().equals("2")){
								dto.setAfterChange("经销商");
							}else{
								dto.setAfterChange("专营店");
							}
						}
						if(dto.getContentName().equals(ShopModifyColumnEnum.BUSINESS_TYPE.getLabel())){
							dto.setAfterChange(dto.getAfterChange().equals("1")?"自有品牌":"代理品牌");
						}
					}
				}
			}
			Long count = shopModifyDetailDAO.selectCountByCondition(shopModifyDetailDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
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
