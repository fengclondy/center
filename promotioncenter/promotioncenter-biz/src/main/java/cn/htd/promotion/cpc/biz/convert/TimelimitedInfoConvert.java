package cn.htd.promotion.cpc.biz.convert;

import org.springframework.beans.BeanUtils;

import cn.htd.promotion.cpc.biz.dmo.TimelimitedInfoDMO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;


public class TimelimitedInfoConvert extends AbstractConvert<TimelimitedInfoDMO, TimelimitedInfoResDTO> {

	@Override
	protected TimelimitedInfoResDTO populateTarget(TimelimitedInfoDMO source) {
		
		TimelimitedInfoResDTO timelimitedInfoResDTO = new TimelimitedInfoResDTO();
		 BeanUtils.copyProperties(source, timelimitedInfoResDTO);
		 
		return timelimitedInfoResDTO;
	}

	@Override
	protected TimelimitedInfoDMO populateSource(TimelimitedInfoResDTO target) {
		// TODO Auto-generated method stub
		return null;
	}


	


}
