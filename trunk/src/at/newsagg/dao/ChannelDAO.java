/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.net.URL;
import java.util.List;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author king
 * @version
 * created on 26.03.2005 10:56:15
 *
 */
public interface ChannelDAO {
    /**
     * save a new channel.
     * 
     * @param channel
     */
    public abstract void saveChannel(Channel channel);
    /**
     * save or update a new channel.
     * 
     * @param channel
     */
    public void saveOrUpdateChannel(Channel channel);
    
    /**
     * update a persisted channel. updates a channel, thats already in db.
     * 
     * @param channel
     */
    public abstract void updateChannel(Channel channel);

    /**
     * Delete Channel Object.
     * 
     * @param channel
     */
    public abstract void removeChannel(Channel channel);

    /**
     * Get Channel by id.
     * 
     * @param id
     * @return
     */
    public abstract Channel getChannel(int id) throws IndexOutOfBoundsException;

    /**
     * Get a Channel by its URL.
     * 
     * @param id
     * @return
     */
    public abstract Channel getChannel(URL location) throws IndexOutOfBoundsException;

    /**
     * Get all Channels order by title.
     * 
     * @return
     */
    public abstract List getChannels();
    
    /**
     * Free Object from Hibernate Session cache.
     * @param o
     */
    public void freeObject (Object o);
}