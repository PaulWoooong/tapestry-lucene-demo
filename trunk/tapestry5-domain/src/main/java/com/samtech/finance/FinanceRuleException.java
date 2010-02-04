package com.samtech.finance;

public class FinanceRuleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 712040944547460433L;
	/**
	 * 
	 */
	public final static int UNKNOW= (100000);
	/**
	 * 账目不平衡
	 */
	public final static int NO_BALANCE= (1);
	/**
	 * 权限
	 */
	public final static int AUTHORIZE_FAIL= (2);
	/**
	 * 只有单向记账，缺少另一方账目
	 */
	public final static  int MISS_SIDE_BALANCE= (3);
	/**
	 * 金额超出Taccount,不能平衡
	 */
	public final static int OUT_OF_BALANCE= (4);
	
	public final static int FREEZE_BALANCE= (101);
	public final static int RECORDDATE_FREEZE_BALANCE= (102);
	
	public final static int ACCOUNT_INITED= (103);
	
	private Integer errorCode;

	public FinanceRuleException() {
		super();
	}
	
	@Override
	public String getMessage() {
		
		 String message = super.getMessage();
		 if(message==null && getErrorCode()!=null){
			 Integer c = getErrorCode();
			 switch (c.intValue()) {
			case NO_BALANCE:
				message="账目不平衡";
				break;
			case MISS_SIDE_BALANCE:
				message="只有单向记账，缺少另一方账目";
				break;
			case AUTHORIZE_FAIL:
				message="无权限操作";
				break;
			case OUT_OF_BALANCE:
				break;
			
			default:
				Throwable cause2 = this.getCause();
				if(cause2!=null)return cause2.getMessage();
				break;
			}
		 }
		 return message;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
}
