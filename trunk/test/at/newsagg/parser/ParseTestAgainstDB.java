/*
 * Created on 11.02.2005
 * king
 * 
 */
package at.newsagg.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.parser.ItemIF;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.ChannelBuilder;
import at.newsagg.model.parser.hibernate.Item;

/**
 * @author king
 * @version created on 11.02.2005 14:05:03
 * 
 * TODO: setup method overwrite; every test performs same setup here..
 *  
 */
public class ParseTestAgainstDB extends Base {

    

    /**
     * Gets an Feed and persists it in DB.
     * 
     * @throws Exception
     */
    public void testStoreFeed() throws Exception {
        Channel channel, channelfromDB;
        ChannelDAO dao = (ChannelDAO) ctx
                .getBean("channelDAO");
        ChannelBuilder cb = new ChannelBuilder();

        File inpFile = new File("test/at/newsagg/parser/vecego.rss");

        FeedParser fp = (FeedParser) ctx.getBean("feedParser");

        channel = (Channel) fp.parse(cb, inpFile);

        dao.saveChannel(channel);

        channelfromDB = dao.getChannel(channel.getIntId());
        System.out.println(channelfromDB.getTitle() + " = "
                + channel.getTitle());
        assertTrue(channelfromDB.getIntId() == channel.getIntId());
        
        //check getChannel Method
        Channel channel2 = dao.getChannel(channel.getLocation());
        assertTrue (channel2.equals(channel));
        
    }

    /**
     * Gets a new Feed and tries to update it in DB.
     * 
     * 
     * 
     * @throws Exception
     */
    public void testBadUpdateFeed() throws Exception {
        Channel channel, channelfromDB;
        ChannelDAO dao = (ChannelDAO) ctx
                .getBean("channelDAO");
        ChannelBuilder cb = new ChannelBuilder();

        File inpFile = new File("test/at/newsagg/parser/vecego.rss");

        FeedParser fp = (FeedParser) ctx.getBean("feedParser");

        channel = (Channel) fp.parse(cb, inpFile);

        try {
            dao.updateChannel(channel);
            fail("exception must have been thrown before, because we are updateing a NEW feed. ");
        } catch (DataIntegrityViolationException e) {
            System.out.println("updating a transient channel failed. good!");
        }
    }

    /**
     * Tests if an update on an already persistent Object of the DB works.
     * 
     * @throws Exception
     */
    public void testGoodUpdateFeed() throws Exception {
        Channel channel, channelfromDB;
        ChannelDAO dao = (ChannelDAO) ctx
                .getBean("channelDAO");
        ChannelBuilder cb = new ChannelBuilder();

        File inpFile = new File("test/at/newsagg/parser/vecego.rss");

        FeedParser fp = (FeedParser) ctx.getBean("feedParser");

        channel = (Channel) fp.parse(cb, inpFile);

        dao.saveChannel(channel);
        System.out.println(channel.getIntId());

        channel.setPublisher("test");
        dao.updateChannel(channel);

        assertTrue(((Channel) dao.getChannel(channel.getIntId()))
                .getPublisher().equals("test"));
    }
    
    
    /**
     * Tests ItemDAOHibernate.countItemwithURL() and getIDfromItemwithURL().
     * 
     * After a channel and its items wher inserted, the 
     * countItemwithURL-method must return >0 for an item's url just inserted.
     * 
     * @throws Exception
     */
    public void testOnItemwithURL() throws Exception {
        Channel channel, channelfromDB;
        ChannelDAO dao = (ChannelDAO) ctx
                .getBean("channelDAO");
        ItemDAO itemdao = (ItemDAO) ctx
        .getBean("itemDAO");
        ChannelBuilder cb = new ChannelBuilder();

        File inpFile = new File("test/at/newsagg/parser/vecego.rss");

        FeedParser fp = (FeedParser) ctx.getBean("feedParser");

        channel = (Channel) fp.parse(cb, inpFile);
        Item itemBeforePersistent = (Item)((ArrayList)channel.getItems()).get(0);
        
        dao.saveChannel(channel);

        int d = itemdao.countItemwithURL(itemBeforePersistent.getLink());
        System.out.println("bereits"+d+"einträge");
        //check getChannel Method
        assertTrue (d > 0);
        //Url can be found in DB
        assertTrue(itemdao.getIDfromItemwithURL(itemBeforePersistent.getLink()) >= 0);
        //Url "foobar" cant be found
        assertTrue(-1 == itemdao.getIDfromItemwithURL("foobar"));
            
                
            
        
    }
    
    

}