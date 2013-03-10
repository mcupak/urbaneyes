package edu.toronto.ece1778.urbaneyes.rest;

import java.lang.reflect.Member;
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
 * RESTful service to read the contents of the members table.
 */
@Path("/users")
@RequestScoped
public class UserResourceRESTService {
	@Inject
	private EntityManager em;

	@GET
	@Produces("text/xml")
	public List<User> listAllUsers() {
		@SuppressWarnings("unchecked")
		final List<User> results = em.createQuery(
				"select m from User m order by m.name").getResultList();
		return results;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public User lookupUserById(@PathParam("id") long id) {
		return em.find(User.class, id);
	}
}
