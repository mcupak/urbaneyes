package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.solder.logging.Logger;

import edu.toronto.ece1778.urbaneyes.model.Question;

@Stateless
@Named
public class QuestionManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Question getQuestion(Long id) {
		return em.find(Question.class, id);
	}

	@Produces
	@Model
	public List<Question> getQuestions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Question.class));
		return em.createQuery(cq).getResultList();
	}

	public void addQuestion(Question question) {
		if (question != null) {
			em.persist(question);
		}
	}

	public boolean deleteQuestion(Long id) {
		Question question = getQuestion(id);
		if (question == null) {
			return false;
		} else {
			em.remove(question);
			return true;
		}
	}

	public void editQuestion(Question question) {
		if (getQuestion(question.getId()) != null) {
			em.merge(question);
		}
	}
}
