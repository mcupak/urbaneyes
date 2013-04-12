package edu.toronto.ece1778.urbaneyes.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Initializer of the database with demo records.
 * 
 */
@Singleton
@Startup
public class DBInitializer {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Initializes the database at startup/deployment.
	 */
	@PostConstruct
	public void initialize() {
		User u = new User("Sean Smith", "foo@example.com", "hello", null, null);
		manager.persist(u);
		User u2 = new User("Miroslav Cupak", "mirocupak@gmail.com", "hello",
				null, null);
		manager.persist(u2);
		User u3 = new User("Owen Wilson", "ow@gmail.com", "hello", null, null);
		manager.persist(u3);

		Question q = new Question("What is this place?", AnswerType.TEXT,
				new HashSet<Option>());
		manager.persist(q);
		Question q2 = new Question("Is this place noisy?", AnswerType.TEXT,
				new HashSet<Option>());
		manager.persist(q2);

		Set<Question> questions = new HashSet<Question>();
		questions.add(q);
		Set<User> users = new HashSet<User>();
		users.add(u);
		users.add(u2);
		Survey s = new Survey("Test Survey", false, null, null,
				"This is a sample survey.", null);
		s.setOwner(u);
		s.setContributors(users);
		manager.persist(s);
		Survey s2 = new Survey("Recycling survey", true, null, null,
				"Survey about recycling possibilities.", null);
		s2.setOwner(u2);
		Survey s3 = new Survey("Noise survey", false, null, null,
				"Measurement of street noise.", null);
		s3.setOwner(u2);
		users = new HashSet<User>();
		users.add(u2);
		s2.setContributors(users);
		s3.setContributors(users);
		manager.persist(s2);
		manager.persist(s3);

		s.setQuestions(questions);
		manager.merge(s);
		questions = new HashSet<Question>();
		questions.add(q2);
		s2.setQuestions(questions);
		manager.merge(s2);

		Point p = new Point("U of T campus", Double.valueOf(40.0),
				Double.valueOf(38.0), Float.valueOf(0),
				"64 St George St, Toronto, ON, CA");
		manager.persist(p);

		Answer a = new Answer("I don't know.", q);
		manager.persist(a);
		Answer a2 = new Answer("U of T", q);
		manager.persist(a2);
		Answer a3 = new Answer("Toronto", q);
		manager.persist(a3);

		List<Answer> answers = new ArrayList<Answer>();
		answers.add(a);
		Submission su = new Submission(u, new Date(), p, answers, s);
		manager.persist(su);
		answers = new ArrayList<Answer>();
		answers.add(a2);
		Submission su2 = new Submission(u2, new Date(), p, answers, s);
		manager.persist(su2);
		answers = new ArrayList<Answer>();
		answers.add(a3);
		Submission su3 = new Submission(u3, new Date(), p, answers, s);
		manager.persist(su3);
	}
}
