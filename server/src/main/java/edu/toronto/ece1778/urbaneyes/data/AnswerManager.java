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

import edu.toronto.ece1778.urbaneyes.model.Answer;

@Stateless
@Named
public class AnswerManager {

	@Inject
	private EntityManager em;
	@Inject
	private Logger log;

	public Answer getAnswer(Long id) {
		return em.find(Answer.class, id);
	}

	@Produces
	@Model
	public List<Answer> getAnswers() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Answer.class));
		return em.createQuery(cq).getResultList();
	}

	public void addAnswer(Answer answer) {
		if (answer != null) {
			em.persist(answer);
		}
	}

	public boolean deleteAnswer(Long id) {
		Answer answer = getAnswer(id);
		if (answer == null) {
			return false;
		} else {
			em.remove(answer);
			return true;
		}
	}

	public void editAnswer(Answer answer) {
		if (getAnswer(answer.getId()) != null) {
			em.merge(answer);
		}
	}
}
