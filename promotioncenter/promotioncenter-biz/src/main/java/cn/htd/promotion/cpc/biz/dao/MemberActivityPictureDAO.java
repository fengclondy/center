package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
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

	List<MemberActivityPictureResDTO> selectMemberActivityPictureList(@Param("entity") MemberActivityPictureReqDTO memberActivityPictureReqDTO,
			@Param("pager") Pager<MemberActivityPictureReqDTO> pager);
	
	Long selectMemberActivityPictureCount(@Param("entity") MemberActivityPictureReqDTO memberActivityPictureReqDTO);
}