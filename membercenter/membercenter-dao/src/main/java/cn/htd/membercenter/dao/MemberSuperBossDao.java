package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;

public interface MemberSuperBossDao {

	public long selectMemberCountByCustmanagerCode(@Param("managerCode") String managerCode);

	public List<MemberBaseInfoDTO> selectMemberByCustmanagerCode(@Param("managerCode") String managerCode,
			@Param("pager") Pager pager);
}
