package at.newsagg.dao;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Base class for DAO TestCases.
 * @author Matt Raible
 */
public class Base extends TestCase {
    protected static Log log = LogFactory.getLog(Base.class);
    protected ApplicationContext ctx = null;

    public Base() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
}
