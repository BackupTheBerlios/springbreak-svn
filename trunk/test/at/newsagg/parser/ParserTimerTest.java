/*
 * Created on 26.03.2005
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
 * ONLY RUN THIS TEST IF YOU REALLY NEED IT.
 * 
 * Just runs for 30min to test Spring's Timerstuff for ParserCronJobTest!
 * 
 * @author Roland Vecera
 * @deprecated
 * @version
 * created on 26.03.2005 15:45:33
 *
 */
public class ParserTimerTest extends TestCase{
    
    protected static Log log = LogFactory.getLog(ParserTimerTest.class);

    protected ApplicationContext ctx = null;
    
    public ParserTimerTest ()
    {
        
            String[] paths = { "/WEB-INF/applicationContext.xml", "/WEB-INF/applicationContext-timers.xml" };
            ctx = new ClassPathXmlApplicationContext(paths);
            
    }
    
    public void testRun ()
    {
       
       
       try {
        Thread.sleep(60*1000*7);
    } catch (InterruptedException e) {
        
    }
       
    }

}
