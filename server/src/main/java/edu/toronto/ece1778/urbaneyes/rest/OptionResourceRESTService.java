package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.Option;

/**
 * RESTful service to read the contents of the members table.
 */
@Path("/options")
@RequestScoped
public class OptionResourceRESTService {
	@Inject
	private EntityManager em;

	@GET
	@Produces("text/xml")
	public List<Option> listAllOptions() {
		@SuppressWarnings("unchecked")
		final List<Option> results = em.createQuery(
				"select m from Option m order by m.name").getResultList();
		return results;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Option lookupOptionById(@PathParam("id") long id) {
		return em.find(Option.class, id);
	}
}
