/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.net.URL;

import at.newsagg.model.parser.hibernate.Item;

/**
 * @author king
 * @version
 * created on 26.03.2005 11:12:33
 *
 */
public class ItemDAOTest extends Base {

    /**
     * testing equals methode.
     * testing getChannels().
     * 
     * 2 Channels are equal if they have the same lowercase-URL
     */
    public void testEqualsMethod() throws Exception
    {
     Item i = new Item();
     Item j = new Item();
     
     
     i.setLink("http://vecego.0wnz.at/rss.php");
     j.setLink("http://vecego.0wnz.at/rss.php");
     
     
     assertTrue(i.equals(j));
     
     i.setLink("http://vecego.0wnz.at/rss.php");
     j.setLink("http://vecEGO.0WNZ.at/rss.php");
     
     assertTrue(i.equals(j));
     
     i.setLink("http://vecego.0wnz.at/rss.php");
     j.setLink("http://0WNZ.at/rss.php");
     
     assertFalse(i.equals(j));
     
     
    
    }
    
}
