/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.ArrayList;
import java.util.Date;

import at.newsagg.model.Category;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author Roland Vecera
 * @version
 * created on 28.03.2005 20:54:56
 *
 */
public class FeedSubscriberDAOTest extends Base {
    
    /**
     * tests save*, count*, get*-methods of FeedSubscriberDAO.
     * 
     * @author Roland Vecera
     * 
     * TODO: Test geht im Moment nur einmal --> am Ende des Testes den Eintrag auch wieder löschen
     * TODO: ACHTUNG: FeedSubscriber-Category cascade ist noch zu überprüfen
     *
     */
    public void testSaveSelectCount ()
    {
        
     FeedSubscriberDAO fDAO = (FeedSubscriberDAO)ctx.getBean("feedSubscriberDAO");
     
     FeedSubscriber f = new FeedSubscriber ();
     
     User u = new User ();
     u.setUsername("vec"+new Date ().toString());
     u.setFirstName("roland");
     u.setLastName("vecera");
     UserDAO uDAO = (UserDAO)ctx.getBean("userDAO");
     uDAO.saveUser(u);
     
     
     ChannelDAO cDAO = (ChannelDAO)ctx.getBean("channelDAO");
     Channel c = (Channel)cDAO.getChannels().get(0);
     
     Category cat = new Category();
     cat.setTitle("newCat"+new Date().toString());
     cat.setHtmlColor("BLACK");
     
     
     f.setUser(u);
     f.setChannel(c);
     f.setCategory(cat);
     
     int byUser1 = fDAO.countFeedSubscriberByUser(u.getUsername());
     int byChannel1 = fDAO.countFeedSubscriberByChannelURL(c.getLocationString());
     int byCat1 = fDAO.countFeedSubscriberByUserWithCategory(u.getUsername(),cat.getId());
     
     fDAO.saveFeedSubscriber(f);
     
     FeedSubscriber freturn = (FeedSubscriber)((ArrayList)fDAO.getFeedSubscriberByCategory(cat.getId())).get(0);
     assertTrue (f.getId() == freturn.getId());
     
    freturn = (FeedSubscriber)((ArrayList)fDAO.getFeedSubscriberByChannelURL(c.getLocationString())).get(0);
     assertTrue (f.getId() == freturn.getId());
     
     freturn = (FeedSubscriber)((ArrayList)fDAO.getFeedSubscriberByUser(u.getUsername())).get(0);
     assertTrue (f.getId() == freturn.getId());
     
     int byUser2 = fDAO.countFeedSubscriberByUser(u.getUsername());
     int byChannel2 = fDAO.countFeedSubscriberByChannelURL(c.getLocationString());
     int byCat2 = fDAO.countFeedSubscriberByUserWithCategory(u.getUsername(),cat.getId());
     
     
     assertTrue (byUser2 == (byUser1+1));
     assertTrue (byChannel2 == (byChannel1+1));
     assertTrue (byCat2 ==  (byCat1+1));
    }
}
