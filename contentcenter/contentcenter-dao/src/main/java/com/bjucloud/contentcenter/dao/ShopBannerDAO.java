package com.bjucloud.contentcenter.dao;

import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.ShopBannerDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taolei on 2017/6/29.
 */
public interface ShopBannerDAO {
    /**
     * 根据id查询banner信息
     *
     * @param id
     * @return
     */
    ShopBannerDTO queryById(Long id);

    /**
     * 按条件分页查询banner信息
     * @param banner
     * @param pager
     * @return
     */
    List<ShopBannerDTO> queryForPage(@Param("banner") ShopBannerDTO banner, @Param("page") Pager pager);

    /**
     * 根据id删除banner
     *
     * @param id
     * @return
     */
    Integer deleteById(String[] idList);

    /**
     * 新增banner信息
     *
     * @param banner
     * @return
     */
    Integer addBanner(@Param("banner") ShopBannerDTO banner);

    /**
     * 更新banner信息
     *
     * @param banner
     * @return
     */
    Integer updateBanner(@Param("banner") ShopBannerDTO banner);
    
    /**
     * 按条件分页查询banner信息
     * @param banner
     * @param pager
     * @return
     */
    List<ShopBannerDTO> queryVMSBannerBySellerCode(@Param("entity") ShopBannerDTO banner);


    /**
     * 查询总条数
     *
     * @return
     */
    Long queryCount();
}
