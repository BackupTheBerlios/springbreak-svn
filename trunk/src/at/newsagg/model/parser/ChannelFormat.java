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

// $Id: ChannelFormat.java,v 1.8 2004/05/13 22:55:16 niko_schmuck Exp $

package at.newsagg.model.parser;

import java.io.Serializable;

/**
 * Holds constants to describe which syntax is used by a channel
 * description.</p>
 *
 * @author Niko Schmuck (niko@nava.de)
 */
public class ChannelFormat implements Serializable {

  private String formatSpec;

  private ChannelFormat(String formatSpec) {
    this.formatSpec = formatSpec;
  }

  public String toString() {
    return formatSpec;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof ChannelFormat)) {
      return false;
    }
    ChannelFormat cf = (ChannelFormat) obj;
    return cf.formatSpec.equals(formatSpec);
  }

  public int hashCode() {
    return formatSpec.hashCode();
  }

  /** Convenient null value to make code more robust */
  public static final ChannelFormat UNKNOWN_CHANNEL_FORMAT =
          new ChannelFormat("Unknown");

  /** Syntax according to RSS 0.9 specification. */
  public static final ChannelFormat RSS_0_90 = new ChannelFormat("RSS 0.90");

  /** Syntax according to RSS 0.91 specification. */
  public static final ChannelFormat RSS_0_91 = new ChannelFormat("RSS 0.91");

  /** Syntax according to RSS 0.92 specification. */
  public static final ChannelFormat RSS_0_92 = new ChannelFormat("RSS 0.92");

  /** Syntax according to RSS 0.93 specification. */
  public static final ChannelFormat RSS_0_93 = new ChannelFormat("RSS 0.93");

  /** Syntax according to RSS 0.94 specification. */
  public static final ChannelFormat RSS_0_94 = new ChannelFormat("RSS 0.94");

  /** Syntax according to RSS 1.0 specification. */
  public static final ChannelFormat RSS_1_0 = new ChannelFormat("RSS 1.0");

  /** Syntax according to RSS 2.0 specification. */
  public static final ChannelFormat RSS_2_0 = new ChannelFormat("RSS 2.0");

  
  /** Syntax according to the Atom 0.1 specification. */
  public static final ChannelFormat ATOM_0_1 = new ChannelFormat("Atom 0.1");

  /** Syntax according to the Atom 0.2 specification. */
  public static final ChannelFormat ATOM_0_2 = new ChannelFormat("Atom 0.2");

  /** Syntax according to the Atom 0.3 specification. */
  public static final ChannelFormat ATOM_0_3 = new ChannelFormat("Atom 0.3");

}
