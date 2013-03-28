package edu.toronto.ece1778.urbaneyes.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.toronto.ece1778.urbaneyes.data.UserManager;
import edu.toronto.ece1778.urbaneyes.model.User;

/**
 * Servlet for authentication of a smartphone app.
 * 
 * @author mcupak
 * 
 */
@WebServlet(value = "/auth", name = "authservlet")
public class AuthenticationServlet extends HttpServlet {

	public static final String USER_PARAM = "user";
	public static final String PASSWORD_PARAM = "pass";
	private static final long serialVersionUID = 1L;

	@Inject
	private UserManager um;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String email = request.getParameter(USER_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);

		try {
			if (email == null || password == null) {
				out.write("ACCESS_DENIED");
			} else {
				User u = um.getUser(email, password);
				if (u == null) {
					out.write("ACCESS_DENIED");
				} else {
					out.write(u.getId().toString());
				}
			}
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
		return "Authentication servlet";
	}
}