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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.solder.logging.Logger;

import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

@Stateless
@Named
public class SurveyManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Survey getSurvey(Long id) {
		return em.find(Survey.class, id);
	}

	@Produces
	@Model
	public List<Survey> getSurveys() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Survey.class));
		return em.createQuery(cq).getResultList();
	}

	public List<Survey> getSurveysByOwner(User u) {
		if (u == null) {
			return new ArrayList<Survey>();
		}
		TypedQuery<Survey> query = em.createQuery(
				"SELECT m FROM Survey m WHERE m.owner = :owner", Survey.class);
		query.setParameter("owner", u);

		return query.getResultList();
	}

	public List<Survey> getAvailableSurveys(User u) {
		if (u == null) {
			return new ArrayList<Survey>();
		}
		TypedQuery<Survey> query = em
				.createQuery(
						"SELECT m FROM Survey m WHERE m.priv = false OR :user MEMBER OF m.contributors",
						Survey.class);
		query.setParameter("user", u);

		return query.getResultList();
	}

	public void addSurvey(Survey survey) {
		if (survey != null) {
			em.persist(survey);
		}
	}

	public boolean deleteSurvey(Long id) {
		Survey survey = getSurvey(id);
		if (survey == null) {
			return false;
		} else {
			em.remove(survey);
			return true;
		}
	}

	public void editSurvey(Survey survey) {
		if (getSurvey(survey.getId()) != null) {
			em.merge(survey);
		}
	}
}
