package com.samtech.finance.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.samtech.business.database.Gender;
import com.samtech.business.domain.User;
import com.samtech.hibernate3.BaseServiceInf;

public class UserEditAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431428717032717369L;

	private User editUser;
	private String queryUserId;
	BaseServiceInf userBaseDao;

	public String getQueryUserId() {
		return queryUserId;
	}

	public void setQueryUserId(String password) {
		this.queryUserId = password;
	}

	public User getEditUser() {
		if (editUser == null)
			editUser = new User();
		return editUser;
	}

	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}
	
	public static class GenderValue{
		private String key,value;
		public GenderValue(){
			
		}
		GenderValue(String key,String value){
			this.key=key;
			this.value=value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	public List<GenderValue> getGenderValues(){
		//Gender[] values = Gender.values();
		/*Map g=new HashMap(2);
		for(int i=0;i<values.length;i++){
			g.put(values[i].name(),values[i]);
		}*/
		//return g;
		List<GenderValue> values=new ArrayList<GenderValue>();
		values.add(new GenderValue("MALE", "男"));
		values.add(new GenderValue("FEMALE", "女"));
		return values;
	}
	@Override
	public void prepare() throws Exception {
		
		super.prepare();
		if(this.queryUserId!=null){
			User u = (User) this.getUserBaseDao().getObject(
					this.queryUserId);
			if(u!=null)this.setEditUser(u);
		}
	}
	@Override
	public String execute() throws Exception {
		String qid = this.getQueryUserId();
		if(qid!=null && this.editUser==null){
			User u = (User) this.getUserBaseDao().getObject(
					qid);
			if(u!=null)this.setEditUser(u);
			else{
				this.addActionError("没有该用户 employeeId="+qid);
			}
		}
		return super.execute();
	}
	// 保存页面并返回新增问题页面
	public String doAddAndNew() {
		checkEditError();
		queryUserId = null;
		try {
			// search the id whether have be register
			User u = (User) this.getUserBaseDao().getObject(
					this.editUser.getEmployeeId());
			if (u == null) {
				// if never register then report error
				getUserBaseDao().saveObject(editUser);
				this.addActionMessage("用户新增成功！");
			} else {
				// if have register then update
				getUserBaseDao().updateObject(editUser);
				addActionMessage("用户名已存在！");
			}
		} catch (Exception e) {
			// setMessage(e.getMessage());
			this.addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	// 请求增加问题页面并浏览面
	
	public String doAddAndView() {
		checkEditError();

		try {
			User u = (User) this.getUserBaseDao().getObject(
					this.editUser.getEmployeeId());
			if (u == null) {
				getUserBaseDao().saveObject(editUser);
				addActionMessage("新增用户成功！");
			} else {
				// if have register then update
				editUser=(User) getUserBaseDao().updateObject(editUser);
				addActionMessage("修改用户成功！");

			}
			this.setQueryUserId(editUser.getEmployeeId());
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	private void checkEditError() {

	}

	private BaseServiceInf getUserBaseDao() {
		return userBaseDao;
	}

	public void setUserBaseDao(BaseServiceInf userBaseDao) {
		this.userBaseDao = userBaseDao;
	}

}
