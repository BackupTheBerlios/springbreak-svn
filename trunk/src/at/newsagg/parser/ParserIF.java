/*
 * Created on 20.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import org.jdom.Element;
import at.newsagg.model.parser.*;


/**
 * Base Interface for all Parsers.
 * 
 * 
 * @author roland vecera
 * @version
 * created on 20.03.2005 14:43:43
 *
 */
public interface ParserIF {
    public ChannelIF parse(ChannelBuilderIF cBuilder, Element channel)
    throws ParseException;
}
