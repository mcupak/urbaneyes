package edu.toronto.ece1778.urbaneyes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.data.SubmissionManager;
import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.model.Submission;

/**
 * RESTful service to expose submissions via XML.
 */
@Path("/submissions")
@RequestScoped
public class SubmissionResourceRESTService {
	@Inject
	private SubmissionManager sm;
	@Inject
	private SurveyManager am;

	/**
	 * Exposes all the submissions in the database via XML.
	 * 
	 * @return list of all the submissions
	 */
	@GET
	@Produces("text/xml")
	public List<Submission> listAllSubmissions() {
		return sm.getSubmissions();
	}

	/**
	 * Looks up a submission by ID and exposes it via XML.
	 * 
	 * @param id
	 *            submission id
	 * @return submission with the given ID
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Submission lookupSubmissionById(@PathParam("id") long id) {
		return sm.getSubmission(id);
	}

	/**
	 * Looks up submissions of a survey and exposes them via XML
	 * 
	 * @param id
	 *            survey id
	 * @return submission of the survey with the given ID
	 */
	@GET
	@Path("/sur-{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public List<Submission> lookupSubmissionBySurveyId(@PathParam("id") long id) {
		return sm.getSubmissionsBySurvey(am.getSurvey(id));
	}
}
