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
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//


// $Id: Channel.java,v 1.30 2004/09/02 09:07:56 spyromus Exp $

package at.newsagg.model.parser.hibernate;
/*
 * Roland Vecera
 * 11.02.2005
 * 
 * extends BaseObject um Hibernate equals, hashCode zu erfüllen
 * removed properties Source, Enclosure, TextInput, everything with category
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.*;

import org.jdom.Element;

import at.newsagg.utils.XmlPathUtils;

/**
 * Hibernate implementation of the ChannelIF interface.
 *
 * @author Niko Schmuck (niko@nava.de)
 *
 * @hibernate.class
 *  table="CHANNELS"
 */
public class Channel  extends BaseObject implements at.newsagg.model.parser.ChannelIF, java.io.Serializable {

  private int id = -1;
  private String title;
  private String description;
  private URL location;
  private URL site;
  private String creator;
  private String publisher;
  private String language;
  private at.newsagg.model.parser.ChannelFormat format;
  private Collection items;
  private Set groups;

  private at.newsagg.model.parser.ImageIF image;

  private String copyright;
  private Collection categories;
  private Date lastUpdated;
  private Date lastBuild;
  private Date pubDate;

  private String rating;
  private String generator;
  private String docs;
  private int ttl = -1;
  private Element channelElement;

  // RSS 1.0 Syndication Module values
  private String updatePeriod = UPDATE_DAILY;
  private int updateFrequency = 1;
  private Date updateBase;

  private transient Collection observers;

  public Channel() {
    
  }

  //TODO: nur default Konstruktor; Roland 11.02.2005
  public Channel(String title) {
    this(null, title);
  }

  public Channel(Element channelElement) {
    this(channelElement, "Unnamed channel");
  }

  public Channel(Element channelElement, String title) {
    this.channelElement = channelElement;
    this.title = title;
    this.items = new ArrayList();
    this.categories = new ArrayList();
    this.observers = new ArrayList();
    this.groups = new HashSet();
    this.format = at.newsagg.model.parser.ChannelFormat.UNKNOWN_CHANNEL_FORMAT;
    this.lastUpdated = new Date();
  }

  // --------------------------------------------------------------
  // implementation of ChannelIF interface
  // --------------------------------------------------------------

  /**
   * @hibernate.id
   *  generator-class="native"
   *  column="CHANNEL_ID"
   *  type="integer"
   *  unsaved-value="-1"
   *
   * @return integer representation of identity.
   */
  public int getIntId() {
    return id;
  }

  public void setIntId(int anId) {
    this.id = anId;
  }

  public long getId() {
    return id;
  }

  public void setId(long longid) {
    this.id = (int) longid;
  }

  /**
   * @hibernate.property
   *  column="TITLE"
   *  not-null="true"
   *  type="text"
   *
   * @return title.
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String aTitle) {
    this.title = aTitle;
  }

  /**
   * @hibernate.property
   *  column="DESCRIPTION"
   *  type="text"
   *
   * @return description.
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String aDescription) {
    this.description = aDescription;
  }

// We store the Location as a text string in the database, but as a URL in the memory based object.
// As far as Hibernate is concerned this is a STRING property. However the getter and setter
// convert to and from text for Informa.

  /**
   * @hibernate.property
   *  column="LOCSTRING"
   *  type="string"
   *
   * @return location as a string.
   */
  public String getLocationString() {
    return (location == null) ? "" : location.toString();
  }

  public void setLocationString(String loc) throws MalformedURLException{
    if (loc == null || loc.trim().length() == 0) {
      location = null;
      return;
    } else {
     
        this.location = new URL(loc);
      
    }
  }

  public URL getLocation() {
    return location;
  }

  public void setLocation(URL location)  {
    this.location = location;
  }


  /**
   * @hibernate.property
   *  column="SITE"
   *
   * @return URL of the site.
   */
  public URL getSite() {
    return site;
  }

  public void setSite(URL siteUrl) {
    this.site = siteUrl;
  }

  /**
   * @hibernate.property
   *  column="CREATOR"
   *  type="text"
   * @return name of creator.
   */
  public String getCreator() {
    return creator;
  }

  public void setCreator(String aCreator) {
    this.creator = aCreator;
  }

