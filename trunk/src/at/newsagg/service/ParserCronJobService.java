/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.service;

import java.util.TimerTask;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.parser.FeedParser;

/**
 * @author king
 * @version
 * created on 26.03.2005 14:37:18
 *
 */
public interface ParserCronJobService {
    /**
     * This methode resolves all Channels needed to be updated now, parses their
     * RSS Feed and saves/updates in DB.
     * 
     * This methode should be called by a timer. In every run it is updating the
     * neccessary Channels.
     *  
     */
    public void runUpdate();

    /**
     * @return Returns the channelDao.
     */
    public ChannelDAO getChannelDao();

    /**
     * @param channelDao The channelDao to set.
     */
    public void setChannelDao(ChannelDAO channelDao);

    /**
     * @return Returns the itemDao.
     */
    public ItemDAO getItemDao();

    /**
     * @param itemDao The itemDao to set.
     */
    public void setItemDao(ItemDAO itemDao);

    /**
     * @return Returns the parser.
     */
    public FeedParser getParser();

    /**
     * @param parser
     *            The parser to set.
     */
    public void setParser(FeedParser parser);
}