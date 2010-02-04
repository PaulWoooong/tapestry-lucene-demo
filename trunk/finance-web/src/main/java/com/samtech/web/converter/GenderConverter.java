package com.samtech.web.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.samtech.business.database.Gender;

public class GenderConverter extends StrutsTypeConverter{

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values!=null && values.length>0){
			String string = values[0];
			if(string!=null && Gender.MALE.name().equals(string)){
				 return Gender.MALE;
			}
			if(string!=null && Gender.FEMALE.name().equals(string)){
				 return Gender.FEMALE;
			}
		}
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(o!=null && o instanceof Gender){
			if(Gender.MALE.equals(o))
				return Gender.MALE.name();
			if(Gender.FEMALE.equals(o))
				return Gender.FEMALE.name();
		}
		return "";
	}

}
