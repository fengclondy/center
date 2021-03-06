package cn.htd.goodscenter.dao;

import cn.htd.goodscenter.domain.PreSaleProductPush;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/8/14.
 */
public interface PreSaleProductPushMapper {

    int insert(PreSaleProductPush preSaleProductPush);

    List<PreSaleProductPush> select(PreSaleProductPush preSaleProductPush);

    List<PreSaleProductPush> selectbySchedule(Map<String, Object> map);

    PreSaleProductPush getByItemId(Long itemId);

    void update(PreSaleProductPush preSaleProductPush);

    int updateStatus(@Param("id")Long id, @Param("status")int status,  @Param("preStatusList")List<Integer> preStatus);

}
