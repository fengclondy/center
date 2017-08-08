package cn.htd.goodscenter.dao.productplus;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.productplus.OuterChannelBrandMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OuterChannelBrandMappingMapper {
    /**
     * 查询外部渠道商品品牌关系映射列表
     * @param outerChannelBrandMapping 参数
     * @param isMapped 是否已经映射
     * @return 结果集
     */
    Long selectOuterChannelBrandMappingListCount(@Param("entity")OuterChannelBrandMapping outerChannelBrandMapping, @Param("isMapped") boolean isMapped);

    /**
     * 查询外部渠道商品品牌关系映射列表-总数
     * @param outerChannelBrandMapping 参数
     * @param isMapped 是否已经映射
     * @return
     */
    List<OuterChannelBrandMapping> selectOuterBrandCategoryMappingList(@Param("entity")OuterChannelBrandMapping outerChannelBrandMapping,  @Param("page")Pager pager, @Param("isMapped") boolean isMapped);

    /**
     * 根据渠道编码和外部品牌编码查询个数
     * @param outerChannelBrandMapping
     * @return 数量
     */
    Long selectCountByOCCCodeAndChannelCode(OuterChannelBrandMapping outerChannelBrandMapping);

    /**
     *  根据外部编码查询对象
     * @param outerChannelBrandMapping 参数
     * @return OuterChannelBrandMapping
     */
    OuterChannelBrandMapping select(@Param("entity") OuterChannelBrandMapping outerChannelBrandMapping);

    int deleteByPrimaryKey(Long brandMappingId);

    int insert(OuterChannelBrandMapping record);

    int insertSelective(OuterChannelBrandMapping record);

    OuterChannelBrandMapping selectByPrimaryKey(Long brandMappingId);

    int updateByPrimaryKeySelective(OuterChannelBrandMapping record);

    int updateByPrimaryKey(OuterChannelBrandMapping record);

    int updateByOuterBrandCodeSelective(OuterChannelBrandMapping record);
}