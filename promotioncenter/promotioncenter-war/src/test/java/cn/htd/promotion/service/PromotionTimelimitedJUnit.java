package cn.htd.promotion.service;
import javax.annotation.Resource;

import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;

/**
 * Created by zf.zhang on 2017/9/1.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})
public class PromotionTimelimitedJUnit {

    @Resource
    private PromotionTimelimitedInfoAPI promotionTimelimitedInfoAPI;
    
    @Resource
    private GeneratorUtils noGenerator;

    @Before
    public void setUp() throws Exception {

    }

    /**
     * 添加秒杀活动
     */
	@Test
	@Rollback(false) 
    public void addTimelimitedInfoTest(){
    	try {
            String messageId = "342453251349";
    		String buyerCode = "htd657126";
    		String promotionId = "23171923070179";
    		//ExecuteResult<PromotionTimelimitedShowDTO> timelimited = promotionTimelimitedInfoAPI.getPromotionTimelimitedInfoDetail(messageId, promotionId, buyerCode);
    		ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>>timelimited = promotionTimelimitedInfoAPI.getPromotionTimelimitedList(messageId, buyerCode, null);
    		System.out.println(JSON.toJSONString(timelimited.getResult()));
            
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    /**
     * 添加秒杀活动
     */
    @Test
    @Rollback(false)
    public void deletePromotionTimelimitedInfoTest(){
        try {
            String messageId = "342453251349";
            String buyerCode = "htd657126";
            String promotionId = "23171610400238";
            PromotionValidDTO promotionValidDTO = new  PromotionValidDTO();
            promotionValidDTO.setOperatorId(Long.parseLong("123123"));
            promotionValidDTO.setPromotionId(promotionId);
            promotionValidDTO.setStatus("9");
            promotionValidDTO.setOperatorName(buyerCode);
            ExecuteResult<?>timelimited = promotionTimelimitedInfoAPI.deletePromotionTimelimitedInfoBySkuCode(messageId, promotionValidDTO);
            System.out.println(JSON.toJSONString(timelimited));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
