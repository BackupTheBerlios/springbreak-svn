/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.Collection;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.FeedSubscriberDAO;
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
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF saveFeedSubscriber(FeedSubscriberIF feedsubscriber) {
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

}