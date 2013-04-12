package edu.toronto.ece1778.urbaneyes.security;

import java.io.Serializable;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

/**
 * Authorizator for a user.
 * 
 * @author mcupak
 * 
 */
public class UserAuthorizator implements Serializable {

	private static final long serialVersionUID = 5118096668237559557L;

	/**
	 * Determines if the user is signed in.
	 * 
	 * @param identity
	 *            user identity object
	 * @return true if the users is signed in, false otherwise
	 */
	@Secures
	@Admin
	public boolean isAdmin(Identity identity) {
		if (!identity.isLoggedIn()) {
			return false;
		}
		return true;
	}
}
