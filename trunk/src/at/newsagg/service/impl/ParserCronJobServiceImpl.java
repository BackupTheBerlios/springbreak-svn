/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.service.impl;

import java.io.IOException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.parser.ChannelIF;
import at.newsagg.model.parser.ItemIF;
import at.newsagg.model.parser.ParseException;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.ChannelBuilder;
import at.newsagg.parser.FeedParser;
import at.newsagg.service.ParserCronJobService;

/**
 * Cronjob class to parse over all persistent Feed-Channels.
 * 
 * @author Roland Vecera
 * @version created on 26.03.2005 13:48:25
 *  
 */
public class ParserCronJobServiceImpl extends TimerTask implements ParserCronJobService {
    private static Log log = LogFactory.getLog(ParserCronJobServiceImpl.class);

    private ChannelDAO channelDao;

    private ItemDAO itemDao;

    private FeedParser parser;

    ParserCronJobServiceImpl() {
    }

    /**
     * executes runUpdate().
     * 
     */
    public void run ()
    {
        log.info ("start parserrun");
        System.out.println((new java.util.Date()).toLocaleString()  + "NEUER RUN ParserCronjob");
         this.runUpdate();
    }
    /**
     * This methode resolves all Channels needed to be updated now, parses their
     * RSS Feed and saves/updates in DB.
     * 
     * This methode should be called by a timer. In every run it is updating the
     * neccessary Channels.
     *  
     */
    public void runUpdate() {
       
        /**
         * TODO: implement a better algorithm to just take the needed channels
         */
        java.util.List channels = channelDao.getChannels();
        java.util.Iterator i = channels.iterator();

        ChannelBuilder cb = new ChannelBuilder();
        ChannelIF channelresult;

        while (i.hasNext()) {
            ChannelIF channel = (ChannelIF) i.next();
            cb.setChannel(channel);

            try {
                channelresult = (Channel) parser.parse(cb, channel
                        .getLocation());

                //set Id of the resulting channel to the given channelid;
                //so hibernate should know, that it has to update
                channelresult.setId(channel.getId());
                this.checkForNewItems(channelresult);
                //persist in DB
                channelDao.saveOrUpdateChannel((Channel)channelresult);

            } catch (IOException e) {

                log.error(e.fillInStackTrace());
            } catch (ParseException e) {

                log.error(e.fillInStackTrace());
            }

        }

    }

    //helper
    /**
     * Checks for paresed Items if they are already in Database, or not.
     * @author roland vecera
     * @return ChannelIF  ChannelIF with updated id-Attributes of its Items.
     **/
    private ChannelIF checkForNewItems(ChannelIF channelresult) {
        java.util.Iterator j;
        ItemIF itemnew;

        //      For every item check, wheter its new, or not!
        j = channelresult.getItems().iterator();
        while (j.hasNext()) {
            itemnew = (ItemIF) j.next();
            //if already persistet, it would return a value > 0
            if (itemDao.countItemwithURL(itemnew.getLink()) > 0) {
                //so set itemnew's id
                //signal hibernate, that it just needs to update
                itemnew.setId(itemDao.getIDfromItemwithURL(itemnew.getLink()));
            }
        }
        return channelresult;

    }

    //Getter and Setter
    /**
     * @return Returns the log.
     */
    public static Log getLog() {
        return log;
    }

    /**
     * @param log
     *            The log to set.
     */
    public static void setLog(Log log) {
        ParserCronJobServiceImpl.log = log;
    }

   

    /**
     * @return Returns the channelDao.
     */
    public ChannelDAO getChannelDao() {
        return channelDao;
    }
    /**
     * @param channelDao The channelDao to set.
     */
    public void setChannelDao(ChannelDAO channelDao) {
        this.channelDao = channelDao;
    }
    /**
     * @return Returns the itemDao.
     */
    public ItemDAO getItemDao() {
        return itemDao;
    }
    /**
     * @param itemDao The itemDao to set.
     */
    public void setItemDao(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }
    /**
     * @return Returns the parser.
     */
    public FeedParser getParser() {
        return parser;
    }

    /**
     * @param parser
     *            The parser to set.
     */
    public void setParser(FeedParser parser) {
        this.parser = parser;
    }
}