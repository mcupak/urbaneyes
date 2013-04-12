package edu.toronto.ece1778.urbaneyes.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.Survey;

/**
 * RESTful service to expose surveys via XML.
 */
@Path("/surveys")
@RequestScoped
public class SurveyResourceRESTService {
	@Inject
	private SurveyManager sm;
	@Inject
	private UserManager um;

	/**
	 * Exports all the surveys to XML.
	 * 
	 * @return list of surveys
	 */
	@GET
	@Produces("text/xml")
	public List<SurveyItem> listAllSurveys() {
		final List<Survey> results = sm.getSurveys();
		List<SurveyItem> items = new ArrayList<SurveyItem>();
		for (Survey s : results) {
			items.add(new SurveyItem(s));
		}
		return items;
	}

	/**
	 * Looks up a survey by ID and exposes it via XML.
	 * 
	 * @param id
	 *            survey id
	 * @return survey
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public Survey lookupSurveyById(@PathParam("id") long id) {
		return sm.getSurvey(id);
	}

	/**
	 * Looks up a survey by its owner's ID and exposes it via XML.
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/user-{id:[0-9][0-9]*}")
	@Produces("text/xml")
	public List<SurveyItem> lookupSurveyByUserId(@PathParam("id") long id) {
		final List<Survey> results = sm.getAvailableSurveys(um.getUser(id));
		List<SurveyItem> items = new ArrayList<SurveyItem>();
		for (Survey s : results) {
			items.add(new SurveyItem(s));
		}
		return items;
	}
}
