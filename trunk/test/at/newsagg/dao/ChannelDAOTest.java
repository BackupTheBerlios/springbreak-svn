/*
 * Created on 21.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author Roland Vecera
 * 
 * Testing ChannelDAOHibernate
 * 
 * @version
 * created on 21.03.2005 13:39:34
 *
 */
public class ChannelDAOTest extends at.newsagg.dao.Base {

    /**
     * TODO: Make sure, that DB is filled
     * 
     */
    
    /**
     * testing equals methode.
     * testing getChannels().
     * 
     * 2 Channels are equal if they have the same lowercase-URL
     */
    public void testEqualsMethod() throws Exception
    {
     ChannelDAO c= (ChannelDAO)ctx.getBean("channelDAO");
     
     Channel channel =(Channel)c.getChannels().get(0);
     Channel channel2 =(Channel)c.getChannels().get(0);
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocation(null);
     channel2.setLocation(null);
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0wnz.at/rss.php");
     channel2.setLocationString("http://vecego.0wnz.at/rss.php");
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0WNZ.at/rss.php");
     channel2.setLocationString("http://VECEGO.0wnz.at/rss.php");
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0WNZ.at/rss.php");
     channel2.setLocationString("http://0wnz.at/rss.php");
     
     assertFalse(channel.equals(channel2));
     
     
    
    }
}
