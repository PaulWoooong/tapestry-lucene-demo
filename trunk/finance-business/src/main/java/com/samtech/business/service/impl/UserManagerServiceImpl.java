package com.samtech.business.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.JpaCallback;

import com.samtech.business.AuthorizeException;
import com.samtech.business.database.Employee;
import com.samtech.business.domain.User;
import com.samtech.business.service.UserManagerService;
import com.samtech.common.domain.IUser;
import com.samtech.hibernate3.impl.AbstractEntityService;

public class UserManagerServiceImpl extends AbstractEntityService implements UserManagerService{

	public IUser addUser(User user) throws AuthorizeException {
		
		return null;
	}

	public void deleteUser(final Serializable id) throws AuthorizeException {
		this.getJpaTemplate().execute(new JpaCallback() {
			
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Employee e = em.find(Employee.class, id);
				if(e!=null){
					em.remove(e);
				}
				return e;
			}
		});		
	}

	public IUser login(final String account, final String password)
			throws AuthorizeException {
		if(StringUtils.isBlank(account)){
			AuthorizeException ex = new AuthorizeException("account is null");
			ex.setCode(AuthorizeException.ACCOUNT_ERROR);
			throw ex;
		}
		Object result = this.getJpaTemplate().execute(new JpaCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				 Query query = em.createQuery("select o from "+Employee.class.getName()+" as o where o.employeeId=:p_employeeId ");
				 query.setParameter("p_employeeId", account);
				 List<Employee> lst = query.getResultList();
				 if(lst!=null && !lst.isEmpty()){
					 for (Employee e : lst) {
						String p=e.getPassword();
						if(p==null && password==null)return e;
						if(p!=null && password!=null){
							if(p.trim().equals(password.trim()))return e;
						}
					}
					 return AuthorizeException.PASSWORD_ERROR;
				 }else return AuthorizeException.ACCOUNT_ERROR; 
				//return null;
			}
		});	
		if(result!=null){
			if(result instanceof Integer){
				final int intValue = ((Integer)result).intValue();
				if(AuthorizeException.ACCOUNT_ERROR.intValue()==intValue){
					AuthorizeException ex = new AuthorizeException("account is incorrect");
					ex.setCode(AuthorizeException.ACCOUNT_ERROR);
					throw ex;
				}
				if(AuthorizeException.PASSWORD_ERROR.intValue()==intValue){
					AuthorizeException ex = new AuthorizeException("password is incorrect");
					ex.setCode(AuthorizeException.PASSWORD_ERROR);
					throw ex;
				}
				if(AuthorizeException.USER_NOTFOUND.intValue()==intValue){
					AuthorizeException ex = new AuthorizeException("user no found");
					ex.setCode(AuthorizeException.USER_NOTFOUND);
					throw ex;
				}
			}
			if(result instanceof IUser)return (IUser)result;
			if(result instanceof Employee){
				Employee e=(Employee)result;
				User user = new User();
				convertToUser(e, user);
				return user;
			}
		}
		return null;
	}

	public static void convertToUser(Employee e, User user) {
		user.setEmployeeId(e.getEmployeeId());
		user.setExpireDate(e.getExpireDate());
		user.setEducation(e.getEducation());
		user.setPassword(e.getPassword());
		user.setProfessionTitle(e.getProfessionTitle());
		user.setGender(e.getGender());
		user.setName(e.getName());
		user.setBirthDay(e.getBirthDay());
	}

	public IUser updateUser(User user) throws AuthorizeException {
		
		return null;
	}

}
