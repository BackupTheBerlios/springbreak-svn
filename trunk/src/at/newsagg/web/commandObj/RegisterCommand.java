package at.newsagg.web.commandObj;


import at.newsagg.model.User;

/**
 * @author Szabolcs Rozsnyai
 * $Id:$
 */

public class RegisterCommand {
	private User user;
	private String secondPassword;
	
	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
	/**
	 * @return Returns the secondPassword.
	 */
	public String getSecondPassword() {
		return secondPassword;
	}
	/**
	 * @param secondPassword The secondPassword to set.
	 */
	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}
}