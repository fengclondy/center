package cn.htd.goodscenter.dao;

import java.util.List;

import cn.htd.goodscenter.domain.common.ProductChannel;

public interface ProductChannelMapper {
    int deleteByPrimaryKey(Long channelId);

    int insert(ProductChannel record);

    int insertSelective(ProductChannel record);

    ProductChannel selectByPrimaryKey(Long channelId);

    int updateByPrimaryKeySelective(ProductChannel record);

    int updateByPrimaryKey(ProductChannel record);
    
    List<ProductChannel> queryAllProductChannel();

    List<ProductChannel> selectProductChannelList(ProductChannel productChannel);

    ProductChannel selectProductChannelByCode(String code);

    String queryChannelCodeByName(String channelName);

}