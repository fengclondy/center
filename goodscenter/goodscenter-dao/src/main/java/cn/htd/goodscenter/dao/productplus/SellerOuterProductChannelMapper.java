package cn.htd.goodscenter.dao.productplus;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.productplus.SellerOuterProductChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellerOuterProductChannelMapper {
    /**
     * 查询卖家接入列表
     * @param sellerOuterProductChannel 参数
     * @return 列表
     */
    List<SellerOuterProductChannel> selectSellerOuterProductChannelList(@Param("entity") SellerOuterProductChannel sellerOuterProductChannel, @Param("page") Pager pager);

    /**
     * 查询卖家接入列表-总数
     * @param sellerOuterProductChannelParam
     * @return 列表
     */
    Long selectSellerOuterProductChannelListCount(@Param("entity") SellerOuterProductChannel sellerOuterProductChannelParam);

    /**
     * 根据SellerId和ChannelCode, 查询库里条数
     * @param sellerOuterProductChannel
     * @return
     */
    SellerOuterProductChannel selectBySellerIdAndChannelCode(SellerOuterProductChannel sellerOuterProductChannel);

    /**
     * 根据SellerId查询所有接入渠道
     * @param sellerId
     * @return
     */
    List<SellerOuterProductChannel> selectBySellerId(Long sellerId);

    /**
     * 根据SellerId查询【已接入】渠道
     * @param sellerId
     * @return
     */
    List<SellerOuterProductChannel> selectAccessedListBySellerId(Long sellerId);

    /**
     * 批量插入
     * @param sellerOuterProductChannels
     */
    void batchInsert(List<SellerOuterProductChannel> sellerOuterProductChannels);

    int deleteByPrimaryKey(Long accessId);

    int insert(SellerOuterProductChannel record);

    int insertSelective(SellerOuterProductChannel record);

    SellerOuterProductChannel selectByPrimaryKey(Long accessId);

    int updateByPrimaryKeySelective(SellerOuterProductChannel record);

    int updateByPrimaryKey(SellerOuterProductChannel record);



}