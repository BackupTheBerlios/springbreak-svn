package at.newsagg.service; 



import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.newsagg.dao.UserDAOTest;
import at.newsagg.model.User;

public class UserManagerTest extends TestCase { 
	private static Log log = LogFactory.getLog(UserManagerTest.class); 
	private ApplicationContext ctx; 
	private User user; 
	private UserManager mgr; 
	
	protected void setUp() throws Exception { 
		String[] paths = {"/WEB-INF/applicationContext.xml"}; 
		ctx = new ClassPathXmlApplicationContext(paths); 
		mgr = (UserManager) ctx.getBean("userManager"); 
	} 
	
	protected void tearDown() throws Exception { 
		user = null; 
		mgr = null; 
	} 
	
	public void testAddAndRemoveUser() throws Exception { 
		user = new User(); 
		user.setUsername("testuser");
		user.setFirstName("Easter"); 
		user.setLastName("Bunny"); 
		user = mgr.saveUser(user); 
		assertTrue(user.getUsername() != null); 
		
		if (log.isDebugEnabled()) { 
			log.debug("removing user..."); 
		} 
		
		String userName = user.getUsername().toString(); 
		mgr.removeUser(userName); 
		user = mgr.getUser(userName); 
		
		if (user != null) { 
			fail("User object found in database!"); 
		} 
	}
	
	public void testAddAndUpdateAndRemoveUser() throws Exception { 
		user = new User(); 
		user.setUsername("testuser");
		user.setFirstName("Easter"); 
		user.setLastName("Bunny"); 
		user = mgr.saveUser(user); 
		assertTrue(user.getUsername() != null); 
		user.setFirstName("happy");
		user = mgr.updateUser(user);
		assertTrue(user.getFirstName().equals("happy"));
		
		if (log.isDebugEnabled()) { 
			log.debug("removing user..."); 
		} 
		
		String userName = user.getUsername().toString(); 
		mgr.removeUser(userName); 
		user = mgr.getUser(userName); 
		
		if (user != null) { 
			fail("User object found in database!"); 
		} 
	}
	
	public static void main(String[] args) { 
		junit.textui.TestRunner.run(UserDAOTest.class); 
	} 
}
