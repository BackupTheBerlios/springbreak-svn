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

package at.newsagg.parser;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

import at.newsagg.model.parser.*;
import at.newsagg.utils.ParserUtils;




/**
 * Parser which reads in document instances according to the RSS 2.0
 * specification and generates a news channel object.
 *
 * @author Anthony Eden, Niko Schmuck
 */

//Simplified for use in Springbreak
//comment out: <source>, <enclosure>, <textinput>, <category>, <cloud> no longer parsed
//Roland Vecera 
//11.02.2005

class RSS_2_0_Parser implements RSS_2_0_ParserIF {

  private static Log logger = LogFactory.getLog(RSS_2_0_Parser.class);
/*
 * We do not interpret the <category>-Tage, hence we will not need this method at the moment
 * Roland Vecera
 * 11.02.2005
 * 
 * 
    private static CategoryIF getCategoryList(CategoryIF parent, String title, Hashtable children) {
        // Assuming category hierarchy for each category element
        // is already mapped out into Hashtable tree;  Hense the children Hashtable

        // create channel builder to help create CategoryIF objects
        ChannelBuilder builder = new ChannelBuilder();

        // create current CategoryIF object; Parent may be null if at top level
        CategoryIF cat = builder.createCategory(parent, title);
        // iterate off list of keys from children list
        Enumeration itChild = children.keys();
        while (itChild.hasMoreElements()) {
            String childKey = (String)itChild.nextElement();
            // don't need to keep track of return CategoryIF since it will be added as child of another instance
            getCategoryList(cat, childKey, (Hashtable)children.get(childKey));
        }
        return cat;
    }
*/
  
  
  public ChannelIF parse(ChannelBuilderIF cBuilder, Element root)
    throws ParseException {
    if (cBuilder == null) {
      throw new RuntimeException(
        "Without builder no channel can " + "be created.");
    }
    Date dateParsed = new Date();
    logger.debug("start parsing.");

    Namespace defNS = ParserUtils.getDefaultNS(root);
    if (defNS == null) {
      defNS = Namespace.NO_NAMESPACE;
      logger.info("No default namespace found.");
    }
    Namespace dcNS = ParserUtils.getNamespace(root, "dc");
    // fall back to default name space
    if (dcNS == null) {
      dcNS = defNS;
    }

    // Get the channel element (only one occurs)
    Element channel = root.getChild("channel", defNS);
    if (channel == null) {
      logger.warn("Channel element could not be retrieved from feed.");
      throw new ParseException("No channel element found in feed.");
    }

    // --- read in channel information

    // 1 title element
    ChannelIF chnl =
      cBuilder.createChannel(channel, channel.getChildTextTrim("title", defNS));

    // set channel format
    chnl.setFormat(ChannelFormat.RSS_2_0);

    // 1 description element
    chnl.setDescription(channel.getChildTextTrim("description", defNS));

    // 1 link element
    chnl.setSite(ParserUtils.getURL(channel.getChildTextTrim("link", defNS)));

    // 1 language element
    chnl.setLanguage(channel.getChildTextTrim("language", defNS));

    /*
     * *******************************************************
     * start parsing <item>
     * *******************************************************
     */
    // 1..n item elements
    List items = channel.getChildren("item", defNS);
    Iterator i = items.iterator();
    while (i.hasNext()) {
      Element item = (Element) i.next();

      // get title element
      Element elTitle = item.getChild("title", defNS);
      String strTitle = "<No Title>";
      if (elTitle != null) {
        strTitle = elTitle.getTextTrim();
      }
      if (logger.isDebugEnabled()) {
        logger.debug("Item element found (" + strTitle + ").");
      }

      // get link element
      Element elLink = item.getChild("link", defNS);
      String strLink = "";
      if (elLink != null) {
        strLink = elLink.getTextTrim();
      }

      // get description element
      Element elDesc = item.getChild("description", defNS);
      String strDesc = "";
      if (elDesc != null) {
        strDesc = elDesc.getTextTrim();
      }

      // generate new RSS item (link to article)
      ItemIF rssItem = cBuilder.createItem(item, chnl, strTitle, strDesc,
                                           ParserUtils.getURL(strLink));

      // get subject element
      Element elSubject = item.getChild("subject", defNS);
      if (elSubject == null) {
        // fallback mechanism: get dc:subject element
        elSubject = item.getChild("subject", dcNS);
      }
      if (elSubject != null) {
        rssItem.setSubject(elSubject.getTextTrim());
      }
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
      // get category list
      // get list of <category> elements
      List listCategory = item.getChildren("category", defNS);
      if (listCategory.size() < 1) {
        // fallback mechanism: get dc:category element
        listCategory = item.getChildren("category", dcNS);
      }
      if (listCategory.size() > 0) {
        Hashtable catTable = new Hashtable();

        // for each category, parse hierarchy
        Iterator itCat = listCategory.iterator();
        while (itCat.hasNext()) {
          Hashtable currTable = catTable;
          Element elCategory = (Element)itCat.next();
          // get contents of category element
          String [] titles = elCategory.getTextNormalize().split("/");
          for (int x=0; x<titles.length; x++) {
            // tokenize category string to extract out hierarchy
            if (currTable.containsKey(titles[x]) == false) {
              // if token does not exist in current map, add it with child Hashtable
              currTable.put(titles[x], new Hashtable());
            }
            // reset current Hashtable to child's Hashtable then iterate to next token
            currTable = (Hashtable)currTable.get(titles[x]);
          }
        }
        ArrayList catList = new ArrayList();
        // transform cat list & hierarchy into list of CategoryIF elements
        Enumeration enumCategories = catTable.keys();
        while (enumCategories.hasMoreElements()) {
          String key = (String)enumCategories.nextElement();
          // build category list: getCategoryList(parent, title, children)
          CategoryIF cat = getCategoryList(null, key, (Hashtable)catTable.get(key));
          catList.add(cat);
        }
        if (catList.size() > 0) {
          // if categories were actually created, then add list to item node
          rssItem.setCategories(catList);
        }
      }*/

      // get publication date
      Element elDate = item.getChild("pubDate", defNS);
      if (elDate == null) {
        // fallback mechanism: get dc:date element
        elDate = item.getChild("date", dcNS);
      }
      if (elDate != null) {
        rssItem.setDate(ParserUtils.getDate(elDate.getTextTrim()));
      }

      rssItem.setFound(dateParsed);

      // get Author element
      Element elAuthor = item.getChild("author", defNS);
      if (elAuthor == null) {
        // fallback mechanism: get dc:creator element
        elAuthor = item.getChild("creator", dcNS);
      }
      if (elAuthor != null)
        rssItem.setCreator(elAuthor.getTextTrim());

      // get Comments element
      Element elComments = item.getChild("comments", defNS);
      String strComments = "";
      if (elComments != null) {
        strComments = elComments.getTextTrim();
      }
      rssItem.setComments(ParserUtils.getURL(strComments));

      // get guid element
      Element elGuid = item.getChild("guid", defNS);
      if (elGuid != null) {
        String guidUrl = elGuid.getTextTrim();
        if (guidUrl != null) {
          boolean permaLink = true;
          Attribute permaLinkAttribute = elGuid.getAttribute("isPermaLink", defNS);
          if (permaLinkAttribute != null) {
            String permaLinkStr = permaLinkAttribute.getValue();
            if (permaLinkStr != null) {
              permaLink = Boolean.valueOf(permaLinkStr).booleanValue();
            }
          }
          ItemGuidIF itemGuid =
            cBuilder.createItemGuid(rssItem, guidUrl, permaLink);
          rssItem.setGuid(itemGuid);
        }
      }
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
      // get source element
      Element elSource = item.getChild("source", defNS);
      if (elSource != null) {
        String sourceName = elSource.getTextTrim();
        Attribute sourceAttribute = elSource.getAttribute("url", defNS);
        if (sourceAttribute != null) {
          String sourceLocation = sourceAttribute.getValue().trim();
          ItemSourceIF itemSource = cBuilder.createItemSource(rssItem, sourceName,
                                                              sourceLocation, null);
          rssItem.setSource(itemSource);
        }
      }
      */
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
      // get enclosure element
      Element elEnclosure = item.getChild("enclosure", defNS);
      if (elEnclosure != null) {
        URL location = null;
        String type = null;
        int length = -1;
        Attribute urlAttribute = elEnclosure.getAttribute("url", defNS);
        if (urlAttribute != null) {
          location = ParserUtils.getURL(urlAttribute.getValue().trim());
        }
        Attribute typeAttribute = elEnclosure.getAttribute("type", defNS);
        if (typeAttribute != null) {
          type = typeAttribute.getValue().trim();
        }
        Attribute lengthAttribute = elEnclosure.getAttribute("length", defNS);
        if (lengthAttribute != null) {
          try {
            length = Integer.parseInt(lengthAttribute.getValue().trim());
          } catch (NumberFormatException e) {
            logger.warn(e);
          }
        }
        ItemEnclosureIF itemEnclosure =
          cBuilder.createItemEnclosure(rssItem, location, type, length);
        rssItem.setEnclosure(itemEnclosure);
      }*/
    }//end while
    
    //END parsing <item>-tags
    

    // 0..1 image element
    Element image = channel.getChild("image", defNS);
    if (image != null) {
      ImageIF rssImage =
        cBuilder.createImage(
          image.getChildTextTrim("title", defNS),
          ParserUtils.getURL(image.getChildTextTrim("url", defNS)),
          ParserUtils.getURL(image.getChildTextTrim("link", defNS)));
      Element imgWidth = image.getChild("width", defNS);
      if (imgWidth != null) {
        try {
          rssImage.setWidth(Integer.parseInt(imgWidth.getTextTrim()));
        } catch (NumberFormatException e) {
          logger.warn("Error parsing width: " + e.getMessage());
        }
      }
      Element imgHeight = image.getChild("height", defNS);
      if (imgHeight != null) {
        try {
          rssImage.setHeight(Integer.parseInt(imgHeight.getTextTrim()));
        } catch (NumberFormatException e) {
          logger.warn("Error parsing height: " + e.getMessage());
        }
      }
      Element imgDescr = image.getChild("description", defNS);
      if (imgDescr != null) {
        rssImage.setDescription(imgDescr.getTextTrim());
      }
      chnl.setImage(rssImage);
    }
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005  
    
     
       // 0..1 textinput element
    Element txtinp = channel.getChild("textinput", defNS);
    if (txtinp != null) {
      TextInputIF rssTextInput =
        cBuilder.createTextInput(
          txtinp.getChildTextTrim("title", defNS),
          txtinp.getChildTextTrim("description", defNS),
          txtinp.getChildTextTrim("name", defNS),
          ParserUtils.getURL(txtinp.getChildTextTrim("link", defNS)));
      chnl.setTextInput(rssTextInput);
    }
*/
    // 0..1 copyright element
    Element copyright = channel.getChild("copyright", defNS);
    if (copyright != null) {
      chnl.setCopyright(copyright.getTextTrim());
    }

    // 0..1 Rating element
    Element rating = channel.getChild("rating", defNS);
    if (rating != null) {
      chnl.setRating(rating.getTextTrim());
    }

    // 0..1 Docs element
    Element docs = channel.getChild("docs", defNS);
    if (docs != null) {
      chnl.setDocs(docs.getTextTrim());
    }

    // 0..1 Generator element
    Element generator = channel.getChild("generator", defNS);
    if (generator != null) {
      chnl.setGenerator(generator.getTextTrim());
    }

    // 0..1 ttl element
    Element ttl = channel.getChild("ttl", defNS);
    if (ttl != null) {
      chnl.setTtl(Integer.parseInt(ttl.getTextTrim()));
    }

    // 0..1 pubDate element
    Element pubDate = channel.getChild("pubDate", defNS);
    if (pubDate != null) {
      chnl.setPubDate(ParserUtils.getDate(pubDate.getTextTrim()));
    }

    // 0..1 lastBuildDate element
    Element lastBuildDate = channel.getChild("lastBuildDate", defNS);
    if (lastBuildDate != null) {
      chnl.setLastBuildDate(ParserUtils.getDate(lastBuildDate.getTextTrim()));
    }
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
			 * 
    // get category list
    // get list of <category> elements
    List listCategory = channel.getChildren("category", defNS);
    if (listCategory.size() < 1) {
      // fallback mechanism: get dc:category element
      listCategory = channel.getChildren("category", dcNS);
    }
    if (listCategory.size() > 0) {
      Hashtable catTable = new Hashtable();
           // for each category, parse hierarchy
      Iterator itCat = listCategory.iterator();
      while (itCat.hasNext()) {
        Hashtable currTable = catTable;
        Element elCategory = (Element)itCat.next();
        // get contents of category element
        String [] titles = elCategory.getTextNormalize().split("/");
        for (int x=0; x<titles.length; x++) {
          // tokenize category string to extract out hierarchy
          if (currTable.containsKey(titles[x]) == false) {
            // if token does not exist in current map, add it with child Hashtable
            currTable.put(titles[x], new Hashtable());
          }
          // reset current Hashtable to child's Hashtable then iterate to next token
          currTable = (Hashtable)currTable.get(titles[x]);
        }
      }
      ArrayList catList = new ArrayList();
      // transform cat list & hierarchy into list of CategoryIF elements
      Enumeration enumCategories = catTable.keys();
      while (enumCategories.hasMoreElements()) {
        String key = (String)enumCategories.nextElement();
        // build category list: getCategoryList(parent, title, children)
        CategoryIF cat = getCategoryList(null, key, (Hashtable)catTable.get(key));
        catList.add(cat);
      }
      if (catList.size() > 0) {
        // if categories were actually created, then add list to item node
        chnl.setCategories(catList);
      }
    }*/

    // 0..1 managingEditor element
    Element managingEditor = channel.getChild("managingEditor", defNS);
    if (managingEditor != null) {
      chnl.setCreator(managingEditor.getTextTrim());
    }

    // 0..1 webMaster element
    Element webMaster = channel.getChild("webMaster", defNS);
    if (webMaster != null) {
      chnl.setPublisher(webMaster.getTextTrim());
    }
/*
 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 2.0 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
			 * 
    // 0..1 cloud element
    Element cloud = channel.getChild("cloud", defNS);
    if (cloud != null) {
      String _port = cloud.getAttributeValue("port", defNS);
      int port = -1;
      if (_port != null) {
        try {
          port = Integer.parseInt(_port);
        } catch (NumberFormatException e) {
          logger.warn(e);
        }
      }
      chnl.setCloud(cBuilder.createCloud(cloud.getAttributeValue("domain", defNS),
                                         port,
                                         cloud.getAttributeValue("path", defNS),
                                         cloud.getAttributeValue("registerProcedure", defNS),
                                         cloud.getAttributeValue("protocol", defNS)));
    }
*/
    chnl.setLastUpdated(dateParsed);

    // 0..1 skipHours element
    // 0..1 skipDays element

    return chnl;
  }

}
