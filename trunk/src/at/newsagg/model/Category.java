/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.model;

/**
 * Category model.
 
 * @hibernate.class
 * table="CATEGORIES"
 *
 * @author Roland Vecera
 * @version
 * created on 28.03.2005 16:05:02
 *
 * TODO: über equals, hashcode nachdenken
 */
public class Category extends BaseObject{
    
    private int id = -1;
    private String title;
    private String htmlColor;
    private int userID;
    
    
   public Category ()
   {}
    
    /**
     * @hibernate.property
     *  column="HTMLCOLOR"
     *  not-null="true"
     *  
     * @return Returns the htmlColor.
     */
    public String getHtmlColor() {
        return htmlColor;
    }
    /**
     * @param htmlColor The htmlColor to set.
     */
    public void setHtmlColor(String htmlColor) {
        this.htmlColor = htmlColor;
    }
    
    /**
     * @hibernate.id
     *  generator-class="native"
     *  column="CATEGORY_ID"
     *  type="integer"
     *  unsaved-value="-1"
     *
     * @return integer representation of identity.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @hibernate.property
     *  column="TITLE"
     *  not-null="true"
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    
    public int getUserID() {
        return userID;
    }
    /**
     * @param userID The userID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
