package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberLicenceInfoVO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;

/**
 * Created by thinkpad on 2016/11/17.
 */
public interface MemberLicenceInfoDao {
	
	
    public List<MemberLicenceInfo> searchMemberLicenceInfoList(MemberLicenceInfoVO memberLicenceInfoVO);
    
    
    public MemberLicenceInfoDetailDTO searchMemberPasswordUnCheckDetail(MemberLicenceInfoVO memberLicenceInfoVO);

   
    public List<VerifyDetailInfo> queryVerifyDetailInfoByVerifyId(@Param("verifyId") Long verify_id);

   
    public int updateMemberLicenceInfo(MemberLicenceInfoVO memberLicenceInfoVO);


	public int updateMemberStatusInfo(@Param("dto") MemberUncheckedDetailDTO dto);
	
	
	public int updateVerifyInfo(MemberRemoveRelationshipDTO dto);
	
	
	public int updateBelongRelationship(MemberRemoveRelationshipDTO dto);
	
	
	public int updateMemberBaseInfo(MemberRemoveRelationshipDTO dto);
	
	
	public int updateMemberInvoiceInfo(MemberInvoiceDTO dto);
}
