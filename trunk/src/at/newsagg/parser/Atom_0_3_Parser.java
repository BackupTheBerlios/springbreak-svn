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

// $Id: Atom_0_3_Parser.java,v 1.6 2004/07/29 12:23:22 pitosalas Exp $

//Roland Vecera
//11.02.2005
//Just added some comments

package at.newsagg.parser;

import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;

import at.newsagg.model.parser.*;
import at.newsagg.utils.ParserUtils;



/**
 * Parser which reads in document instances according to the Atom 0.3
 * specification and generates a news channel object. Currently the
 * support for the atom syntax is not complete.
 *
 * @author Niko Schmuck
 */


class Atom_0_3_Parser implements Atom_0_3_ParserIF {

  private static Log logger = LogFactory.getLog(Atom_0_3_Parser.class);

  public ChannelIF parse(ChannelBuilderIF cBuilder, Element channel)
    throws ParseException {
    if (cBuilder == null) {
      throw new RuntimeException(
        "Without builder no channel can " + "be created.");
    }
    Date dateParsed = new Date();
    Namespace defNS = ParserUtils.getDefaultNS(channel);
    if (defNS == null) {
      defNS = Namespace.NO_NAMESPACE;
      logger.info("No default namespace found.");
    }
    // RSS 1.0 Dublin Core Module namespace
    Namespace dcNS = ParserUtils.getNamespace(channel, "dc");
    if (dcNS == null) {
      logger.debug("No namespace for dublin core found");
      dcNS = defNS;
    }
    
    logger.debug("start parsing.");

    // get version attribute
    
    //TODO: formatVersion is never set somewhere, chnl.setFormat always to ChannelFormat.ATOM_0_3
    // see below!
    
    String formatVersion = "0.3";
    if (channel.getAttribute("version") != null) {
      formatVersion = channel.getAttribute("version").getValue().trim();
      logger.debug("Atom version " + formatVersion + " specified in document.");
    } else {
      logger.info("No format version specified, using default");
    }

    // --- read in channel information

    // title element
    ChannelIF chnl =
      cBuilder.createChannel(channel, channel.getChildTextTrim("title", defNS));
    // TODO: support attributes: type, mode

    chnl.setFormat(ChannelFormat.ATOM_0_3);

    // language
    String language = channel.getAttributeValue("lang",Namespace.XML_NAMESPACE);
    if (language != null) {
      chnl.setLanguage( language );
    }
        
    // description element
    if (channel.getChild("description") != null) {
      chnl.setDescription(channel.getChildTextTrim("description", defNS));
    } else {
      // fallback
      chnl.setDescription(channel.getChildTextTrim("tagline", defNS));
    }

    // ttl in dc namespace
    Element ttl = channel.getChild("ttl", dcNS);
    if (ttl != null) {
      String ttlString =  ttl.getTextTrim();
      if ( ttlString!= null) {
        chnl.setTtl( Integer.parseInt(ttlString));
      }
    }
    
    //  lastbuild element : modified ?
    Element modified = channel.getChild("modified", defNS);
    if (modified != null) {
      chnl.setPubDate(ParserUtils.getDate(modified.getTextTrim()));
    }
  
    // TODO : issued value
    /*
    if (modified != null) {
      modified = channel.getChild("issued", defNS);
      chnl.setLastBuildDate (ParserUtils.getDate(modified.getTextTrim()));
    }
    */
    
    // author element
    Element author = channel.getChild("author", defNS);
    if (author != null) {
      chnl.setCreator(author.getChildTextTrim("name", defNS));
    }

    // generator element
    Element generator = channel.getChild("generator", defNS);
    if (generator != null) {
      chnl.setGenerator(generator.getTextTrim());
    }

    // copyright element
    Element copyright = channel.getChild("copyright", defNS);
    if (copyright != null) {
      chnl.setCopyright(getValue(copyright));
    }
    
    // n link elements
    // TODO : type attribut of link (text, application...)
    List links = channel.getChildren("link", defNS);
    Iterator i = links.iterator();
    while (i.hasNext()) {
      Element linkElement = (Element) i.next();
      // use first 'alternate' link
      String rel = linkElement.getAttributeValue("rel");
      String href = linkElement.getAttributeValue("href");
      if (rel != null && href != null && rel.equals("alternate")) {
        URL linkURL = ParserUtils.getURL(href);
        chnl.setSite(linkURL);
        break;
      }
      
      // TODO: further extraction of link information
    }

    //***********************************
    //start <entry>, equals <item> in RSS!
    //***********************************
    // 1..n entry elements
    List items = channel.getChildren("entry", defNS);
    i = items.iterator();
    while (i.hasNext()) {
      Element item = (Element) i.next();

      // get title element
      // TODO : deal with type attribut
      Element elTitle = item.getChild("title", defNS);
      String strTitle = "<No Title>";
      if (elTitle != null) {
        strTitle = getValue( elTitle );
        logger.debug("Parsing title "+elTitle.getTextTrim()+"->"+strTitle);
      }
      if (logger.isDebugEnabled()) {
        logger.debug("Entry element found (" + strTitle + ").");
      }
      // TODO : deal with multiple links
      // get link element
      Element elLink = item.getChild("link", defNS);
      String strLink = "";
      if (elLink != null) {
        strLink = elLink.getAttributeValue("href").trim();
      }
      // TODO dealing with elLink.getAttributeValue("rel")
      logger.info("url read : "+strLink+" ,"+elLink.getAttributeValue("rel"));
      
      // get description element
      Element elDesc = item.getChild("summary", defNS);
      String strDesc = "";
      if (elDesc != null) {
        strDesc = getValue(elDesc); //.getTextTrim();
      }
      // TODO: where to store the content?

      // generate new news item (link to article)
      ItemIF curItem = cBuilder.createItem(item, chnl, strTitle, strDesc,
                                           ParserUtils.getURL(strLink));
      curItem.setFound(dateParsed);

      // get issued element (required)
      Element elIssued = item.getChild("issued", defNS);
      curItem.setDate(ParserUtils.getDate(elIssued.getTextTrim()));

      // get subject element
      Element elSubject = item.getChild("subject", dcNS);
      if (elSubject != null) {
        // TODO: Mulitple subject elements not handled currently
        curItem.setSubject(elSubject.getTextTrim());
      }      
    } //END While, end <entry>-tags

    // set to current date
    chnl.setLastUpdated(dateParsed);

    return chnl;
  }

  
  private static String getValue(Element elt) {
    if (elt == null)
      return "";
    String typeElt = elt.getAttributeValue("type");
    String modeElt = elt.getAttributeValue("mode");
    String value;
    String unescapedValue = elt.getTextTrim();
    
    if( unescapedValue==null || unescapedValue.length()==0) {
      // maybe value in a div ?
      java.util.List listContent = elt.getContent();
      Iterator iter = listContent.iterator();
      while ( iter.hasNext() ) {
        org.jdom.Content content = (org.jdom.Content) iter.next();
        logger.debug("getValue : element read = "+ content);
        if (content instanceof Element) {
          unescapedValue = ((Element)content).getValue();
          logger.debug("elt found in type :"+unescapedValue);
        }
      }
    }
    value = unescapedValue;
    if ("base64".equals(modeElt)) {
      value = ParserUtils.decodeBase64(value);
    }
    if ("text/html".equals(typeElt) || "application/xhtml+xml".equals(typeElt)) {
      value = ParserUtils.unEscape(value);
    }
  
    return value;
  }

}
