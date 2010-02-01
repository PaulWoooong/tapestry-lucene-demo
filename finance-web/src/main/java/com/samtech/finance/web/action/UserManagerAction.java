package com.samtech.finance.web.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.samtech.business.AuthorizeException;
import com.samtech.business.service.UserManagerService;
import com.samtech.common.domain.IUser;


public class UserManagerAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
		private boolean logined = false;
        private String password, username;
        
        private UserManagerService userManager;
        
        public String doLoginAction() {
                this.checkError();
                Map<String, List<String>> fieldErrors = this.getFieldErrors();
                if(fieldErrors!=null && !fieldErrors.isEmpty()){
                	return INPUT;
                }
                try {
					IUser user = userManager.login(this.getUsername(), this.getPassword());
					HttpSession session = this.getServletRequest().getSession();
					ActionContext.getContext().getSession().put(
							SESSION_ATTRIBUTE_KEY_USER, user);
					ActionContext.getContext().getSession().put(LOGIN_KEY, Boolean.TRUE);
					
					if(session!=null){
						session.setAttribute(USER_INFO_KEY, user);
						session.setAttribute(LOGIN_KEY, Boolean.TRUE);
					}
				} catch (AuthorizeException e) {
					String message = e.getMessage();
					if(e.getCode()!=null && e.getMessage()==null){
						if(e.getCode().intValue()==AuthorizeException.ACCOUNT_ERROR.intValue()){
						 message="用户名错误!";
						 
						}else if(e.getCode().intValue()==AuthorizeException.PASSWORD_ERROR.intValue()){
						 message="密码错误!";
						 
						}else if(e.getCode().intValue()==AuthorizeException.USER_NOTFOUND.intValue()){
						 message="无此用户或用户过期!";
						 
						} 
					}
					this.addActionError(message);
				}
                
                Collection<String> actionErrors = this.getActionErrors();
                if(actionErrors!=null && !actionErrors.isEmpty())
                	return ERROR;
                
                return SUCCESS;
        }
        
    protected void checkError(){
        String username2 = this.getUsername();
 	   String password2 = this.getPassword();
 	   if(StringUtils.isBlank( username2)){
 		   this.addFieldError("username",  "请输入用户名");
 		   
 	   }
 	   if(StringUtils.isBlank( password2)){
 		  this.addFieldError("password",  "请输入密码");
 	   }
    }

        public String doLogoutAction() {

                logined = false;
                               
                clearSession();
                HttpServletResponse resp = this.getServletResponse();
    			resp.setHeader( "Cache-Control" , "no-cache" ); 
    			//Forces caches to obtain a new copy of the page from the origin server 
    			resp.setHeader( "Cache-Control" , "no-store" ); 
    			//Directs caches not to store the page under any circumstance 
    			resp.setDateHeader( "Expires" , 0); 
    			//Causes the proxy cache to see the page as "stale" 
    			resp.setHeader( "Pragma" , "no-cache" );
                return "login";
        }
        
        @SuppressWarnings("unchecked")
		protected void clearSession() {

    		SessionMap sessionMap = (SessionMap) ActionContext.getContext().getSession();
    		try {
    			sessionMap.clear();
    		} catch (Exception e) {
    			//
    		}
    		
    		HttpSession httpSession = this.getServletRequest().getSession();
    		try {
    			if(httpSession!=null)
    			httpSession.invalidate();
    		} catch (Exception e) {
    			//
    		}
    	}
        
      
       

		public boolean isLogined() {
                return logined;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

       
        @Override
        public void prepare() throws Exception {
        
        super.prepare();
        IUser loginUser = this.getLoginUser();
        if(loginUser!=null)this.logined=true;
        }
		

        public void setUserManager(UserManagerService manager){
        	this.userManager=manager;
        }
}
