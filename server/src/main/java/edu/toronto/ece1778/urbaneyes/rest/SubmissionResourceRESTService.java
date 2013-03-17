package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.Submission;

/**
 * RESTful service to read the contents of the members table.
 */
@Path("/submissions")
@RequestScoped
public class SubmissionResourceRESTService {
	@Inject
	private EntityManager em;

	@GET
	@Produces("text/xml")
	public List<Submission> listAllSubmissions() {
		@SuppressWarnings("unchecked")
		final List<Submission> results = em.createQuery(
				"select m from Submission m order by m.name").getResultList();
		return results;
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Submission lookupSubmissionById(@PathParam("id") long id) {
		return em.find(Submission.class, id);
	}
}
