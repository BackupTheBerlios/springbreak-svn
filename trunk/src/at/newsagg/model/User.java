package at.newsagg.model;

import java.util.Collection;

import at.newsagg.model.parser.hibernate.Channel;


/**
 * 
 * @hibernate.class
 * table="newsagguser"
 * 
 * 
 * @author Roland Vecera xdoclet-hibernate
 * @version
 *
 *
 */
public class User extends BaseObject { 
	private String username; 
	private String firstName; 
	private String lastName;
	private String password;
	private String email;
	private boolean isAdmin;
	private Collection categories;
	
	
	/**
	 * @hibernate.property
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * 
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @hibernate.property
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @hibernate.property
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @hibernate.id
	 * unsaved-value="0"
	 * column="username"
	 * generator-class="assigned"
	 * 
	 * 
	 * 
	 * @author king
	 * @version
	 * created on 28.03.2005 18:26:23
	 *
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 
	 * @param admin
	 */
	public void setIsAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
	/**
	 * @hibernate.property
	 * @param admin
	 */
	public boolean getIsAdmin() {
		return this.isAdmin;
	}
	
	
	
	/**
	 * @hibernate.property
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
  

   
    /**
     * @param isAdmin The isAdmin to set.
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    /**
     * @hibernate.bag
     *  table="CATEGORIES"
     *  cascade="all"
     *  inverse="true"
     *  lazy="true"
     *  order-by="title ASC"
     * @hibernate.collection-key
     *  column="USER_ID"
     * @hibernate.collection-one-to-many
     *  class="at.newsagg.model.Category"
     *
     * @return items of channel.
     */
    public Collection getCategories() {
        return categories;
    }
    /**
     * @param categories The categories to set.
     */
    public void setCategories(Collection categories) {
        this.categories = categories;
    }
    
    public boolean equals(Object other) {
        if (this==other) return true;
        if ( !(other instanceof User) ) return false;
        final User that = (User) other;
        return this.getUsername().equals( that.getUsername() );
        }
    
    
    
    public int hashCode() {
        return this.getUsername().hashCode();
        
        }
    
}