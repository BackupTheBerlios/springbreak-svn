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


// $Id: FeedParser.java,v 1.6 2004/10/18 20:14:19 niko_schmuck Exp $

package at.newsagg.parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import at.newsagg.model.parser.*;


//import de.nava.informa.utils.NoOpEntityResolver;

/**
 * Parser class which allows reading in of RSS news channels.
 * The concrete rules how the XML elements map to our channel object model
 * are delegated to version specific private classes.</p>
 *
 * Currently the FeedParser support RSS formats 0.9x, 1.0 (RDF) and 2.0.
 *
 * Modified for use in Springbreak. 
 * 
 *
 * @author Niko Schmuck
 * @author Roland Vecera
 */
public class FeedParser {

  private static Log logger = LogFactory.getLog(FeedParser.class);
  private RSS_0_91_ParserIF rss091;
  private RSS_1_0_ParserIF rss10;
  private RSS_2_0_ParserIF rss20;
  private Atom_0_3_ParserIF atom03;
  

/**
 * @return Returns the atom3.
 */
public Atom_0_3_ParserIF getAtom03() {
    return atom03;
}
/**
 * @param atom3 The atom3 to set.
 */
public void setAtom03(Atom_0_3_ParserIF atom03) {
    this.atom03 = atom03;
}
/**
 * @return Returns the rss091.
 */
public RSS_0_91_ParserIF getRss091() {
    return rss091;
}
/**
 * @param rss091 The rss091 to set.
 */
public void setRss091(RSS_0_91_ParserIF rss091) {
    this.rss091 = rss091;
}
/**
 * @return Returns the rss10.
 */
public RSS_1_0_ParserIF getRss10() {
    return rss10;
}
/**
 * @param rss10 The rss10 to set.
 */
public void setRss10(RSS_1_0_ParserIF rss10) {
    this.rss10 = rss10;
}
/**
 * @return Returns the rss20.
 */
public RSS_2_0_ParserIF getRss20() {
    return rss20;
}
/**
 * @param rss20 The rss20 to set.
 */
public void setRss20(RSS_2_0_ParserIF rss20) {
    this.rss20 = rss20;
}

//Ende Getter/Setter
//----------------------------------------------------------------------
//Parser Helpers
  public ChannelIF parse(ChannelBuilderIF cBuilder, URL aURL)
                throws IOException,ParseException {
    return parse(cBuilder, new InputSource(aURL.toExternalForm()), aURL);
  }

  public  ChannelIF parse(ChannelBuilderIF cBuilder, String url)
                throws IOException, ParseException {
    URL aURL = null;
    try {
      aURL = new URL(url);
    } catch (java.net.MalformedURLException e) {
      logger.warn("Could not create URL for " + url);
    }
    return parse(cBuilder, new InputSource(url), aURL);
  }


  public  ChannelIF parse(ChannelBuilderIF cBuilder, File aFile)
                throws IOException, ParseException {
    URL aURL = null;
    try {
      aURL = aFile.toURL();
    } catch (java.net.MalformedURLException e) {
      throw new IOException("File " + aFile + " had invalid URL " +
                            "representation.");
    }
    return parse(cBuilder, new InputSource(aURL.toExternalForm()), aURL);
  }

  public  ChannelIF parse(ChannelBuilderIF cBuilder,
                                InputSource inpSource,
                                URL baseLocation)
                throws IOException, ParseException {
    // document reading without validation
    SAXBuilder saxBuilder = new SAXBuilder(false);
    // turn off DTD loading
    // saxBuilder.setEntityResolver(new NoOpEntityResolver());
    try {
      Document doc = saxBuilder.build(inpSource);
      ChannelIF channel = parse(cBuilder, doc);
      channel.setLocation(baseLocation);
      return channel;
    } catch (JDOMException e) {
      throw new ParseException(e);
    }
  }

  // ------------------------------------------------------------
  
  //
  // Eigentliche Methode hier
  // entscheidet welche RSS Variante das Document doc enthält
  // entsprechend werden parser aufgerufen
  // ------------------------------------------------------------

  private  synchronized ChannelIF parse(ChannelBuilderIF cBuilder,
                                              Document doc)
                              throws ParseException {

    if (cBuilder == null) {
      throw new RuntimeException("Without builder no channel can " +
                                 "be created.");
    }
    logger.debug("start parsing.");
    // Get the root element (must be rss)
    Element root = doc.getRootElement();
    String rootElement = root.getName().toLowerCase();
    // Decide which parser to use
    if (rootElement.startsWith("rss")) {
      String rssVersion = root.getAttribute("version").getValue();
      if (rssVersion.indexOf("0.91") >= 0) {
        logger.info("Channel uses RSS root element (Version 0.91).");
        return rss091.parse(cBuilder, root);
      } else if (rssVersion.indexOf("0.92") >= 0) {
        logger.info("Channel uses RSS root element (Version 0.92).");
        // logger.warn("RSS 0.92 not fully supported yet, fall back to 0.91.");
        // TODO: support RSS 0.92 when aware of all subtle differences.
        return rss091.parse(cBuilder, root);
      } else if (rootElement.indexOf("0.93") >= 0) {
        logger.info("Channel uses RSS root element (Version 0.93).");
        logger.warn("RSS 0.93 not fully supported yet, fall back to 0.91.");
        // TODO: support RSS 0.93 when aware of all subtle differences.
      } else if (rootElement.indexOf("0.94") >= 0) {
        logger.info("Channel uses RSS root element (Version 0.94).");
        logger.warn("RSS 0.94 not fully supported yet, will use RSS 2.0");
        // TODO: support RSS 0.94 when aware of all subtle differences.
        return rss20.parse(cBuilder, root);
      } else if (rssVersion.indexOf("2.0") >= 0 || rssVersion.equals("2")) {
        logger.info("Channel uses RSS root element (Version 2.0).");
        return rss20.parse(cBuilder, root);
      } else {
        throw new UnsupportedFormatException("Unsupported RSS version [" +
                                             rssVersion + "].");
      }
    } else if (rootElement.indexOf("rdf") >= 0) {
      return rss10.parse(cBuilder, root);
    } else if (rootElement.indexOf("feed") >= 0) {
      String feedVersion = root.getAttribute("version").getValue();
      if (feedVersion.indexOf("0.1") >= 0 || feedVersion.indexOf("0.2") >= 0) {
        logger.info("Channel uses feed root element (Version " + feedVersion + ").");
        logger.warn("This atom version is not really supported yet, assume Atom 0.3 format");
        return atom03.parse(cBuilder, root);
      } else if (feedVersion.indexOf("0.3") >= 0) {
        logger.info("Channel uses feed root element (Version 0.3).");
        return atom03.parse(cBuilder, root);
      } else {
        throw new UnsupportedFormatException("Unsupported feed version [" +
                                             feedVersion + "].");
      }
    } 

    // did not match anything
    throw new UnsupportedFormatException("Unsupported root element [" +
                                         rootElement + "].");
  }

  
  // ==========================================================
}
