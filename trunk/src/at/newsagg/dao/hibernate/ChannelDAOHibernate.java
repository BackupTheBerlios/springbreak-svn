/*
 * Created on 20.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.net.URL;
import java.util.List;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.dao.ChannelDAO;
/**
 * Channel DAO.
 * 
 * @author Roland Vecera
 * @version created on 20.03.2005 19:57:39
 *  
 */
public class ChannelDAOHibernate extends HibernateDaoSupport implements ChannelDAO {
    private Log log = LogFactory.getLog(ChannelDAOHibernate.class);

    /**
     * save a new channel.
     * 
     * @param channel
     */
    public void saveChannel(Channel channel) {
        getHibernateTemplate().save(channel);

        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " stored!");
        }
    }
    
    public void saveOrUpdateChannel(Channel channel)
    {
        getHibernateTemplate().saveOrUpdateCopy(channel);
            
        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " stored!");
        } 
    }

    /**
     * update a persisted channel. updates a channel, thats already in db.
     * 
     * @param channel
     */
    public void updateChannel(Channel channel) {
        getHibernateTemplate().update(channel);

        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " updated!");
        }
    }

    /**
     * Delete Channel Object.
     * 
     * @param channel
     */
    public void removeChannel(Channel channel) {

        getHibernateTemplate().delete(channel);
    }

    /**
     * Get Channel by id.
     * 
     * @param id
     * @return
     */
    public Channel getChannel(int id) {
        return (Channel) getHibernateTemplate().load(Channel.class,
                new Integer(id));
        //return (Channel)getHibernateTemplate().find("from Channel u where
        // u.id = ?", new Integer(id)).get(0);

    }
    
    /**
     * Get a Channel by its URL.
     * 
     * 
     * 
     * @param id
     * @return
     * @throws IndexOutOfBoundsException if no Channel was found
     */
    public Channel getChannel(URL location) throws IndexOutOfBoundsException{
        
        return (Channel)getHibernateTemplate().find("from Channel u where u.locationString like ?",location.toString(),Hibernate.STRING).get(0);
        

    }
    

    /**
     * Get all Channels order by title.
     * 
     * @return
     */
    public List getChannels() {
        return getHibernateTemplate().find("from Channel order by title");
    }

    /**
     * Free Object from Hibernate Session cache.
     * @param o
     */
    public void freeObject (Object o)
    {
        getHibernateTemplate().evict(o);
    }
}