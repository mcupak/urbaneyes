package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.Answer;

/**
 * RESTful service to expose answers via XML.
 */
@Path("/answers")
@RequestScoped
public class AnswerResourceRESTService {
	@Inject
	private EntityManager em;

	/**
	 * Exposes all the answers in the database via XML.
	 * 
	 * @return list of all the answers
	 */
	@GET
	@Produces("text/xml")
	public List<Answer> listAllAnswers() {
		@SuppressWarnings("unchecked")
		final List<Answer> results = em.createQuery(
				"select m from Answer m order by m.name").getResultList();
		return results;
	}

	/**
	 * Looks up an answer by its ID and exposes it via XML.
	 * 
	 * @param id
	 *            answer id
	 * @return answer with the given ID
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Answer lookupAnswerById(@PathParam("id") long id) {
		return em.find(Answer.class, id);
	}
}
