package cn.htd.goodscenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListInDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListOutDTO;
import cn.htd.goodscenter.service.venus.VmsItemExportService;
import cn.htd.goodscenter.test.common.CommonTest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VmsItemExportServiceTest extends CommonTest {

    @Autowired
    private VmsItemExportService vmsItemExportService;

    @Test
    public void testQueryMyItemList() {
        QueryVmsMyItemListInDTO queryVmsMyItemListInDTO = new QueryVmsMyItemListInDTO();
        queryVmsMyItemListInDTO.setSellerId(517L);
//        queryVmsMyItemListInDTO.setProductCode("10018231");
        queryVmsMyItemListInDTO.setAuditStatus(3);
//        queryVmsMyItemListInDTO.setBrandName("五粮液");
        queryVmsMyItemListInDTO.setFirstCategoryId(11177L);
        Pager pager = new Pager(1, 100);
        ExecuteResult<DataGrid<QueryVmsMyItemListOutDTO>> executeResult = this.vmsItemExportService.queryMyItemList(queryVmsMyItemListInDTO, pager);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testqueryItemSkuDetail() {
        ExecuteResult<VenusItemSkuDetailOutDTO> executeResult = this.vmsItemExportService.queryItemSkuDetail(241347L);
        System.out.println(JSON.toJSONString(executeResult));
    }


    @Test
    public void testqueryItemSpuDataList() {
        VenusItemMainDataInDTO venusItemSpuInDTO = new VenusItemMainDataInDTO();
        venusItemSpuInDTO.setSellerId(517L);
        Pager<String> page = new Pager<>();
        ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>>  executeResult = this.vmsItemExportService.queryItemSpuDataList(venusItemSpuInDTO, page);
        System.out.println(JSON.toJSONString(executeResult));
    }
}
