package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDAdvertisementDao</p>
* @author root
* @date 2017年1月12日
* <p>Description: 
*		汇通达广告接口
* </p>
 */
public interface HTDAdvertisementDAO extends BaseDAO<HTDAdvertisementDAO>{
	
	/**
	 * 获取有效广告id
	 * @return
	 */
	public Long getAvailableAdvId(@Param("is_handoff")String isHandoff);
	
	/**
	 * 更新被顶掉的广告状态为已结束
	 * @param sort_num
	 */
	public void updateAdvStatusOneToTwo(@Param("advId")Long advId);

	/**
	 * 查询广告列表前整体更新状态为0
	 * @return
	 */
	public int updateTopAdvStatusInitZero(@Param("advId")Long advId);
	
	/**
	 * 将其他is_handoff为2的设置为1
	 * @return
	 */
	public int updateAdvIsHandoffOne(@Param("advId")Long advId);
	
	/**
	 * 查询广告列表前整体更新状态为1
	 * @return
	 */
	public int updateTopAdvStatusInitOne(@Param("advId")Long advId);
	
	/**
	 * 更新超时的广告为结束状态
	 * @param sortNum
	 * @return
	 */
	public int updateTimeOutAdv();
	
	
	/**
	 * 查询状态为1的广告
	 * @return
	 */
	public List<HTDAdvertisementDTO> queryTopAdvUp();
	/**
	 * 查询顶通广告列表
	 * @param page
	 * @param dto
	 * @return
	 */
	public List<HTDAdvertisementDTO> queryTopAdvertisement(@Param("page")Pager page,@Param("dto")HTDAdvertisementDTO dto);

	/**
	 * 更新广告
	 * @param dto
	 * @return
	 */
	public int updateTopAdvertisement(HTDAdvertisementDTO dto);

	/**
	 * 删除广告
	 * @param advId
	 * @return
	 */
	public int deleteById(Long advId);

	/**
	 * 根据id查询广告
	 * @param advId
	 * @return
	 */
	public HTDAdvertisementDTO queryTopAdvById(Long advId);

	/**
	 * 增加广告
	 * @param dto
	 * @return
	 */
	public int addTopAdvertisement(HTDAdvertisementDTO dto);
	
	/**
	 * 添加更改记录
	 * @param dto
	 * @return
	 */
	public int addEditDetail(HTDEditDetailInfoDTO dto);
	
	/**
	 * 查询更改记录
	 * @param modify_type
	 * @return
	 */
	public List<HTDEditDetailInfoDTO> queryEditDetail(@Param("modify_type")String modify_type,
			@Param("advId")Long advId,@Param("pager")Pager pager);

	public List<HTDEditDetailInfoDTO> queryByType(String type);

	public List<HTDAdvertisementDTO> queryWillBeUp();

	public List<HTDAdvertisementDTO> queryMallAdList4Task(@Param("entity") HTDAdvertisementDTO condition,
														  @Param("page") Pager<HTDAdvertisementDTO> page);

	public Integer updateTopAdStatusById(HTDAdvertisementDTO htdAdvertisementDTO);

	public Integer updateStatus(HTDAdvertisementDTO htdAdvertisementDTO);

	
}
