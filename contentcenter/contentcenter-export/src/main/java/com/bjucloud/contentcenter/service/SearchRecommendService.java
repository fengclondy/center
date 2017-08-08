package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.SearchRecommendDTO;

/**
 * Created by thinkpad on 2017/3/7.
 */
public interface SearchRecommendService {
    //根据条件查找记录
    public DataGrid<SearchRecommendDTO> queryByCondition(SearchRecommendDTO searchRecommendDTO, Pager page);

    //保存
    public ExecuteResult<String> addRecommend(SearchRecommendDTO searchRecommendDTO);

    //根据条件修改
    public ExecuteResult<String> modifyByCondition(SearchRecommendDTO searchRecommendDTO);

    //根据Id查找记录
    public ExecuteResult<SearchRecommendDTO> qeuryById(Long id);

    //根据ID删除推荐组合
    public ExecuteResult<String> deleteById(Long id);

    public DataGrid<SearchRecommendDTO> queryByName(String name);
}
