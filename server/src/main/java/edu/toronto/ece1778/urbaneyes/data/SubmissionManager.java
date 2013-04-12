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
import javax.persistence.criteria.CriteriaQuery;

import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Managing bean for submissions. Performs operations on submissions in a
 * database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class SubmissionManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds submission by its ID.
	 * 
	 * @param id
	 *            submission ID
	 * @return submission with the given ID
	 */
	public Submission getSubmission(Long id) {
		return em.find(Submission.class, id);
	}

	/**
	 * Retrieves all the submissions in the database.
	 * 
	 * @return list of all the submissions
	 */
	@Produces
	@Model
	public List<Submission> getSubmissions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Submission.class));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Finds submissions for a given survey.
	 * 
	 * @param s
	 *            survey
	 * @return list of the given survey's submissions
	 */
	public List<Submission> getSubmissionsBySurvey(Survey s) {
		if (s == null) {
			return new ArrayList<Submission>();
		}
		TypedQuery<Submission> query = em.createQuery(
				"SELECT m FROM Submission m WHERE m.survey = ?",
				Submission.class);
		query.setParameter(1, s);

		return query.getResultList();
	}

	/**
	 * Finds submissions submitted by a given user for a given survey.
	 * 
	 * @param s
	 *            survey
	 * @param u
	 *            user
	 * @return list of submissions per user/survey
	 */
	public List<Submission> getSubmissionsBySurveyUser(Survey s, User u) {
		if (s == null) {
			return new ArrayList<Submission>();
		}
		TypedQuery<Submission> query = em
				.createQuery(
						"SELECT m FROM Submission m WHERE m.survey = :survey AND m.user = :user",
						Submission.class);
		query.setParameter("survey", s);
		query.setParameter("user", u);

		return query.getResultList();
	}

	/**
	 * Saves a new submission to a database.
	 * 
	 * @param submission
	 *            submission to save
	 */
	public void addSubmission(Submission submission) {
		if (submission != null) {
			em.persist(submission);
		}
	}

	/**
	 * Removes a submission from a database.
	 * 
	 * @param id
	 *            ID of a submission to remove
	 * @return true if remove was performed, false otherwise
	 */
	public boolean deleteSubmission(Long id) {
		Submission submission = getSubmission(id);
		if (submission == null) {
			return false;
		} else {
			em.remove(submission);
			return true;
		}
	}

	/**
	 * Saves the changes to an existing persisted submission.
	 * 
	 * @param submission
	 *            submission to save
	 */
	public void editSubmission(Submission submission) {
		if (getSubmission(submission.getId()) != null) {
			em.merge(submission);
		}
	}
}
