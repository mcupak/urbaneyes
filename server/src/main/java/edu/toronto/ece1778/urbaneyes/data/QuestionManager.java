package edu.toronto.ece1778.urbaneyes.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import edu.toronto.ece1778.urbaneyes.model.Question;

/**
 * Managing bean for questions. Performs operations on questions in a database.
 * 
 * @author mcupak
 * 
 */
@Stateless
@Named
public class QuestionManager {

	@Inject
	private EntityManager em;

	/**
	 * Finds a question by its ID.
	 * 
	 * @param id
	 *            ID of a question
	 * @return question with the given ID
	 */
	public Question getQuestion(Long id) {
		return em.find(Question.class, id);
	}

	/**
	 * Retrieves all the questions in a database.
	 * 
	 * @return list of all the questions
	 */
	@Produces
	@Model
	public List<Question> getQuestions() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(Question.class));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * Saves a new question to a database.
	 * 
	 * @param question
	 *            question to save
	 */
	public void addQuestion(Question question) {
		if (question != null) {
			em.persist(question);
		}
	}

	/**
	 * Removes an existing question from a database.
	 * 
	 * @param id
	 *            ID of a question to remove
	 * @return true if the remove was performed, false otherwise
	 */
	public boolean deleteQuestion(Long id) {
		Question question = getQuestion(id);
		if (question == null) {
			return false;
		} else {
			em.remove(question);
			return true;
		}
	}

	/**
	 * Saves changes to an existing persisted question.
	 * 
	 * @param question
	 *            question to save
	 */
	public void editQuestion(Question question) {
		if (getQuestion(question.getId()) != null) {
			em.merge(question);
		}
	}
}
