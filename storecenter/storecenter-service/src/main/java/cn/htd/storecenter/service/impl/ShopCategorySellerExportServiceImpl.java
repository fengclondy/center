package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.storecenter.dao.ShopInfoDAO;
import cn.htd.storecenter.dto.ShopCategorySellerQueryDTO;
import cn.htd.storecenter.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopCategorySellerDAO;
import cn.htd.storecenter.dto.ShopCategorySellerDTO;
import cn.htd.storecenter.service.ShopCategorySellerExportService;

@Service("shopCategorySellerExportService")
public class ShopCategorySellerExportServiceImpl implements ShopCategorySellerExportService {
	private final static Logger logger = LoggerFactory.getLogger(ShopCategorySellerExportServiceImpl.class);
	@Resource
	private ShopCategorySellerDAO shopCategorySellerDAO;
	@Resource
	private ShopInfoDAO shopInfoDAO;

	/**
	 * <p>
	 * Discription:[店铺分类修改]
	 * </p>
	 * 
	 * @param dto
	 */
	public ExecuteResult<String> update(ShopCategorySellerDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			shopCategorySellerDAO.update(dto);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[店铺分类列表查询]
	 * </p>
	 * 
	 * @param dto
	 * @param pager
	 */

	@Override
	public ExecuteResult<DataGrid<ShopCategorySellerDTO>> queryShopCategoryList(ShopCategorySellerDTO dto,
			Pager<ShopCategorySellerDTO> pager) {
		ExecuteResult<DataGrid<ShopCategorySellerDTO>> result = new ExecuteResult<DataGrid<ShopCategorySellerDTO>>();
		logger.info("ShopCategorySellerExportService 【queryShopCategoryList】入参{} "+dto.toString());
		try {
			DataGrid<ShopCategorySellerDTO> dg = new DataGrid<ShopCategorySellerDTO>();
			dg.setRows(shopCategorySellerDAO.selectListByCondition(dto, pager));
			dg.setTotal(shopCategorySellerDAO.selectCountByCondition(dto));
			result.setResult(dg);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[店铺分类单个删除]
	 * </p>
	 *
	 */

	@Override
	public ExecuteResult<String> deleteById(ShopCategorySellerDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		try {
			Integer count = shopCategorySellerDAO.delete(dto);
			result.setResultMessage("success");
			result.setResult(count.toString());
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[店铺分类添加]
	 * </p>
	 * 
	 * @param dto
	 */

	@Override
	public ExecuteResult<Long> addShopCategory(ShopCategorySellerDTO dto) {
		ExecuteResult<Long> result = new ExecuteResult<Long>();
		try {
			dto.setDeleted(0);
			dto.setHomeShow(1);
			shopCategorySellerDAO.insertShopCategory(dto);
			result.setResult(dto.getCid());
			result.setResultMessage("success");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[店铺分类全部删除]
	 * </p>
	 * 
	 * @param dto
	 */
	@Override
	public ExecuteResult<String> deletes(ShopCategorySellerDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			int count = shopCategorySellerDAO.deletes(dto);
			result.setResultMessage("success");
			result.setResult(count + "");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * @Title: selectListByCondition @Description: 根据条件查询所有 @param dto @return
	 *         设定文件 @return ShopCategorySellerDTO 返回类型 @throws
	 */
	@Override
	public String selectNameByCondition(ShopCategorySellerDTO dto) {
		return shopCategorySellerDAO.selectNameByCondition(dto);
	}

	@Override
	public Boolean isExist(ShopCategorySellerDTO dto) {
		List<ShopCategorySellerDTO> list = shopCategorySellerDAO.selectListByCondition(dto, new Pager<ShopCategorySellerDTO>(1, 1));
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ExecuteResult<String> delete(ShopCategorySellerDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			int count = shopCategorySellerDAO.delete(dto);
			result.setResultMessage("success");
			result.setResult(count + "");
		} catch (Exception e) {
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public DataGrid<ShopCategorySellerDTO> queryChildCategoryByShopId(ShopCategorySellerDTO dto,Integer count) {
		DataGrid<ShopCategorySellerDTO> dataGrid = new DataGrid<ShopCategorySellerDTO>();
		try{
			List<ShopCategorySellerDTO> list = shopCategorySellerDAO.queryChildCategory(dto,count);
			dataGrid.setRows(list);
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<ShopCategorySellerQueryDTO> addOrQueryByCondition(ShopCategorySellerQueryDTO dto) {
		ExecuteResult<ShopCategorySellerQueryDTO> result = new ExecuteResult<ShopCategorySellerQueryDTO>();
		try{
			ShopDTO shopDTO = shopInfoDAO.selectBySellerId(dto.getSellerId());
			if(shopDTO == null){
				result.setResult(null);
				result.setResultMessage("没有此店铺！");
				return result;
			}
			ShopCategorySellerQueryDTO queryOutDto = new ShopCategorySellerQueryDTO();
			queryOutDto.setSellerId(dto.getSellerId());
			queryOutDto.setParentCName(dto.getParentCName());
			queryOutDto.setCname(dto.getCname());
			//查询是否存在此一级类目
			ShopCategorySellerDTO queryDTO = new ShopCategorySellerDTO();
			queryDTO.setSellerId(dto.getSellerId());
			queryDTO.setCname(dto.getParentCName());
			queryDTO.setLev(1);
			List<ShopCategorySellerDTO> list =  shopCategorySellerDAO.selectListByCondition(queryDTO,null);
			if(list != null && list.size() > 0){
				//如果有此一级类目，查询是否存在此二级类目
				queryOutDto.setParentCid(list.get(0).getCid());

				ShopCategorySellerDTO query2Dto = new ShopCategorySellerDTO();
				query2Dto.setSellerId(dto.getSellerId());
				query2Dto.setParentCid(list.get(0).getCid());
				query2Dto.setCname(dto.getCname());
				query2Dto.setLev(2);
				List<ShopCategorySellerDTO> query2List =  shopCategorySellerDAO.selectListByCondition(query2Dto,null);
				if(query2List != null && query2List.size() > 0){
					queryOutDto.setCid(query2List.get(0).getCid());
				}else{
					ShopCategorySellerDTO sonDto = new ShopCategorySellerDTO();
					sonDto.setParentCid(list.get(0).getCid());
					sonDto.setCname(dto.getCname());
					sonDto.setLev(2);
					sonDto.setShopId(shopDTO.getShopId());
					sonDto.setSellerId(dto.getSellerId());
					sonDto.setHasLeaf(1);
					sonDto.setSortNumber(1);
					sonDto.setHomeShow(1);
					sonDto.setExpand(2);
					sonDto.setDeleted(0);
					sonDto.setCreateId(dto.getCreateId());
					sonDto.setCreateName(dto.getCreateName());
					sonDto.setModifyId(dto.getCreateId());
					sonDto.setModifyName(dto.getCreateName());
					shopCategorySellerDAO.insertShopCategory(sonDto);
					queryOutDto.setCid(sonDto.getCid());
				}
			}else{
				//添加一级类目
				ShopCategorySellerDTO parentDto = new ShopCategorySellerDTO();
				parentDto.setParentCid(0l);
				parentDto.setCname(dto.getParentCName());
				parentDto.setLev(1);
				parentDto.setShopId(shopDTO.getShopId());
				parentDto.setSellerId(dto.getSellerId());
				parentDto.setHasLeaf(1);
				parentDto.setSortNumber(1);
				parentDto.setHomeShow(1);
				parentDto.setExpand(2);
				parentDto.setDeleted(0);
				parentDto.setCreateId(dto.getCreateId());
				parentDto.setCreateName(dto.getCreateName());
				parentDto.setModifyId(dto.getCreateId());
				parentDto.setModifyName(dto.getCreateName());
				shopCategorySellerDAO.insertShopCategory(parentDto);
				//添加二级类目
				ShopCategorySellerDTO sonDto = new ShopCategorySellerDTO();
				sonDto.setParentCid(parentDto.getCid());
				sonDto.setCname(dto.getCname());
				sonDto.setLev(2);
				sonDto.setShopId(shopDTO.getShopId());
				sonDto.setSellerId(dto.getSellerId());
				sonDto.setHasLeaf(1);
				sonDto.setSortNumber(1);
				sonDto.setHomeShow(1);
				sonDto.setExpand(2);
				sonDto.setDeleted(0);
				sonDto.setCreateId(dto.getCreateId());
				sonDto.setCreateName(dto.getCreateName());
				sonDto.setModifyId(dto.getCreateId());
				sonDto.setModifyName(dto.getCreateName());
				shopCategorySellerDAO.insertShopCategory(sonDto);

				queryOutDto.setCid(sonDto.getCid());
				queryOutDto.setParentCid(sonDto.getParentCid());
			}
			result.setResult(queryOutDto);
			result.setResultMessage("success");
		}catch (Exception e){
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<ShopCategorySellerDTO> queryParentCname(Long cid) {
		ExecuteResult<ShopCategorySellerDTO> result = new ExecuteResult<ShopCategorySellerDTO>();
		try{
			ShopCategorySellerDTO dto = this.shopCategorySellerDAO.selectParentCname(cid);
			if(dto != null){
				result.setResult(dto);
				result.setResultMessage("success");
			}else {
				result.setResultMessage("无此记录！");
			}
		}catch (Exception e){
			result.getErrorMessages().add(e.getMessage());
			result.setResultMessage("error");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}
}
