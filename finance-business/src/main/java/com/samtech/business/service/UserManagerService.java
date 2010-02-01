package com.samtech.business.service;

import java.io.Serializable;

import com.samtech.business.AuthorizeException;
import com.samtech.business.domain.User;
import com.samtech.common.domain.IUser;

public interface UserManagerService {
 IUser login(String account,String password)throws AuthorizeException;
 
 IUser addUser(User user)throws AuthorizeException;
 
 IUser updateUser(User user)throws AuthorizeException;
 
 void deleteUser(Serializable id)throws AuthorizeException;
 
 
 
}
