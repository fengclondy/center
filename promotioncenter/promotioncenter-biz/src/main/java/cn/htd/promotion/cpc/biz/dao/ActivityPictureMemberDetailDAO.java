package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureMemberDetailReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureMemberDetailResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.activityPictureMemberDetailDAO")
public interface ActivityPictureMemberDetailDAO extends BaseDAO<ActivityPictureMemberDetailReqDTO>{
    int deleteByPrimaryKey(Long id);

    void insert(ActivityPictureMemberDetailReqDTO record);

    int insertSelective(ActivityPictureMemberDetailReqDTO record);

    ActivityPictureMemberDetailResDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityPictureMemberDetailReqDTO record);

    int updateByPrimaryKey(ActivityPictureMemberDetailReqDTO record);

	void deleteByPictureId(String pictureId);

	List<ActivityPictureMemberDetailResDTO> selectByPictureId(String pictureId);
	
	List<ActivityPictureMemberDetailResDTO> selectMaterielDownloadMember(@Param("pictureID") String pictureID,
			@Param("pager") Pager<ActivityPictureMemberDetailResDTO> pager);

	Long selectMaterielDownloadMemberCount(@Param("pictureID")String pictureID);
}