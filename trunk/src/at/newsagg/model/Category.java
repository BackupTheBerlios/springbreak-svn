/*
 * Created on 28.03.2005
 * king
 * 
 */
package at.newsagg.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import at.newsagg.model.parser.hibernate.Channel;

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
public class Category extends BaseObject implements CategoryIF{
    
    private int id = -1;
    private String title;
    private String htmlColor;
    private User User;
    
    
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
    
    
    
    
    
    /**
     * @hibernate.many-to-one
     *  column="USER_ID"
     *  class="at.newsagg.model.User"
     *  not-null="true"
     *
     * @return parent user.
     */
    public User getUser() {
        return User;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        User = user;
    }
   /**
    * TODO: die ist scheiße!
    */ 
    public boolean equals(Object other) {
        if (this==other) return true;
        if ( !(other instanceof CategoryIF) ) return false;
        final CategoryIF that = (CategoryIF) other;
        return (this.getTitle().toLowerCase().equals(that.getTitle().toLowerCase()));
        
    }

    public int hashCode() {
        return this.getTitle().hashCode();}
}
