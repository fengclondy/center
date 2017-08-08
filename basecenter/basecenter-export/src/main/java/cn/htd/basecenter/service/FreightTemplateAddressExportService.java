package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.FreightTemplateAddressDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lih
 * Date: 2017/3/31
 * Time: 11:50
 */
public interface FreightTemplateAddressExportService {

    // 查询所有省市
    public List<FreightTemplateAddressDTO> queryAll();

}
