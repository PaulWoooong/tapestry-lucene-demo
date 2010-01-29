package com.samtech.hibernate3.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;


//import org.apache.lucene.util.Version;

import org.springframework.orm.jpa.JpaCallback;

import com.samtech.common.domain.PagingAndSorting;
import com.samtech.common.domain.ParamValue;
import com.samtech.hibernate3.BaseServiceInf;

public abstract class AbstractBaseDaoServiceBean<T> extends AbstractEntityService implements
		BaseServiceInf<T> {
	private static Map<String, Boolean> indexedmap = new HashMap<String, Boolean>(
			2);

	@SuppressWarnings("unchecked")
	public void saveObject(T o) {
		this.getJpaTemplate().persist(o);
		if (o != null) {
			Method[] methods = o.getClass().getMethods();
			String p = null;
			Class returnType = null;
			for (int i = 0; i < methods.length; i++) {
				Id idAnn = methods[i].getAnnotation(Id.class);
				if (idAnn != null) {
					String name = methods[i].getName();
					try {
						if (name.startsWith("get")) {
							p = name.substring(3);
							Object id = methods[i].invoke(o, new Object[0]);
							System.out.println(id);
							break;
						}
						if (name.startsWith("set")) {
							p = name.substring(3);
							Class<?>[] parameterTypes = methods[i]
									.getParameterTypes();
							if (parameterTypes != null
									&& parameterTypes.length == 1) {
								returnType = parameterTypes[0];
							}
							break;
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			if (p != null && returnType != null) {
				for (int i = 0; i < methods.length; i++) {
					String name = methods[i].getName();
					try {
						if (name.startsWith("get")) {
							Class<?> rt = methods[i].getReturnType();
							if (rt != null && rt.equals(returnType)
									&& name.indexOf(p) > 0) {
								Object id = methods[i].invoke(o, new Object[0]);
								System.out.println("found method id=" + id);
								break;
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			} else {
				Field[] fields = o.getClass().getFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if (field.getAnnotation(Id.class) != null) {
						try {
							Object object = field.get(o);
							System.out.println("found field id=" + object);
							break;
						} catch (IllegalArgumentException e) {

							e.printStackTrace();
						} catch (IllegalAccessException e) {

							e.printStackTrace();
						}
					}
				}
			}

		}

	}

	public void deleteObject(T o) {
		Object merge = this.getJpaTemplate().merge(o);
		this.getJpaTemplate().remove(merge);

	}

	public <T> T getObject(Class<T> clazz, Serializable id) {
		return this.getJpaTemplate().find(clazz, id);

	}
	

	@SuppressWarnings("unchecked")
	public List<T> query(final String sql, final Map<String, Object> params,
			final PagingAndSorting pg) {
		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(PagingAndSorting.buildOrderBy(sql,
						pg));

				if (params != null && !params.isEmpty()) {
					Iterator iter = params.keySet().iterator();
					while (iter.hasNext()) {
						String parameterName = (String) iter.next();
						Object object = params.get(parameterName);
						if (object instanceof ParamValue) {
							ParamValue pv = (ParamValue) object;
							int type = pv.getType();
							if (pv.getValue() instanceof Date
									|| pv.getValue() instanceof Calendar) {
								boolean isDate = false;
								if (pv.getValue() instanceof Date) {
									isDate = true;
								}
								if (type == Types.DATE) {
									if (isDate)
										query.setParameter(parameterName,
												(Date) pv.getValue(),
												TemporalType.DATE);
									else
										query.setParameter(parameterName,
												(Calendar) pv.getValue(),
												TemporalType.DATE);
								} else if (type == Types.TIMESTAMP) {
									if (isDate)
										query.setParameter(parameterName,
												(Date) pv.getValue(),
												TemporalType.TIMESTAMP);
									else
										query.setParameter(parameterName,
												(Calendar) pv.getValue(),
												TemporalType.TIMESTAMP);
								} else if (type == Types.TIME) {
									if (isDate)
										query.setParameter(parameterName,
												(Date) pv.getValue(),
												TemporalType.TIME);
									else
										query.setParameter(parameterName,
												(Calendar) pv.getValue(),
												TemporalType.TIME);
								} else {
									query.setParameter(parameterName, pv
											.getValue());
								}
							} else {
								query
										.setParameter(parameterName, pv
												.getValue());
							}

						} else if (object instanceof Date) {

							query.setParameter(parameterName, (Date) object,
									TemporalType.DATE);
						} else if (object instanceof Collection) {
							query.setParameter(parameterName,
									(Collection) object);
						} else {
							query.setParameter(parameterName, object);
						}

					}
				}
				if (pg != null && pg.getStart() >= 0) {
					query.setFirstResult(pg.getStart());
					if (pg.getEnd() != -1) {
						query.setMaxResults(pg.getEnd() - pg.getStart() + 1);
					}
					query.setHint("org.hibernate.fetchSize", new Integer(
							getFetchSize(pg.getStart())));
					// query.setFetchSize();
				}
				List results = query.getResultList();
				return results;

			}
		}, true);
		if (o != null) {
			if (o instanceof List)
				return (List) o;
			if (o.getClass().isArray()) {
				Arrays.asList(o);
			}
			List ls = new ArrayList(1);
			ls.add(o);
			return ls;
		}
		return Collections.EMPTY_LIST;
	}

	

	private static int getFetchSize(int first) {
		if (first > 0) {
			if (first < 50) {
				return 20;
			}
			if (first < 100) {
				return 50;
			}
			if (first < 1000) {
				return 100;
			}
			if (first < 4000) {
				return 200;
			}
			return 500;
		} else
			return 20;
	}

	public T updateObject(T o) {
		return this.getJpaTemplate().merge(o);
	}

}
