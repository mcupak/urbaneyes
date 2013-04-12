package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.model.Question;

/**
 * RESTful service to expose questions via XML.
 */
@Path("/questions")
@RequestScoped
public class QuestionResourceRESTService {
	@Inject
	private EntityManager em;

	/**
	 * Exposes all the questions in a database via XML.
	 * 
	 * @return list of all the questions
	 */
	@GET
	@Produces("text/xml")
	public List<Question> listAllQuestions() {
		@SuppressWarnings("unchecked")
		final List<Question> results = em.createQuery(
				"select m from Question m order by m.name").getResultList();
		return results;
	}

	/**
	 * Looks up a question by its ID and exposes it via XML.
	 * 
	 * @param id
	 *            question ID
	 * @return question with the given ID
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Question lookupQuestionById(@PathParam("id") long id) {
		return em.find(Question.class, id);
	}
}
