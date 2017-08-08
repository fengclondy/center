package cn.htd.basecenter.service.impl;

import cn.htd.basecenter.dto.AddressInfo;
import cn.htd.basecenter.dto.FreightTemplateAddressDTO;
import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.basecenter.service.FreightTemplateAddressExportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lih
 * Date: 2017/3/31
 * Time: 11:57
 */
@Service("freightTemplateAddressExportService")
public class FreightTemplateAddressExportServiceImpl implements FreightTemplateAddressExportService{

    @Resource
    private BaseAddressService baseAddressService;


    @Override
    public List<FreightTemplateAddressDTO> queryAll() {

        // 先获取所有省份
        List<AddressInfo> addressBaseList = baseAddressService.getAddressList("");
        List<FreightTemplateAddressDTO> list = new ArrayList<>();
        for (AddressInfo addressInfo : addressBaseList){
            FreightTemplateAddressDTO freightTemplateAddressDTO = new FreightTemplateAddressDTO();
            freightTemplateAddressDTO = this.set(freightTemplateAddressDTO, addressInfo);
            // 获取该省份下所有城市
            List<AddressInfo> childrenDto = baseAddressService.getAddressList(addressInfo.getCode());
            List<FreightTemplateAddressDTO> childrenList = new ArrayList<>();
            for (AddressInfo childrenOldDto : childrenDto){
                FreightTemplateAddressDTO newChildrenDto = new FreightTemplateAddressDTO();
                newChildrenDto = this.set(newChildrenDto, childrenOldDto);
                childrenList.add(newChildrenDto);
            }
            freightTemplateAddressDTO.setFreightTemplateAddressDTOs(childrenList);
            list.add(freightTemplateAddressDTO);
        }

        return list;
    }

    private FreightTemplateAddressDTO set(FreightTemplateAddressDTO newDto, AddressInfo oldDto){
        newDto.setCode(oldDto.getCode());
        newDto.setName(oldDto.getName());
        newDto.setLevel(oldDto.getLevel());

        return newDto;
    }
}
