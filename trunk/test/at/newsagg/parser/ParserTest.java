/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import java.net.URL;

import at.newsagg.model.parser.ItemIF;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.ChannelBuilder;

/**
 * @author Roland Vecera
 * @version
 * created on 26.03.2005 12:32:33
 *
 */
public class ParserTest extends Base {
    
    /**
     * Run test against www.javablogs.com
     * 
     * @throws Exception
     */
    public void testResultOfAFeed() throws Exception {
        Channel channel;

        URL inpFile = new URL(
                "http://www.javablogs.com/ViewDaysBlogs.action?view=rss");
        ChannelBuilder cb = new ChannelBuilder();
        cb.setChannel(new Channel());

        FeedParser fp = (FeedParser) ctx.getBean("feedParser");

        channel = (Channel) fp.parse(cb, inpFile);
        System.out.println(channel.getCreator());
        System.out.println(channel.getDescription());

        java.util.Iterator i = channel.getItems().iterator();
        while (i.hasNext()) {
            ItemIF item = (ItemIF) i.next();
            System.out.println(item.getSubject() + item.getTitle()
                    + item.getDate());
        }
        assertTrue(channel.getItems().size() > 0);
        assertNotNull(channel.getLocation());
    }

}
