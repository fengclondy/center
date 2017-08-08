package cn.htd.goodscenter.dao.vip;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.vip.VipChannelItemInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VipChannelItemInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VipChannelItemInfo record);

    int insertSelective(VipChannelItemInfo record);

    VipChannelItemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VipChannelItemInfo record);

    int updateByPrimaryKey(VipChannelItemInfo record);

    void batchInsert(@Param("records")List<VipChannelItemInfo> records);

    void deleteAll();

    List<VipChannelItemInfo> selectListByPage(@Param("page")Pager pager);

    Long selectListCount();

}