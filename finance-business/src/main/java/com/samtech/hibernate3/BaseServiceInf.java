package com.samtech.hibernate3;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseServiceInf {

	<T> T getObject(Class<T> clazz, Serializable id);

	@SuppressWarnings("unchecked")
	List query(String sql, Map<String, Object> param, PagingAndSorting pg);

	void saveObject(Object o);

	Serializable updateObject(Object o);

	void deleteObject(Object o);

	/*@SuppressWarnings("unchecked")
	void updateClassIndexed(final Class clazz);

	@SuppressWarnings("unchecked")
	List searchByKeywords(Class clazz, Map<String, Float> properties,
			String keywords);*/
}
