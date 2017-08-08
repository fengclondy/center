package cn.htd.goodscenter.dao;

import cn.htd.goodscenter.domain.ModifyDetailInfo;

public interface ModifyDetailInfoMapper {
    int deleteByPrimaryKey(Long modifyId);

    int insert(ModifyDetailInfo record);

    int insertSelective(ModifyDetailInfo record);

    ModifyDetailInfo selectByPrimaryKey(Long modifyId);

    int updateByPrimaryKeySelective(ModifyDetailInfo record);

    int updateByPrimaryKey(ModifyDetailInfo record);
}