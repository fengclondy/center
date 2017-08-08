package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallAdCountDTO;

/**
 * <p>
 * 广告点击量数据交互接口
 * </p>
 * 
 **/
public interface MallAdCountDAO extends BaseDAO<MallAdCountDTO> {

	// 广告报表 分页
	public List<AdReportOutDTO> queryReportList(@Param("entity") AdReportInDTO adReportInDto,
			@Param("page") Pager pager);

	public Long queryReportCount(@Param("entity") AdReportInDTO adReportInDto);

	// 轮播图报表 分页
	public List<AdReportOutDTO> queryBannerReportList(@Param("entity") AdReportInDTO adReportInDto,
			@Param("page") Pager pager);

	public Long queryBannerReportCount(@Param("entity") AdReportInDTO adReportInDto);
}