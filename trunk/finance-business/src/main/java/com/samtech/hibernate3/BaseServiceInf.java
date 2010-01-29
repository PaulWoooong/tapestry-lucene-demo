package com.samtech.hibernate3;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.samtech.common.domain.PagingAndSorting;

public interface BaseServiceInf<T> {

	<T> T getObject(Class<T> clazz, Serializable id);
	T getObject(Serializable id);

	List<T> query(String sql, Map<String, Object> param, PagingAndSorting pg);

	void saveObject(T o);

	T updateObject(T o);

	void deleteObject(T o);
	
}
