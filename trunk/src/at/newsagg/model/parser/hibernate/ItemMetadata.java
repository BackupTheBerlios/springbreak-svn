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


// $Id: ItemMetadata.java,v 1.3 2003/10/15 10:04:19 niko_schmuck Exp $

package at.newsagg.model.parser.hibernate;


import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.*;
/**
 * Hibernate implementation of the ItemMetadataIF interface.
 * 
 * @author Niko Schmuck (niko@nava.de) 
 * 
 * @hibernate.class 
 *  table="ITEM_METADATA"
 */
public class ItemMetadata  extends BaseObject implements ItemMetadataIF, java.io.Serializable {

  private int id;
  private ItemIF item;
  private boolean markedRead;
  private int score;

  public ItemMetadata() {
    
  }

  
  //TODO: wegdamit, nur default konstruktor! Roland, 11.02.2005
  /**
   * Default constructor which sets this metadata to unread and to
   * the default score (see
   * {@link de.nava.informa.core.ItemMetadataIF#DEFAULT_SCORE}).
   */
  public ItemMetadata(ItemIF item) {
    this.item = item;
    this.markedRead = false;
    this.score = DEFAULT_SCORE;
  }
  
  /**
   * @hibernate.id
   *  column="ITEM_METADATA_ID"
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
  // implementation of ItemMetadataIF interface
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
   *  column="MARKED_READ"
   */
  public boolean isMarkedRead() {
    return markedRead;
  }

  public void setMarkedRead(boolean markedRead) {
    this.markedRead = markedRead;
  }
 
  /**
   * @hibernate.property
   *  column="SCORE"
   */
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
  
}
