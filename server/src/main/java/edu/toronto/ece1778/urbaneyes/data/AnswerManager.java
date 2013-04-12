package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import edu.toronto.ece1778.urbaneyes.model.Answer;

/**
 * Managing bean for answers. Performs operations on answers in a database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class AnswerManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds an answer by its ID.
	 * 
	 * @param id
	 *            ID of an answer to find
	 * @return answer with the given ID
	 */
	public Answer getAnswer(Long id) {
		return em.find(Answer.class, id);
	}

	/**
	 * Retrieves all the answers in a database.
	 * 
	 * @return list of all the answers.
	 */
	@Produces
	@Model
	public List<Answer> getAnswers() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Answer.class));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Saves a new answer to the database.
	 * 
	 * @param answer
	 *            answer to save
	 */
	public void addAnswer(Answer answer) {
		if (answer != null) {
			em.persist(answer);
		}
	}

	/**
	 * Removes an answer from the database.
	 * 
	 * @param id
	 *            ID of the answer to remove
	 * @return true if the remove was performed, false otherwise
	 */
	public boolean deleteAnswer(Long id) {
		Answer answer = getAnswer(id);
		if (answer == null) {
			return false;
		} else {
			em.remove(answer);
			return true;
		}
	}

	/**
	 * Saves changes to an existing persisted answer.
	 * 
	 * @param answer answer to save
	 */
	public void editAnswer(Answer answer) {
		if (getAnswer(answer.getId()) != null) {
			em.merge(answer);
		}
	}
}
