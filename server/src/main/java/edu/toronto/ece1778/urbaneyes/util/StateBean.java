package edu.toronto.ece1778.urbaneyes.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.toronto.ece1778.urbaneyes.data.SubmissionManager;
import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;

@Named
@SessionScoped
public class StateBean implements Serializable {
	private static final long serialVersionUID = -5156711102367948040L;

	@Inject
	private SurveyManager sem;
	@Inject
	private SubmissionManager sum;
	private Survey surveyForSubmissions = null;
	private List<Submission> submissions = null;

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
}