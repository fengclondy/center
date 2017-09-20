package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.request.MemberActivityPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.MemberActivityPictureResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.memberActivityPictureDAO")
public interface MemberActivityPictureDAO extends BaseDAO<MemberActivityPictureReqDTO> {
	int deleteByPrimaryKey(Long id);

	void insert(MemberActivityPictureReqDTO record);

	int insertSelective(MemberActivityPictureReqDTO record);

	MemberActivityPictureResDTO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MemberActivityPictureReqDTO record);

	int updateByPrimaryKey(MemberActivityPictureReqDTO record);
}