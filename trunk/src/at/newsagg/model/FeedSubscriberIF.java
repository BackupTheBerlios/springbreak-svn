/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.model;

import java.util.Date;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author king
 * @version
 * created on 28.03.2005 20:24:38
 *
 */
public interface FeedSubscriberIF {
    /**
     * @hibernate.id
     *  column="FEEDSUBSCRIBER_ID"
     *  generator-class="native"
     *  type="int"
     *  unsaved-value="-1"
     *
     * @return integer representation of identity.
     */
    public int getId();

    /**
     * @param id The id to set.
     */
    public void setId(int id);

    /**
     * @hibernate.property
     * column="ADDEDAT"
     *
     * @return Returns the addedAt.
     */
    public java.util.Date getAddedDate();

    /**
     * @param addedAt The addedAt to set.
     */
    public void setAddedDate(java.util.Date addedDate);

    /**
     * @hibernate.many-to-one
     *   name="category"
     *   column="CATEGORY_ID"
     *   class="at.newsagg.model.Category"
     *   not-null="true"/>
     * @return Returns the category.
     */
    public CategoryIF getCategory();

    /**
     * @param category The category to set.
     */
    public void setCategory(Category category);

    /**
     * @hibernate.many-to-one
     *   name="channel"
     *   column="CHANNEL_ID"
     *   class="at.newsagg.model.parser.hibernate.Channel"
     *   not-null="true"/>
     * @return Returns the channel.
     */
    public Channel getChannel();

    /**
     * @param channel The channel to set.
     */
    public void setChannel(Channel channel);

    /**
     * @hibernate.many-to-one
     *   name="user"
     *   column="username"
     *   class="at.newsagg.model.User"
     *   not-null="true"/>
     *
     * @return subscribed User.
     */
    public User getUser();

    /**
     * @param user The user to set.
     */
    public void setUser(User user);
}