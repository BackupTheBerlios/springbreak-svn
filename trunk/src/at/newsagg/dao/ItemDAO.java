/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import net.sf.hibernate.Hibernate;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.Item;

/**
 * @author king
 * @version
 * created on 26.03.2005 11:14:09
 *
 */
public interface ItemDAO {
    /**
     * Returns count of persistent Items with given url.
     * 
     * Should return 0 or 1, because the URL should be the natural key to item.
     *  
     * @param url url-value of an FeedItem.
     * @return
     */
    public int countItemwithURL(String url);
    
    /**
     * Get Item by id.
     * @param id
     * @return
     */
    public Item getItem(int id);
    
    /**
     * Get Item by Link.
     * @param id
     * @return
     */
    public Item getItemByLink(String link);
    /**
     * Returns the Id of an Item with the given url.
     *  
     * @param url url-value of an FeedItem.
     * @return
     */
    public long getIDfromItemwithURL (String url);
}