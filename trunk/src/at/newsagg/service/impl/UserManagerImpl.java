package at.newsagg.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.UserDAO;
import at.newsagg.model.User;
import at.newsagg.service.UserManager;

public class UserManagerImpl implements UserManager { 
	private static Log log = LogFactory.getLog(UserManagerImpl.class); 
	private UserDAO dao; 
	
	public void setUserDAO(UserDAO dao) { 
		this.dao = dao; 
	} 
	
	public List getUsers() { 
		return dao.getUsers(); 
	} 
	
	public User getUser(String username) { 
		User user = dao.getUser(username); 
		
		if (user == null) { 
			log.warn("Username '" + username + "' not found in database."); 
		} 

		return user; 
	} 
	
	public User checkUser(String username) { 
		User user = dao.checkUser(username); 
		
		if (user == null) { 
			log.warn("Username '" + username + "' not found in database."); 
		} 

		return user; 
	} 
	
	public User saveUser(User user) { 
		dao.saveUser(user); 
		return user; 
	} 
	
	public User updateUser(User user) { 
		dao.updateUser(user); 
		return user; 
	} 
	
	public void removeUser(String username) { 
		dao.removeUser(username); 
	} 
}