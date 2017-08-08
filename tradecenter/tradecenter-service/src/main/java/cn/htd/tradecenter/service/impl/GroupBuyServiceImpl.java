package cn.htd.tradecenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.tradecenter.service.GroupBuyService;
import org.springframework.stereotype.Service;

/**
 * Created by taolei on 2017/5/22.
 */
@Service("groupBuyService")
public class GroupBuyServiceImpl implements GroupBuyService {
    @Override
    public DataGrid<Object> getSignInList() {
        //todo 暂时不做
        return null;
    }
}
