package at.newsagg.web; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import at.newsagg.model.User;



public class IndexViewController implements Controller { 
	private static Log log = LogFactory.getLog(IndexViewController.class); 
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		User user = new User();
		return new ModelAndView("index", "login", user); 
	}
}