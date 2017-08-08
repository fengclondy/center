package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

public interface HTDStaticAdvertiseDAO extends BaseDAO<HTDStaticAdvertiseDAO>{
	
	/**
	 * 获取有效广告id
	 * @return
	 */
	public Long getAvailableAdvId(@Param("sortNum")Long sortNum,@Param("is_handoff")String isHandoff);

	/**
	 * 查询广告列表前整体更新状态为0
	 * @return
	 */
	public int updateStaticAdvStatusInitZero(@Param("sortNum")Long sortNum,@Param("advId")Long advId);
	/**
	 * 将其他is_handoff为2的设置为1
	 * @return
	 */
	public int updateAdvIsHandoffOne(@Param("sortNum")Long sortNum,@Param("advId")Long advId);
	/**
	 * 查询广告列表前整体更新状态为1
	 * @return
	 */
	public int updateStaticAdvStatusInitOne(@Param("advId")Long advId);
	
	/**
	 * 更新超时的广告为结束状态
	 * @param sortNum
	 * @return
	 */
	public int updateTimeOutAdv();
	
	/**
	 * 更新被顶掉的广告状态为已结束
	 * @param sort_num
	 */
	public int updateAdvStatusOneToTwo(@Param("sortNum")Long sort_num,@Param("advId")Long advId);
	
	/**
	 * 查询状态为1的广告
	 * @return
	 */
	public List<HTDAdvertisementDTO> queryStaticAdvUp(@Param("sortNum")Long sortNum);
	
	/**
	 * 查询静态广告列表
	 * @param page
	 * @param dto
	 * @return
	 */
	public List<HTDAdvertisementDTO> queryStaticAdvertisement(@Param("page")Pager page, @Param("dto")HTDAdvertisementDTO dto);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public HTDAdvertisementDTO queryStaticAdvById(@Param("advId")Long advId);
	
	/**
	 * 删除
	 * @param advId
	 */
	public void deleteById(Long advId);
	
	/**
	 * 更新静态广告
	 * @param dto
	 */
	public void updateStaticAdvertisement(HTDAdvertisementDTO dto);
	
	/**
	 * 增加广告
	 * @param dto
	 * @return
	 */
	public int addStaticAdvertisement(HTDAdvertisementDTO dto);

	public List<HTDAdvertisementDTO> queryWillBeUp(@Param("sortNum")Long sortNum);

	public List<HTDAdvertisementDTO> queryStaticAdList4Task(@Param("entity") HTDAdvertisementDTO condition,
														  @Param("page") Pager<HTDAdvertisementDTO> page);

	public Integer updateStaticAdStatusById(HTDAdvertisementDTO htdAdvertisementDTO);

	public Integer updateBySortNum(HTDAdvertisementDTO htdAdvertisementDTO);

	
}
