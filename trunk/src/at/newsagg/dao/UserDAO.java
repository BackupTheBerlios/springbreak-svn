package at.newsagg.dao;

import at.newsagg.model.User;
import java.util.List; 

public interface UserDAO extends DAO 
{ 
	public List getUsers(); 
	public User getUser(String username); 
	public User checkUser(String username);
	public void saveUser(User user); 
	public void updateUser(User user); 
	public void removeUser(String username);
}