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


// $Id: Image.java,v 1.4 2003/10/15 10:04:18 niko_schmuck Exp $

package at.newsagg.model.parser.hibernate;

import java.net.URL;

import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.ImageIF;

/**
 * Hibernate implementation of the ImageIF interface.
 *
 * @author Niko Schmuck (niko@nava.de)
 *
 * @hibernate.class
 *  table="IMAGES"
 */
public class Image  extends BaseObject implements ImageIF, java.io.Serializable {

  private int id;
  private String title;
  private String description;
  private URL location;
  private URL link;
  private int width;
  private int height;

  public Image() {
    
  }
//TODO: weg damit nur default konstuktor!
  
  public Image(String title, URL location, URL link) {
    this.title = title;
    this.location = location;
    this.link = link;
  }

  /**
   * @hibernate.id
   *  column="IMAGE_ID"
   *  generator-class="native"
   *  type="integer"
   */
  public int getIntId() {
    return id;
  }

  public void setIntId(int id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long longid) {
    this.id = (int) longid;
  }

  // --------------------------------------------------------------
  // implementation of ImageIF interface
  // --------------------------------------------------------------

  /**
   * @hibernate.property
   *  column="TITLE"
   *  not-null="true"
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @hibernate.property
   *  column="DESCRIPTION"
   */
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @hibernate.property
   *  column="LOCATION"
   */
  public URL getLocation() {
    return location;
  }

  public void setLocation(URL location) {
    this.location = location;
  }

  /**
   * @hibernate.property
   *  column="LINK"
   */
  public URL getLink() {
    return link;
  }

  public void setLink(URL link) {
    this.link = link;
  }

  /**
   * @hibernate.property
   *  column="WIDTH"
   */
  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * @hibernate.property
   *  column="HEIGHT"
   */
  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

}
