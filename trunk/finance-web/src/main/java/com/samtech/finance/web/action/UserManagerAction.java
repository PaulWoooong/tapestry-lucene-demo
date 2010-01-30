package com.samtech.finance.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;


public class UserManagerAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
        private boolean logined = false;
        private String password, username;
        //private UserAccountServiceInf userAccountService;

        public String doLoginAction() {
                

                return SUCCESS;
        }

        public String doLogoutAction() {

                HttpSession session = this.getServletRequest().getSession();
                logined = false;
               // user = null;
                Map<String, Object> smap = ActionContext.getContext().getSession();
                
                
                if(smap!=null){
                        smap.remove(LOGIN_KEY);
                        smap.remove(USER_INFO_KEY);
                }
                if (session != null) {
                        session.removeAttribute(LOGIN_KEY);
                        session.removeAttribute(USER_INFO_KEY);
                        session.invalidate();
                }

                return SUCCESS;
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

       

		

}
