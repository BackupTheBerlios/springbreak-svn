package at.newsagg.dao;

import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import at.newsagg.model.User;


public class UserDAOTest extends Base { 
	private User user = null; 
	private UserDAO dao = null; 
	
	protected void setUp() throws Exception { 
		log = LogFactory.getLog(UserDAOTest.class); 
		dao = (UserDAO) ctx.getBean("userDAO"); 
	} 
	
	protected void tearDown() throws Exception { 
		dao = null; 
	} 
	
	public static void main(String[] args) { 
		junit.textui.TestRunner.run(UserDAOTest.class); 
	} 
	
	public void testIt() throws Exception {
		log.info("test");
	}
	
	public void testAddAndRemove() throws Exception { 
		user = new User(); 
		user.setUsername("srozsnyai");
		user.setPassword("letmein");
		user.setFirstName("Szabolcs"); 
		user.setLastName("Rozsnyai");
		user.setIsAdmin(true);
		
		dao.saveUser(user); 
		
		assertTrue(user.getUsername() != null);
		assertTrue(user.getFirstName().equals("Szabolcs")); 
		
		if (log.isDebugEnabled()) { 
			log.debug("removing user..."); 
		} 
		
		dao.removeUser(user.getUsername ()); 
		try {
            dao.getUser(user.getUsername());
            fail();
        } catch (ObjectRetrievalFailureException e) {
           
            log.info("User shouldn't be found in DB!");
        } 
	}
}