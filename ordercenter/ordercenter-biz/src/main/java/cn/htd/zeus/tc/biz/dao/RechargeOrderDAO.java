package cn.htd.zeus.tc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.zeus.tc.biz.dmo.RechargeOrderDMO;

@Repository("cn.htd.zeus.tc.dao.RechargeOrderDAO")
public interface RechargeOrderDAO {
	int deleteByPrimaryKey(Long id);

	int insert(RechargeOrderDMO record);

	int insertSelective(RechargeOrderDMO record);

	RechargeOrderDMO selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RechargeOrderDMO record);

	int updateByPrimaryKey(RechargeOrderDMO record);

	int selectCountByRechargeOrderNo(RechargeOrderDMO record);

	int updateRechargeOrderByRechargeOrderNo(RechargeOrderDMO record);

	List<RechargeOrderDMO> selectRechargeOrderByMemberCode(RechargeOrderDMO record);

	RechargeOrderDMO selectByOrderNo(RechargeOrderDMO record);
}