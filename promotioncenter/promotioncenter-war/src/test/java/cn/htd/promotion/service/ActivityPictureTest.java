package cn.htd.promotion.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.cpc.api.MaterielDownloadAPI;
import cn.htd.promotion.cpc.dto.request.ActivityPictureInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.ActivityPictureMemberDetailReqDTO;
import cn.htd.promotion.cpc.dto.response.ActivityPictureInfoResDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class ActivityPictureTest {
	
	@Resource
	private MaterielDownloadAPI materielDownloadAPI;
    
    @Before  
    public void setUp() throws Exception {  
    	
    }
    
    public static void main(String[] args) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		System.out.println(cal.getTime());
	}
    @Test
    @Rollback(false)
    public void promotion(){
    	String activityPictureInfoReqDTO = "";
    	ActivityPictureInfoReqDTO aa = new ActivityPictureInfoReqDTO();
    	aa.setPictureName("test2");
    	aa.setPictureType("2");
    	aa.setEffectiveTime(new Date());
    	aa.setInvalidTime(new Date());
    	aa.setIsVip(0);
    	List<ActivityPictureMemberDetailReqDTO> activityPictureMemberDetailList = new ArrayList<ActivityPictureMemberDetailReqDTO>();
    	ActivityPictureMemberDetailReqDTO e = new ActivityPictureMemberDetailReqDTO();
    	e.setMemberCode("123");
    	e.setMemberName("tee");
		activityPictureMemberDetailList.add(e );
		aa.setActivityPictureMemberDetailList(activityPictureMemberDetailList );
    	aa.setMessageId("0112233445566");
    	activityPictureInfoReqDTO = JSON.toJSONString(aa);
    	activityPictureInfoReqDTO = materielDownloadAPI.addMaterielDownload(activityPictureInfoReqDTO);
    	System.out.println(activityPictureInfoReqDTO);
    	ActivityPictureInfoResDTO bb = JSON.parseObject(activityPictureInfoReqDTO, ActivityPictureInfoResDTO.class);
		String cc = materielDownloadAPI.viewMaterielDownload(bb.getPictureId());
		System.out.println(cc);
		ActivityPictureInfoResDTO ccc = JSON.parseObject(cc,ActivityPictureInfoResDTO.class);
		ccc.setMessageId("0112233445566");
		String dd = materielDownloadAPI.editMaterielDownload(JSON.toJSONString(ccc));
		System.out.println(dd);
    }
}
