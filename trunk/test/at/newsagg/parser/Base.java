/*
 * Created on 20.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Base Class for Parse Test Cases.
 * 
 * Sets up Spring's applicationContext.xml.
 * 
 * @author Roland Vecera
 * @version created on 20.03.2005 15:10:10
 *  
 */
public class Base extends TestCase {

    /**
     * Base class for TestCases.
     * 
     * @author Matt Raible
     */

    protected static Log log = LogFactory.getLog(Base.class);

    protected ApplicationContext ctx = null;

    public Base() {
        String[] paths = { "/WEB-INF/applicationContext.xml" };
        ctx = new ClassPathXmlApplicationContext(paths);
    }

}