/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.FeedSubscriberIF;

/**
 * DAO for FeedSubscriber
 * 
 * @author Roland Vecera
 * @version created on 28.03.2005 20:20:38
 *  
 */
public class FeedSubscriberDAOHibernate extends HibernateDaoSupport implements FeedSubscriberDAO {
    private Log log = LogFactory.getLog(FeedSubscriberDAOHibernate.class);

    /**
     * Save a new FeedSubscriber.
     * 
     * Stores actual Date in feedsubscriber.AddedDate();
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF saveFeedSubscriber(FeedSubscriberIF feedsubscriber) {
        
        feedsubscriber.setAddedDate(new java.util.Date());
        getHibernateTemplate().save(feedsubscriber);

        if (log.isDebugEnabled()) {
            log.debug("Feedsubscriber for "
                    + feedsubscriber.getUser().getUsername() + " "
                    + feedsubscriber.getChannel().getLocationString()
                    + " stored!");
        }
        return feedsubscriber;
    }

    /**
     * Update a FeedSubscriber
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF updateFeedSubscriber(FeedSubscriberIF feedsubscriber) {
        getHibernateTemplate().update(feedsubscriber);

        if (log.isDebugEnabled()) {
            log.debug("Feedsubscriber for "
                    + feedsubscriber.getUser().getUsername() + " "
                    + feedsubscriber.getChannel().getLocationString()
                    + " updated!");
        }
        return feedsubscriber;
    }

    /**
     * Delete an Feedsubscriber.
     * 
     * Doesn't delete recursively.
     * 
     * @param feedsubscriber_id
     */
    public void removeFeedSubscriber(int feedsubscriber_id) {
        Object f = getHibernateTemplate().load(FeedSubscriber.class,
                new Integer(feedsubscriber_id));
        getHibernateTemplate().delete(f);
    }
    
    /**
     * Gets a FeedSubscriber by id.
     * @param feedsubscriber_id
     * @return
     */
    public FeedSubscriberIF getFeedSubscriber (int feedsubscriber_id)
    {
        return (FeedSubscriberIF)getHibernateTemplate().load(FeedSubscriber.class,
                new Integer(feedsubscriber_id));
    }

    /**
     * Returns all FeedSubscriber to a given User.
     * 
     * @param username
     * @return
     */
    public Collection getFeedSubscriberByUser(String username) {
        return getHibernateTemplate().find(
                "from FeedSubscriber f where f.user.username like ?", username,
                Hibernate.STRING);
    }

    /**
     * Returns all FeedSubscriber to a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public Collection getFeedSubscriberByChannelURL(String locationURL) {
        return getHibernateTemplate().find(
                "from FeedSubscriber f where f.channel.locationString like ?",
                locationURL, Hibernate.STRING);
    }

    /**
     * Returns all FeedSubcriber to a given Category.
     * 
     * @param category_id
     * @return
     */
    public Collection getFeedSubscriberByCategory(int category_id) {
        return getHibernateTemplate().find(
                "from FeedSubscriber f where f.category.id like ?",
                new Integer(category_id), Hibernate.INTEGER);
    }

    /**
     * Returns Collection of FeedSubscriber of a given User in his/her given Category.
     * 
     * @param username
     * @param category_id
     * @return
     */
    public Collection getFeedSubscriberForUserByCategory(String username,
            int category_id) {
        Object[] o = { new Integer(category_id), username };
        net.sf.hibernate.type.Type[] p = { Hibernate.INTEGER, Hibernate.STRING };
        return getHibernateTemplate()
                .find(
                        "from FeedSubscriber f where f.category.id = ? and f.user.username like ?",
                        o, p);

    }

    /**
     * Returns the FeedSubscriber of a given User and a given Channel.
     * 
     * @param username
     * @param category_id
     * @return
     */
    public FeedSubscriber getFeedSubscriberForUserOnChannel(String username,
            int channel_id) throws IndexOutOfBoundsException {
        Object[] o = { new Integer(channel_id), username };
        net.sf.hibernate.type.Type[] p = { Hibernate.INTEGER, Hibernate.STRING };
        return (FeedSubscriber) (getHibernateTemplate()
                .find(
                        "from FeedSubscriber f where f.channel.id = ? and f.user.username like ?",
                        o, p).get(0));

    }

