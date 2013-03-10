package edu.toronto.ece1778.urbaneyes.security;

import java.io.Serializable;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

public class UserAuthorizator implements Serializable {

	private static final long serialVersionUID = 5118096668237559557L;

	@Secures
	@Admin
	public boolean isAdmin(Identity identity) {
		if (!identity.isLoggedIn()) {
			return false;
		}
		return true;
	}
}
