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

// $Id: RSS_0_91_Parser.java,v 1.32 2003/11/04 19:09:43 niko_schmuck Exp $

//Simplified for use in Springbreak
//comment out: <source>,<enclosure>, <cloud> and <textinput> is not longer parsed
//Roland Vecera 
//11.02.2005

package at.newsagg.parser;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;

import at.newsagg.model.parser.*;
import at.newsagg.utils.ParserUtils;
/**
 * Parser which reads in document instances according to the RSS 0.91
 * specification and generates a news channel object.
 * 
 * @author Niko Schmuck
 */
class RSS_0_91_Parser implements RSS_0_91_ParserIF {

	private static Log logger = LogFactory.getLog(RSS_0_91_Parser.class);

	public ChannelIF parse(ChannelBuilderIF cBuilder, Element root)
			throws ParseException {
		if (cBuilder == null) {
			throw new RuntimeException("Without builder no channel can "
					+ "be created.");
		}
		Date dateParsed = new Date();
		logger.debug("start parsing.");

		// Get the channel element (only one occurs)
		Element channel = root.getChild("channel");
		if (channel == null) {
			logger.warn("Channel element could not be retrieved from feed.");
			throw new ParseException("No channel element found in feed.");
		}

		// --- read in channel information

		// 1 title element
		ChannelIF chnl = cBuilder.createChannel(channel, channel
				.getChildTextTrim("title"));

		chnl.setFormat(ChannelFormat.RSS_0_91);

		// 1 description element
		chnl.setDescription(channel.getChildTextTrim("description"));

		// 1 link element
		chnl.setSite(ParserUtils.getURL(channel.getChildTextTrim("link")));

		// 1 language element
		chnl.setLanguage(channel.getChildTextTrim("language"));

		// 1..n item elements

		//**********************************
		// get all <item>-elements from Feed and store values in ItemIF
		//**********************************

		List items = channel.getChildren("item");
		Iterator i = items.iterator();
		while (i.hasNext()) {
			Element item = (Element) i.next();

			// get title element
			Element elTitle = item.getChild("title");
			String strTitle = "<No Title>";
			if (elTitle != null) {
				strTitle = elTitle.getTextTrim();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Item element found (" + strTitle + ").");
			}

			// get link element
			Element elLink = item.getChild("link");
			String strLink = "";
			if (elLink != null) {
				strLink = elLink.getTextTrim();
			}

			// get description element
			Element elDesc = item.getChild("description");
			String strDesc = "";
			if (elDesc != null) {
				strDesc = elDesc.getTextTrim();
			}

			// generate new RSS item (link to article)
			ItemIF rssItem = cBuilder.createItem(item, chnl, strTitle, strDesc,
					ParserUtils.getURL(strLink));
			rssItem.setFound(dateParsed);

			// End Basic RSS 0.91
			/*
			 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 0.92 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005
			 *  // Additional RSS 0.92 Elements // get source element (an RSS
			 * 0.92 element) Element source = item.getChild("source"); if
			 * (source != null) { String sourceName = source.getTextTrim();
			 * Attribute sourceAttribute = source.getAttribute("url"); if
			 * (sourceAttribute != null) { String location =
			 * sourceAttribute.getValue().trim(); ItemSourceIF itemSource =
			 * cBuilder.createItemSource( rssItem, sourceName, location, null);
			 * rssItem.setSource(itemSource); } }
			 *  
			 */

			/*
			 * This Element not currently set/used in or Application For
			 * Simplicity we do not persist this Information in DB
			 * 
			 * However it's part of RSS 0.92 Spec and will eventually used at a
			 * later point in our application
			 * 
			 * Roland Vecera 10. Feb 2005 // get enclosure element (an RSS 0.92
			 * element) Element enclosure = item.getChild("enclosure"); if
			 * (enclosure != null) { URL location = null; String type = null;
			 * int length = -1; Attribute urlAttribute =
			 * enclosure.getAttribute("url"); if (urlAttribute != null) {
			 * location = ParserUtils.getURL(urlAttribute.getValue().trim()); }
			 * Attribute typeAttribute = enclosure.getAttribute("type"); if
			 * (typeAttribute != null) { type = typeAttribute.getValue().trim(); }
			 * Attribute lengthAttribute = enclosure.getAttribute("length"); if
			 * (lengthAttribute != null) { try { length =
			 * Integer.parseInt(lengthAttribute.getValue().trim()); } catch
			 * (NumberFormatException e) { logger.warn(e); } } ItemEnclosureIF
			 * itemEnclosure = cBuilder.createItemEnclosure(rssItem, location,
			 * type, length); rssItem.setEnclosure(itemEnclosure); }
			 */
		}//end while
		/*
		 * **************************** Ende <item>-Elements
		 * *******************************
		 */

		// 0..1 image element
		Element image = channel.getChild("image");
		if (image != null) {
			ImageIF rssImage = cBuilder.createImage(image
					.getChildTextTrim("title"), ParserUtils.getURL(image
					.getChildTextTrim("url")), ParserUtils.getURL(image
					.getChildTextTrim("link")));
			Element imgWidth = image.getChild("width");
			if (imgWidth != null) {
				try {
					rssImage.setWidth(Integer.parseInt(imgWidth.getTextTrim()));
				} catch (NumberFormatException e) {
					logger.warn(e);
				}
			}
			Element imgHeight = image.getChild("height");
			if (imgHeight != null) {
				try {
					rssImage.setHeight(Integer
							.parseInt(imgHeight.getTextTrim()));
				} catch (NumberFormatException e) {
					logger.warn(e);
				}
			}
			Element imgDescr = image.getChild("description");
			if (imgDescr != null) {
				rssImage.setDescription(imgDescr.getTextTrim());
			}
			chnl.setImage(rssImage);
		}
		/*
		 * This Element not currently set/used in or Application For Simplicity
		 * we do not persist this Information in DB
		 * 
		 * Even RSS 2.0 Spec says "The purpose of the <textInput> element is
		 * something of a mystery. You can use it to specify a search engine
		 * box. Or to allow a reader to provide feedback. Most aggregators
		 * ignore it."
		 * 
		 * http://blogs.law.harvard.edu/tech/rss
		 * 
		 * However it's part of RSS 0.91/2.0 Spec and will eventually used at a
		 * later point in our application
		 * 
		 * Roland Vecera 10. Feb 2005
		 *  // 0..1 textinput element Element txtinp =
		 * channel.getChild("textinput"); if (txtinp != null) { TextInputIF
		 * rssTextInput = cBuilder.createTextInput(
		 * txtinp.getChild("title").getTextTrim(),
		 * txtinp.getChild("description").getTextTrim(),
		 * txtinp.getChild("name").getTextTrim(),
		 * ParserUtils.getURL(txtinp.getChild("link").getTextTrim()));
		 * chnl.setTextInput(rssTextInput); }
		 */

		// 0..1 copyright element
		Element copyright = channel.getChild("copyright");
		if (copyright != null) {
			chnl.setCopyright(copyright.getTextTrim());
		}

		// 0..1 rating element
		Element rating = channel.getChild("rating");
		if (rating != null) {
			chnl.setRating(rating.getTextTrim());
		}

		// 0..1 pubDate element
		Element pubDate = channel.getChild("pubDate");
		if (pubDate != null) {
			chnl.setPubDate(ParserUtils.getDate(pubDate.getTextTrim()));
		}

		// 0..1 lastBuildDate element
		Element lastBuildDate = channel.getChild("lastBuildDate");
		if (lastBuildDate != null) {
			chnl.setLastBuildDate(ParserUtils.getDate(lastBuildDate
					.getTextTrim()));
		}

		// 0..1 docs element
		Element docs = channel.getChild("docs");
		if (docs != null) {
			chnl.setDocs(docs.getTextTrim());
		}

		// 0..1 managingEditor element
		Element managingEditor = channel.getChild("managingEditor");
		if (managingEditor != null) {
			chnl.setCreator(managingEditor.getTextTrim());
		}

		// 0..1 webMaster element
		Element webMaster = channel.getChild("webMaster");
		if (webMaster != null) {
			chnl.setPublisher(webMaster.getTextTrim());
		}
		/*
		 * This Element not currently set/used in or Application For Simplicity
		 * we do not persist this Information in DB
		 * 
		 * 
		 * However it's part of RSS 0.92/2.0 Spec and will eventually used at a
		 * later point in our application
		 * 
		 * Roland Vecera 10. Feb 2005
		 *  // 0..1 cloud element Element cloud = channel.getChild("cloud"); if
		 * (cloud != null) { String _port = cloud.getAttributeValue("port"); int
		 * port = -1; if (_port != null) { try { port = Integer.parseInt(_port); }
		 * catch (NumberFormatException e) { logger.warn(e); } } chnl.setCloud(
		 * cBuilder.createCloud( cloud.getAttributeValue("domain"), port,
		 * cloud.getAttributeValue("path"),
		 * cloud.getAttributeValue("registerProcedure"),
		 * cloud.getAttributeValue("protocol"))); }
		 */
		chnl.setLastUpdated(dateParsed);
		// 0..1 skipHours element
		// 0..1 skipDays element

		return chnl;
	}

}