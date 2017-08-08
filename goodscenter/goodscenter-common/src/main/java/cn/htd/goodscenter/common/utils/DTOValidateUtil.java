package cn.htd.goodscenter.common.utils;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * DTO入参统一校验器
 * @author chenkang
 */
public class DTOValidateUtil {
	
	public final static String ERROR_MSG_SEPERATOR =";";
	
	private static Validator validator;
	
	 static {
		 if(validator == null){
			 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    	 validator = factory.getValidator();
		 }
	 }
	 
	 /**
	  * 校验参数
	  * @param paramT 待校验对象
	  * @return
	  */
	 public static <T> ValidateResult validate(T paramT){
		 ValidateResult validateResult=new ValidateResult();
		 
		 Set<ConstraintViolation<T>> constraintViolations=validator.validate(paramT);
			
		if(CollectionUtils.isEmpty(constraintViolations)){
			return validateResult;
		}
			
		String message=makeUpErrorMsgFromConstraintViolations(constraintViolations);
			
		if(StringUtils.isEmpty(message)){
			return validateResult;
		}
		validateResult.setIsPass(false);
		validateResult.setMessage(message);
		return validateResult;
	}
	 /**
	  * 
	  * @param paramT
	  * @param paramString
	  * @param paramArrayOfClass
	  * @return
	  */
	public static <T> ValidateResult validateProperty(T paramT,
			String paramString, Class<?>[] paramArrayOfClass){
		ValidateResult validateResult=new ValidateResult();
		
		Set<ConstraintViolation<T>> constraintViolations=validator.validateProperty(paramT, paramString, paramArrayOfClass);
		
		if(CollectionUtils.isEmpty(constraintViolations)){
			return validateResult;
		}
		
		String message=makeUpErrorMsgFromConstraintViolations(constraintViolations);
		
		if(StringUtils.isEmpty(message)){
			return validateResult;
		}
		validateResult.setIsPass(false);
		validateResult.setMessage(message);
		return validateResult;
	}
	
	/**
	 * 
	 * @param paramClass
	 * @param paramString
	 * @param paramObject
	 * @param paramArrayOfClass
	 * @return
	 */
	public static <T> ValidateResult validateValue(
			Class<T> paramClass, String paramString, Object paramObject,
			Class<?>[] paramArrayOfClass){
		ValidateResult validateResult=new ValidateResult();
		
		Set<ConstraintViolation<T>> constraintViolations=validator.validateValue(paramClass, paramString, paramObject, paramArrayOfClass);
		if(CollectionUtils.isEmpty(constraintViolations)){
			return validateResult;
		}
		
		String message=makeUpErrorMsgFromConstraintViolations(constraintViolations);
		
		if(StringUtils.isEmpty(message)){
			return validateResult;
		}
		validateResult.setIsPass(false);
		validateResult.setMessage(message);
		return validateResult;
	}
	
	private static <T> String makeUpErrorMsgFromConstraintViolations(Set<ConstraintViolation<T>> constraintViolations){
		
		if(CollectionUtils.isEmpty(constraintViolations)){
			return "";
		}
		
		List<String> errorMsgList=Lists.newArrayList();
		
		for(ConstraintViolation<T> cc:constraintViolations){
			
			if(StringUtils.isEmpty(cc.getMessage())){
				continue;
			}
			
			errorMsgList.add(cc.getMessage());
    	}
		
		String message=Joiner.on(ERROR_MSG_SEPERATOR).join(errorMsgList);
		return message;
	}
    
}


