package com.samtech.business.dao;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.orm.jpa.JpaCallback;

import com.samtech.business.database.Employee;
import com.samtech.business.domain.User;
import com.samtech.common.domain.PagingAndSorting;
import com.samtech.common.domain.ParamValue;
import com.samtech.hibernate3.impl.AbstractBaseDaoServiceBean;

public class UserBaseDao extends AbstractBaseDaoServiceBean<User> {

	@Override
	protected User convertT(Object o) {
		
		return null;
	}
	@Override
	public User updateObject(final User o) {
		
		return (User)this.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String employeeId = o.getEmployeeId();
				
				if(employeeId!=null){
					Employee e = em.find(Employee.class, employeeId);
					if(e!=null){
						e.setBirthDay(o.getBirthDay());
						e.setEducation(o.getEducation());
						e.setExpireDate(o.getExpireDate());
						e.setGender(o.getGender());
						e.setName(o.getName());
						e.setProfessionTitle(o.getProfessionTitle());
						em.merge(e);
						return o;
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public void deleteObject(final User o) {
		
		this.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String employeeId = o.getEmployeeId();
				
				if(employeeId!=null){
					Employee e = em.find(Employee.class, employeeId);
					if(e!=null){
						em.remove(e);
					}
				}
				return null;
			}
		});
	}
	
	@Override
	public void saveObject(final User o) {

		
		this.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String employeeId = o.getEmployeeId();
				
				if(employeeId!=null){
					Employee e = em.find(Employee.class, employeeId);
					if(e!=null){
						/*e.setBirthDay(o.getBirthDay());
						e.setEducation(o.getEducation());
						e.setExpireDate(o.getExpireDate());
						e.setGender(o.getGender());
						e.setName(o.getName());
						e.setProfessionTitle(o.getProfessionTitle());
						em.merge(e);
						return o;*/
						throw new PersistenceException("already had employee id="+employeeId);
					}else{
						e=new Employee();
						e.setBirthDay(o.getBirthDay());
						e.setEducation(o.getEducation());
						e.setExpireDate(o.getExpireDate());
						e.setGender(o.getGender());
						e.setName(o.getName());
						e.setPassword(o.getPassword());
						e.setProfessionTitle(o.getProfessionTitle());
						e.setCreateDate(new Date());
						em.persist(e);
						return o;
					}
				}else{
					throw new NullPointerException(" employee id");
				}
				
			}
		});
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> query(final String sql, final Map<String, Object> params,
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
			List ao=null;
			if (o instanceof List){
			 	ao=(List)o;
			}else if (o.getClass().isArray()) {
				ao=Arrays.asList(o);
			}
			if(ao!=null && !ao.isEmpty()){
				Object object = ao.get(0);
				if(object !=null && !(object instanceof User)){
					List<User> users=new ArrayList<User>(ao.size());
					for (Object t : ao) {
						users.add(this.convertT(t));
					}
					return users;
				}
			}
			return ao;
		}
		return Collections.EMPTY_LIST;
	}
	
	public User getObject(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
