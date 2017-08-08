package cn.htd.goodscenter.dao.spu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.spu.ItemSpuPicture;
import cn.htd.goodscenter.dto.ItemSpuPictureDTO;
import cn.htd.goodscenter.dto.SpuInfoDTO;

public interface ItemSpuPictureMapper {
    int deleteByPrimaryKey(Long pictureId);

    /**
     * 推入商品模板图片库
     * @param record
     * @return
     */
    int add(ItemSpuPicture record);

    int insertSelective(ItemSpuPicture record);

    ItemSpuPicture selectByPrimaryKey(Long pictureId);

    int updateBySpuIdSelective(ItemSpuPicture record);

    int updateByPrimaryKey(ItemSpuPicture record);

    /**
     * 根据spuId查询商品模板图片
     * @param spuId
     * @return
     */
    List<ItemSpuPictureDTO> queryBySpuId(Long spuId);

    int updateDeleteFlag(@Param("entity") SpuInfoDTO spuInfoDTO);
    
    void batchInsert(List<ItemSpuPicture> list);
    
    List<ItemSpuPicture> queryItemSpuPicsByErpcode(@Param("erpCode") String erpCode);
}