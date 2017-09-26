package cn.htd.promotion.cpc.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("bargainPriceSplit")
public class BargainPriceSplit {
	/**
	 * 每个红包都要有钱，最低不能低于1分
	 */
	private static final int MINMONEY =1;
	/**
	 * 这里为了避免某一个红包占用大量资金，我们需要设定非最后一个红包的最大金额，我们把他设置为红包金额平均值的N倍；
	 */
	private static final double TIMES =1.5;
	
	/**金额为分的格式 */  
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    
	/**
	 * 拆分红包
	 * @param money ：红包总金额
	 * @param count ：个数
	 * @return
	 * @throws Exception 
	 */
	public List<String> splitRedPackets(int money,int count) throws Exception{
		int maxMoney = (int)(money * 0.9);
		//红包 合法性校验
		if(!isRight(money,count)){
			return null;
		}
		//红包列表
		List<String> list =new ArrayList<String>();
		//每个红包最大的金额为平均金额的Times 倍
		int max =(int)(money*TIMES/count);
		max = max>maxMoney ? maxMoney : max;
		//分配红包
		for (int i = 0; i < count; i++) {
			int one = randomRedPacket(money,MINMONEY,max,count-i,maxMoney);
			String oneStr = changeF2Y(String.valueOf(one));
			list.add(oneStr);
			money -=one;
		}
		// 重新打乱列表
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * 随机分配一个红包
	 * @param money
	 * @param minS :最小金额
	 * @param maxS ：最大金额(每个红包的默认Times倍最大值)
	 * @param count
	 * @param maxMoney : 金额最大限制
	 * @return
	 */
	private int randomRedPacket(int money, int minS, int maxS, int count, int maxMoney) {
		//若是只有一个，直接返回红包
		if(count==1){
			return money;
		}
		//若是最小金额红包 == 最大金额红包， 直接返回最小金额红包
		if(minS ==maxS){
			return minS;
		}
		//校验 最大值 max 要是比money 金额高的话？ 去 money 金额
		int max = maxS>money ? money : maxS;
		//随机一个红包 = 随机一个数* (金额-最小)+最小
		int one =((int)Math.rint(Math.random()*(max-minS)+minS));
		//剩下的金额
		int moneyOther =money-one;
		//校验这种随机方案是否可行，不合法的话，就要重新分配方案
		if(isRight(moneyOther, count-1)){
			return one;
		}else{
			//重新分配
			double avg =moneyOther /(count-1);
			//本次红包过大，导致下次的红包过小；如果红包过大，下次就随机一个小值到本次红包金额的一个红包
			if(avg<MINMONEY){
				 //递归调用，修改红包最大金额  
				return randomRedPacket(money, minS, one, count, maxMoney);
				
			}else if(avg>maxMoney){
				 //递归调用，修改红包最小金额  
				return randomRedPacket(money, one, maxS, count, maxMoney);
			}
		}
		return one;
	}
	/**
	 * 红包 合法性校验
	 * @param money
	 * @param count
	 * @return
	 */
	private boolean isRight(int money, int count) {
		double avg =money/count;
		//小于最小金额
		if(avg<MINMONEY){
			return false;
		} 
		return true;
	}
	
	/** 
     * 将分为单位的转换为元 （除100） 
     *  
     * @param amount 
     * @return 
     * @throws Exception  
     */  
    public static String changeF2Y(String amount) throws Exception{  
        if(!amount.matches(CURRENCY_FEN_REGEX)) {  
            throw new Exception("金额格式有误");  
        }  
        String moneyStr = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();  
        if (StringUtils.isNumeric(moneyStr))
        {
        	return moneyStr + ".00";
        }else{
        	int index = moneyStr.indexOf(".");
        	String moneySub = moneyStr.substring(index + 1, moneyStr.length() - 1);
        	if(StringUtils.isEmpty(moneySub)){
        		moneyStr = moneyStr + "0";
        	}
        	return moneyStr;
        }
    } 
//	
//	public static void main(String[] args) {
//		//随机一个188.88  5个红包
//		BargainPriceSplit dd = new BargainPriceSplit();
//		//单位是分
//		try {
//			System.out.println(dd.splitRedPackets(20000, 30));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
