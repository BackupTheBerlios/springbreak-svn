/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.model;



import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.Category;

/**
 * User abo on a Channel.
 * 
 * @hibernate.class
 * table="FEEDSUBSCRIBERS"
 * 
 * 
 * @author Roland Vecera
 * @version
 * created on 28.03.2005 16:00:37
 *
 * TODO: über equals, hashcode nachdenken
 */
public class FeedSubscriber extends BaseObject implements FeedSubscriberIF{
    
    private int id;
    
    private User user;
    private Channel channel;
    private Category category;
    
    private java.util.Date addedDate;
    
    public FeedSubscriber()
    {}
    
    
    
    

    /**
   * @hibernate.id
   *  column="FEEDSUBSCRIBER_ID"
   *  generator-class="native"
   *  type="int"
   *  unsaved-value="-1"
   *
   * @return integer representation of identity.
   */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @hibernate.property
     * column="ADDEDAT"
     *
     * @return Returns the addedAt.
     */
    public java.util.Date getAddedDate() {
        return addedDate;
    }
    /**
     * @param addedAt The addedAt to set.
     */
    public void setAddedDate(java.util.Date addedDate) {
        this.addedDate = addedDate;
    }
    /**
     * @hibernate.many-to-one
     *   name="category"
     *   column="CATEGORY_ID"
     *   class="at.newsagg.model.Category"
     *   not-null="true"/>
     * @return Returns the category.
     */
    public Category getCategory() {
        return category;
    }
    /**
     * @param category The category to set.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    /**
     * @hibernate.many-to-one
     *   name="channel"
     *   column="CHANNEL_ID"
     *   class="at.newsagg.model.parser.hibernate.Channel"
     *   not-null="true"/>
     * @return Returns the channel.
     */
    public Channel getChannel() {
        return channel;
    }
    /**
     * @param channel The channel to set.
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    /**
     * @hibernate.many-to-one
     *   name="user"
     *   column="username"
     *   class="at.newsagg.model.User"
     *   not-null="true"/>
     *
     * @return subscribed User.
     */
    public User getUser() {
        return user;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
   
    
}
