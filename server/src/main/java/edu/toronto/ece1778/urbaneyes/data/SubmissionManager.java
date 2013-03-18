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

import org.jboss.solder.logging.Logger;

import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;

@Stateless
@Named
public class SubmissionManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Submission getSubmission(Long id) {
		return em.find(Submission.class, id);
	}

	@Produces
	@Model
	public List<Submission> getSubmissions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Submission.class));
		return em.createQuery(cq).getResultList();
	}

	public List<Submission> getSubmissionsBySurvey(Survey s) {
		if (s == null) {
            return new ArrayList<Submission>();
        }
        TypedQuery<Submission> query = em.createQuery(
                "SELECT m FROM Submission m WHERE m.survey = ?", Submission.class);
        query.setParameter(1, s);

        return query.getResultList();
	}

	public void addSubmission(Submission submission) {
		if (submission != null) {
			em.persist(submission);
		}
	}

	public boolean deleteSubmission(Long id) {
		Submission submission = getSubmission(id);
		if (submission == null) {
			return false;
		} else {
			em.remove(submission);
			return true;
		}
	}

	public void editSubmission(Submission submission) {
		if (getSubmission(submission.getId()) != null) {
			em.merge(submission);
		}
	}
}
