package cn.htd.goodscenter.dao.spu;

import cn.htd.goodscenter.domain.spu.ItemSpuDescribe;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import org.apache.ibatis.annotations.Param;

public interface ItemSpuDescribeMapper {
    int deleteByPrimaryKey(Long desId);

    /**
     * 推入商品模板详情库
     * @param record
     * @return
     */
    int add(ItemSpuDescribe record);

    int insertSelective(ItemSpuDescribe record);

    ItemSpuDescribe selectByPrimaryKey(Long desId);

    int updateBySpuIdSelective(ItemSpuDescribe record);

    int updateByPrimaryKeyWithBLOBs(ItemSpuDescribe record);

    int updateByPrimaryKey(ItemSpuDescribe record);

    ItemSpuDescribe queryBySpuId(Long spuId);
    /**
     * 根据商品模版ID更新删除标记
     * @param spuInfoDTO
     * @return
     */
    public int updateDeleteFlag(@Param("entity") SpuInfoDTO spuInfoDTO);
}