    /**
     * Counts number of Channels a User has subscribed in total.
     * 
     * @param username
     * @return
     */
    public int countFeedSubscriberByUser(String username) {
        return ((Integer) getHibernateTemplate()
                .find(
                        "select count (*) from FeedSubscriber f where f.user.username like ?",
                        username, Hibernate.STRING).get(0)).intValue();
    }

    /**
     * Counts number of subscribes on a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public int countFeedSubscriberByChannelURL(String locationURL) {
        return ((Integer) getHibernateTemplate()
                .find(
                        "select count (*) from FeedSubscriber f where f.channel.locationString like ?",
                        locationURL, Hibernate.STRING).get(0)).intValue();
    }

    /**
     * Counts all subscribed Channels of a User in a Category.
     * 
     * @param username
     *            name=key of a User
     * @param category_id
     *            id of a Category
     * @return
     */
    public int countFeedSubscriberByUserWithCategory(String username,
            int category_id) {
        Object[] o = { username, new Integer(category_id) };

        return ((Integer) getHibernateTemplate()
                .find(
                        "select count (*) from FeedSubscriber f where f.user.username like ? and f.category.id = ?",
                        o).get(0)).intValue();
    }

    /**
     * returns the latest=numberHottest Channel-Items for a given User.
     * 
     * @author Roland Vecera
     * @version created on 29.03.2005 09:41:30
     *  
     */

    /**
     * TODO: these are the ugliest methods around. Don't want it like that, but
     * it was only way I found - for some reason java.util.Iterator did not
     * work. So I have to use Arraylist. - Every found Channel is used in the
     * second Query with OR --> pfui - limit is hardcoded in SQL-Query. Didn't
     * found a way to make it via Hibernate
     */

    public Collection getHottestItemsForUser(String username, int numberHottest) {

        ArrayList c = (ArrayList) this.getFeedSubscriberByUser(username);
        logger.info("channels found " + c.size());
        Object[] fs = c.toArray();

        String query = " from Item i where";
        for (int i = 0; i < fs.length; i++) {
            logger.info("Channel: "
                    + ((FeedSubscriber) fs[i]).getChannel().getId());
            query = query + " i.channel = "
                    + ((FeedSubscriber) fs[i]).getChannel().getId();
            if (i < fs.length - 1)
                query = query + " or";
        }
        query = query + " order by i.found DESC limit " + numberHottest;
        logger.info(query);

        return (Collection) getHibernateTemplate().find(query);

        // I want it somehow like this: --> but i get an exception on this..
        //            ((Collection) getHibernateTemplate().find(
        //         "select f.channel.items from FeedSubscriber f where f.user.username
        // like ?",username,Hibernate.STRING));
    }

    /**
     * Returns all Channel-Items for a given User that where found since
     * java.util.Date since.
     * 
     * @author Roland Vecera
     * @param username
     * @param since
     * @return
     */

    public Collection getItemsForUserSince(String username, java.util.Date since) {

        ArrayList c = (ArrayList) this.getFeedSubscriberByUser(username);
        logger.info("channels found " + c.size());
        Object[] fs = c.toArray();

        String query = " from Item i where i.found >= ?  and (";
        for (int i = 0; i < fs.length; i++) {
            logger.info("Channel: "
                    + ((FeedSubscriber) fs[i]).getChannel().getId());
            query = query + " i.channel = "
                    + ((FeedSubscriber) fs[i]).getChannel().getId();
            if (i < fs.length - 1)
                query = query + " or";
        }
        query = query + ") order by i.found DESC";
        logger.info(query);

        //set since in query!
        return (Collection) getHibernateTemplate().find(query, since,
                Hibernate.DATE);

        // I want it somehow like this: --> but i get an exception on this..
        //            ((Collection) getHibernateTemplate().find(
        //         "select f.channel.items from FeedSubscriber f where f.user.username
        // like ?",username,Hibernate.STRING));
    }

