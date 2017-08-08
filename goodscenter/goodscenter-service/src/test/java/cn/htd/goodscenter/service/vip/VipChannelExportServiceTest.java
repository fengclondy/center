package cn.htd.goodscenter.service.vip;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.vip.VipChannelItemOutDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by admin on 2017/1/18.
 */
public class VipChannelExportServiceTest extends CommonTest {

    @Autowired
    private VipChannelExportService vipChannelExportService;

    @Test
    public void testPushVipItemToVipChannel() {
        try {
            ExecuteResult<String>  executeResult = vipChannelExportService.pushVipItemToVipChannel("HTDH_100000001"); // ,
            System.out.println("结果" + executeResult.getErrorMessages());
        } catch (Exception e) {
            System.out.println("捕获到异常");
        }

    }

    @Test
    public void testqueryVipChannelItemStr() {
        try {
            ExecuteResult<String>  executeResult = vipChannelExportService.queryVipChannelItemStr(); // ,
            System.out.println("结果" + executeResult.getResult());
        } catch (Exception e) {
            System.out.println("捕获到异常");
        }

    }

    @Test
    public void testQueryVipItemList() {
        Pager pager = new Pager();
        ExecuteResult<List<VipChannelItemOutDTO>>  executeResult = this.vipChannelExportService.queryVipItemList(pager, "1102");
        Assert.assertTrue(executeResult.isSuccess());
        System.out.println(executeResult.getResult());
        System.out.println(executeResult.getResult().size());
    }
}
