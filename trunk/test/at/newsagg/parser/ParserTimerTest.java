/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import java.util.Date;

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
public class ParserTimerTest extends Base {
    
    public void testRun ()
    {
       
       
       try {
        Thread.sleep(60*1000*7);
    } catch (InterruptedException e) {
        
    }
       
    }

}
