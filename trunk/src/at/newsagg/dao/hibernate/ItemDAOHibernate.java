/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.ItemDAO;

/**
 * Hibernate DAO for Item.
 * 
 * @author Roland Vecera
 * @version
 * created on 26.03.2005 11:00:05
 *
 */
public class ItemDAOHibernate extends HibernateDaoSupport implements ItemDAO{
    private Log log = LogFactory.getLog(ItemDAOHibernate.class);
    
    /**
     * Returns count of persistent Items with given url.
     * 
     * Should return 0 or 1, because the URL should be the natural key to item.
     *  
     * @param url url-value of an FeedItem.
     * @return
     */
    public int countItemwithURL (String url)
    {
        log.debug(url);
        return ((Integer)getHibernateTemplate().find("select count (*) from Item i where i.link like ?",url,Hibernate.STRING).get(0)).intValue();  
    }
    /**
     * Returns the Id of an Item with the given url.
     * 
     * returns -1 if no Item was found.
     * 
     * @param url
     * @return
     */
    public long getIDfromItemwithURL (String url)  
    {
        try {
            return ((Long)getHibernateTemplate().find("select i.id from Item i where i.link like ?",url,Hibernate.STRING).get(0)).longValue();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        } 
    }

}
