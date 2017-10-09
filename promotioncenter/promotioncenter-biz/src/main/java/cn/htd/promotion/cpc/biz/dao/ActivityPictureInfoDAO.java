package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.activityPictureInfoDAO")
public interface ActivityPictureInfoDAO extends BaseDAO<ActivityPictureInfoReqDTO> {
	int deleteByPrimaryKey(Long id);

	void insert(ActivityPictureInfoReqDTO record);

	int insertSelective(ActivityPictureInfoReqDTO record);

	ActivityPictureInfoResDTO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ActivityPictureInfoReqDTO record);

	int updateByPrimaryKey(ActivityPictureInfoReqDTO record);

	ActivityPictureInfoResDTO selectByPictureId(String pictureId);

	void deleteByPictureId(String activityPictureInfoId);

	List<ActivityPictureInfoResDTO> selectMaterielDownloadList(
			@Param("dto") ActivityPictureInfoReqDTO activityPictureInfoReqDTO,
			@Param("pager") Pager<ActivityPictureInfoResDTO> pager);

	Long selectMaterielDownloadListCount(@Param("dto") ActivityPictureInfoReqDTO activityPictureInfoReqDTO);

	/*
	 * List<ActivityPictureInfoResDTO>
	 * selectMaterielDownloadByMemberCode(Map<String, String> map,
	 * 
	 * @Param("pager") Pager<ActivityPictureInfoResDTO> pager);
	 */
	List<ActivityPictureInfoResDTO> selectMaterielDownloadByMemberCode(@Param("pictureType") String pictureID,
			@Param("memberCode") String memberCode, @Param("pager") Pager<ActivityPictureInfoResDTO> pager);

}