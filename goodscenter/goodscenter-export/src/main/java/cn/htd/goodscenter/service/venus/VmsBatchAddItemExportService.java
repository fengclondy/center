package cn.htd.goodscenter.service.venus;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.vms.BatchAddItemInDTO;
import cn.htd.goodscenter.dto.vms.BatchAddItemOutDTO;

import java.util.List;

/**
 * VMS批量导入服务
 */
public interface VmsBatchAddItemExportService {

    /**
     * 我的商品 - 批量新增商品 （导入）
     * @param batchAddItemInDTOList
     * @return
     */
    ExecuteResult<BatchAddItemOutDTO> batchAddItem(List<BatchAddItemInDTO> batchAddItemInDTOList);
}
