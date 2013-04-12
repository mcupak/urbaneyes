package edu.toronto.ece1778.urbaneyes.view;

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

/**
 * Bean responsible for state of the application.
 * 
 * @author mcupak
 * 
 */
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
	private Long surveyForEditId = null;
	private Long submissionForEditId = null;
	private boolean edit = false;
	private boolean editQuestions = false;
	private boolean mySurveysView = true;
	private boolean mySubmissionsView = true;
	private String problem = "";

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

	/**
	 * Loads the submissions to memory based on surveyForSubmission.
	 */
	public void loadSubmissions() {
		if (surveyForSubmissions == null) {
			submissions = new ArrayList<Submission>();
		} else {
			submissions = sum.getSubmissionsBySurvey(surveyForSubmissions);
		}
	}

	/**
	 * Looks up surveys available to a given user.
	 * 
	 * @param userId
	 *            user id
	 * @return list of surveys
	 */
	public List<Survey> getAvailableSurveys(String userId) {
		if (userId == null || userId.isEmpty()) {
			return sem.getPublicSurveys();
		} else {
			if (user == null) {
				user = um.getUser(Long.valueOf(userId));
			}
			return sem.getAvailableSurveys(user);
		}
	}

	/**
	 * Looks up surveys by their owner.
	 * 
	 * @param userId
	 *            owner id
	 * @return list of survey of a given owner
	 */
	public List<Survey> getSurveysByOwner(String userId) {
		if (user == null) {
			user = um.getUser(Long.valueOf(userId));
		}
		return sem.getSurveysByOwner(user);
	}

	/**
	 * Load submissions to memory based on a given user.
	 * 
	 * @param userId
	 *            user id
	 */
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

	public boolean isEdit() {
		return edit;
	}

	public boolean isEditQuestions() {
		return editQuestions;
	}

	public void setEditQuestions(boolean editQuestions) {
		this.editQuestions = editQuestions;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Long getSurveyForEditId() {
		return surveyForEditId;
	}

	public void setSurveyForEditId(Long surveyForEditId) {
		this.surveyForEditId = surveyForEditId;
	}

	public Long getSubmissionForEditId() {
		return submissionForEditId;
	}

	public void setSubmissionForEditId(Long submissionForEditId) {
		this.submissionForEditId = submissionForEditId;
	}

	public boolean isMySurveysView() {
		return mySurveysView;
	}

	public boolean isMySubmissionsView() {
		return mySubmissionsView;
	}

	public void setMySubmissionsView(boolean mySubmissionsView) {
		this.mySubmissionsView = mySubmissionsView;
	}

	public void setMySurveysView(boolean mySurveysView) {
		this.mySurveysView = mySurveysView;
	}

	public String getProblem() {
		return problem;
	}

	/**
	 * Empties error messages once they're shown.
	 * 
	 * @return
	 */
	public String showProblem() {
		String p = problem;
		problem = "";
		return p;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	/**
	 * Takes care of a selection of a survey to display using a dynamically
	 * generated view based on editing capabilities.
	 * 
	 * @param surveyForEditId
	 *            survey id
	 * @param edit
	 *            true if you want to edit the survey, false if you only want to
	 *            view it
	 * @param editQuestions
	 *            true if you want to change the questions of a survey, false if
	 *            you want to only display them
	 * @return redirect to the survey view
	 */
	public String selectSurvey(Long surveyForEditId, boolean edit,
			boolean editQuestions) {
		this.edit = edit;
		this.editQuestions = editQuestions;
		this.surveyForEditId = surveyForEditId;
		return "/survey.xhtml?faces-redirect=true";
	}

	/**
	 * Takes of a selection of a submission to view.
	 * 
	 * @param id
	 *            submission ID
	 * @return redirect to the submission view
	 */
	public String selectSubmission(Long id) {
		this.submissionForEditId = id;
		return "/submission.xhtml?faces-redirect=true";
	}

}