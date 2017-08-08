package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.goodscenter.domain.ItemDraftPicture;
import cn.htd.goodscenter.domain.ItemPicture;

public interface ItemDraftPictureMapper {
    int deleteByPrimaryKey(Long itemDraftPicId);

    int insert(ItemDraftPicture record);

    int insertSelective(ItemDraftPicture record);

    ItemDraftPicture selectByPrimaryKey(Long itemDraftPicId);

    int updateByPrimaryKeySelective(ItemDraftPicture record);

    int updateByPrimaryKey(ItemDraftPicture record);
    
    void deleteDraftPicByItemDraftId(Long itemDraftId);
    
    void batchInsert(List<ItemDraftPicture> list);

    List<ItemDraftPicture> queryItemDraftPicsByDraftId(@Param("itemDraftId") Long itemDraftId);
    
    List<ItemPicture> queryItemDraftPicsByItemId(Long itemId);
}