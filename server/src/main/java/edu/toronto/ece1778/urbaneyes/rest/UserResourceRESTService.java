package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * RESTful service to expose users via XMl.
 */
@Path("/users")
@RequestScoped
public class UserResourceRESTService {
	@Inject
	private EntityManager em;

	/**
	 * Exports all the users in XML.
	 * 
	 * @return list of users
	 */
	@GET
	@Produces("text/xml")
	public List<User> listAllUsers() {
		@SuppressWarnings("unchecked")
		final List<User> results = em.createQuery(
				"select m from User m order by m.name").getResultList();
		return results;
	}

	/**
	 * Exports a single user in XML.
	 * 
	 * @param id
	 *            user id
	 * @return user
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public User lookupUserById(@PathParam("id") long id) {
		return em.find(User.class, id);
	}
}
