package cn.htd.goodscenter.dao;

import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemDraftDescribe;

public interface ItemDraftDescribeMapper {
    int deleteByPrimaryKey(Long itemDraftDesId);

    int insert(ItemDraftDescribe record);

    int insertSelective(ItemDraftDescribe record);

    ItemDraftDescribe selectByPrimaryKey(Long itemDraftDesId);

    int updateByPrimaryKeySelective(ItemDraftDescribe record);

    int updateByPrimaryKeyWithBLOBs(ItemDraftDescribe record);

    int updateByPrimaryKey(ItemDraftDescribe record);
    
    ItemDraftDescribe  selectByItemDraftId(Long itemDraftId);
    
    ItemDescribe selectByItemId(Long itemId);
}