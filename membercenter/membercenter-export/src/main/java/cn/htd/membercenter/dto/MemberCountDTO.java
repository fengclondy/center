package cn.htd.membercenter.dto;

import java.io.Serializable;
/**
 * VMS - 我的会员、担保会员、非会员数量
 * @author li.jun
 *
 */
public class MemberCountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 我的会员 - 数量 
	 */
	private int myMemberCount;
	
	/**
	 * 担保会员会员 - 数量 
	 */
	private int guaranteeMemberCount;
	/**
	 * 非会员 - 数量 
	 */
	private int noMemberCount;
	
	
	public int getMyMemberCount() {
		return myMemberCount;
	}
	
	public void setMyMemberCount(int myMemberCount) {
		this.myMemberCount = myMemberCount;
	}
	
	public int getGuaranteeMemberCount() {
		return guaranteeMemberCount;
	}
	
	public void setGuaranteeMemberCount(int guaranteeMemberCount) {
		this.guaranteeMemberCount = guaranteeMemberCount;
	}
	
	public int getNoMemberCount() {
		return noMemberCount;
	}
	
	public void setNoMemberCount(int noMemberCount) {
		this.noMemberCount = noMemberCount;
	}
	
	

}
