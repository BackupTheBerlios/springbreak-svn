package at.newsagg.web; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.newsagg.service.UserManager;

public class UserController implements Controller { 
	private static Log log = LogFactory.getLog(UserController.class); 
	private UserManager mgr = null; 
	
	public void setUserManager(UserManager userManager) { 
		this.mgr = userManager; 
	} 
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		return new ModelAndView("userList", "users", mgr.getUsers()); 
	}
}