    /**
     * Returns a number=numberHottest of Items for a given User and a given Channel.
     * 
     * Returns null if user not subscribed on Channel.
     * 
     * @param username
     * @param numberHottest
     * @param channel_id
     * @return
     */
    public Collection getHottestItemsForUserByChannel(String username,
            int numberHottest, int channel_id) {
        FeedSubscriber f = null;
        try {
            f = this.getFeedSubscriberForUserOnChannel(username, channel_id);
        } catch (IndexOutOfBoundsException e) {
            logger.warn("no FeedSubscribe found for User " + username
                    + " on Channel " + channel_id);
            return null;
        }

        String query = " from Item i where" + " i.channel = ? "
                + " order by i.found DESC limit " + numberHottest;
        logger.info(query);

        return (Collection) getHibernateTemplate().find(query,
                new Long(f.getChannel().getIntId()), Hibernate.INTEGER);
    }

    /**
     * Returns a number=numberhottest Items for a given User in his/her given
     * Category.
     * 
     * @param username
     * @param category_id
     * @param numberHottest
     * @return
     */
    public Collection getHottestItemsForUserByCategory(String username,
            int category_id, int numberHottest) {
        ArrayList c = (ArrayList) this.getFeedSubscriberForUserByCategory(
                username, category_id);
        logger.info("channels found " + c.size());
        Object[] fs = c.toArray();

        String query = " from Item i  (";
        for (int i = 0; i < fs.length; i++) {
            logger.info("Channel: "
                    + ((FeedSubscriber) fs[i]).getChannel().getId());
            query = query + " i.channel = "
                    + ((FeedSubscriber) fs[i]).getChannel().getId();
            if (i < fs.length - 1)
                query = query + " or";
        }
        query = query + ") order by i.found DESC limit " + numberHottest;
        logger.info(query);

        return (Collection) getHibernateTemplate().find(query);

    }

    /**
     * Returns Items for a given User in a subscribed Channel found at/after Date since.
     * 
     * Returns null if user not subscribed on Channel.
     * 
     * @param username
     * @param channel_id
     * @param date
     * @return
     */
    public Collection getItemsForUserOnChannelSince(String username,
            int channel_id, java.util.Date since) {
        FeedSubscriber f = null;
        try {
            f = this.getFeedSubscriberForUserOnChannel(username, channel_id);
        } catch (IndexOutOfBoundsException e) {
            logger.warn("no FeedSubscribe found for User " + username
                    + " on Channel " + channel_id);
            return null;
        }
        String query = " from Item i where i.found >= ?  and ("
                + " i.channel = ? " + " order by i.found DESC";
        logger.info(query);

        Object[] o = { since, new Integer(channel_id) };
        net.sf.hibernate.type.Type[] p = { Hibernate.DATE, Hibernate.INTEGER };

        return (Collection) getHibernateTemplate().find(query, o, p);

    }

    /**
     * Returns Items for a given User in given Category found at/after Date
     * since.
     * 
     * @param username
     * @param category_id
     * @param since
     * @return
     */
    public Collection getItemsForUserByCategorySince(String username,
            int category_id, java.util.Date since) {
        ArrayList c = (ArrayList) this.getFeedSubscriberForUserByCategory(
                username, category_id);
        logger.info("channels found " + c.size());
        Object[] fs = c.toArray();

        String query = " from Item i where i.found >= ?  and (";
        for (int i = 0; i < fs.length; i++) {
            logger.info("Channel: "
                    + ((FeedSubscriber) fs[i]).getChannel().getId());
            query = query + " i.channel = "
                    + ((FeedSubscriber) fs[i]).getChannel().getId();
            if (i < fs.length - 1)
                query = query + " or";
        }
        query = query + ") order by i.found DESC";
        logger.info(query);

        //set since in query!
        return (Collection) getHibernateTemplate().find(query, since,
                Hibernate.DATE);

    }

}