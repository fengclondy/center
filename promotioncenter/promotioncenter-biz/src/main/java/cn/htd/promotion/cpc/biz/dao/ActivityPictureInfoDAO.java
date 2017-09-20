package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

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
}