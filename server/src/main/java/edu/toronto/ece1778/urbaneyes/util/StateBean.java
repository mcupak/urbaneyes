package edu.toronto.ece1778.urbaneyes.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.toronto.ece1778.urbaneyes.data.SubmissionManager;
import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

@Named
@SessionScoped
public class StateBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;

	@Inject
	private SurveyManager sem;
	@Inject
	private UserManager um;
	@Inject
	private SubmissionManager sum;
	private Survey surveyForSubmissions = null;
	private Survey userSurveyForSubmissions = null;
	private List<Submission> submissions = null;
	private List<Submission> userSubmissions = null;
	private User user = null;

	public Survey getSurveyForSubmissions() {
		return surveyForSubmissions;
	}

	public void setSurveyForSubmissions(Survey surveyForSubmissions) {
		this.surveyForSubmissions = surveyForSubmissions;
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}

	public void loadSubmissions() {
		if (surveyForSubmissions == null) {
			submissions = new ArrayList<Submission>();
		} else {
			submissions = sum.getSubmissionsBySurvey(surveyForSubmissions);
		}
	}

	public List<Survey> getAvailableSurveys(String userId) {
		if (user == null) {
			user = um.getUser(Long.valueOf(userId));
		}
		return sem.getAvailableSurveys(user);
	}

	public void loadUserSubmissions(String userId) {
		if (userSurveyForSubmissions == null) {
			userSubmissions = new ArrayList<Submission>();
		} else {
			if (user == null) {
				user = um.getUser(Long.valueOf(userId));
			}
			userSubmissions = sum.getSubmissionsBySurveyUser(
					userSurveyForSubmissions, user);
		}
	}

	public Survey getUserSurveyForSubmissions() {
		return userSurveyForSubmissions;
	}

	public void setUserSurveyForSubmissions(Survey userSurveyForSubmissions) {
		this.userSurveyForSubmissions = userSurveyForSubmissions;
	}

	public List<Submission> getUserSubmissions() {
		return userSubmissions;
	}

	public void setUserSubmissions(List<Submission> userSubmissions) {
		this.userSubmissions = userSubmissions;
	}

}