package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.BaseSmsConfigShowDTO;
import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseSmsConfigServiceTest {
    ApplicationContext ctx = null;
    BaseSmsConfigService baseSmsConfigService = null;
    DictionaryUtils dictionary = null;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
        baseSmsConfigService = (BaseSmsConfigService) ctx.getBean("baseSmsConfigService");
        dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
    }

    @Test
    public void querySMSConfigStatusListTest() {
        Pager<BaseSmsConfigDTO> page = new Pager<BaseSmsConfigDTO>();
        ExecuteResult<DataGrid<BaseSmsConfigShowDTO>> result = baseSmsConfigService.querySMSConfigStatusList(page);
        System.out.println("-------------" + JSONObject.toJSONString(result));
    }

    @Test
    public void updateSMSConfigValidTest() {
        ValidSmsConfigDTO inDTO = new ValidSmsConfigDTO();
        inDTO.setChannelCode(
                dictionary.getValueByCode(DictionaryConst.TYPE_SMS_CHANNEL, DictionaryConst.OPT_SMS_CHANNEL_MANDAO));
        inDTO.setOperatorId(new Long(1));
        inDTO.setOperatorName("admin");
        ExecuteResult<String> result = baseSmsConfigService.updateSMSConfigValid(inDTO);
        System.out.println("-------------" + JSONObject.toJSONString(result));
    }
}
