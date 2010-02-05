package com.samtech.web.converter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConverter extends StrutsTypeConverter{

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values!=null && values.length>0){
			String s=values[0];
			if(s!=null && s.trim().length()>0){
			BigDecimal bd = new BigDecimal(s);
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
		
	}

}
