//
// Informa -- RSS Library for Java
// Copyright (c) 2002 by Niko Schmuck
//
// Niko Schmuck
// http://sourceforge.net/projects/informa
// mailto:niko_schmuck@users.sourceforge.net
//
// This library is free software.
//
// You may redistribute it and/or modify it under the terms of the GNU
// Lesser General Public License as published by the Free Software Foundation.
//
// Version 2.1 of the license should be included with this distribution in
// the file LICENSE. If the license is not included with this distribution,
// you may find a copy at the FSF web site at 'www.gnu.org' or 'www.fsf.org',
// or you may write to the Free Software Foundation, 675 Mass Ave, Cambridge,
// MA 02139 USA.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied waranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//

// $Id: ChannelBuilder.java,v 1.25 2004/07/10 09:11:06 spyromus Exp $

package at.newsagg.model.parser.hibernate;

import at.newsagg.model.parser.*;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;


/**
 * Factory for the creation of the channel object model with the hibernate
 * persistent store.
 * 
 * @author Niko Schmuck (niko@nava.de)
 */
public class ChannelBuilder implements ChannelBuilderIF {
    //Roland Vecera
    //11.02.2005
    // deleted everything that had to do with hibernate-transactionmngment
    // big REFACTORING!!! Attention wheter this is good or not!
    // ATTENTION: can this CLASS BE a SINGLETON? RATHER NOT! Channel is unique
    // to every Channelbuilder
    //TODO: Kommentare neu!

    private static Log logger = LogFactory.getLog(ChannelBuilder.class);

    private ChannelIF channel;

    //TODO: schauen, ob der ChannelBuilder irgendwas HIbernatespezifisches
    // braucht
    public ChannelBuilder() {

    }


    // --------------------------------------------------------------
    // implementation of ChannelBuilderIF interface
    // --------------------------------------------------------------

    public void init(Properties props) throws ChannelBuilderException {
        logger
                .debug("initialising channel builder for hibernate backend; nothing happens with given Properties");
    }
//TODO: schauen ob ich die 2 createChannelmethoden so möchte
    public ChannelIF createChannel(String title) {
        setChannel(new Channel());
        this.channel.setTitle(title);

        return this.channel;
    }

    public ChannelIF createChannel(Element channelElement, String title) {
        this.channel=  new Channel(channelElement);
        this.channel.setTitle(title);

        return this.channel;
    }

    //TODO: hier die übergabe des ChannelIF channel ist gaga
    // sollte weg!

    public ItemIF createItem(ChannelIF channel, String title,
            String description, URL link) {
        return createItem(null, channel, title, description, link);
    }

    public ItemIF createItem(Element itemElement, ChannelIF channel,
            String title, String description, URL link) {
        ItemIF obj = new at.newsagg.model.parser.hibernate.Item(itemElement, channel, title, description, link);
        //TODO: übergebenes channel-object wird nur hergenommen, wenn es dem
        // hiesigen entspricht
        //TODO: methode refactoren, und channel ganz raus!
        if (channel.equals(this.channel)) {
            this.channel.addItem(obj);
        }
        return obj;
    }

    public ItemIF createItem(ChannelIF channel, ItemIF item) {
        if (channel.equals(this.channel)) {
            this.channel.addItem(item);
        }
        return item;
    }

    public ImageIF createImage(String title, URL location, URL link) {
        ImageIF obj = new at.newsagg.model.parser.hibernate.Image(title, location, link);
        this.channel.setImage(obj);
        return obj;
    }

    public ItemGuidIF createItemGuid(ItemIF item, String location,
            boolean permaLink) {
        return new ItemGuid(item, location, permaLink);
    }

    public void close() throws ChannelBuilderException {
        logger.debug("closing channel builder for hibernate backend");
    }

    /**
     * @return Returns the channel.
     */
    public ChannelIF getChannel() {
        return channel;
    }

    /**
     * @param channel
     *            The channel to set.
     */
    public void setChannel(ChannelIF chnl) {
        this.channel = chnl;
    }
}