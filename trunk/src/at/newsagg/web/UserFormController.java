package at.newsagg.web; 

import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import at.newsagg.model.User;
import at.newsagg.service.UserManager;
import at.newsagg.web.commandObj.*;
/**
 * @author szabolcs
 * 
 * 
 */
public class UserFormController extends SimpleFormController { 
	private static Log log = LogFactory.getLog(UserFormController.class); 
	private UserManager mgr = null;
	
	public void setUserManager(UserManager userManager) { 
		this.mgr = userManager; 
	} 
	
	public UserManager getUserManager() { 
		return this.mgr; 
	}
	
	/**
	 *  Set up a custom property editor for converting Longs 
	 */ 
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) { 
		NumberFormat nf = NumberFormat.getNumberInstance(); 
		binder.registerCustomEditor(Long.class, null,new CustomNumberEditor(Long.class, nf, true)); 
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, 
									HttpServletResponse response, 
									Object command, BindException errors) 
	throws Exception { 
		if (log.isDebugEnabled()) { 
			log.debug("entering 'onSubmit' method..."); 
		}
		UserFormCommand userFormCmd = (UserFormCommand) command;
		
		User user = userFormCmd.getUser();
		
		// if delete was called delete object and return to the userlist
		if (request.getParameter("delete") != null) { 
			log.debug("delete user");
			mgr.removeUser(user.getUsername().toString()); 
			request.getSession().setAttribute("message", 
					getMessageSourceAccessor() 
					.getMessage("user.deleted", 
							new Object[] {user.getFirstName() + ' ' + user.getLastName()}));
		} else { 
			// check if password and retyped password are the same
			//if ( userFormCmd.getSecondPassword() != null || !user.getPassword().equals(userFormCmd.getSecondPassword())) {
				//errors.rejectValue("secondpassword","not the same", "not the same");
				//return new ModelAndView(new RedirectView(getFormView()), "fieldError", errors); 
			//}
				
			// distinct between update and save - because of assigned id in relation user
			if (request.getParameter("update") != null) {
				mgr.updateUser(user);
			}
			else {
				if(mgr.checkUser(user.getUsername()) == null)  {
					mgr.saveUser(user);
				} else {
					errors.rejectValue("user.username", "username exists", null, getMessageSourceAccessor().getMessage("user.usernaAlreadyExists")); 
					return showForm(request, response, errors);
				}
			}
			
			request.getSession().setAttribute("message", 
					getMessageSourceAccessor().getMessage("user.saved", 
							new Object[] {user.getFirstName() + ' ' + user.getLastName()})); 
		} 
		
		return new ModelAndView(new RedirectView(getSuccessView())); 
	} 
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException { 
		String username = request.getParameter("usernameID"); 
		if ((username != null) && !username.equals("")) {
				UserFormCommand userFormCmd = new UserFormCommand();
				userFormCmd.setUser(mgr.getUser(request.getParameter("usernameID")));
				userFormCmd.setSecondPassword(mgr.getUser(request.getParameter("usernameID")).getPassword());
				return userFormCmd;
		} 
		else {
			//return new User(); 
			//return new UserFormCommand().setUser(new User());
			UserFormCommand cmd = new UserFormCommand(); 
			cmd.setUser(new User());
			return cmd;
		} 
	} 
}