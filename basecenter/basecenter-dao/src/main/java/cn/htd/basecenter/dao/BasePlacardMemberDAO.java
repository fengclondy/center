package cn.htd.basecenter.dao;

import java.util.List;

import cn.htd.basecenter.domain.BasePlacardMember;
import cn.htd.common.dao.orm.BaseDAO;

public interface BasePlacardMemberDAO extends BaseDAO<BasePlacardMember> {

	/**
	 * 更新商家公告会员读取标志
	 * 
	 * @param placardMember
	 */
	public void updatePlacardReadStatus(BasePlacardMember placardMember);

	/**
	 * 更新商家公告会员的删除标记
	 * 
	 * @param memberParameter
	 */
	public void deletePlacardMemberByIdList(BasePlacardMember memberParameter);

	/**
	 * 批量保存公告发送会员信息
	 * 
	 * @param memberList
	 */
	public void addBasePlacardMemberList(List<BasePlacardMember> memberList);

	/**
	 * 更新会员所有未读商家公告为已读
	 * 
	 * @param placardMember
	 */
	public void updateAllPlacardReadStatus(BasePlacardMember placardMember);

}
