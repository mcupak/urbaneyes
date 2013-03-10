package edu.toronto.ece1778.urbaneyes.security;

import javax.inject.Inject;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import edu.toronto.ece1778.urbaneyes.data.UsersManager;
import edu.toronto.ece1778.urbaneyes.model.User;

public class UserAuthenticator extends BaseAuthenticator {

	@Inject
	private Credentials credentials;

	@Inject
	private UsersManager usersManager;

	public void authenticate() {
		System.out.println(credentials.getUsername());
		System.out.println(((PasswordCredential) credentials.getCredential()).getValue());
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
				return "user";
			}
		});
		setStatus(AuthenticationStatus.SUCCESS);
	}
}
