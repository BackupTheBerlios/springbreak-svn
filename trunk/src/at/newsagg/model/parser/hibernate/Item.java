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


// $Id: Item.java,v 1.19 2004/09/02 09:07:56 spyromus Exp $

package at.newsagg.model.parser.hibernate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;

import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.*;
import at.newsagg.utils.XmlPathUtils;

//Roland Vecera
//11.02.2005
//extends BaseObject now
//removed properties: categories, enclosure, source,


/**
 * Hibernate implementation of the ItemIF interface.
 *
 * @author Niko Schmuck (niko@nava.de)
 *
 * @hibernate.class
 *  table="ITEMS"
 */

public class Item  extends BaseObject implements ItemIF, java.io.Serializable {

  private static final Log LOG = LogFactory.getLog(Item.class);

  private long id = -1;
  private String title;
  private String description;
  private String link;
  private String creator;
  private String subject;
  private Date date;
  private Date found;
  private ItemGuidIF guid;
  private URL comments;
  private ChannelIF channel;
  private Element itemElement;

  
  public Item ()
  {
      }

  public Item(String title, String description, URL link) {
    this(null, null, title, description, link);
  }

  public Item(ChannelIF channel, String title, String description, URL link) {
    this(null, channel, title, description, link);
  }

  public Item(Element itemElement, String title, String description, URL link) {
    this(itemElement, null, title, description, link);
  }

  public Item(Element itemElement, ChannelIF channel, String title, String description, URL link) {
    this.itemElement = itemElement;
    this.channel = channel;
    this.title = title;
    this.description = description;
    this.link = link.toExternalForm();

  }

  // --------------------------------------------------------------
  // implementation of ItemIF interface
  // --------------------------------------------------------------

  /**
   * @hibernate.id
   *  column="ITEM_ID"
   *  generator-class="native"
   *  type="long"
   *  unsaved-value="-1"
   *
   * @return integer representation of identity.
   */
  public long getId() {
    return id;
  }

  public void setId(long longid) {
    this.id = longid;
  }

  /**
   * @hibernate.many-to-one
   *  column="CHANNEL_ID"
   *  class="at.newsagg.model.parser.hibernate.Channel"
   *  not-null="true"
   *
   * @return parent channel.
   */
  public ChannelIF getChannel() {
    return channel;
  }

  public void setChannel(ChannelIF parentChannel) {
    this.channel = parentChannel;
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



  /**
   * @hibernate.property
   *  column="LINK"
   *  type="text"
   *
   * @return link to original article.
   */
  public String getLink() {
      return link;
    }

    public void setLink(String aLink) {
      this.link = aLink;
    }
  
  
  
  public URL getLinkURL() throws MalformedURLException{
    return new URL(link);
  }

  public void setLinkURL(URL aLink) {
    this.link = aLink.toExternalForm();
  }

 

  /**
   * @hibernate.property
   *  column="CREATOR"
   *  type="text"
   *
   * @return creator.
   */
  public String getCreator() {
    return creator;
  }

  public void setCreator(String aCreator) {
    this.creator = aCreator;
  }

  /**
   * @hibernate.property
   *  column="SUBJECT"
   *  type="text"
   *
   * @return subject.
   */
  public String getSubject() {
    return subject;
  }

  public void setSubject(String aSubject) {
    this.subject = aSubject;
  }

  /**
   * @hibernate.property
   *  column="DATE"
   *
   * @return date.
   */
  public Date getDate() {
    return date;
  }

  public void setDate(Date aDate) {
    this.date = aDate;
  }

  /**
   * @hibernate.property
   *  column="FOUND"
   *
   * @return date when item was found.
   */
  public Date getFound() {
    return found;
  }

  public void setFound(Date foundDate) {
    this.found = foundDate;
  }

  /**
   * @hibernate.many-to-one
   *  class="at.newsagg.model.parser.hibernate.ItemGuid"
   *  column="GUID"
   *
   * @return guid.
   */
  public ItemGuidIF getGuid() {
    return guid;
  }

  public void setGuid(ItemGuidIF guid) {
    this.guid = guid;
  }

  /**
   * @hibernate.property
   *  column="COMMENTS"
   *
   * @return comments.
   */
  public URL getComments() {
    return comments;
  }

  public void setComments(URL comments) {
    this.comments = comments;
  }



  

  public String getElementValue(final String path) {
    return XmlPathUtils.getElementValue(itemElement, path);
  }

  public String[] getElementValues(final String path, final String[] elements) {
    return XmlPathUtils.getElementValues(itemElement, path, elements);
  }

  public String getAttributeValue(final String path, final String attribute) {
    return XmlPathUtils.getAttributeValue(itemElement, path, attribute);
  }

  public String[] getAttributeValues(final String path, final String[] attributes) {
    return XmlPathUtils.getAttributeValues(itemElement, path, attributes);
  }
  
//overwriting equals and hashCode!
  //2 Channels are equal if they have the same url
  
  public boolean equals(Object other) {
      if (this==other) return true;
      if ( !(other instanceof Item) ) return false;
      final Item that = (Item) other;
      return this.getLink().toLowerCase().equals( that.getLink().toLowerCase() );
      }
  
  
  
  public int hashCode() {
      return this.getLink().toLowerCase().hashCode();
      
      }


}
