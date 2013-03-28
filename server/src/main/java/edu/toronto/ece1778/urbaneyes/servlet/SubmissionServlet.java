package edu.toronto.ece1778.urbaneyes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.toronto.ece1778.urbaneyes.data.AnswerManager;
import edu.toronto.ece1778.urbaneyes.data.PointManager;
import edu.toronto.ece1778.urbaneyes.data.SubmissionManager;
import edu.toronto.ece1778.urbaneyes.data.SurveyManager;
import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.Answer;
import edu.toronto.ece1778.urbaneyes.model.Point;
import edu.toronto.ece1778.urbaneyes.model.Question;
import edu.toronto.ece1778.urbaneyes.model.Submission;
import edu.toronto.ece1778.urbaneyes.model.Survey;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Servlet for uploading of submissions from the app.
 * 
 * @author mcupak
 * 
 */
@WebServlet(value = "/upload", name = "submissionservlet")
public class SubmissionServlet extends HttpServlet {

	public static final String USER_PARAM = "user";
	public static final String SURVEY_PARAM = "sur";
	public static final String SUBMISSION_PARAM = "sum";
	public static final String ALTITUDE_PARAM = "alt";
	public static final String LATITUDE_PARAM = "lat";
	public static final String LONGITUDE_PARAM = "lon";
	public static final String ADDRESS_PARAM = "add";
	public static final String QUESTION_PARAM = "q";
	public static final String SUCCESS = "OK";
	public static final String FAILURE = "ERR";
	private static final long serialVersionUID = 2L;

	@Inject
	private UserManager um;
	@Inject
	private SurveyManager xm;
	@Inject
	private PointManager pm;
	@Inject
	private AnswerManager am;
	@Inject
	private SubmissionManager sm;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		Long userId = null;
		Long surveyId = null;
		Long submissionId = null;
		Float alt = null;
		Double lat = null;
		Double lon = null;
		String address = null;
		try {
			// parse data
			try {
				userId = Long.valueOf(request.getParameter(USER_PARAM));
			} catch (Exception ex) {
			}
			try {
				submissionId = Long.valueOf(request
						.getParameter(SUBMISSION_PARAM));
			} catch (Exception ex) {
			}
			try {
				surveyId = Long.valueOf(request.getParameter(SURVEY_PARAM));
			} catch (Exception ex) {
			}
			try {
				alt = Float.valueOf(request.getParameter(ALTITUDE_PARAM));
			} catch (Exception ex) {
			}
			try {
				lat = Double.valueOf(request.getParameter(LATITUDE_PARAM));
			} catch (Exception ex) {
			}
			try {
				lon = Double.valueOf(request.getParameter(LONGITUDE_PARAM));
			} catch (Exception ex) {
			}
			address = request.getParameter(ADDRESS_PARAM);

			// create objects
			if (userId != null && surveyId != null && alt != null
					&& lat != null && lon != null && address != null) {
				// fetch user and survey
				User u = um.getUser(userId);
				Survey x = xm.getSurvey(surveyId);
				if (u != null && x != null) {
					// create submission
					Submission s = null;
					if (submissionId == null) {
						s = new Submission();
					} else {
						s = sm.getSubmission(submissionId);
					}
					if (s == null) {
						out.write(FAILURE);
					} else {
						// create a point
						Point p = new Point("-", lat, lon, alt, address);
						pm.addPoint(p);

						// create answers
						List<Answer> answers = new ArrayList<Answer>();
						for (Question q : x.getQuestions()) {
							Answer a = new Answer();
							a.setQuestion(q);
							String name = request.getParameter(QUESTION_PARAM
									+ q.getId().toString());
							a.setName((name == null) ? "0" : name);
							answers.add(a);
							am.addAnswer(a);
						}

						s.setDate(new Date());
						s.setPoint(p);
						s.setSurvey(x);
						s.setUser(u);
						s.setAnswers(answers);

						if (submissionId == null) {
							sm.addSubmission(s);
						} else {
							sm.editSubmission(s);
						}

						out.write(SUCCESS);
					}
				} else {
					out.write(FAILURE);
				}
			} else {
				out.write(FAILURE);
			}
		} catch (Exception ex) {
			out.write(FAILURE);
		} finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Submission upload servlet";
	}
}