  /**
   * @hibernate.property
   *  column="PUBLISHER"
   *  type="text"
   * @return publisher.
   */
  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String aPublisher) {
    this.publisher = aPublisher;
  }

  /**
   * @hibernate.property
   *  column="LANGUAGE"
   *
   * @return language of channel.
   */
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String aLanguage) {
    this.language = aLanguage;
  }

  /**
   * @hibernate.property
   *  column="FORMAT"
   *  type="string"
   *
   * @return format string.
   */
  public String getFormatString() {
    return format.toString();
  }

  public void setFormatString(String strFormat) {
    // TODO: this could be improved by a format resolver
    if (strFormat.equals(ChannelFormat.RSS_0_90.toString())) {
      format = ChannelFormat.RSS_0_90;
    } else if (strFormat.equals(ChannelFormat.RSS_0_91.toString())) {
      format = ChannelFormat.RSS_0_91;
    } else if (strFormat.equals(ChannelFormat.RSS_0_92.toString())) {
      format = ChannelFormat.RSS_0_92;
    } else if (strFormat.equals(ChannelFormat.RSS_0_93.toString())) {
      format = ChannelFormat.RSS_0_93;
    } else if (strFormat.equals(ChannelFormat.RSS_0_94.toString())) {
      format = ChannelFormat.RSS_0_94;
    } else if (strFormat.equals(ChannelFormat.RSS_1_0.toString())) {
      format = ChannelFormat.RSS_1_0;
    } else if (strFormat.equals(ChannelFormat.RSS_2_0.toString())) {
      format = ChannelFormat.RSS_2_0;
    }
  }

  public ChannelFormat getFormat() {
    return format;
  }

  public void setFormat(ChannelFormat aFormat) {
    this.format = aFormat;
  }


  /**
   * @hibernate.bag
   *  table="ITEMS"
   *  cascade="all"
   *  inverse="true"
   * @hibernate.collection-key
   *  column="CHANNEL_ID"
   * @hibernate.collection-one-to-many
   *  class="at.newsagg.model.parser.hibernate.Item"
   *
   * @return items of channel.
   */
  public Collection getItems() {
    return items;
  }

  public void setItems(Collection anItems) {
    this.items = anItems;
  }

  public void addItem(ItemIF item) {
    items.add(item);
    item.setChannel(this);

  }

  public void removeItem(ItemIF item) {
    items.remove(item);
  }

  public ItemIF getItem(long itemId) {
    // TODO: improve performance
    // hibernate query cannot be used (not possible: no session object)
    // may be use transient map: items.get(new Long(id));
    ItemIF theItem = null;
    Iterator it = items.iterator();
    while (it.hasNext()) {
      ItemIF curItem = (ItemIF) it.next();
      if (curItem.getId() == itemId) {
        theItem = curItem;
        break;
      }
    }
    return theItem;
  }

  /**
   * @hibernate.many-to-one
   *  column="IMAGE_ID"
   *  class="at.newsagg.model.parser.hibernate.Image"
   *  not-null="false"
   *
   * @return image.
   */
  public ImageIF getImage() {
    return image;
  }

  public void setImage(ImageIF anImage) {
    this.image = anImage;
  }

 

  /**
   * @hibernate.property
   *  column="COPYRIGHT"
   *
   * @return copyright note.
   */
  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String aCopyright) {
    this.copyright = aCopyright;
  }

  /**
   * @hibernate.property
   *  column="RATING"
   *
   * @return rating.
   */
  public String getRating() {
    return rating;
  }

  public void setRating(String aRating) {
    this.rating = aRating;
  }



  /**
   * @hibernate.property
   *  column="GENERATOR"
   *
   * @return generator.
   */
  public String getGenerator() {
    return generator;
  }

  public void setGenerator(String aGenerator) {
    this.generator = aGenerator;
  }

  /**
   * @hibernate.property
   *  column="DOCS"
   *
   * @return docs.
   */
  public String getDocs() {
    return docs;
  }

  public void setDocs(String aDocs) {
    this.docs = aDocs;
  }

  /**
   * @hibernate.property
   *  column="TTL"
   *
   * @return TTL value.
   */
  public int getTtl() {
    return ttl;
  }

  public void setTtl(int aTtl) {
    this.ttl = aTtl;
  }



  /**
   * @hibernate.property
   *  column="LAST_UPDATED"
   *
   * @return date of last update.
   */
  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date date) {
    this.lastUpdated = date;
   
  }

  /**
   * @hibernate.property
   *  column="LAST_BUILD_DATE"
   *
   * @return date of last builing.
   */
  public Date getLastBuildDate() {
    return lastBuild;
  }

  public void setLastBuildDate(Date date) {
    this.lastBuild = date;
  }

  /**
   * @hibernate.property
   *  column="PUB_DATE"
   *
   * @return publication date.
   */
  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date date) {
    this.pubDate = date;
  }

  // RSS 1.0 Syndication Module methods

  /**
   * @hibernate.property
   *  column="UPDATE_PERIOD"
   *
   * @return update period.
   */
  public String getUpdatePeriod() {
    return updatePeriod;
  }

  public void setUpdatePeriod(String anUpdatePeriod) {
    this.updatePeriod = anUpdatePeriod;
  }

  /**
   * @hibernate.property
   *  column="UPDATE_FREQUENCY"
   *
   * @return update frequency.
   */
  public int getUpdateFrequency() {
    return updateFrequency;
  }

  public void setUpdateFrequency(int anUpdateFrequency) {
    this.updateFrequency = anUpdateFrequency;
  }

  /**
   * @hibernate.property
   *  column="UPDATE_BASE"
   *
   * @return update base.
   */
  public Date getUpdateBase() {
    return updateBase;
  }

  public void setUpdateBase(Date date) {
    this.updateBase = date;
  }

  public String getElementValue(final String path) {
    return XmlPathUtils.getElementValue(channelElement, path);
  }

  public String[] getElementValues(final String path, final String[] elements) {
    return XmlPathUtils.getElementValues(channelElement, path, elements);
  }

  public String getAttributeValue(final String path, final String attribute) {
    return XmlPathUtils.getAttributeValue(channelElement, path, attribute);
  }

  public String[] getAttributeValues(final String path, final String[] attributes) {
    return XmlPathUtils.getAttributeValues(channelElement, path, attributes);
  }

  //overwriting equals and hashCode!
  //2 Channels are equal if they have the same url
  
  public boolean equals(Object other) {
      if (this==other) return true;
      if ( !(other instanceof Channel) ) return false;
      final Channel that = (Channel) other;
      return this.getLocationString().toLowerCase().equals( that.getLocationString().toLowerCase() );
      }
  
  
  
  public int hashCode() {
      return this.getLocationString().toLowerCase().hashCode();
      
      }

 


}
