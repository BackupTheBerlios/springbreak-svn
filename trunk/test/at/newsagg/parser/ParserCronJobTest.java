/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import java.io.File;

import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.ChannelBuilder;
import at.newsagg.service.ParserCronJobService;

/**
 * @author king
 * @version
 * created on 26.03.2005 14:32:54
 *
 *
 *TODO: kein gescheiter UNIT Test by now!!
 *
 */
public class ParserCronJobTest extends Base {

    public void testFeedUpdate ()
    {
        ParserCronJobService cronjob = (ParserCronJobService) ctx
        .getBean("parserCronJobService");
        
        cronjob.runUpdate();
    }
}
