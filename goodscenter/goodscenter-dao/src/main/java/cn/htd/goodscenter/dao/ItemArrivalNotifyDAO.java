package cn.htd.goodscenter.dao;

import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.domain.ItemArrivalNotify;

/**商品到货通知DAO
 * Created by GZG on 2016/11/17.
 */
public interface ItemArrivalNotifyDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ItemArrivalNotify record);

    int insertSelective(ItemArrivalNotify record);

    ItemArrivalNotify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemArrivalNotify record);

    int updateByPrimaryKey(ItemArrivalNotify record);
    
    List<ItemArrivalNotify> queryWatingNotifyInfoList(Map<String,Object> param);
    
    void updateItemArrivalNotifyStatus(ItemArrivalNotify record);
}
