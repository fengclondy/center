package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.MallBannerInDTO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.MallBanner;
import com.bjucloud.contentcenter.dto.MallBannerDTO;

public interface MallBannerDAO extends BaseDAO<MallBanner> {

	public List<MallBannerDTO> queryListDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag,
			@Param("page") Pager page);

	public Long queryCountDTO(@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag);

	public List<MallBannerDTO> queryListDTOToAdmin(@Param("entity") MallBannerDTO mallBannerDTO,
			@Param("nowTime") String nowTime, @Param("publishFlag") String publishFlag, @Param("page") Pager page);

	public Long queryCountDTOToAdmin(@Param("entity") MallBannerDTO mallBannerDTO, @Param("nowTime") String nowTime,
			@Param("publishFlag") String publishFlag);

	public Integer updateStatusById(@Param("id") Long id, @Param("publishFlag") String publishFlag);

	public Integer updateSortNumberById(@Param("id") Long id, @Param("sortNum") Integer sortNum);

	public MallBannerDTO queryById(@Param("id") Long id);

	/**
	 * 
	 * <p>
	 * Discription:[查询定时轮播图列表]
	 * </p>
	 */
	public List<MallBannerDTO> queryTimeListDTO();

	public List<MallBannerDTO> queryMobileShopBannerList(@Param("entity")  MallBannerDTO mallBannerDTO, @Param("page")Pager pager);

	public Long queryMobileShopBannerCount(@Param("entity") MallBannerDTO mallBannerDTO);

	public MallBannerDTO queryMobileShopBannerById(@Param("id") Long id);
}
