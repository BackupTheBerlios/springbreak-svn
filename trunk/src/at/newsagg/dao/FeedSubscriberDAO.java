/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;

import at.newsagg.model.FeedSubscriberIF;

/**
 * @author king
 * @version
 * created on 28.03.2005 20:57:54
 *
 */
public interface FeedSubscriberDAO {
    /**
     * Save a new FeedSubscriber.
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF saveFeedSubscriber(FeedSubscriberIF feedsubscriber);

    /**
     * Update a FeedSubscriber
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF updateFeedSubscriber(FeedSubscriberIF feedsubscriber);

    /**
     * Returns all FeedSubscriber to a given User.
     * 
     * @param username
     * @return
     */
    public Collection getFeedSubscriberByUser(String username);

    /**
     * Returns all FeedSubscriber to a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public Collection getFeedSubscriberByChannelURL(String locationURL);

    /**
     * Returns all FeedSubcriber to a given Category.
     * 
     * @param category_id
     * @return
     */
    public Collection getFeedSubscriberByCategory(int category_id);

    /**
     * Counts number of Channels a User has subscribed in total.
     * 
     * @param username
     * @return
     */
    public int countFeedSubscriberByUser(String username);

    /**
     * Counts number of subscribes on a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public int countFeedSubscriberByChannelURL(String locationURL);

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
            int category_id);
}