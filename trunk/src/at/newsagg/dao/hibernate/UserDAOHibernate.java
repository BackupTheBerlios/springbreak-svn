package at.newsagg.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.UserDAO;
import at.newsagg.model.User;

public class UserDAOHibernate extends HibernateDaoSupport implements UserDAO { 
	private Log log = LogFactory.getLog(UserDAOHibernate.class); 
	
	public List getUsers() { 
		return getHibernateTemplate().find("from User order by username"); 
	} 
	
	public User getUser(String username) { 
		User user = (User) getHibernateTemplate().get(User.class, username); 
		if (user == null) { 
			throw new ObjectRetrievalFailureException(User.class, username); 
		} return user;
	} 
	
	public User checkUser(String username) { 
		User user = (User) getHibernateTemplate().get(User.class, username); 
		
		return user;
	} 
	
	public void saveUser(User user) { 
		//getHibernateTemplate().saveOrUpdate(user); 
		getHibernateTemplate().save(user);
		
		
		if (log.isDebugEnabled()) { 
			log.debug("username set to: "  + user.getUsername()); } 
		} 
	
	public void updateUser(User user) { 
		getHibernateTemplate().update(user);
		
		
		if (log.isDebugEnabled()) { 
			log.debug("username set to: "  + user.getUsername()); } 
		} 
	
	
	public void removeUser(String username) { 
		Object user = getHibernateTemplate().load(User.class, username); 
		getHibernateTemplate().delete(user); 
	} 
}