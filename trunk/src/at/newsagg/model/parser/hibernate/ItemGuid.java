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

// $Id: ItemGuid.java,v 1.3 2003/10/15 10:04:19 niko_schmuck Exp $

package at.newsagg.model.parser.hibernate;

import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.*;
/**
 * Hibernate implementation of the ItemGuidIF interface.
 *
 * @author Michael Harhen
 *
 * @hibernate.class
 *  table="ITEM_GUID"
 */
public class ItemGuid  extends BaseObject implements ItemGuidIF, java.io.Serializable {

  private int id;
  private ItemIF item;
  private String location;
  private boolean permaLink;

  public ItemGuid() {
   
  }
//TODO: weg damit, nur mehr default Konstrutor
//Roland 11.02.2005  
  
  /**
   * Default constructor.
   */
  public ItemGuid(ItemIF item) {
    this(item, null, true);
  }

  public ItemGuid(ItemIF item, String location, boolean permaLink) {
    this.item = item;
    this.location = location;
    this.permaLink = permaLink;
  }

  /**
   * @hibernate.id
   *  generator-class="native"
   *  column="ITEM_GUID_ID"
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
  // implementation of ItemGuidIF interface
  // --------------------------------------------------------------

  /**
   * @hibernate.many-to-one
   *  column="ITEM_ID"
   *  class="at.newsagg.model.parser.hibernate.Item"
   *  not-null="true"
   */
  public ItemIF getItem() {
    return item;
  }

  public void setItem(ItemIF item) {
    this.item = item;
  }

  /**
   * @hibernate.property
   *  column="LOCATION"
   */
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * @hibernate.property
   *  column="PERMA_LINK"
   */
  public boolean isPermaLink() {
    return permaLink;
  }

  public void setPermaLink(boolean permaLink) {
    this.permaLink = permaLink;
  }

}
