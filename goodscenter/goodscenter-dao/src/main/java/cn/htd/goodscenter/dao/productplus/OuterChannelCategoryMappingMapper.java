package cn.htd.goodscenter.dao.productplus;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.productplus.OuterChannelCategoryMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 外部渠道类目映射关系持久化
 * @author chenkang
 */
public interface OuterChannelCategoryMappingMapper {
    /**
     * 查询外部渠道商品类目关系映射列表
     * @param outerChannelCategoryMapping 参数
     * @param pager 分页信息
     * @param isMapped 是否已经映射
     * @return 结果集
     */
    List<OuterChannelCategoryMapping> selectOuterChannelCategoryMappingList(@Param("entity") OuterChannelCategoryMapping outerChannelCategoryMapping, @Param("page") Pager pager, @Param("isMapped") boolean isMapped);

    /**
     * 查询外部渠道商品类目关系映射列表-总数
     * @param outerChannelCategoryMapping 参数
     * @param isMapped 是否已经映射
     * @return
     */
    Long selectOuterChannelCategoryMappingListCount(@Param("entity") OuterChannelCategoryMapping outerChannelCategoryMapping, @Param("isMapped") boolean isMapped);

    /**
     * 根据渠道编码和编码查询格式
     * @return
     */
    Long selectCountByOCCCodeAndChannelCode(OuterChannelCategoryMapping outerChannelCategoryMapping);

    /**
     * 查询OuterChannelCategoryMapping
     * @param outerChannelCategoryMapping 参数
     * @return OuterChannelCategoryMapping
     */
    OuterChannelCategoryMapping select(@Param("entity") OuterChannelCategoryMapping outerChannelCategoryMapping);

    int deleteByPrimaryKey(Long categoryMappingId);

    int insert(OuterChannelCategoryMapping record);

    int insertSelective(OuterChannelCategoryMapping record);

    OuterChannelCategoryMapping selectByPrimaryKey(Long categoryMappingId);

    int updateByPrimaryKeySelective(OuterChannelCategoryMapping record);

    int updateByPrimaryKey(OuterChannelCategoryMapping record);

    int updateByOuterCategoryCodeSelective(OuterChannelCategoryMapping outerChannelCategoryMapping);
}