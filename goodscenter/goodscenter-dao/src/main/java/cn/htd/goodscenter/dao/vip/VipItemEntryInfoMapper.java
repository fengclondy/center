package cn.htd.goodscenter.dao.vip;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.vip.VipItemEntryInfo;

public interface VipItemEntryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VipItemEntryInfo record);

    int insertSelective(VipItemEntryInfo record);

    VipItemEntryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VipItemEntryInfo record);

    int updateByPrimaryKey(VipItemEntryInfo record);
    
    int deleteAllVipItemEntryInfo(@Param("vipItemId")  Long vipItemId);
}