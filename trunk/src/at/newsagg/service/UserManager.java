package at.newsagg.service;

import java.util.List;

import at.newsagg.model.User;

public interface UserManager { 
	public List getUsers(); 
	public User getUser(String username); 
	public User checkUser(String username);
	public User saveUser(User user);
	public User updateUser(User user); 
	public void removeUser(String username); 
}