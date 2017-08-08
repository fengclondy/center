package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import com.bjucloud.contentcenter.dto.ShopBannerDTO;

import java.util.List;


/**
 * Created by taolei on 2017/6/29.
 */
public interface ShopBannerService {
    /**
     * 根据id查询banner信息
     *
     * @param id
     * @return
     */
    ExecuteResult<ShopBannerDTO> queryById(Long id);

    /**
     * 分页查询banner
     * @param banner
     * @param page
     * @return
     */
    ExecuteResult<List<ShopBannerDTO>> queryForPage(ShopBannerDTO banner, Pager page);

    ExecuteResult<Long> queryCount();

    /**
     * 根据id删除banner
     *
     * @param configIds 支持批量删除多个id用逗号',' 隔开
     * @return
     */
    ExecuteResult<Integer> deleteById(String configIds);

    /**
     * 新增banner信息
     *
     * @param banner
     * @return
     */
    ExecuteResult<Integer> addBanner(ShopBannerDTO banner);

    /**
     * 更新banner信息
     *
     * @param banner
     * @return
     */
    ExecuteResult<Integer> updateBanner(ShopBannerDTO banner);
    
    /**
     * 取在VMS设置已经上架且在有效期的图片
     *
     * @param sellerCode :htd开头的账号
     * @return
     */
    ExecuteResult<List<ShopBannerDTO>> queryVMSBannerBySellerCode(ShopBannerDTO banner);
  
}
