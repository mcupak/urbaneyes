package edu.toronto.ece1778.urbaneyes.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Managing bean for surveys. Performs operations on surveys in a database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class SurveyManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds a survey by its ID.
	 * 
	 * @param id
	 *            survey id
	 * @return survey with the given ID
	 */
	public Survey getSurvey(Long id) {
		return em.find(Survey.class, id);
	}

	/**
	 * Retrieves all the surveys from the database.
	 * 
	 * @return list of all the surveys
	 */
	@Produces
	@Model
	public List<Survey> getSurveys() {
		TypedQuery<Survey> query = em.createQuery(
				"SELECT m FROM Survey m ORDER BY m.name", Survey.class);

		return query.getResultList();
	}

	/**
	 * Finds all the surveys owned by a given user.
	 * 
	 * @param u
	 *            owner
	 * @return list of surveys with the given owner
	 */
	public List<Survey> getSurveysByOwner(User u) {
		if (u == null) {
			return new ArrayList<Survey>();
		}
		TypedQuery<Survey> query = em
				.createQuery(
						"SELECT m FROM Survey m WHERE m.owner = :owner ORDER BY m.name",
						Survey.class);
		query.setParameter("owner", u);

		return query.getResultList();
	}

	/**
	 * Finds all the surveys a user can contribute to, i.e. public surveys and
	 * private surveys to which he is assigned as a contributor.
	 * 
	 * @param u
	 *            user
	 * @return list of surveys available to a given user
	 */
	public List<Survey> getAvailableSurveys(User u) {
		if (u == null) {
			return new ArrayList<Survey>();
		}
		TypedQuery<Survey> query = em
				.createQuery(
						"SELECT m FROM Survey m WHERE m.priv = false OR :user MEMBER OF m.contributors ORDER BY m.name",
						Survey.class);
		query.setParameter("user", u);

		return query.getResultList();
	}

	/**
	 * Finds all the surveys with privacy status set to public, i.e. surveys
	 * open to all the users including anonymous users.
	 * 
	 * @return list of public surveys
	 */
	public List<Survey> getPublicSurveys() {
		TypedQuery<Survey> query = em.createQuery(
				"SELECT m FROM Survey m WHERE m.priv = false ORDER BY m.name",
				Survey.class);

		return query.getResultList();
	}

	/**
	 * Saves a new survey to a database.
	 * 
	 * @param survey
	 *            survey to save
	 */
	public void addSurvey(Survey survey) {
		if (survey != null) {
			em.persist(survey);
		}
	}

	/**
	 * Removes a survey from the database.
	 * 
	 * @param id
	 *            survey ID
	 * @return true if remove was performed, false otherwise
	 */
	public boolean deleteSurvey(Long id) {
		Survey survey = getSurvey(id);
		if (survey == null) {
			return false;
		} else {
			em.remove(survey);
			return true;
		}
	}

	/**
	 * Saves the changes to an existing persisted survey.
	 * 
	 * @param survey
	 *            survey to save
	 */
	public void editSurvey(Survey survey) {
		if (getSurvey(survey.getId()) != null) {
			em.merge(survey);
		}
	}
}
