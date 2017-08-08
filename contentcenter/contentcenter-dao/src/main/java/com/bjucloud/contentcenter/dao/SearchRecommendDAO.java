package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.dto.SearchRecommendDTO;
import com.bjucloud.contentcenter.dto.SearchRecommendProductDTO;
import com.bjucloud.contentcenter.dto.SearchRecommendWordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by thinkpad on 2017/3/7.
 */
public interface SearchRecommendDAO extends BaseDAO<SearchRecommendDTO> {

    //根据recommendWordId查找记录
    public List<SearchRecommendWordDTO> selectWordByRecommendId(@Param("recommendWordId") Long recommendWordId);

    public List<SearchRecommendProductDTO> selectProductByRecommendId(@Param("recommendWordId") Long recommendWordId);

    //添加
    public Integer insertRecommend(SearchRecommendDTO searchRecommendDTO);

    public Integer insertRecommendWord(SearchRecommendWordDTO searchRecommendWordDTO);

    public Integer insertRecommendProduct(SearchRecommendProductDTO searchRecommendProductDTO);

    //修改
    public Integer updateRecommendByCondition(SearchRecommendDTO searchRecommendDTO);

    public Integer updateWordByCondition(SearchRecommendWordDTO searchRecommendWordDTO);

    public  Integer updateProductByCondition(SearchRecommendProductDTO searchRecommendProductDTO);

    //删除
    public void deleteRecommendById(Long id);

    public void deleteWordByRecommendId(Long recommendWordId);

    public void deleteProductByRecommendId(Long recommendWordId);

    List<SearchRecommendDTO> selectByName(String name);

}
