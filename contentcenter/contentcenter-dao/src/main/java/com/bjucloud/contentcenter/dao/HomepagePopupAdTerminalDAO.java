package com.bjucloud.contentcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HomepagePopupTerminalAd;

public interface HomepagePopupAdTerminalDAO extends BaseDAO<HomepagePopupTerminalAd> {

    /**
     * 批量插入展示终端对象
     *
     * @param popupTerminalAdList
     */
    public void insertList(List<HomepagePopupTerminalAd> popupTerminalAdList);

    /**
     * 根据广告ID查询展示终端信息
     *
     * @param id
     * @return
     */
    public List<HomepagePopupTerminalAd> queryByAdId(Long id);

    /**
     * 根据广告ID删除展示终端信息
     *
     * @param terminalAd
     * @return
     */
    public Integer deleteByAdId(HomepagePopupTerminalAd terminalAd);
}
