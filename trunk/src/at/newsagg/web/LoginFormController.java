package at.newsagg.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.*;

import at.newsagg.model.User;
import at.newsagg.service.UserManager;
import at.newsagg.web.commandObj.UserFormCommand;
 


/**
 * @author Szabolcs Rozsnyai
 * $Id:$
 */
public class LoginFormController extends SimpleFormController { 
		private static Log log = LogFactory.getLog(LoginFormController.class);
		private UserManager mgr = null; 
		
		public void setUserManager(UserManager userManager) { 
			this.mgr = userManager; 
		} 
		
		public UserManager getUserManager() { 
			return this.mgr; 
		}
	
		public ModelAndView onSubmit(HttpServletRequest request, 
					HttpServletResponse response, 
					Object command, BindException errors) 
		throws Exception { 
			User loginData = (User) command;
			//if (request.getParameter("loginUsername") != null && request.getParameter("loginPassword") != null) { 
				//user = mgr.checkUser(request.getParameter("loginUsername")); 
				User user = mgr.checkUser(loginData.getUsername());
				if (user != null) {
					//if (user.getUsername().equals(request.getParameter("loginUsername")) && user.getPassword().equals(request.getParameter("loginPassword"))) {
					if (user.getUsername().equals(loginData.getUsername()) && user.getPassword().equals(loginData.getPassword())) {
						// create session for user
						UserSession userSession = new UserSession(user);
						request.getSession().setAttribute("userSession", userSession);
						
						return new ModelAndView(new RedirectView(getSuccessView())); 
					}
				}
			//} 
			
				errors.rejectValue("username", "login failed", null, getMessageSourceAccessor().getMessage("login.datanotok")); 
				return showForm(request, response, errors); 
			//return new ModelAndView(new RedirectView(getFormView()), "messageLogin",  getMessageSourceAccessor().getMessage("login.datanotok"));
			
		}
		
		
}
