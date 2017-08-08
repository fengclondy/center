package cn.htd.storecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.storecenter.dao.QQCustomerDAO;
import cn.htd.storecenter.dto.QQCustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dao.ShopFavouriteDAO;
import cn.htd.storecenter.dto.ShopFavouriteDTO;
import cn.htd.storecenter.service.ShopFavouriteService;

/**
 * <p>
 * Description: 店铺收藏
 * </p>
 */
@Service("shopFavouriteService")
public class ShopFavouriteServiceImpl implements ShopFavouriteService {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopFavouriteDAO shopDao;
	@Resource
	private QQCustomerDAO qqCustomerDAO;

	/**
	 * <p>
	 * Discription:收藏
	 * </p>
	 */

	@Override
	public ExecuteResult<String> addFavouriteShop(ShopFavouriteDTO favourite) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			ShopFavouriteDTO queryDto = new ShopFavouriteDTO();
			queryDto.setSellerId(favourite.getSellerId());
			queryDto.setBuyerId(favourite.getBuyerId());
			List<ShopFavouriteDTO> list = shopDao.list(queryDto);
			Integer count = list.size();
			if (count == 0) {
				favourite.setDeleted(0);
				this.shopDao.add(favourite);
				er.setResult("success");
			}else{
				for(ShopFavouriteDTO dto : list){
					shopDao.updateModifyTime(dto.getId());
				}
				er.setResult("success");
			}
		} catch (Exception e) {
			LOG.error("店铺收藏错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:收藏查询
	 * </p>
	 */
	@Override
	public DataGrid<ShopFavouriteDTO> queryFavouriteShopList(ShopFavouriteDTO favourite, Pager<ShopFavouriteDTO> pager) {
		DataGrid<ShopFavouriteDTO> dg = new DataGrid<ShopFavouriteDTO>();
		try{
			favourite.setDeleted(0);
			List<ShopFavouriteDTO> shops = this.shopDao.queryPage(pager, favourite);
			if(shops != null && shops.size() > 0){
				for(ShopFavouriteDTO dto : shops){
					QQCustomerDTO qqCustomerDTO = new QQCustomerDTO();
					qqCustomerDTO.setShopId(dto.getShopId());
					List<QQCustomerDTO> list = qqCustomerDAO.selectListByCondition(qqCustomerDTO,null);
					dto.setQqCustomerDTOs(list);
				}
			}
			Integer total = this.shopDao.queryPageCount(favourite);

			dg.setRows(shops);
			dg.setTotal(Long.valueOf(total));
		}catch (Exception e){
			LOG.error(e.getMessage());
		}

		return dg;
	}

	/**
	 * <p>
	 * Discription:收藏删除
	 * </p>
	 */

	@Override
	public ExecuteResult<String> deleteFavouriteShop(ShopFavouriteDTO favourite) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.shopDao.del(favourite);
			er.setResult("success");
		} catch (Exception e) {
			LOG.error("删除店铺收藏错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

}
