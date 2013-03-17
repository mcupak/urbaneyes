package edu.toronto.ece1778.urbaneyes.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.toronto.ece1778.urbaneyes.model.Answer;
import edu.toronto.ece1778.urbaneyes.model.AnswerType;
import edu.toronto.ece1778.urbaneyes.model.Option;
import edu.toronto.ece1778.urbaneyes.model.Point;
import edu.toronto.ece1778.urbaneyes.model.Question;
import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Fills database with demo records.
 * 
 */
@Singleton
@Startup
public class DBInitializer {

	@PersistenceContext
	private EntityManager manager;

	@PostConstruct
	public void initialize() {
		User u = new User("John Smith", "foo@example.com", "hello", null, null);
		manager.persist(u);

		Question q = new Question("What is this place?", AnswerType.TEXT,
				new ArrayList<Option>(), null);
		manager.persist(q);

		List<Question> questions = new ArrayList<Question>();
		questions.add(q);
		List<User> users = new ArrayList<User>();
		users.add(u);
		Survey s = new Survey("Test Survey", false, null, null,
				"This is a sample survey.", null);
		s.setOwner(u);
		s.setContributors(users);
		manager.persist(s);

		s.setQuestions(questions);
		manager.merge(s);

		Point p = new Point("Sample point", Double.valueOf(40.0),
				Double.valueOf(38.0), Float.valueOf(0));
		manager.persist(p);

		Answer a = new Answer("I don't know.", q);
		manager.persist(a);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a);
		Submission su = new Submission(u, new Date(), p, answers, s);
		manager.persist(su);
	}
}
