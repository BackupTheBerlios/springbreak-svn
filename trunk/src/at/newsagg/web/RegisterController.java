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
import at.newsagg.web.commandObj.RegisterCommand;;

/**
 * @author szabolcs
 * 
 * 
 */
public class RegisterController extends SimpleFormController { 
	private static Log log = LogFactory.getLog(RegisterController.class); 
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
		RegisterCommand userFormCmd = (RegisterCommand) command;
		User registerData = userFormCmd.getUser();
	
		// check if username exists
		User user =  mgr.checkUser(registerData.getUsername());
		if (user != null) {
			// if it exists return error message
			//return new ModelAndView(new RedirectView(getFormView()), "messageRegister",  getMessageSourceAccessor().getMessage("register.usernaAlreadyExists"));
			errors.rejectValue("user.username", "username exists", null, getMessageSourceAccessor().getMessage("register.usernaAlreadyExists")); 
			return showForm(request, response, errors); 
		} else {
			// if not create user and goto login screen
			mgr.saveUser(registerData);
			//return new ModelAndView(new RedirectView(getSuccessView())); 
			return new ModelAndView(getSuccessView(), "user", registerData);
		}
		
	} 
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException { 
		RegisterCommand cmd = new RegisterCommand(); 
		cmd.setUser(new User());
		return cmd;
	} 
}