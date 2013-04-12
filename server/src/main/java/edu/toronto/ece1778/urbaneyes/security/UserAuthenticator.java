package edu.toronto.ece1778.urbaneyes.security;

import javax.inject.Inject;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Simple authenticator for users.
 * 
 * @author mcupak
 * 
 */
public class UserAuthenticator extends BaseAuthenticator {

	@Inject
	private Credentials credentials;

	@Inject
	private UserManager usersManager;

	/**
	 * Authenticates the user.
	 */
	public void authenticate() {
		System.out.println(credentials.getUsername());
		System.out.println(((PasswordCredential) credentials.getCredential())
				.getValue());
		final User user = usersManager.getUser(credentials.getUsername(),
				((PasswordCredential) credentials.getCredential()).getValue());
		System.out.println(user);
		if (user == null) {
			setStatus(AuthenticationStatus.FAILURE);
			return;
		}
		setUser(new org.picketlink.idm.api.User() {
			public String getId() {
				return Long.toString(user.getId());
			}

			public String getKey() {
				return user.getEmail();
			}
		});
		setStatus(AuthenticationStatus.SUCCESS);
	}
}
