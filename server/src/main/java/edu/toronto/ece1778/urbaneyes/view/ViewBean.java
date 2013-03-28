package edu.toronto.ece1778.urbaneyes.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.toronto.ece1778.urbaneyes.data.QuestionManager;
import edu.toronto.ece1778.urbaneyes.data.SubmissionManager;
import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.AnswerType;
import edu.toronto.ece1778.urbaneyes.model.Option;
import edu.toronto.ece1778.urbaneyes.model.Question;
import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

@Named
@ViewScoped
public class ViewBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;

	@Inject
	private SurveyManager sem;
	@Inject
	private UserManager um;
	@Inject
	private QuestionManager qm;
	@Inject
	private SubmissionManager sm;
	@Inject
	private StateBean state;
	private List<AnswerType> answerTypes;

	private SurveyProxy survey = null;
	private Submission submission = null;
	private User newUser = null;
	private QuestionProxy newQuestion = null;

	public ViewBean() {
		super();
		newQuestion = new QuestionProxy();
		answerTypes = new ArrayList<AnswerType>();
		for (AnswerType a : AnswerType.values()) {
			answerTypes.add(a);
		}
	}

	public void loadSurvey(Long surveyId, String userId) {
		if (surveyId < 0) {
			newSurvey(userId);
		} else {
			loadSurvey(surveyId);
		}
	}

	public void loadSubmission(Long id) {
		submission = sm.getSubmission(id);
	}

	private void newSurvey(String userId) {
		survey = new SurveyProxy();
		User owner = um.getUser(Long.valueOf(userId));
		survey.setOwner(owner);
		List<User> contrib = new ArrayList<User>();
		contrib.add(owner);
		survey.setContributors(contrib);
		survey.setPriv(false);
	}

	private void loadSurvey(Long id) {
		survey = new SurveyProxy(sem.getSurvey(id));
	}

	public String saveSurvey() {
		// owner should always be able to contribute to its survey
		if (survey.getContributors() == null) {
			survey.setContributors(new ArrayList<User>());
		}
		if (survey.getContributors().contains(survey.getOwner())) {
			survey.addContributor(survey.getOwner());
		}

		Survey s = null;
		if (survey.getId() < 0) {
			// new survey
			s = new Survey();
			s.setName(survey.getName());
			s.setDescription(survey.getDescription());
			s.setOwner(survey.getOwner());
			s.setPriv(survey.getPriv());
			Set<User> contrib = new HashSet<User>();
			contrib.addAll(survey.getContributors());
			s.setContributors(contrib);
			Set<Question> qs = new HashSet<Question>();
			for (QuestionProxy q : survey.getQuestions()) {
				Question n = new Question(q.getName(), q.getAnswerType(),
						new HashSet<Option>());
				qm.addQuestion(n);
				qs.add(n);
			}
			s.setQuestions(qs);
			sem.addSurvey(s);
		} else {
			// update survey, but don't allow change of questions
			s = sem.getSurvey(survey.getId());
			if (s != null) {
				s.setName(survey.getName());
				s.setDescription(survey.getDescription());
				s.setOwner(survey.getOwner());
				s.setPriv(survey.getPriv());
				Set<User> contrib = new HashSet<User>();
				contrib.addAll(survey.getContributors());
				s.setContributors(contrib);

				sem.editSurvey(s);
			}
		}
		return "/mysurveys.xhtml?faces-redirect=true";
	}

	public String removeSurvey(Long id) {
		Survey s = null;
		if (id != null) {
			s = sem.getSurvey(id);
		}
		if (s != null) {
			if (sm.getSubmissionsBySurvey(s).isEmpty()) {
				for (Question q : s.getQuestions()) {
					qm.deleteQuestion(q.getId());
				}
				sem.deleteSurvey(id);
			} else {
				state.setProblem("Cannot delete a survey with submissions!");
			}
		}
		return "/mysurveys.xhtml?faces-redirect=true";
	}

	public void addContributor() {
		survey.addContributor(newUser);
		newUser = null;
	}

	public void removeContributor(Long id) {
		if (id != null) {
			survey.removeContributor(um.getUser(id));
		}
	}

	public void addQuestion() {
		if (!newQuestion.getName().isEmpty()) {
			survey.addQuestion(newQuestion);
			newQuestion = new QuestionProxy();
		}
	}

	public void removeQuestion(Long id) {
		QuestionProxy q = null;
		if (id != null) {
			for (QuestionProxy a : survey.getQuestions()) {
				if (a.getId().equals(id)) {
					q = a;
				}
			}
		}
		if (q != null) {
			survey.removeQuestion(q);
		}
	}

	public SurveyProxy getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyProxy survey) {
		this.survey = survey;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public QuestionProxy getNewQuestion() {
		return newQuestion;
	}

	public void setNewQuestion(QuestionProxy newQuestion) {
		this.newQuestion = newQuestion;
	}

	public List<AnswerType> getAnswerTypes() {
		return answerTypes;
	}

	public void setAnswerTypes(List<AnswerType> answerTypes) {
		this.answerTypes = answerTypes;
	}

}