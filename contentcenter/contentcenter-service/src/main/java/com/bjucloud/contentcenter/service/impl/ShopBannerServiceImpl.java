package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import com.bjucloud.contentcenter.constant.UtilsConstant;
import com.bjucloud.contentcenter.dao.ShopBannerDAO;
import com.bjucloud.contentcenter.dto.ShopBannerDTO;
import com.bjucloud.contentcenter.service.ShopBannerService;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by taolei on 2017/6/29.
 */
@Service("shopBannerService")
public class ShopBannerServiceImpl implements ShopBannerService {
    @Resource
    private ShopBannerDAO shopBannerDAO;

    @Override
    public ExecuteResult<ShopBannerDTO> queryById(Long id) {
        ExecuteResult<ShopBannerDTO> result = new ExecuteResult<ShopBannerDTO>();
        try {
            ShopBannerDTO ShopBannerDTO = shopBannerDAO.queryById(id);
            if (ShopBannerDTO == null) {
                result.addErrorMessage("banner信息不存在");
                return result;
            }
            result.setResult(ShopBannerDTO);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryById】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<List<ShopBannerDTO>> queryForPage(ShopBannerDTO banner, Pager page) {
        ExecuteResult<List<ShopBannerDTO>> result = new ExecuteResult();
        try {
            if(page!=null&& page.getPage()>0 &&page.getRows()>0){
                page.setPageOffset((page.getPage()-1)*page.getRows());
            }
            List<ShopBannerDTO> bannerList = shopBannerDAO.queryForPage(banner, page);
            if (bannerList == null) {
                result.addErrorMessage("banner信息不存在");
                return result;
            }
            result.setResult(bannerList);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryForPage】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<Long> queryCount() {
        ExecuteResult<Long> result = new ExecuteResult();
        try {
            Long count = shopBannerDAO.queryCount();
            if (count == null) {
                result.addErrorMessage("查询错误");
                return result;
            }
            result.setResult(count);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryCount】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<Integer> deleteById(String idList) {
        ExecuteResult<Integer> result = new ExecuteResult<Integer>();
        try {
            String[] idArr = idList.split(",");
            if(idArr.length<=0){
                result.addErrorMessage("删除出错，id不存在");
                return result;
            }

            Integer rsId = shopBannerDAO.deleteById(idArr);
            if (rsId == null) {
                result.addErrorMessage("banner信息不存在");
                return result;
            }
            result.setResult(rsId);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryById】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<Integer> addBanner(ShopBannerDTO banner) {
        ExecuteResult<Integer> result = new ExecuteResult<Integer>();
        try {
            Integer rsId = shopBannerDAO.addBanner(banner);
            if (rsId == null) {
                result.addErrorMessage("添加数据失败");
                return result;
            }
            result.setResult(rsId);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryById】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<Integer> updateBanner(ShopBannerDTO banner) {
        ExecuteResult<Integer> result = new ExecuteResult<Integer>();
        try {
            Integer id = shopBannerDAO.updateBanner(banner);
            if (id == null) {
                result.addErrorMessage("更新数据失败");
                return result;
            }
            result.setResult(id);
        } catch (Exception e) {
            result.addErrorMessage("执行该方法【queryById】报错：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }
    

	/**
	 * @desc 根据供应商编码查询供应商下的轮播图
	 * @author li.jun
	 * @param banner
	 */
	@Override
	public ExecuteResult<List<ShopBannerDTO>> queryVMSBannerBySellerCode(ShopBannerDTO banner) {
		ExecuteResult<List<ShopBannerDTO>> result = new ExecuteResult<List<ShopBannerDTO>>();
		List<ShopBannerDTO> bannerList = null;
		String returnCode = "";
		try {
            if (null != banner && StringUtils.isNotEmpty(banner.getShopId())) {
                bannerList = shopBannerDAO.queryVMSBannerBySellerCode(banner);
				if (null != bannerList && bannerList.size() > 0) {
					result.setResult(bannerList);
				}else{
					returnCode = UtilsConstant.VMS_SELLER_BANNER_RESULT_ERROR;
				}
			} else {
				returnCode = UtilsConstant.VMS_SELLER__RESULT_ERROR;
			}
			result.setCode(returnCode);
		} catch (Exception e) {
            result.setCode(UtilsConstant.VMS_SELLER_BANNER_ERROR);
			result.addErrorMessage("执行该方法【queryVMSBannerBySellerCode】报错：" + e.getMessage());
		}
		return result;
	}
}
